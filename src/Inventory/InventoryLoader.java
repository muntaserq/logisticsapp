package Inventory;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public interface InventoryLoader {
	public HashMap<String, ArrayList<Inventory>> loadInventory(String source) throws InvalidDataException;
}
