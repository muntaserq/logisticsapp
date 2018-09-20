package Facility;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
public class FacilitiyLoaderXmlImpl implements FacilityLoader {

	private Double drivingHoursPerDay;
	private Double averageMilesPerHour;

	public FacilitiyLoaderXmlImpl(Double drivingHoursPerDay, Double averageMilesPerHour) {
		this.drivingHoursPerDay = drivingHoursPerDay;
		this.averageMilesPerHour = averageMilesPerHour;
	}

	@Override
	public HashMap<String, Facility> loadFacilities(String source) throws InvalidDataException {
		HashMap<String, Facility> facilities = new HashMap<String, Facility>();

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

			NodeList facilitiyEntries = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < facilitiyEntries.getLength(); i++) {
				String entryName = facilitiyEntries.item(i).getNodeName();
				if (entryName.equalsIgnoreCase("facility")) {
					Element elem = (Element) facilitiyEntries.item(i);
					String location = elem.getElementsByTagName("location").item(0).getTextContent();
					Integer processingRate = Integer
							.parseInt(elem.getElementsByTagName("processingRate").item(0).getTextContent());
					Integer facilityLimit = Integer
							.parseInt(elem.getElementsByTagName("facilityLimit").item(0).getTextContent());
					Double processingDailyRateCost = Double
							.parseDouble(elem.getElementsByTagName("cost").item(0).getTextContent());

					HashMap<String, FacilityLink> linkedFacilities = new HashMap<String, FacilityLink>();

					NodeList linkedFacilitiesList = elem.getElementsByTagName("facilitiyLinks");
					entryName = linkedFacilitiesList.item(0).getNodeName();

					elem = (Element) linkedFacilitiesList.item(0);
					NodeList facilitiesList = elem.getElementsByTagName("link");

					for (int j = 0; j < facilitiesList.getLength(); j++) {
						String linkedLocation = elem.getElementsByTagName("location").item(j).getTextContent();
						Double distance = Double
								.parseDouble(elem.getElementsByTagName("distance").item(j).getTextContent());
						FacilityLink fe = new FacilityLink(linkedLocation, distance, this.drivingHoursPerDay,
								this.averageMilesPerHour);
						linkedFacilities.put(linkedLocation, fe);
					}

					Facility facility = FacilityFactory.createFacility(location, processingRate,
							processingDailyRateCost, linkedFacilities, facilityLimit);
					facilities.put(location, facility);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}
		return facilities;
	}

}
