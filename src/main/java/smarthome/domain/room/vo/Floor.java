package smarthome.domain.room.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * The Floor class represents a floor in a building.
 * It is a value object in the domain-driven design context.
 */
public class Floor implements ValueObject {
    // The floor number
    private final int floor;

    /**
     * Constructs a new Floor object with the given floor number.
     *
     * @param floor the floor number
     */
    public Floor(int floor) {
        this.floor = floor;
    }

    /**
     * Returns the floor number.
     *
     * @return the floor number
     */
    public int getFloor() {
        return floor;
    }
    /**
     * Checks if this Floor object is equal to the specified object.
     * The result is true if and only if the argument is not null and is a Floor object that represents the same floor number as this object.
     *
     * @param o the object to compare this Floor against
     * @return true if the given object represents a Floor equivalent to this floor, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor1 = (Floor) o;
        return floor== floor1.floor;
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getFloor());
    }
}