package WeatherData;/*
 * An interface specifying the base functionality for the
 * weather data point hierarchy.
 * 04/02/20 - Added file
 */

/**
 * Specifies base functionality for classes that represent
 * weather data points.
 * @author Spencer Little
 * @version 0.0.0
 */
public interface WDataPoint {
    /** Add a data point from the sensor to the cumulative total tracked by the object. */
    void addDataPoint(double point); // should this be double? other data type?
}
