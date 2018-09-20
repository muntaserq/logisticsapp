package Order;

import java.util.ArrayList;

import Exceptions.InvalidDataException;


public interface OrderLoader {
	public ArrayList<Order> loadOrders(String source, ArrayList<String> facilities, ArrayList<String> items) throws InvalidDataException;
}
