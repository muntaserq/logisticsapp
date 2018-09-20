package Item;
import java.util.HashMap;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public interface ItemLoader {
	public HashMap<String, Item> loadItems(String source) throws InvalidDataException;
}
