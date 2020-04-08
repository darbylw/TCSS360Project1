/*
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
abstract class HistoricalDataPoint implements WDataPoint, Serializable {

    /** Calendar object, tracks time stamps. */
    public final GregorianCalendar currCal = new GregorianCalendar();
    /** All data points received from sensor. */
    protected final List<Double> allReadings = new ArrayList<>();
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

    public List<Double> getAllReadings() {
        return allReadings;
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public double getDailyHigh() {
        return dailyHigh;
    }

    public double getDailyLow() {
        return dailyLow;
    }

    public double getMonthlyHigh() {
        return monthlyHigh;
    }

    public double getMonthlyLow() {
        return monthlyLow;
    }

    public double getYearlyHigh() {
        return yearlyHigh;
    }

    public double getYearlyLow() {
        return yearlyLow;
    }

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
        allReadings.add(point);
        currentReading = point;

        if ((currCal.getTimeInMillis() - hourInterval) >= 3600000) {
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
