package Item;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Exceptions.InvalidDataException;

/**
 * @author muntaserqutub
 *
 */
public class ItemLoaderXmlImpl implements ItemLoader {

	public ItemLoaderXmlImpl() {
	}

	@Override
	public HashMap<String, Item> loadItems(String source) throws InvalidDataException {

		HashMap<String, Item> loadedItems = new HashMap<String, Item>();

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

			NodeList itemEntries = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < itemEntries.getLength(); i++) {
				String entryName = itemEntries.item(i).getNodeName();
				if (entryName.equalsIgnoreCase("item")) {
					Element elem = (Element) itemEntries.item(i);
					String itemId = elem.getElementsByTagName("id").item(0).getTextContent();
					Double itemPrice = Double.parseDouble(elem.getElementsByTagName("price").item(0).getTextContent());

					Item readItem = ItemFactory.createItem(itemId, itemPrice);

					loadedItems.put(itemId, readItem);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}

		return loadedItems;
	}

}
