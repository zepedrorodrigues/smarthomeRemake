package smarthome.domain.sensor.vo.values;

import smarthome.ddd.ValueObject;

/**
 * Represents a value of a sensor.
 */
public interface Value extends ValueObject {

    /**
     * Converts the value to a string representation.
     *
     * @return A string representation of the value.
     */
    String valueToString();
}
