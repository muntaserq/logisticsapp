package Inventory;
import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class InventoryImpl implements Inventory {

	private String itemId;
	private Integer quantity;
	private String location;
	private boolean depleated;

	public InventoryImpl(String itemId, Integer quantity, String location) throws InvalidDataException {
		setItemId(itemId);
		setQuantity(quantity);
		setLocation(location);
		setDepleated(false);
	}

	public void setDepleated(boolean value) {
		this.depleated = value;
	}

	public void setQuantity(Integer quantity) throws InvalidDataException {
		if (quantity == null || quantity < 0) {
			throw new InvalidDataException("The data entered for quantity is invalid.");
		}
		this.quantity = quantity;
	}

	@Override
	public String getItemId() {
		return itemId;
	}

	@Override
	public Integer getQuantity() {
		return quantity;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "Item ID: " + getItemId() + " Quantity: " + getQuantity();
	}

	@Override
	public boolean isDepleated() {
		return this.depleated;
	}
	
	private void setItemId(String itemId) throws InvalidDataException {
		if (itemId == null || itemId.isEmpty()) {
			throw new InvalidDataException("The data entered for inventory is invalid.");
		}
		this.itemId = itemId;
	}

	private void setLocation(String location) throws InvalidDataException {
		if (location == null || location.isEmpty()) {
			throw new InvalidDataException("The data entered for location is invalid.");
		}
		this.location = location;
	}
}
