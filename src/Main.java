import Facility.FacilityManager;
import Item.ItemManager;
import Order.OrderManager;
import Order.OrderProcessor;

public class Main {

	private Main() {
	}

	public static void main(String[] args) {

		Double drivingHoursPerDay = 8.0;
		Double averageMilesPerHour = 50.0;
		
		//could potentially have these be passed in as args
		String itemsFileName = "items.xml";
		String facilitiesFileName = "facilities.xml";
		String ordersFileName = "orders.xml";

		System.out.println("Logistics Application, Phase III");
		System.out.println("Muntaser Qutub\nSE 450\n");

		if (args.length == 2) {
			drivingHoursPerDay = Double.parseDouble(args[0]);
			averageMilesPerHour = Double.parseDouble(args[1]);
		}

		try {
			ItemManager itemManager = new ItemManager(itemsFileName);
			FacilityManager facilityManager = new FacilityManager(facilitiesFileName, drivingHoursPerDay,
					averageMilesPerHour);

			for (String facilityLocation : facilityManager.getListOfFacilities()) {
				facilityManager.printFacilityStatus(facilityLocation);
			}

			OrderManager orderManager = new OrderManager(ordersFileName, facilityManager.getListOfFacilities(), itemManager.getListOfAllItems());
			
			OrderProcessor orderProcessor = new OrderProcessor(orderManager, facilityManager, itemManager);
			orderProcessor.processOrders();
			
			for (String facilityLocation : facilityManager.getListOfFacilities()) {
				facilityManager.printFacilityStatus(facilityLocation);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
