package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents the GPS coordinates of a location.
 * A GPS is a value object that consists of a Latitude and Longitude.
 */
public class Gps implements ValueObject {

    private final Latitude latitude;

    private final Longitude longitude;

    /**
     * Constructs a GPS object with the specified Latitude and Longitude.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Gps(Latitude latitude, Longitude longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException();
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Retrieves the Latitude of the GPS.
     *
     * @return the latitude of the GPS
     */
    public Latitude getLatitude() {
        return latitude;
    }

    /**
     * Retrieves the Longitude of the GPS.
     *
     * @return the longitude of the GPS
     */
    public Longitude getLongitude() {
        return longitude;
    }
    /**
     * Compares this GPS object with another object to determine if they are the same.
     *
     * @param o the object to compare
     * @return true if the objects are the same or have the same latitude and longitude, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gps gps = (Gps) o;
        return latitude.equals(gps.latitude) && longitude.equals(gps.longitude);
    }

    /**
     * Generates a hash code for the GPS object.
     *
     * @return the hash code of the GPS object
     */
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
