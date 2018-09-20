package Facility;

public class FacilityRecord implements Comparable<FacilityRecord>{
	
	private String nameOfFacility;
	private Integer numberOfItems;
	private Double daysNeededToProcess;
	private Double travelTime;
	private Integer arrivalTime;

	public FacilityRecord(String nameOfFacility, Integer numberOfItems, Double daysNeededToProcess, Double travelTime, Integer arrivalTime) 
	{
		this.nameOfFacility = nameOfFacility;
		this.numberOfItems = numberOfItems;
		this.daysNeededToProcess = daysNeededToProcess;
		this.travelTime = travelTime;
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the nameOfFacility
	 */
	public String getNameOfFacility() {
		return nameOfFacility;
	}

	/**
	 * @return the numberOfItems
	 */
	public Integer getNumberOfItems() {
		return numberOfItems;
	}

	/**
	 * @return the processingEndDay
	 */
	public Double getDaysNeededToProcess() {
		return daysNeededToProcess;
	}

	/**
	 * @return the travelTime
	 */
	public Double getTravelTime() {
		return travelTime;
	}

	/**
	 * @return the arrivalTime
	 */
	public Integer getArrivalTime() {
		return arrivalTime;
	}

	@Override
	public int compareTo(FacilityRecord o) {
		 return  this.getArrivalTime().compareTo(o.getArrivalTime());
	}
	
	public void setNumberOfItems(Integer numberOfItems)
	{
		this.numberOfItems = numberOfItems;
	}
	
	

}
