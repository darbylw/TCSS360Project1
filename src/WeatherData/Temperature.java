package WeatherData;/*
 * The temperature class manages sensor input from the
 * temperature sensor, providing historical metrics.
 */

import WeatherData.HistoricalDataPoint;

import java.util.Objects;

/**
 * The temperature class processes input from the
 * temperature sensor and provides historical metrics.
 * @author Spencer Little
 * @version 0.0.0
 */
public class Temperature extends HistoricalDataPoint {

    /** The data type (category) that describes this object. */
    private final DataType dataType = DataType.TEMPERATURE;
    /** The temperature paradigm used to measure data. */
    public enum Temp {CELCIUS, FAHRENHEIT}
    /** The sensor type providing this instance with data. */
    private Sensor sensorType;
    /** The upper bound of the acceptable input range. */
    private double rangeHigh;
    /** The lower bound of the acceptable input range. */
    private double rangeLow;

    /**
     * Constructs a new WeatherData.Temperature data processing
     * instance for the specified scale and sensor.
     * @param sns the type of sensor that will send this
     *            object data (inside, outside, or extra)
     * @param tmp the desired temperature scale (C or F)
     */
    public Temperature(Sensor sns, Temp tmp) {
        Objects.requireNonNull(sns, "WeatherData.Sensor type cannot be null.");
        Objects.requireNonNull(tmp, "WeatherData.Temperature scale cannot be null.");
        sensorType = sns;
        if (sns ==  Sensor.INSIDE) {
            rangeHigh = tmp == Temp.CELCIUS ? 60 : 140;
            rangeLow = tmp == Temp.CELCIUS ? 0 : 32;
        } else {
            rangeHigh = tmp == Temp.CELCIUS ? 65 : 150;
            rangeLow = -40;
        }
    }

    /**
     * Adds a data point to the historical data set,
     * ignoring points that are out of the appropriate
     * range.
     * @param point the data point to be added.
     */
    @Override
    public void addDataPoint(double point) {
        if (point <= rangeHigh && point >= rangeLow && sensorType != Sensor.EXTRA)
            super.addDataPoint(point);
        else if (point <= rangeHigh && point >= rangeLow && sensorType == Sensor.EXTRA)
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