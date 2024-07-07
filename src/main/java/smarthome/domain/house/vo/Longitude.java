package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * This class represents a Longitude value object.
 * It is used to encapsulate the longitude value of a location in the domain of a smart home.
 */
public class Longitude implements ValueObject {
    // The longitude value
    private final double longitude;

    /**
     * Constructor for Longitude class that takes a double as a parameter
     * and throws an IllegalArgumentException if the value is not between -180 and 180.
     *
     * @param longitude double value representing the longitude
     * @throws IllegalArgumentException if the longitude value is not between -180 and 180
     */
    public Longitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException();
        }
        this.longitude = longitude;
    }

    /**
     * Getter for the longitude value.
     *
     * @return double representing the longitude value
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Method that checks if two Longitude objects are equal by comparing their values.
     *
     * @param o Object to compare with
     * @return boolean true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Longitude longitude1 = (Longitude) o;
        return Double.compare(longitude, longitude1.longitude) == 0;
    }

    /**
     * Method that returns a hash code value for the object.
     *
     * @return int representing the hash code value of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(longitude);
    }
}