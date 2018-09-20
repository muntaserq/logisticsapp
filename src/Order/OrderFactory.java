package Order;

import java.util.ArrayList;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class OrderFactory {

	private OrderFactory() { }
	
	public static Order createOrder(String orderId, String destination, Integer orderTime, ArrayList<OrderItem> orderItems) throws InvalidDataException
	{
		return new OrderImpl(orderId, destination, orderTime, orderItems);
	}
	
	public static OrderLoader createOrderLoader()
	{
		return new OrderLoaderXmlImpl();
	}

}
