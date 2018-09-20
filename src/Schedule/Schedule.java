package Schedule;

/**
 * @author muntaserqutub
 *
 */
public interface Schedule {
	public Double daysToProcessNumberOfItems(Integer numberOfItems);

	public Integer scheduleNumberOfItems(Integer numberOfItems);

	public void printSchedule();
	
	public void nextDay();
	
	public Integer getCurrentDay();
	
	public Integer nextOpenDay(Integer numberOfItems);
}
