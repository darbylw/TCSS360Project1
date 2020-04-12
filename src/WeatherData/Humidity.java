package WeatherData;/*
 * The humidity data processing model, accepting humidity
 * metrics from the respective sensors.
 */

import WeatherData.HistoricalDataPoint;

import java.util.Objects;

/**
 * Collects and processes humidity data from the
 * respective sensors.
 * @author Spencer Little
 * @version 0.0.0
 */
public class Humidity extends HistoricalDataPoint {

    /** The data type (category) that describes this object. */
    private final DataType dataType = DataType.HUMIDITY;
    /** The sensor type providing this instance with data. */
    private Sensor sensor;
    /** The upper bound of the acceptable input range. */
    private final int rangeHigh = 100;
    /** The lower bound of the acceptable input range. */
    private final int rangeLow = 1;

    /**
     * Constructs a new WeatherData.Humidity data processing
     * instance for the specified sensor.
     * @param sns the type of sensor that will send this
     *            object data (inside, outside, or extra)
     */
    public Humidity(Sensor sns) {
        Objects.requireNonNull(sns, "WeatherData.Sensor type cannot be null.");
        sensor = sns;
    }

    /**
     * Adds a data point to the historical data set,
     * ignoring points that are out of the appropriate
     * range.
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
     * Returns this objects data type.
     * @return an enum specifying the type of data this object represents
     */
    @Override
    public DataType getType() {
        return dataType;
    }
}
