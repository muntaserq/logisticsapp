package Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Exceptions.InvalidDataException;


/**
 * @author muntaserqutub
 *
 */
public class OrderImpl implements Order{

	private String orderId;
	private String destination;
	private Integer orderTime;
	private HashMap<String, OrderItem> items;
	
	public OrderImpl(String orderId, String destination, Integer orderTime, ArrayList<OrderItem> orderItems) throws InvalidDataException {
		setOrderId(orderId);
		setDestination(destination);
		setOrderTime(orderTime);
		setItems(orderItems);
	}
	
	private void setItems(ArrayList<OrderItem> items) throws InvalidDataException
	{
		if (items == null)
		{
			throw new InvalidDataException("The data entered for the order item list is invalid.");
		}
		
		this.items = new HashMap<String, OrderItem>();
		for (OrderItem item: items)
		{
			this.items.put(item.getItemId(), item);
		}
	}

	private void setOrderId(String orderId) throws InvalidDataException {
		if (orderId == null || orderId.isEmpty())
		{
			throw new InvalidDataException("The data entered for the order ID is invalid.");
		}
		this.orderId = orderId;
		
	}

	private void setDestination(String destination) throws InvalidDataException {
		if (destination == null || destination.isEmpty())
		{
			throw new InvalidDataException("The data entered for the destinationis invalid.");
		}
		this.destination = destination;
	}
	
	private void setOrderTime(Integer orderTime) throws InvalidDataException {
		if (orderTime == null || orderTime < 0)
		{
			throw new InvalidDataException("The data entered for the order time is invalid.");
		}
		this.orderTime = orderTime;
	}

	@Override
	public String getOrderId() {
		return orderId;
	}

	@Override
	public Integer getOrderTime() {
		return orderTime;
	}

	@Override
	public ArrayList<OrderItem> getListOfItems() {
		ArrayList<OrderItem> listOfOrders = new ArrayList<OrderItem>();
		for (Entry<String, OrderItem> orderItem : items.entrySet()) {
			listOfOrders.add(orderItem.getValue());
		}
		return listOfOrders;
	}

	@Override
	public String getDestination() {
		return destination;
	}

	@Override
	public void printOrder(int orderNumber) {
		String newLine = "\n";
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("Order #" + orderNumber).append(newLine);
		stringBuilder.append(String.format("%-17s%-25s", "- Order Id: ", this.orderId)).append(newLine);
		stringBuilder.append(String.format("%-17s%-25s", "- Order Time: ", this.orderTime)).append(newLine);
		stringBuilder.append(String.format("%-17s%-25s", "- Destination: ", this.destination)).append(newLine);
		stringBuilder.append("- List of Order Items: ").append(newLine);
		
		for (Entry<String, OrderItem> orderItem : items.entrySet()) {
			stringBuilder.append("   -Item ID: ").append(orderItem.getKey()).append(", Quantity: ").append(orderItem.getValue().getQuantity()).append(newLine);
		}
		
		System.out.println(stringBuilder);
	}

}
