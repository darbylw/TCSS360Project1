package WeatherData;

import WeatherData.HistoricalDataPoint;

import java.util.Objects;

/**
 * Collects the data from the sensor and adds to 
 * the readings and throw warning if gets higher than normal.
 * @author Paras Sharma
 * @version 0.01
 */
public class SolarRadiation extends HistoricalDataPoint {

    /** The data type (category) that describes this object. */
    private final DataType dataType = DataType.SOLAR_RADIATION;
    /** The value at which a warning is registered. */
    private final int WARNING_LEVEL = 200;
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
     * Constructs a new WeatherData.UltraViolet data processing
     * instance for the specified sensor.
     *
     * @param s the type of sensor that will send this
     *          object data (inside,
     */
    public SolarRadiation(Sensor s) {
        Objects.requireNonNull(s, "WeatherData.Sensor type cannot be null.");
        this.sensor = s;
        this.rangeLow = 0;
        this.rangeHigh = 1800; // W/m^2

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
     * Throws a warning on display if the W/m^2 go higher
     * than hourly or daily reading.
     *
     * @param point, data that will compared with range
     * @return warning String
     */
    public String alarmWarning(double point) {
        double point1 = point;
        StringBuilder sb = new StringBuilder();
        if (point1 >= WARNING_LEVEL) {
            sb.append("WARNING!, Higher Radiations than usual");
        }

        return sb.toString();
    }

    /**
     * Returns this objects data type.
     * @return an enum specifying the type of data this object represents
     */
    @Override
    public DataType getType() {
        return dataType;
    }

    /**
     * Returns the upper bound of the range of acceptable
     * values for this data point.
     * @return a double representing the upper bound of acceptable
     *         values for this data point
     */
    @Override
    public double getUpperBound() {
        return rangeHigh;
    }

    /**
     * Returns the lower bound of the range of acceptable
     * values for this data point.
     * @return a double representing the lower bound of acceptable
     *         values for this data point
     */
    @Override
    public double getLowerBound() {
        return rangeLow;
    }
}