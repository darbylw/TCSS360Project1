import java.util.Objects;

/**
 * Collects and processes ultraVoilet data from the
 * respective sensors.
 * @author Paras Sharma
 * @version 0.01
 */
public class UltraViolet extends HistoricalDataPoint {

    /**
     * The sensor type providing this instance with data.
     */
    private Sensor sensor;
    /**
     * Range the lower bound accepts
     */
    private final int rangeLow;
    /**
     * Range the upper bound accepts
     */
    private final int rangeHigh;
    /**
     * Alarm lowRange
     */
    private final int alarmRangeLow;
    /**
     * Alarm highRange
     */
    private final double alarmRangeHigh;


    /**
     * Constructs a new UltraViolet data processing
     * instance for the specified sensor.
     *
     * @param s the type of sensor that will send this
     *            object data (inside,
     */
    public UltraViolet(Sensor s) {
        Objects.requireNonNull(s, "Sensor type cannot be null.");
        this.sensor = s;
        this.rangeLow = 0;
        this.rangeHigh = 199; //MEDs
        this.alarmRangeLow = 0;
        this.alarmRangeHigh = 19.9; //MEDs
        //Not sure if we need to pull data from this since the sensor are purchased
    }

    /**
     * Adds a data point to the historical data set,
     * ignoring points that are out of the appropriate
     * range.
     *
     * @param point the data point to be added.
     */
    @Override
    public void addDataPoint(double point) {
        if (point <= rangeHigh && point >= rangeLow && sensor != Sensor.EXTRA)
            super.addDataPoint(point);
        else if (point <= rangeHigh && point >= rangeLow && sensor == Sensor.EXTRA)
            allReadings.add(point);
    }

    /**
     * Throws a warning on display if the MEDs go high.
     *
     * @param point, data that will compared with range
     * @return warning String
     */
    public String alarmWarning(double point) {
        double point1 = point;
        StringBuilder sb = new StringBuilder();
        if (point1 >= alarmRangeHigh) {
            sb.append("WARNING!, UV dosage is HIGH");

        } else if (point1 == alarmRangeLow) {
            sb.append("No protection needed");
        }

        return sb.toString();
    }
}