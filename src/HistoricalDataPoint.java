/*
 * A base class that manages historical data processing.
 */

import java.io.Serializable;
import java.util.*;

/**
 * Cumulatively processes sensor input and tacks historical
 * metrics.
 * @author Spencer Little
 * @version 0.0.0
 */
abstract class HistoricalDataPoint implements WDataPoint, Serializable {

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
    /** All readings. */
    private final List<Double> allReadings = new ArrayList<>();
    /** Calendar object, tracks time stamps. */
    private final GregorianCalendar currCal = new GregorianCalendar();
    /** Epoch time stamp, tracks when an hour has passed. */
    private long hourInterval = currCal.getTime().getTime();
    /** The Calendar day in which the most recent data point was added. */
    private int currDay = currCal.get(Calendar.DAY_OF_YEAR);
    /** The Calendar month in which the most recent data point was added. */
    private int currMonth = currCal.get(Calendar.MONTH);
    /** The Calendar year in which the most recent data point was added. */
    private int currYear = currCal.get(Calendar.YEAR);


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

        if ((currCal.getTime().getTime() - hourInterval) >= 3600000) {
            hourlyReadings.add(point);
            hourInterval = currCal.getTime().getTime();
        }
        if (currCal.get(Calendar.DAY_OF_YEAR) != currDay) {
            dailyHigh = point;
            dailyLow = point;
        }
        if (currCal.get(Calendar.MONTH) != currMonth) {
            monthlyHigh = point;
            monthlyLow = point;
        }
        if (currCal.get(Calendar.YEAR) != currYear) {
            yearlyHigh = point;
            yearlyLow = point;
        }

        if (point < dailyLow || point < monthlyLow || point < yearlyLow) {
            dailyLow = Math.min(point, dailyLow);
            monthlyLow = Math.min(point, monthlyLow);
            yearlyLow = Math.min(point, yearlyLow);
        }
        if (point > dailyHigh || point > monthlyHigh || point > yearlyHigh) {
            dailyHigh = Math.min(point, dailyHigh);
            monthlyHigh = Math.max(point, monthlyHigh);
            yearlyHigh = Math.max(point, yearlyHigh);
        }

    }
}
