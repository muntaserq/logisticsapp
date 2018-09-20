package Item;
import java.util.Formatter;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class ItemImpl implements Item {
	
	private String id;
	private Double price;
	
	public ItemImpl(String id, Double price) throws InvalidDataException {
		setId(id);
		setPrice(price);
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @return the price
	 */
	@Override
	public Double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		Double price = getPrice();
		Formatter priceFormat = new Formatter();
        priceFormat.format("%.2f", price);
        
        String returnValue = "Item ID: " + getId() + " Price: $" + priceFormat.toString();
        priceFormat.close();
		return returnValue;
	}

	private void setPrice(Double price) throws InvalidDataException {
		if (price == null || price < 0.0)
		{
			throw new InvalidDataException("The data entered for the item price is invalid.");
		}
		this.price = price;
	}

	private void setId(String id) throws InvalidDataException {
		if (id == null || id.isEmpty())
		{
			throw new InvalidDataException("The data entered for the item id is invalid.");
		}
		
		this.id = id;
	}
}
