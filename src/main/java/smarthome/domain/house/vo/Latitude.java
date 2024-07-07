package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents the latitude of a location.
 */
public class Latitude implements ValueObject {

    private final double latitude;
    private static final double MIN_LATITUDE = -90;
    private static final double MAX_LATITUDE = 90;

    /**
     * Constructs a Latitude object with the specified latitude value.
     *
     * @param latitude the latitude value
     * @throws IllegalArgumentException if the latitude value is out of range
     */
    public Latitude(double latitude) {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new IllegalArgumentException();
        }
        this.latitude = latitude;
    }

    /**
     * Retrieves the latitude value.
     *
     * @return the latitude value
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Determines if this Latitude object is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Latitude latitude1 = (Latitude) o;
        return Double.compare(latitude, latitude1.latitude) == 0;
    }

    /**
     * Generates a hash code for this Latitude object.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(latitude);
    }
}
