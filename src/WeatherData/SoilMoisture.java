package WeatherData;

/**
 * The soil moisture data processing model which
 * measures highs and lows. 
 * @author Deline Zent
 */
public class SoilMoisture extends HistoricalDataPoint {

    /** The data type (category) that describes this object. */
    private final DataType dataType = DataType.SOIL_MOISTURE;
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

    /**
     * Returns this objects data type.
     * @return an enum specifying the type of data this object represents
     */
    @Override
    public DataType getType() {
        return dataType;
    }

}
