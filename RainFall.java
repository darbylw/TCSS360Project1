import java.util.Objects;

/**
 * The rainfall data processing model which
 * measures highs and lows for rainfall.
 * @author Deline Zent
 */
public class RainFall extends HistoricalDataPoint {

    /** A serial number for the class. */
    private static final long serialVersionUID = -6415044752166543910L;
    /** The upper bound of the acceptable input range in inches. */
    private final double rangeHigh;
    /** The lower bound of the acceptable input range in inches. */
    private final int rangeLow = 0;
    /** The user specified measuring options. */
    public enum length {INCHES, MILLIMETERS}
    
    /**
     * Constructs a new RainFall data processing
     * instance for the specified measuring unit.
     * @param theLength is the unit of measurement
     */
    public RainFall(length theLength) {
        Objects.requireNonNull(theLength, "Length scale cannot be null.");
        if (theLength == length.INCHES) {
            rangeHigh = 99.99;
        } else {
            rangeHigh = 999.98;
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
        if (point <= rangeHigh && point >= rangeLow)
            super.addDataPoint(point);
        else if (point <= rangeHigh && point >= rangeLow)
            allReadings.add(point);
    }
    
}
