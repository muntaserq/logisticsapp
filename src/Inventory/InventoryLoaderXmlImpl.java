package Inventory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
public class InventoryLoaderXmlImpl implements InventoryLoader {

	public InventoryLoaderXmlImpl() { }

	@Override
	public HashMap<String, ArrayList<Inventory>> loadInventory(String source) throws InvalidDataException {

		HashMap<String, ArrayList<Inventory>> inventory = new HashMap<String, ArrayList<Inventory>>();

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

			NodeList facilitiyInventoryEntries = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < facilitiyInventoryEntries.getLength(); i++) {
				String entryName = facilitiyInventoryEntries.item(i).getNodeName();
				if (entryName.equalsIgnoreCase("facility")) {
					ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
					Element elem = (Element) facilitiyInventoryEntries.item(i);
					// Get a node attribute
					NamedNodeMap aMap = facilitiyInventoryEntries.item(i).getAttributes();
					String location = aMap.getNamedItem("location").getNodeValue();

					NodeList itemList = elem.getElementsByTagName("item");

					for (int j = 0; j < itemList.getLength(); j++) {
						String itemId = elem.getElementsByTagName("id").item(j).getTextContent();
						Integer quantity = Integer
								.parseInt(elem.getElementsByTagName("quantity").item(j).getTextContent());
						Inventory inv = InventoryFactory.createInventory(itemId, quantity, location);
						inventoryList.add(inv);
					}
					inventory.put(location, inventoryList);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}
		return inventory;
	}

}
