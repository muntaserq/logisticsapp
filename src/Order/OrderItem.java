package Order;

import Exceptions.InvalidDataException;

public class OrderItem {

	private String itemId;
	private Integer quantity;
	
	public OrderItem(String itemId, Integer quantity) {
		setItemId(itemId);
		setQuantity(quantity);
	}
	
	public void reduceQuantityBy(Integer reductionAmount) throws InvalidDataException{
		if (reductionAmount > quantity )
		{
			throw new InvalidDataException("The data entered for the order quantity is invalid.");
		}
		quantity = quantity - reductionAmount;
	}

	private void setItemId(String itemId)
	{
		this.itemId = itemId;
	}
	
	private void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}
	
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	
	

}
