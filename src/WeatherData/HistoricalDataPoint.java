package WeatherData;/*
 * A base class that manages historical data processing.
 */

import java.io.Serializable;
import java.util.*;

/**
 * Cumulatively processes sensor input and tracks historical
 * metrics.
 * @author Spencer Little
 * @version 0.0.0
 */
public abstract class HistoricalDataPoint implements WDataPoint, Serializable {

    /** The maximum number of data points held by an object. */
    public static final int CAPACITY = 24;
    /** Calendar object, tracks time stamps. */
    public final GregorianCalendar currCal = new GregorianCalendar();
    /** All data points received from sensor. */
    protected final LinkedList<Double> allReadings = new LinkedList<>();
    /** The most recent reading from the sensor. */
    private double currentReading;
    /** Daily high temp. Resets every 24 hours after initial data point is added.*/
    private double dailyHigh = Double.MIN_VALUE;
    /** Daily low temp. Resets every 24 hours after initial data point is added.*/
    private double dailyLow = Double.MAX_VALUE;
    /** Monthly high temp. Resets every ~30 days after initial data point is added.*/
    private double monthlyHigh = Double.MIN_VALUE;
    /** Monthly low temp. Resets every ~30 days after initial data point is added.*/
    private double monthlyLow = Double.MAX_VALUE;
    /** Yearly high temp. Resets every 365 days after initial data point is added.*/
    private double yearlyHigh = Double.MIN_VALUE;
    /** Yearly low temp. Resets every 365 days after initial data point is added.*/
    private double yearlyLow = Double.MAX_VALUE;
    /** Aggregated readings from each hour. */
    private final List<Double> hourlyReadings = new ArrayList<>();
    /** Epoch time stamp, tracks when an hour has passed. */
    private long hourInterval = currCal.getTimeInMillis();
    /** The Calendar day in which the most recent data point was added. */
    private int currDay = currCal.get(Calendar.DAY_OF_YEAR);
    /** The Calendar month in which the most recent data point was added. */
    private int currMonth = currCal.get(Calendar.MONTH);
    /** The Calendar year in which the most recent data point was added. */
    private int currYear = currCal.get(Calendar.YEAR);

    /**
     * Factor method to construct a data processing object from the
     * specified WeatherData.DataType and WeatherData.Sensor.
     * @param t the desired type of the new data point
     * @param s the type of sensor (only available for some data points)
     * @return a new data point with the specified type and sensor
     */
    public static HistoricalDataPoint fromType(DataType t, Sensor s) {
        Objects.requireNonNull(t, "The data type cannot be null.");
        Objects.requireNonNull(s, "The sensor type cannot be null.");
        HistoricalDataPoint newPoint;
        switch (t) {
            case HUMIDITY:
                newPoint = new Humidity(s);
                break;
            case RAIN_FALL:
                newPoint = new RainFall(RainFall.length.MILLIMETERS);
                break;
            case RAIN_RATE:
                newPoint = new RainRate(RainRate.length.MILLIMETERS); // allow configs for in vs mm
                break;
            case SOIL_MOISTURE:
                newPoint = new SoilMoisture();
                break;
            case SOLAR_RADIATION:
                newPoint = new SolarRadiation(s);
                break;
            case TEMPERATURE:
                newPoint = new Temperature(s, Temperature.Temp.CELCIUS);
                break;
            case ULTRAVIOLET:
                newPoint = new UltraViolet(s);
                break;
            case WIND_DIRECTION:
                newPoint = new WindDirection(s);
                break;
            case WIND_SPEED:
                newPoint = new WindSpeed(s);
                break;
            default:
                throw new IllegalArgumentException("Unable to construct processor from null data type.");
        }
        return newPoint;
    }

    /** Gets the most recent hourly reading. */
    public Double getHourlyReading() {
        return hourlyReadings.get(hourlyReadings.size() - 1);
    }

    /** Gets this objects data type. */
    public abstract DataType getType();

    /** Gets the record of all data point accepted by this object. */
    public List<Double> getAllReadings() {
        return allReadings;
    }

    /** Gets the most recent data point accepted by this object. */
    public double getCurrentReading() {
        return currentReading;
    }

    /** Gets the daily high. */
    public double getDailyHigh() {
        return dailyHigh;
    }

    /** Gets the daily low. */
    public double getDailyLow() {
        return dailyLow;
    }

    /** Gets the monthly high. */
    public double getMonthlyHigh() {
        return monthlyHigh;
    }

    /** Gets the monthly low. */
    public double getMonthlyLow() {
        return monthlyLow;
    }

    /** Gets the yearly high. */
    public double getYearlyHigh() {
        return yearlyHigh;
    }

    /** Gets the yearly low. */
    public double getYearlyLow() {
        return yearlyLow;
    }

    /** Gets all hourly readings. */
    public List<Double> getHourlyReadings() {
        return hourlyReadings;
    }

    /**
     * Adds a data point to the historical collection of data
     * (hourly intervals, daily, monthly, and yearly highs. Assumes
     * the data point is within valid range.
     * @param point the data point to be added.
     */
    @Override
    public void addDataPoint(double point) {
        allReadings.addLast(point);
        if (allReadings.size() > CAPACITY) allReadings.removeFirst();
        currentReading = point;

        if ((currCal.getTimeInMillis() - hourInterval) >= 3600000) {
            hourlyReadings.clear();
            hourInterval = currCal.getTimeInMillis();
            hourlyReadings.add(point);
        }
        if (currCal.get(Calendar.DAY_OF_YEAR) != currDay) {
            currDay = currCal.get(Calendar.DAY_OF_YEAR);
            dailyHigh = point;
            dailyLow = point;
        }
        if (currCal.get(Calendar.MONTH) != currMonth) {
            currMonth = currCal.get(Calendar.MONTH);
            monthlyHigh = point;
            monthlyLow = point;
        }
        if (currCal.get(Calendar.YEAR) != currYear) {
            currYear = currCal.get(Calendar.YEAR);
            yearlyHigh = point;
            yearlyLow = point;
        }

        if (point < dailyLow || point < monthlyLow || point < yearlyLow) {
            dailyLow = Math.min(point, dailyLow);
            monthlyLow = Math.min(point, monthlyLow);
            yearlyLow = Math.min(point, yearlyLow);
        }
        if (point > dailyHigh || point > monthlyHigh || point > yearlyHigh) {
            dailyHigh = Math.max(point, dailyHigh);
            monthlyHigh = Math.max(point, monthlyHigh);
            yearlyHigh = Math.max(point, yearlyHigh);
        }
    }
}
