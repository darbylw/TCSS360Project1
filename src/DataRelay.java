

public class DataRelay {
    public enum Input {HUMIDITY, SOLAR_RADIATION, TEMPERATURE,
        ULTRAVIOLET, RAIN_FALL, RAIN_RATE, SOIL_MOISTURE};

    public void acceptDataPoint(double point, Input type) {
        return; // adds to respective object here
    }
}
