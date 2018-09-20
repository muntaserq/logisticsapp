package Order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class OrderLoaderXmlImpl implements OrderLoader {

	public OrderLoaderXmlImpl() {	}

	@Override
	public ArrayList<Order> loadOrders(String source, ArrayList<String> facilities, ArrayList<String> items) throws InvalidDataException {
		ArrayList<Order> orders = new ArrayList<Order>();
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		try {
			String fileName = source;

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			File xml = new File(fileName);
			if (!xml.exists()) {
				System.err.println("**** XML File '" + fileName + "' cannot be found");
				System.exit(-1);
			}

			Document document = documentBuilder.parse(xml);
			document.getDocumentElement().normalize();

			NodeList orderEntries = document.getDocumentElement().getChildNodes();
			
			for (int i = 0; i < orderEntries.getLength(); i++) {
				String entryName = orderEntries.item(i).getNodeName();
				if (entryName.equalsIgnoreCase("order")) {
					Element elem = (Element) orderEntries.item(i);
					// Get a node attribute
					NamedNodeMap aMap = orderEntries.item(i).getAttributes();
					String orderId = aMap.getNamedItem("id").getNodeValue();
					if (orderId == null || orderId.isEmpty())
					{
						System.out.println("SKIPPING ORDER: An order was attempted to be processed without a valid Order ID number");
						continue;
					}
					String destination = elem.getElementsByTagName("destination").item(0).getTextContent();
					if (!facilities.contains(destination))
					{
						System.out.println("SKIPPING ORDER: An order was attempted to be processed without a valid facility destination.");
						continue;
					}
					
					String orderTimeAsString = elem.getElementsByTagName("orderTime").item(0).getTextContent();
					if (orderTimeAsString == null || orderTimeAsString.isEmpty())
					{
						System.out.println("SKIPPING ORDER: An order was attempted to be processed without a valid order day.");
						continue;
					}
					Integer orderTime = Integer.parseInt(orderTimeAsString);
					if (orderTime == null || orderTime < 1)
					{
						System.out.println("SKIPPING ORDER: An order was attempted to be processed without a valid order day.");
						continue;
					}
					
					NodeList itemList = elem.getElementsByTagName("item");
					
					for (int j = 0; j < itemList.getLength(); j++) {
						String itemId = elem.getElementsByTagName("id").item(j).getTextContent();
						Integer quantity = Integer.parseInt(elem.getElementsByTagName("quantity").item(j).getTextContent());
						
						if (!items.contains(itemId) || (quantity == null || quantity < 1))
						{
							System.out.println("SKIPPING ITEM: An item was attempted to be processed that is not an actual item at any of the facilities");
							continue;
						}
						
						orderItems.add(new OrderItem(itemId, quantity));
					}
					
					Order order = OrderFactory.createOrder(orderId, destination, orderTime, orderItems);
					orders.add(order);
					orderItems.clear();
				}
			}
		}
		catch (ParserConfigurationException | SAXException | IOException | DOMException e) 
		{
			e.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		
		return orders;
	}

}
