package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents a location in the smart home domain.
 * A location is defined by an address and GPS coordinates.
 * This class is a value object in the domain-driven design context.
 */
public class Location implements ValueObject {

    private final Address address;

    private final Gps gps;

    /**
     * Constructs a new Location with the given address and GPS coordinates.
     *
     * @param address the address of the location
     * @param gps     the GPS coordinates of the location
     * @throws IllegalArgumentException if either address or gps is null
     */
    public Location(Address address, Gps gps) {
        if (address == null || gps == null)
            throw new IllegalArgumentException();
        this.address = address;
        this.gps = gps;
    }

    /**
     * Returns the address of this location.
     *
     * @return the address of this location
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns the GPS coordinates of this location.
     *
     * @return the GPS coordinates of this location
     */
    public Gps getGps() {
        return gps;
    }

    /**
     * Checks if this location is equal to the given object.
     * Two locations are considered equal if they have the same address and GPS coordinates.
     *
     * @param o the object to compare with this location
     * @return true if this location is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return address.equals(location.address) && gps.equals(location.gps);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as
     * those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(address, gps);
    }
}
