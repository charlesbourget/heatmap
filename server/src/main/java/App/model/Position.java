package App.model;

import io.jenetics.jpx.Latitude;
import io.jenetics.jpx.Longitude;

public class Position {
    private double longitude;
    private double latitude;

    public Position(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
