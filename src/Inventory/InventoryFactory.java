package Inventory;
import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class InventoryFactory {

	private InventoryFactory() {	}
	
	public static Inventory createInventory(String itemId, Integer quantity, String location)
			throws InvalidDataException
	{
		return new InventoryImpl(itemId, quantity, location);
	}

	public static InventoryLoader createInventoryLoader()
	{
		return new InventoryLoaderXmlImpl();
	}
}
