/*
 * A set of unit tests covering the historical data point
 * class.
 */

import WeatherData.HistoricalDataPoint;
import WeatherData.Humidity;
import WeatherData.Sensor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Random;

/**
 * A set of unit tests covering the WeatherData.HistoricalDataPoint
 * class.
 * @author Spencer Little
 * @version 0.0.0
 */
public class HistoricalDataPointTest {

    /** The test data point, instantiated as a WeatherData.Humidity object. */
    private final HistoricalDataPoint testCollector = new Humidity(Sensor.OUTSIDE);
    /** The random number generator used to create data points. */
    private final Random numGen = new Random();

    /**
     * Tests the tracking of daily high/low data points by generating
     * random humidity values and manually incrementing the WeatherData.Humidity
     * object's Calendar.
     * @author Spencer Little
     * @version 0.0.0
     */
    @Test
    public void testDailyReading() {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int day = testCollector.getCalendar().get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < numGen.nextInt(1000); i++) {
            int data = numGen.nextInt(100) + 1;
            if (day != testCollector.getCalendar().get(Calendar.DAY_OF_YEAR)) { // if day changes, reset max, min
                max = data;
                min = data;
                day = testCollector.getCalendar().get(Calendar.DAY_OF_YEAR);
            } else {
                max = Math.max(data, max);
                min = Math.min(data, min);
            }
            testCollector.addDataPoint(data);
            testCollector.addToCal(Calendar.HOUR, 1); // + 1 hour
        }
        Assert.assertEquals(max, testCollector.getDailyHigh(), 0f);
        Assert.assertEquals(min, testCollector.getDailyLow(), 0f);
    }

    /**
     * Tests the tracking of monthly high/low data points by generating
     * random humidity values and manually incrementing the WeatherData.Humidity
     * object's Calendar.
     * @author Spencer Little
     * @version 0.0.0
     */
    @Test
    public void testMonthlyReading() {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int month = testCollector.getCalendar().get(Calendar.MONTH);
        for (int i = 0; i < numGen.nextInt(1000); i++) {
            int data = numGen.nextInt(100) + 1;
            if (month != testCollector.getCalendar().get(Calendar.MONTH)) { // if month changes, reset max, min
                max = data;
                min = data;
                month = testCollector.getCalendar().get(Calendar.MONTH);
            } else {
                max = Math.max(data, max);
                min = Math.min(data, min);
            }
            testCollector.addDataPoint(data);
            testCollector.addToCal(Calendar.DAY_OF_YEAR, 1); // + 1 day
        }
        Assert.assertEquals(max, testCollector.getMonthlyHigh(), 0f);
        Assert.assertEquals(min, testCollector.getMonthlyLow(), 0f);
    }

    /**
     * Tests the tracking of yearly high/low data points by generating
     * random humidity values and manually incrementing the WeatherData.Humidity
     * object'Sos Calendar.
     * @author Spencer Little
     * @version 0.0.0
     */
    @Test
    public void testYearlyReading() {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int year = testCollector.getCalendar().get(Calendar.YEAR);
        for (int i = 0; i < numGen.nextInt(1000); i++) {
            int data = numGen.nextInt(100) + 1;
            if (year != testCollector.getCalendar().get(Calendar.YEAR)) { // if year changes, reset max, min
                max = data;
                min = data;
                year = testCollector.getCalendar().get(Calendar.YEAR);
            } else {
                max = Math.max(data, max);
                min = Math.min(data, min);
            }
            testCollector.addDataPoint(data);
            testCollector.addToCal(Calendar.MONTH, 1); // + 1 month
        }
        Assert.assertEquals(max, testCollector.getYearlyHigh(), 0f);
        Assert.assertEquals(min, testCollector.getYearlyLow(), 0f);
    }
}
