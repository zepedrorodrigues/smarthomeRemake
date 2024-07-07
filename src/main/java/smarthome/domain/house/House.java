package smarthome.domain.house;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Location;

import java.util.Objects;

/**
 * The House class represents a house in the smart home domain.
 * It implements the AggregateRoot interface with HouseName as the identity type.
 * It contains two fields: houseName and location, each represented by their respective classes.
 * The class provides getter methods for these fields and an overridden sameAs method for equality check.
 */
public class House implements AggregateRoot<HouseName> {

    private final HouseName houseName;

    private Location location;

    /**
     * Constructs a House object with the given houseName and location.
     * If any of the parameters are null, it throws an IllegalArgumentException.
     *
     * @param houseName the name of the house
     * @param location  the location of the house
     */
    protected House(HouseName houseName, Location location) {
        if (validParameters(houseName, location)) {
            throw new IllegalArgumentException();
        }
        this.houseName = houseName;
        this.location = location;
    }

    /**
     * Checks if the given parameters are valid.
     * The parameters are considered invalid if either of them is null.
     *
     * @param houseName the name of the house
     * @param location  the location of the house
     * @return true if the parameters are invalid, false otherwise
     */
    private static boolean validParameters(HouseName houseName, Location location) {
        return houseName == null || location == null;
    }

    /**
     * Returns the identity of the house, which is its name.
     *
     * @return the name of the house
     */
    @Override
    public HouseName getIdentity() {
        return houseName;
    }

    /**
     * Returns the location of the house.
     *
     * @return the location of the house
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Configures the location of the house.
     * Sets the actual location of the house to the given location.
     * If the given location is null, it returns null.
     *
     * @param location the location to configure
     * @return the configured location or null if the given location is null
     */
    public Location configLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException();
        }
        this.location = location;
        return this.location;
    }

    /**
     * Checks if the given object is the same as this house.
     * The objects are considered the same if they are of the same class and have the same house name.
     *
     * @param object the object to compare
     * @return true if the object is the same as this house, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        House house = (House) object;
        return houseName.equals(house.houseName);
    }

    /**
     * Returns a hash code value for the house.
     *
     * @return a hash code value for the house
     */
    @Override
    public int hashCode() {
        return Objects.hash(houseName);
    }
}
