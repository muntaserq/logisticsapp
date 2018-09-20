package Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import Exceptions.InvalidDataException;
import Facility.FacilityManager;
import Facility.FacilityRecord;
import Facility.FacilityWithItemsDTO;
import Item.ItemManager;

/**
 * @author muntaserqutub
 *
 */
public class OrderProcessor {
	private FacilityManager facilityManager;
	private ItemManager itemManager;
	private OrderManager orderManager;
	
	private static final double DAILY_TRAVEL_COST = 500.0;
	
	public OrderProcessor(OrderManager orderManager, FacilityManager facilityManager, ItemManager itemManager) {
		this.orderManager = orderManager;
		this.facilityManager = facilityManager;
		this.itemManager = itemManager;
	}
	
	public void processOrders() throws InvalidDataException
	{
		int orderNumber = 0;
		for (int i = 1; i < 50; i++)
		{
			ArrayList<Order> orders = orderManager.getOrdersWithOrderDay(i);
			for (Order order : orders) {
				orderNumber++;
				order.printOrder(orderNumber);

				ArrayList<FacilityRecord> facilityRecords = new ArrayList<FacilityRecord>();
				ArrayList<OrderItem> orderItems = order.getListOfItems();
				ArrayList<String> backOrders = new ArrayList<String>();
				ArrayList<FacilityRecord> orderItemSolutionList = new ArrayList<FacilityRecord>();
				ArrayList<LogisticRecord> logisticRecords = new ArrayList<LogisticRecord>();
				HashMap<String, ArrayList<LogisticRecord>> allLogisticRecordsPerOrder = new HashMap<String, ArrayList<LogisticRecord>>();

				for (OrderItem orderItem : orderItems) {
					logisticRecords.clear();
					ArrayList<FacilityWithItemsDTO> facilitiesWithItem = facilityManager
							.getAllFacilityLocationsWithItem(orderItem.getItemId());

					Integer totalQuantity = orderItem.getQuantity();

					for (FacilityWithItemsDTO facilityDTO : facilitiesWithItem) {
						if (facilityDTO.facilityName.equals(order.getDestination()))
							continue;

						Double daysToFacility = facilityManager.calculateShortestPath(facilityDTO.facilityName,	order.getDestination(), false);
						Double daysNeededToProcess = facilityManager.daysNeededToProcessItemsAtFacility(facilityDTO.facilityName, orderItem.getQuantity());
						Integer arrivalDay = (int) Math.ceil(daysToFacility + daysNeededToProcess);

						FacilityRecord facilityRecord = new FacilityRecord(facilityDTO.facilityName,facilityDTO.numberOfItems, daysNeededToProcess, daysToFacility, arrivalDay);
						facilityRecords.add(facilityRecord);
					}
					Collections.sort(facilityRecords);

					for (FacilityRecord facilityRecord : facilityRecords) {
						if (orderItem.getQuantity() == 0)
							break;
						
						if (orderItem.getQuantity() <= facilityRecord.getNumberOfItems())
						{
							facilityRecord.setNumberOfItems(orderItem.getQuantity());
							orderItem.reduceQuantityBy(orderItem.getQuantity());
							this.facilityManager.reduceInventoryForItemByAmount(facilityRecord.getNameOfFacility(), orderItem.getItemId(), orderItem.getQuantity());
							this.facilityManager.bookDays(facilityRecord.getNameOfFacility(), orderItem.getQuantity());
							
						}
						else {
							orderItem.reduceQuantityBy(facilityRecord.getNumberOfItems());
							this.facilityManager.reduceInventoryForItemByAmount(facilityRecord.getNameOfFacility(), orderItem.getItemId(), facilityRecord.getNumberOfItems());
							this.facilityManager.bookDays(facilityRecord.getNameOfFacility(), facilityRecord.getNumberOfItems());
						}
						
						orderItemSolutionList.add(facilityRecord);

					}
					
					for (FacilityRecord solutionList : orderItemSolutionList)
					{
						String location = solutionList.getNameOfFacility();
						Integer quantity = solutionList.getNumberOfItems();

						Integer processingStartDay = facilityManager.getNextAvailableDay(location, quantity);
						Integer processingEndDay = processingStartDay + (int) Math.ceil(solutionList.getDaysNeededToProcess());
						Integer travelStartDay = processingEndDay + 1;
						Integer arrivalDay = solutionList.getArrivalTime();
						Integer travelEndDay = arrivalDay - travelStartDay;

						Double itemCost = itemManager.getCost(orderItem.getItemId()) * (double) quantity;
						Double transportCost = Math.ceil(solutionList.getTravelTime()) * DAILY_TRAVEL_COST;
						Double facilityProcessingCost = facilityManager.getProcessingRatePerDayForFacility(location) * solutionList.getDaysNeededToProcess();
						Double cost = itemCost + facilityProcessingCost + transportCost;

						LogisticRecord logisticRecord = new LogisticRecord(location, quantity, totalQuantity, cost, processingStartDay, processingEndDay, travelStartDay, travelEndDay, arrivalDay);

						logisticRecords.add(logisticRecord);
					}

					if (facilityRecords.size() == 0 || orderItem.getQuantity() > 0) {
						backOrders.add("Item ID: " + orderItem.getItemId().toString() + " has been put on back order.");
					}
					
					allLogisticRecordsPerOrder.put(orderItem.getItemId(), new ArrayList<LogisticRecord>(logisticRecords));
					
					orderItemSolutionList.clear();
					logisticRecords.clear();
					facilityRecords.clear();
					backOrders.clear();
				}
				processingSolution(order.getDestination(), order.getOrderId(), allLogisticRecordsPerOrder);
			}
			facilityManager.moveToNextDay();
		}
	}

	private void processingSolution(String destination, String orderId, HashMap<String, ArrayList<LogisticRecord>> allLogisticRecordsPerOrder) {
		Double totalCost = 0.0;
		Integer firstDeliveryDay = Integer.MAX_VALUE;
		Integer lastDeliverDay = Integer.MIN_VALUE;
		
		for (Entry<String, ArrayList<LogisticRecord>> listOfLogisticRecords : allLogisticRecordsPerOrder.entrySet()) {
			ArrayList<LogisticRecord> lr = listOfLogisticRecords.getValue();
			for (LogisticRecord logisticRecord : lr)
			{
				totalCost = totalCost + logisticRecord.getCost();
				
				Integer arrivalTimeMin = logisticRecord.getArrivalDay();
				if (arrivalTimeMin < firstDeliveryDay)
				{
					firstDeliveryDay = arrivalTimeMin;
				}
				
				Integer arrivalTimeMax = logisticRecord.getArrivalDay();
				if (arrivalTimeMax > lastDeliverDay)
				{
					lastDeliverDay = arrivalTimeMax;
				}
			}
		}
		
		Formatter costFormatter = new Formatter();

		costFormatter.format("%.2f", totalCost);
		
		String newLine = "\n";
		
		StringBuilder stringBuilder = new StringBuilder();
		
		
		stringBuilder.append("Processing Solution:").append(newLine);
		stringBuilder.append("Order Id: " + orderId).append(newLine);
		stringBuilder.append("- Destination: " + destination).append(newLine);
		stringBuilder.append("- Total Cost: $" + costFormatter.toString()).append(newLine);
		stringBuilder.append("- 1st Delivery Day: " + firstDeliveryDay).append(newLine);
		stringBuilder.append("- Last Delivery Day: " + lastDeliverDay).append(newLine);
		stringBuilder.append("- Order Items:").append(newLine);
		
		stringBuilder.append(String.format("\t%-15s%-15s%-15s%-15s%-15s%-15s", "Item ID", "Quantity", "Cost", "Num. Sources", "First Day", "Last Day")).append(newLine);
		
		Set<String> orderItems = allLogisticRecordsPerOrder.keySet();
		
		for (String orderItemId : orderItems)
		{
			Integer quantity = 0;
			Double cost = 0.0;
			Integer numberOfSources = 0;
			Integer minimumDelivery = 1000;
			Integer maximumDelivery = 0;
			ArrayList<LogisticRecord> records = allLogisticRecordsPerOrder.get(orderItemId);
			
			for (LogisticRecord lr : records)
			{
				quantity = quantity + lr.getQuantity();
				cost = cost + lr.getCost();
				numberOfSources = records.size();
				
				if (lr.getArrivalDay() < minimumDelivery)
				{
					minimumDelivery = lr.getArrivalDay();
				}
				
				if (lr.getArrivalDay() > maximumDelivery)
				{
					maximumDelivery = lr.getArrivalDay();
				}
			}
			
			Formatter costFormatter2 = new Formatter();
			costFormatter2.format("%.2f", cost);
			
			stringBuilder.append(String.format("\t%-15s%-15s%-15s%-15s%-15s%-15s", orderItemId, quantity, "$" + costFormatter2.toString(), numberOfSources, minimumDelivery, maximumDelivery)).append(newLine);
			costFormatter2.close();

		}
		
		System.out.println(stringBuilder.toString());
		costFormatter.close();
	}


}
