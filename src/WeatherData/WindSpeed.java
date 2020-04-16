package WeatherData;/*
 * Model to receive sensor data for wind speed.
 */

import WeatherData.HistoricalDataPoint;

import java.util.Objects;

/**
 * Collects sensor data for wind speed.
 * 
 * @author Darby Wise
 */
public class WindSpeed extends HistoricalDataPoint {

    /** 
     * The sensor type sending data. 
    **/
    private Sensor sensor;
    
    /** 
     * Input range upper bound. 
     **/
    private final int rangeHigh = 100;
    
    /** 
     * Input range lower bound. 
     **/
    private final int rangeLow = 1;
    
    /** The data type (category) that describes this object. */
    private final DataType dataType = DataType.WIND_SPEED;

    /**
     * Constructor for WeatherData.WindSpeed instance.
     * 
     * @param sns type of sensor that will send this
     *            object data
     */
    public WindSpeed(Sensor sns) {
        Objects.requireNonNull(sns, "WeatherData.Sensor type cannot be null.");
        sensor = sns;
    }

    /**
     * Adds a data point in proper range to historical data set.
     * 
     * @param point data point that will be added.
     */
    @Override
    public void addDataPoint(double point) {
        if (point <= rangeHigh && point >= rangeLow && sensor != Sensor.EXTRA)
            super.addDataPoint(point);
        else if (point <= rangeHigh && point >= rangeLow && sensor == Sensor.EXTRA)
            allReadings.add(point);
    }
    
    /**
     * Returns objects data type.
     * @return enum for the type of data this object represents
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
    
    /**
     * Possibly an alarm message here?
     * 
     */
    
    /**
     * Possible check for maxed out cable length.
     */
}
