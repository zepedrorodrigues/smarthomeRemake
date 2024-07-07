package smarthome.domain.sensor.vo.values;

import java.util.Objects;

/**
 * Represents a wind value with a direction and speed.
 * The direction is represented as an angle in radians, where 0 represents North and values increase in the clockwise
 * direction.
 * The speed is represented as a double value.
 */
public class WindValue implements Value {

    private final double directionValue;
    private final double speedValue;

    /**
     * Constructs a new WindValue with the specified direction and speed.
     *
     * @param direction the direction of the wind in radians. Must be between 0 and 2*PI.
     * @param speed     the speed of the wind. Must be non-negative.
     * @throws IllegalArgumentException if the direction is not between 0 and 2*PI, or if the speed is negative.
     */
    public WindValue(double direction, double speed) {
        if (direction < 0 || direction > 2 * Math.PI || speed < 0) {
            throw new IllegalArgumentException();
        }
        this.directionValue = direction;
        this.speedValue = speed;
    }

    /**
     * Returns a string representation of this WindValue.
     *
     * @return a string representation of this WindValue, which includes the direction and speed values.
     */
    @Override
    public String valueToString() {
        return directionValue + "::" + speedValue;
    }

    /**
     * Determines if this WindValue is equal to another object
     *
     * @param object the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        WindValue windValue = (WindValue) object;
        return Double.compare(directionValue, windValue.directionValue) == 0 && Double.compare(speedValue,
                windValue.speedValue) == 0;
    }

    /**
     * Returns the hash code of the WindValue
     *
     * @return the hash code of the WindValue
     */
    @Override
    public int hashCode() {
        return Objects.hash(directionValue, speedValue);
    }

}