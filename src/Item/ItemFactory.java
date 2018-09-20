package Item;
import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class ItemFactory {
	private ItemFactory() {	}
	
	public static Item createItem(String id, Double price) throws InvalidDataException
	{
		return new ItemImpl(id, price);
	}
	
	public static ItemLoader createLoader(){
		return new ItemLoaderXmlImpl();
	}

}
