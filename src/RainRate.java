import java.util.Objects;

/**
 * The rain rate data processing model, accepting 
 * rain rate metrics from its sensor. 
 * @author Deline Zent
 */
public class RainRate extends HistoricalDataPoint {

    /** The data type (category) that describes this object. */
    private final DataType dataType = DataType.RAIN_RATE;
    /** A serial number for this class. */
    private static final long serialVersionUID = 4427698316186989617L;
    /** The upper bound of the acceptable input range. */
    private final double rangeHigh;
    /** The lower bound of the acceptable input range. */
    private final int rangeLow = 0;
    /** The user specified measuring options. */
    public enum length {INCHES, MILLIMETERS}
    
    /**
     * Constructs a new RainRate data processing
     * instance for the specified measuring unit.
     * @param theLength is the unit of measurement
     */
    public RainRate(length theLength) {
        Objects.requireNonNull(theLength, "Length scale cannot be null.");
        if (theLength == length.INCHES) {
            rangeHigh = 0.04;
        } else {
            rangeHigh = 1;
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

    /**
     * Returns this objects data type.
     * @return an enum specifying the type of data this object represents
     */
    @Override
    public DataType getType() {
        return dataType;
    }

}
