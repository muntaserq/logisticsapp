package Order;

import java.util.ArrayList;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class OrderManager {
	
	ArrayList<Order> orders;

	public OrderManager(String source, ArrayList<String> facilities, ArrayList<String> items) throws InvalidDataException {
		OrderLoader orderLoader = OrderFactory.createOrderLoader();
		this.orders = orderLoader.loadOrders(source, facilities, items);
	}

	public ArrayList<Order> getOrdersWithOrderDay(Integer orderDay){
		ArrayList<Order> listOfOrdersForDay = new ArrayList<Order>();
		for (Order order : orders) {
			if (order.getOrderTime() == orderDay)
			{
				listOfOrdersForDay.add(order);
			}
			
		}
		return listOfOrdersForDay;
	}
	
}
