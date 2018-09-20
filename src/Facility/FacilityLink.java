package Facility;
import java.util.Formatter;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class FacilityLink {

	private String location;
	private Double distanceInMiles;
	private Double drivingHoursPerDay;
	private Double averageMilesPerHour;

	public FacilityLink(String location, Double distanceInMiles, Double drivingHoursPerDay,
			Double averageMilesPerHour) throws InvalidDataException {
		
		setLocation(location);
		setDistanceInMiles(distanceInMiles);
		setDrivingHoursPerDay(drivingHoursPerDay);
		setAverageMilesPerHour(averageMilesPerHour);
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the distanceInMiles
	 */
	public Double getDistanceInMiles() {
		return distanceInMiles;
	}

	/**
	 * @return the drivingHoursPerDay
	 */
	public Double getDrivingHoursPerDay() {
		return drivingHoursPerDay;
	}

	/**
	 * @return the averageMilesPerHour
	 */
	public Double getAverageMilesPerHour() {
		return averageMilesPerHour;
	}

	public String toString() {
		Double travelTime = calculateTravelTime();
		Formatter travelTimeFormat = new Formatter();
		travelTimeFormat.format("%.2f", travelTime);

		String returnValue = location + " (" + travelTimeFormat.toString() + "d)";
		travelTimeFormat.close();

		return returnValue;
	}

	public Double calculateTravelTime() {
		Double travelTime = distanceInMiles / (drivingHoursPerDay * averageMilesPerHour);
		return travelTime;
	}
	

	private void setLocation(String location) throws InvalidDataException
	{
		if (location == null || location.isEmpty())
		{
			throw new InvalidDataException("The data entered for the locaton is invalid.");
		}
		
		this.location = location;
	}
	
	private void setDistanceInMiles(Double distanceInMiles) throws InvalidDataException
	{
		if (distanceInMiles == null || distanceInMiles < 0.0)
		{
			throw new InvalidDataException("The data entered for the distance in miles is invalid.");
		}
		
		this.distanceInMiles = distanceInMiles;
	}
	
	private void setDrivingHoursPerDay(Double drivingHoursPerDay) throws InvalidDataException
	{
		if (drivingHoursPerDay == null || drivingHoursPerDay < 0.0)
		{
			throw new InvalidDataException("The data entered for the driving hours per day is invalid.");
		}
		this.drivingHoursPerDay = drivingHoursPerDay;
	}
	
	private void setAverageMilesPerHour(Double averageMilesPerHour) throws InvalidDataException
	{
		if (averageMilesPerHour == null || averageMilesPerHour < 0.0)
		{
			throw new InvalidDataException("The data entered for the average miles per hour is invalid.");
		}
		
		this.averageMilesPerHour = averageMilesPerHour;
	}

}
