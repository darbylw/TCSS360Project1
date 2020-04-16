/*
 * A set of unit tests covering the serialization features of the
 * DataRelay class.
 */

import WeatherData.HistoricalDataPoint;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Random;

/**
 * Tests the DataRelay objects persistence when serialized and
 * written to local disk (or by extension when sent OTW to the
 * data console). Note that this is in addition to the easily
 * parsed txt file automatically produced by DataRelay.
 */

public class DataTransferTest {

    /**
     * Tests the serialization of DataRelay writing an
     * object to disk then recovering it, and verifying
     * it's equality with the original object.
     */
    @Test
    public void testSerialization() {
        DataRelay testRelay = genRandomRelay(), reserialized = null;
        try {
            FileOutputStream out = new FileOutputStream(testRelay + "_serialized");
            ObjectOutputStream dataOut = new ObjectOutputStream(out);
            dataOut.writeObject(testRelay);

            FileInputStream in = new FileInputStream(testRelay + "_serialized");
            ObjectInputStream dataIn = new ObjectInputStream(in);
            reserialized = (DataRelay) dataIn.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while creating file for DataRelay serialization.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error occurred while writing DataRelay to file (during serialization).");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println("Error occured while reading DataRelay object from file (during reserialization).");
            System.exit(1);
        }

        Assert.assertEquals(testRelay, reserialized);
    }

    /**
     * Creates a new DataRelay with all possible sensor types and
     * populates the data points with random values.
     * @return a new DataRelay populated with random values
     */
    private DataRelay genRandomRelay() {
        DataRelay gen = new DataRelay();
        Random numGen = new Random();
        for (HistoricalDataPoint dp : gen.getDataPoints()) {
            for (int i = 0; i < numGen.nextInt(1000); i++) {
                double data = dp.getLowerBound()
                        + (dp.getUpperBound() - dp.getLowerBound()) * numGen.nextDouble();
                gen.acceptDataPoint(data, dp.getType()); // could be direct call, included gen for clarity
            }
        }
        return gen;
    }
}
