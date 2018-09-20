package Order;
/**
 * 
 */

import java.util.ArrayList;

/**
 * @author muntaserqutub
 *
 */
public interface Order{
	public String getOrderId();
	public Integer getOrderTime();
	public ArrayList<OrderItem> getListOfItems();
	public String getDestination();
	public void printOrder(int orderNumber);
}
