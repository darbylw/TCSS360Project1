import WeatherData.DataType;
import WeatherData.Sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Accepts a test input file which will 
 * emulate data from sensors. It then 
 * passes the data to their respective 
 * classes.
 */
public class Main {
    
    private static DataType[] type = new DataType[]{DataType.HUMIDITY, DataType.TEMPERATURE, DataType.SOIL_MOISTURE,
                DataType.RAIN_FALL, DataType.ULTRAVIOLET, DataType.SOLAR_RADIATION,
                DataType.WIND_DIRECTION, DataType.WIND_SPEED, DataType.RAIN_RATE}; 
        
    public static void main(String[] args) {
        DataRelay dataSet = new DataRelay(type, Sensor.OUTSIDE);
        String inputFileLocation = "test10000.txt"; //TODO add input file location here
        try {
            File inputFile = new File(inputFileLocation);
            Scanner s = new Scanner(inputFile);   
            int iterations = 0;
            while (s.hasNext()) {
                String next = s.next();
                Double data = Double.parseDouble(next); 
                dataSet.acceptDataPoint(data, type[iterations % type.length]);
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
