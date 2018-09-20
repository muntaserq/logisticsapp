package Facility;
import java.util.ArrayList;

import Exceptions.InvalidDataException;
import Inventory.Inventory;
import Schedule.Schedule;

/**
 * @author muntaserqutub
 *
 */
public interface Facility {
	public String getLocation();

	public Integer getProcessingRate();

	public Double getProcessingDailyRateCost();

	public ArrayList<FacilityLink> getLinkedFacilitiesAsList() throws InvalidDataException ;

	public void setInventoryOnce(ArrayList<Inventory> inventory) throws InvalidDataException;

	public void printFacility() throws InvalidDataException ;
	
	public void moveToNextDay();
	
	public Schedule getSchedule();
	
	public Integer bookDays(Integer numberOfItems);
	
	public Integer getFacilityLimit();
}
