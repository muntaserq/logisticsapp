package Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class ItemManager {

	private HashMap<String, Item> items;

	public ItemManager(String source) throws InvalidDataException {
		ItemLoader itemLoader = ItemFactory.createLoader();
		this.items = itemLoader.loadItems(source);
	}
	
	public ArrayList<String> getListOfAllItems()
	{
		ArrayList<String> listOfItems = new ArrayList<String>();
		for (Entry<String, Item> item : items.entrySet()) {
			listOfItems.add(item.getKey());
		}
		return listOfItems;
	}
	
	public Double getCost(String itemId) throws InvalidDataException
	{
		Double cost = 0.0;
		if (itemId == null || itemId.isEmpty())
		{
			throw new InvalidDataException("The data entered for the item id is invalid.");
		}
		else
		{
			Item itemReturned = this.items.get(itemId);
			if (itemReturned != null)
			{
				cost = itemReturned.getPrice();
			}
		}
		
		return cost;
	}
}
