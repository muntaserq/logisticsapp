package Network;
import java.util.ArrayList;

import Exceptions.InvalidDataException;
import Facility.FacilityLink;

/**
 * @author muntaserqutub
 *
 */
public interface Network {
	public ArrayList<FacilityLink> calculateShortestPath(String facilitiyStart, String facilityEnd, boolean print)
			throws InvalidDataException;
}
