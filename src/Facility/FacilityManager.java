package Facility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Exceptions.InvalidDataException;
import Inventory.Inventory;
import Inventory.InventoryManager;
import Network.Network;
import Network.NetworkFactory;

/**
 * @author muntaserqutub
 *
 */
public class FacilityManager {

	private HashMap<String, Facility> facilities;
	private Network networkManager;
	private InventoryManager inventoryManager;

	public FacilityManager(String fileName, Double drivingHoursPerDay, Double averageMilesPerHour)
			throws InvalidDataException {
		FacilityLoader facilityLoader = FacilityFactory.createFacilityLoader(drivingHoursPerDay, averageMilesPerHour);
		this.facilities = facilityLoader.loadFacilities(fileName);

		createNetworkManager(drivingHoursPerDay, averageMilesPerHour);
		createInventoryManager();
		populateInventoryForFacilities();

	}
	
	public Integer getNextAvailableDay(String facilityName, Integer quantity)
	{
		return facilities.get(facilityName).getSchedule().nextOpenDay(quantity);
	}
	
	public Double daysNeededToProcessItemsAtFacility(String facilityName, Integer quantity)
	{
		Double daysNeededToProcess = null;

		daysNeededToProcess = (double)quantity / facilities.get(facilityName).getProcessingRate();
		
		return daysNeededToProcess;
	}

	public Double calculateShortestPath(String sourceFacility, String targetFacility, boolean print) throws InvalidDataException {
		Double shortestPathInDays = null;
		ArrayList<FacilityLink> path = this.networkManager.calculateShortestPath(sourceFacility, targetFacility, print);

		shortestPathInDays = path.get(path.size() - 1).calculateTravelTime();
		return shortestPathInDays;
	}

	public void printFacilityStatus(String facilityLocation) throws InvalidDataException {
		this.facilities.get(facilityLocation).printFacility();
	}

	public ArrayList<String> getListOfFacilities() {
		ArrayList<String> listOfFacilities = new ArrayList<String>();
		for (Entry<String, Facility> facility : facilities.entrySet()) {
			listOfFacilities.add(facility.getKey());
		}
		return listOfFacilities;
	}
	
	public void moveToNextDay()
	{
		for (Entry<String, Facility> facility : facilities.entrySet()) {
			facility.getValue().moveToNextDay();
		}
	}
	
	public ArrayList<FacilityWithItemsDTO> getAllFacilityLocationsWithItem(String itemId)
	{
		ArrayList<FacilityWithItemsDTO> facilitiesWithItem = new ArrayList<FacilityWithItemsDTO>();
		
		for (Entry<String, Facility> facility : facilities.entrySet()) {
			boolean available = this.inventoryManager.hasItemAvailable(facility.getKey(), itemId);
			Integer numberAvailable = this.inventoryManager.numberOfItemsAvailable(facility.getKey(), itemId);
			
			Integer facilityLimit = facility.getValue().getFacilityLimit();

			if (available)
			{
				if (numberAvailable > facilityLimit)
				{
					numberAvailable = facilityLimit;
				}
				
				facilitiesWithItem.add(new FacilityWithItemsDTO(facility.getKey(), numberAvailable));
			}
		}
		
		return facilitiesWithItem;
	}

	private void createInventoryManager() throws InvalidDataException {
		this.inventoryManager = new InventoryManager("facilityInventory.xml");
	}

	private void populateInventoryForFacilities() throws InvalidDataException {
		for (Entry<String, Facility> facility : facilities.entrySet()) {
			ArrayList<Inventory> inventory = inventoryManager.getInventoryForFacility(facility.getKey());
			facility.getValue().setInventoryOnce(inventory);
		}
	}
	
	public Integer bookDays(String location, Integer numberOfItems)
	{
		return this.facilities.get(location).bookDays(numberOfItems);
	}
	
	public Double getProcessingRatePerDayForFacility(String location)
	{
		return this.facilities.get(location).getProcessingDailyRateCost();
	}
	
	public void reduceInventoryForItemByAmount(String location, String itemId, Integer reductionAmount) throws InvalidDataException
	{
		inventoryManager.reduceInventoryForFacilityItemBy(location, itemId, reductionAmount);
	}

	private void createNetworkManager(Double drivingHoursPerDay, Double averageMilesPerHour)
			throws InvalidDataException {
		ArrayList<Facility> list = new ArrayList<Facility>(facilities.values());
		this.networkManager = NetworkFactory.createNetwork(list, drivingHoursPerDay, averageMilesPerHour);
	}
}
