package Facility;
import java.util.HashMap;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public interface FacilityLoader {
	public HashMap<String, Facility> loadFacilities(String source) throws InvalidDataException;
}
