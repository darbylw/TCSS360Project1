/*
 * An object that manages data ingest and output.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;
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
    /** The number of data points between transmission/writing. */
    private final int IO_RATE = 1000;
    /** The base of the url to which weather data will be written (time stamp will be added). */
    private final String DATA_URL = "WDATA_";
    /** Tracks the number of data points that have been processed. */
    private int processed = 0;

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
        if (++processed > IO_RATE) {
            processed = 0;
            writeData();
        }
    }

    /**
     * Writes the data in each file object to a unified text file.
     */
    private void writeData() {
        StringBuilder data = new StringBuilder();
        Formatter frmt = new Formatter(data);
        for (int i = 0; i < aggregators.size(); i++) {
            frmt.format("%-16s", aggregators.get(i).getType().toString() + ", ");
        }
        data.append("\n");
        for (int i = 0; i < HistoricalDataPoint.CAPACITY; i++) { // this method is ugly, needs some cleaning
            for (HistoricalDataPoint dataPoint : aggregators) {
                if (dataPoint.getAllReadings().size() > i) {
                    frmt.format("%-16s", dataPoint.getAllReadings().get(i) + ", ");
                } else {
                    frmt.format("%-16s", "--, ");
                }
            }
            data.append("\n");
        }
        String fileUrl = DATA_URL + String.join("_", (new Date()).toString().split(" ")) + ".csv";
        try {
            FileOutputStream out = new FileOutputStream(fileUrl);
            out.write(data.toString().getBytes());
        } catch (FileNotFoundException fne) {
            System.out.println("Unable to create output file. Are permissions correct?");
        } catch (IOException iox) {
            System.out.println("Error occurred while writing to data file.");
        }
    }
    
}
