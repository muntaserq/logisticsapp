package Facility;
import java.util.HashMap;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class FacilityFactory {
	private FacilityFactory() {	}

	public static Facility createFacility(String location, Integer processingRate, Double processingDailyRateCost,
			HashMap<String, FacilityLink> facilityLink, Integer facilityLimit) throws InvalidDataException {
		return new FacilityImpl(location, processingRate, processingDailyRateCost, facilityLink, facilityLimit);
	}

	public static FacilityLoader createFacilityLoader(Double drivingHoursPerDay, Double averageMilesPerHour) {
		return new FacilitiyLoaderXmlImpl(drivingHoursPerDay, averageMilesPerHour);
	}
}
