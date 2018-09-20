package Inventory;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class InventoryManager {

	private HashMap<String, ArrayList<Inventory>> allInventory;

	public InventoryManager(String fileName) throws InvalidDataException {
		InventoryLoader inventory = InventoryFactory.createInventoryLoader();
		this.allInventory = inventory.loadInventory(fileName);
	}

	public ArrayList<Inventory> getInventoryForFacility(String facility) {
		ArrayList<Inventory> facilityInventory = new ArrayList<Inventory>(this.allInventory.get(facility));
		return facilityInventory;
	}
	
	public Integer numberOfItemsAvailable(String location, String itemId)
	{
		Integer numberOfItemsAvailable = null;
		ArrayList<Inventory> inventoryReturned = this.allInventory.get(location);
		
		for (Inventory inv : inventoryReturned)
		{
			if (inv.getItemId().equals(itemId))
			{
				numberOfItemsAvailable = inv.getQuantity();
				break;
			}
		}
		
		return numberOfItemsAvailable;
	}
	
	public void reduceInventoryForFacilityItemBy(String location, String itemId, Integer reductionAmount) throws InvalidDataException
	{
		ArrayList<Inventory> facilityInventory = allInventory.get(location);
		
		for (int i = 0; i< facilityInventory.size(); i++)
		{
			if (facilityInventory.get(i).getItemId().equals(itemId))
			{
				Integer quantity = facilityInventory.get(i).getQuantity();
				facilityInventory.get(i).setQuantity(quantity - reductionAmount);
				
				if ((quantity - reductionAmount) == 0)
				{
					facilityInventory.get(i).setDepleated(true);
				}
				break;
			}
		}
	}
	
	public Boolean hasItemAvailable(String location, String itemId)
	{
		Boolean itemAvailable = false;
		
		ArrayList<Inventory> inventoryReturned = this.allInventory.get(location);
		
		if (inventoryReturned != null && !inventoryReturned.isEmpty())
		{
			for (Inventory inv : inventoryReturned)
			{
				if (inv.getItemId().equals(itemId))
				{
					itemAvailable = inv.getQuantity() > 0;
				}
			}
		}
		
		return itemAvailable;
	}

}
