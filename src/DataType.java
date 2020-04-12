/*
 * An enum that models the different types of sensor
 * data outputs.
 */

/**
 * Distinguishes different types of data 
 * measured by all the sensors for the 
 * weather station. 
 * @author Deline Zent
 * @version 0.0.0
 */
public enum DataType {
    /** The humidity in the air. */
    HUMIDITY, 
    /** The amount of radiation in the air. */
    SOLAR_RADIATION, 
    /** The temperature in celsius/fahrenheit. */
    TEMPERATURE, 
    /** The amount of UV measured from the sun. */
    ULTRAVIOLET, 
    /** The amount of rain that has fallen in inches/mm. */
    RAIN_FALL, 
    /** The rate the rain is falling in a time frame.  */
    RAIN_RATE, 
    /** The moisture levels in the soil. */
    SOIL_MOISTURE, 
    /** The direction of the wind. */
    WIND_DIRECTION, 
    /** The speed of the wind. */
    WIND_SPEED

}
