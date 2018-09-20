package Network;
import java.util.ArrayList;

import Exceptions.InvalidDataException;
import Facility.Facility;

public class NetworkFactory {

	private NetworkFactory() {	}
	
	public static Network createNetwork(ArrayList<Facility> facilities, Double drivingHoursPerDay,
			Double averageMilesPerHour) throws InvalidDataException
	{
		return new NetworkImpl(facilities, drivingHoursPerDay, averageMilesPerHour);
	}
}
