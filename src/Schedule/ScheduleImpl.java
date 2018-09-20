package Schedule;
import java.util.ArrayList;

/**
 * @author muntaserqutub
 *
 */
public class ScheduleImpl implements Schedule {

	private ArrayList<Integer> schedule;
	private Integer currentDay;
	private Integer processingRate;

	public ScheduleImpl(Integer processingRate) {
		this.processingRate = processingRate;

		int scheduleSize = 1000;
		this.schedule = new ArrayList<Integer>(scheduleSize);
		this.currentDay = 1;

		for (int i = 0; i < scheduleSize; i++) {
			this.schedule.add(processingRate);
		}
	}

	@Override
	public void printSchedule() {
		System.out.printf("%10s ", "Day");
		for (int i = 0; i <= currentDay; i++) {
			System.out.printf("%6d ", i);
		}
		System.out.println();

		System.out.printf("%10s ", "Available");
		for (int i = 0; i <= currentDay; i++) {
			System.out.printf("%6d ", this.schedule.get(i));
		}
		System.out.println();
	}

	@Override
	public Double daysToProcessNumberOfItems(Integer numberOfItems) {
		Double daysToProcessNumberOfItems = (double) numberOfItems / (double) this.processingRate;

		return daysToProcessNumberOfItems;
	}

	@Override
	public Integer scheduleNumberOfItems(Integer numberOfItems) {
		Integer daysToProcessNumber = (int) Math.ceil(daysToProcessNumberOfItems(numberOfItems));
		Integer numberOfItemsRemaining = numberOfItems;

		for (int i = currentDay; i <= daysToProcessNumber; i++) {
			if (numberOfItemsRemaining >= this.schedule.get(i)) {
				numberOfItemsRemaining = numberOfItemsRemaining - this.schedule.get(i);
				this.schedule.set(i, 0);
			} else if (numberOfItemsRemaining < this.schedule.get(i)) {
				this.schedule.set(i, this.schedule.get(i) - numberOfItemsRemaining);
				numberOfItemsRemaining = 0;
			}
		}

		return daysToProcessNumber;
	}

	@Override
	public Integer nextOpenDay(Integer numberOfItems) {
		Integer nextOpenDay = 0;
		for (int i = 1; i < schedule.size(); i++)
		{
			Integer value = schedule.get(i);
			if (numberOfItems <= value)
			{
				nextOpenDay = i;
				break;
			}
		}
		
		return nextOpenDay;
	}

	@Override
	public void nextDay() {
		this.currentDay++;
	}

	@Override
	public Integer getCurrentDay() {
		return currentDay;
	}

}
