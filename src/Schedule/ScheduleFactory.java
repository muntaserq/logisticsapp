package Schedule;
/**
 * @author muntaserqutub
 *
 */
public class ScheduleFactory {
	private ScheduleFactory() {
	}

	public static Schedule createSchedule(Integer processingRate) {
		return new ScheduleImpl(processingRate);
	}
}
