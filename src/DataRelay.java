/*
 * An object that manages data ingest and output.
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Accepts data from the various sensors, aggregates,
 * and writes data to a text file after @code{writeThreshold}
 * data points have been added.
 * @author Spencer Little
 * @version 0.0.0
 */
public class DataRelay implements Serializable {

    /** The set of data point objects that collect incoming data. */
    private final List<HistoricalDataPoint> aggregators = new LinkedList<>();

    /**
     * Constructs a new DataRelay for the specified list of
     * data types.
     * @param types the list of data types supported by this relay
     * @param s the type of sensor this relay supports
     */
    public DataRelay(DataType[] types, Sensor s) {
        for (int i = 0; i < types.length; i++) {
            aggregators.add(HistoricalDataPoint.fromType(types[i], s));
        }
    }


    /**
     * Accepts a data point from a sensor, adding it to the respective
     * DataPoint object.
     * @param point the value of the data point
     * @param type the type of data point
     */
    public void acceptDataPoint(double point, DataType type) {
        for (HistoricalDataPoint p : aggregators) {
            if (p.getType() == type) p.addDataPoint(point);
        }
    }
    
    /** Serializes the data from the output file.
     * @param inputFile is the data file which we must serialize
     * @param serializedFile is the data file which we will transmit
     */
    public File serializeData(File inputFile) {
        try {    
            File serializedFile = new File("serializedFile.txt");
            FileOutputStream file = new FileOutputStream(serializedFile); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
            //out serializes the inputFile into serializedFile
            out.writeObject(inputFile); 
            out.close(); 
            file.close(); 
        } catch(IOException ex) { 
            System.out.println("Error, input file could not be serialized."); 
        } 
    }
}