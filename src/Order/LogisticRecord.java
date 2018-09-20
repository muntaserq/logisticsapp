package Order;

public class LogisticRecord {
	private String location;
	private Integer quantity;
	private Integer totalQuantity;
	private Double cost;
	private Integer processingStartDay;
	private Integer processingEndDay;
	private Integer travelStartDay;
	private Integer travelEndDay;
	private Integer arrivalDay;
	
	public LogisticRecord(String location, Integer quantity, Integer totalQuantity, Double cost, 
			Integer processingStartDay, Integer processingEndDay, Integer travelStartDay, Integer travelEndDay,
			Integer arrivalDay)
	{
		this.location = location;
		this.quantity = quantity;
		this.totalQuantity = totalQuantity;
		this.cost = cost;
		this.processingStartDay = processingStartDay;
		this.processingEndDay = processingEndDay;
		this.travelStartDay = travelStartDay;
		this.travelEndDay = travelEndDay;
		this.arrivalDay = arrivalDay;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @return the totalQuantity
	 */
	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * @return the processingStartDay
	 */
	public Integer getProcessingStartDay() {
		return processingStartDay;
	}

	/**
	 * @return the processingEndDay
	 */
	public Integer getProcessingEndDay() {
		return processingEndDay;
	}

	/**
	 * @return the travelStartDay
	 */
	public Integer getTravelStartDay() {
		return travelStartDay;
	}

	/**
	 * @return the travelEndDay
	 */
	public Integer getTravelEndDay() {
		return travelEndDay;
	}

	/**
	 * @return the arrivalDay
	 */
	public Integer getArrivalDay() {
		return arrivalDay;
	}

}
