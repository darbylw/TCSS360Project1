/**
 * The soil moisture data processing model which
 * measures highs and lows. 
 * @author Deline Zent
 */
public class SoilMoisture extends HistoricalDataPoint {

    /** A serial number for the class. */
    private static final long serialVersionUID = -6927110504185143175L;
    /** The upper bound of the acceptable input range in cb. */
    private final int rangeHigh = 200;
    /** The lower bound of the acceptable input range in cb. */
    private final int rangeLow = 0;
    
    /**
     * Adds a data point to the historical data set,
     * ignoring points that are out of the appropriate
     * range.
     * @param point the data point to be added.
     */
    @Override
    public void addDataPoint(double point) {
        if (point <= rangeHigh && point >= rangeLow)
            super.addDataPoint(point);
        else if (point <= rangeHigh && point >= rangeLow)
            allReadings.add(point);
    }

}
