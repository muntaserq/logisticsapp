package Facility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Exceptions.InvalidDataException;
import Inventory.Inventory;
import Schedule.Schedule;
import Schedule.ScheduleFactory;

/**
 * @author muntaserqutub
 *
 */
public class FacilityImpl implements Facility {

	private String location;
	private Integer processingRate;
	private Double processingDailyRateCost;
	private HashMap<String, FacilityLink> facilityLinks;
	private Schedule schedule;
	private HashMap<String, Inventory> inventory = null;
	private Integer facilityLimit;

	public FacilityImpl(String location, Integer processingRate, Double processingDailyRateCost,
			HashMap<String, FacilityLink> facilitiyLinks, Integer facilityLimit) throws InvalidDataException {
		setLocation(location);
		setProcessingRate(processingRate);
		setProcessingDailyRate(processingDailyRateCost);
		setFacilityLinks(facilitiyLinks);
		setFacilityLimit(facilityLimit);
		populateSchedule();
	}

	private void setFacilityLimit(Integer facilityLimit) throws InvalidDataException {
		if (facilityLimit == null || facilityLimit < 0) {
			throw new InvalidDataException("The data entered for facility limit is invalid.");
		}
		this.facilityLimit = facilityLimit;
	}

	public void setInventoryOnce(ArrayList<Inventory> inventory) throws InvalidDataException {
		if (inventory == null || inventory.isEmpty()) {
			throw new InvalidDataException("The data entered for inventory is invalid.");
		}
		if (this.inventory == null) {
			this.inventory = new HashMap<String, Inventory>();
			for (int i = 0; i < inventory.size(); i++) {
				this.inventory.put(inventory.get(i).getItemId(), inventory.get(i));
			}
		}
	}


	@Override
	public String getLocation() {
		return this.location;
	}

	@Override
	public Integer getProcessingRate() {
		return this.processingRate;
	}

	@Override
	public Double getProcessingDailyRateCost() {
		return this.processingDailyRateCost;
	}

	@Override
	public ArrayList<FacilityLink> getLinkedFacilitiesAsList() throws InvalidDataException {
		ArrayList<FacilityLink> linkedFacilitiesList = new ArrayList<FacilityLink>();

		for (Entry<String, FacilityLink> facilityLink : getFacilityLinks().entrySet()) {

			FacilityLink fe = new FacilityLink(facilityLink.getKey(), facilityLink.getValue().getDistanceInMiles(),
					facilityLink.getValue().getDrivingHoursPerDay(), facilityLink.getValue().getAverageMilesPerHour());
			linkedFacilitiesList.add(fe);
		}

		return linkedFacilitiesList;
	}
	
	@Override
	public void moveToNextDay()
	{
		this.schedule.nextDay();
	}

	@Override
	public void printFacility() throws InvalidDataException {
		String directLinks = "";

		for (FacilityLink fe : getLinkedFacilitiesAsList()) {
			directLinks = directLinks + fe.toString() + "; ";
		}

		directLinks = directLinks.trim().substring(0, directLinks.length() - 2);

		System.out.println(getLocation());
		System.out.println("Direct Links: " + directLinks);

		System.out.println("Active Inventory:");
		System.out.printf("\t%-12s%-15s", "Item ID", "Quantity");
		System.out.printf("\t%-12s%-15s", "Item ID", "Quantity");
		System.out.printf("\t%-12s%-15s", "Item ID", "Quantity");
		System.out.println();

		int counter = 0;
		String depleated = "";
		for (Entry<String, Inventory> inventory : this.inventory.entrySet()) {
			if (!inventory.getValue().isDepleated()) {

				System.out.printf("\t%-12s%-15s", inventory.getValue().getItemId(), inventory.getValue().getQuantity());

				counter++;
				if (counter % 3 == 0) {
					System.out.println();
				}
			} else {
				depleated = depleated + inventory.getValue().getItemId() + ", ";
			}
		}

		if (!depleated.isEmpty()) {
			depleated = depleated.trim().substring(0, depleated.length() - 2);
		} else {
			depleated = "None.";
		}

		System.out.println();
		System.out.println("Depleted (Used-up) Inventory: " + depleated);
		this.schedule.printSchedule();
		System.out.println();
	}
	

	private void populateSchedule() {
		this.schedule = ScheduleFactory.createSchedule(getProcessingRate());
	}

	/**
	 * @param facilityLinks
	 *            the facilityLinks to set
	 * @throws InvalidDataException
	 */
	private void setFacilityLinks(HashMap<String, FacilityLink> facilityLinks) throws InvalidDataException {
		if (facilityLinks == null || facilityLinks.isEmpty()) {
			throw new InvalidDataException("The data entered for the facility links is invalid.");
		}
		this.facilityLinks = new HashMap<String, FacilityLink>(facilityLinks);
	}

	private HashMap<String, FacilityLink> getFacilityLinks() {
		return this.facilityLinks;
	}

	/**
	 * @param location
	 *            the location to set
	 * @throws InvalidDataException
	 */
	private void setLocation(String location) throws InvalidDataException {
		if (location == null || location.isEmpty()) {
			throw new InvalidDataException("The data entered for location is invalid.");
		}
		this.location = location;
	}

	/**
	 * @param processingRate
	 *            the processingRate to set
	 * @throws InvalidDataException
	 */
	private void setProcessingRate(Integer processingRate) throws InvalidDataException {
		if (processingRate == null || processingRate < 0) {
			throw new InvalidDataException("The data entered for processing rate is invalid.");
		}
		this.processingRate = processingRate;
	}

	/**
	 * @param processingDailyRate
	 *            the processingDailyRate to set
	 * @throws InvalidDataException
	 */
	private void setProcessingDailyRate(Double processingDailyRateCost) throws InvalidDataException {
		if (processingDailyRateCost == null || processingDailyRateCost <= 0.0) {
			throw new InvalidDataException("The data entered for the processing daily rate cost is invalid.");
		}
		this.processingDailyRateCost = processingDailyRateCost;
	}

	@Override
	public Schedule getSchedule() {
		return schedule;
	}

	@Override
	public Integer bookDays(Integer numberOfItems) {
		return schedule.scheduleNumberOfItems(numberOfItems);
	}

	@Override
	public Integer getFacilityLimit() {
		return facilityLimit;
	}
}
