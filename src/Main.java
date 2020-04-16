import WeatherData.DataType;
import WeatherData.Sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Accepts a test input file which will 
 * emulate data from sensors. It then 
 * passes the data to their respective 
 * classes.
 */
public class Main {
        
    public static void main(String[] args) {
        DataRelay dataSet = new DataRelay(DataType.ALL_TYPES, Sensor.OUTSIDE);
        String inputFileLocation = "test10000.txt"; //TODO add input file location here
        try {
            File inputFile = new File(inputFileLocation);
            Scanner s = new Scanner(inputFile);   
            int iterations = 0;
            while (s.hasNext()) {
                String next = s.next();
                Double data = Double.parseDouble(next); 
                dataSet.acceptDataPoint(data, DataType.ALL_TYPES[iterations % DataType.ALL_TYPES.length]);
                dataSet.incrementCal(Calendar.MINUTE, 15);
                iterations++;
            }
            s.close();
            
            } catch (FileNotFoundException e) {
                System.out.println("File can not be found.");
            } catch (NumberFormatException e) {
                System.out.println("File contains invalid data, please enter only numbers.");
            }
 

    }

}
