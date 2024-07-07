package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.PowerConsumptionValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class contains unit tests for the SensorOfPowerConsumption class.
 * It tests both constructors and all public methods of the class to ensure they function correctly
 * under various conditions.
 */
class SensorOfPowerConsumptionTest {
    private DeviceId deviceId;
    private SensorModelName sensorModelName;
    private SensorId sensorId;
    private SensorOfPowerConsumption sensor;

    /**
     * Sets up common objects for testing. This method is called before each test method,
     * and is used to create fresh instances so that tests are independent of each other.
     */
    @BeforeEach
    void setUp() {
        deviceId = new DeviceId("device-123");
        sensorModelName = new SensorModelName("PowerSensorX");
        sensorId = new SensorId("sensor-456");
        sensor = new SensorOfPowerConsumption(sensorId, deviceId, sensorModelName);
    }

    /**
     * Tests that the constructor does not throw an exception when valid non-null parameters are passed.
     */
    @Test
    void testConstructorWithoutSensorIdValidShouldNotThrowException() {
        assertDoesNotThrow(() -> new SensorOfPowerConsumption(deviceId, sensorModelName), "Constructor should not " +
                "throw an exception for valid parameters.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when the sensorModelName is null.
     */
    @Test
    void testConstructorWithoutSensorIdNullSensorModelNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorOfPowerConsumption(deviceId, null), "Constructor" +
                " should throw an exception for null sensorModelName.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when the deviceId is null.
     */
    @Test
    void testConstructorWithoutSensorIdNullDeviceIdShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorOfPowerConsumption(null, sensorModelName),
                "Constructor should throw an exception for null deviceId.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when both deviceId and sensorModelName are null.
     */
    @Test
    void testConstructorWithoutSensorIdNullSensorModelNameAndDeviceIdShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorOfPowerConsumption(null, null), "Constructor " +
                "should throw an exception for null sensorModelName and deviceId.");
    }

    /**
     * Tests that the constructor does not throw an exception when all parameters including SensorId are valid.
     */
    @Test
    void testConstructorWithSensorIdValidShouldNotThrowException() {
        assertDoesNotThrow(() -> new SensorOfPowerConsumption(sensorId, deviceId, sensorModelName), "Constructor " +
                "should not throw an exception for valid parameters.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when sensorModelName is null,
     * even if SensorId is provided.
     */
    @Test
    void testConstructorWithSensorIdNullSensorModelNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorOfPowerConsumption(sensorId, deviceId, null),
                "Constructor should throw an " + "exception for null sensorModelName.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when deviceId is null,
     * even if SensorId is provided.
     */
    @Test
    void testConstructorWithSensorIdNullDeviceIdShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorOfPowerConsumption(sensorId, null,
                sensorModelName), "Constructor should throw an exception" + " for null deviceId.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when SensorId is null.
     */
    @Test
    void testConstructorWithSensorIdNullSensorIdShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorOfPowerConsumption(null, deviceId,
                sensorModelName), "Constructor should throw an exception" + " for null sensorId.");
    }

    /**
     * Tests that getSensorModelName returns the correct SensorModelName.
     */
    @Test
    void testGetSensorModelNameShouldReturnSensorModelName() {
        assertEquals(sensorModelName, sensor.getSensorModelName(), "getSensorModelName should return the correct " +
                "SensorModelName.");
    }

    /**
     * Tests that getDeviceId returns the correct DeviceId.
     */
    @Test
    void testGetDeviceIdShouldReturnDeviceId() {
        assertEquals(deviceId, sensor.getDeviceId(), "getDeviceId should return the correct DeviceId.");
    }

    /**
     * Tests that getValue returns the correct initial value for power consumption,
     * which should be a PowerConsumptionValue object.
     */
    @Test
    void testGetValueShouldReturnPowerConsumptionValue() {
        assertEquals(new PowerConsumptionValue(15.0), sensor.getValue(), "getValue should return the correct initial " +
                "value for power consumption.");
    }

    /**
     * Tests that getIdentity (or getSensorId) returns the correct SensorId.
     */
    @Test
    void testGetSensorIdShouldReturnSensorId() {
        assertEquals(sensorId, sensor.getIdentity(), "getIdentity should return the correct SensorId.");
    }

    /**
     * Tests that equals returns true when the same object is compared with itself.
     */
    @Test
    void testEqualsWithSameObjectShouldReturnTrue() {
        assertEquals(sensor, sensor);
    }

    /**
     * Tests that equals returns true when two different SensorOfPowerConsumption objects have the same SensorId.
     */
    @Test
    void testEqualsWith2DifferentSensorsOfPowerConsumptionWithSameSensorIdShouldReturnTrue() {
        SensorOfPowerConsumption anotherSensor = new SensorOfPowerConsumption(sensorId, deviceId, sensorModelName);
        assertEquals(sensor, anotherSensor);
    }

    /**
     * Tests that equals returns false when two SensorOfPowerConsumption objects have different SensorIds.
     */
    @Test
    void testEqualsWith2DifferentSensorsOfPowerConsumptionWithDifferentSensorIdShouldReturnFalse() {
        SensorOfPowerConsumption anotherSensor = new SensorOfPowerConsumption(new SensorId("sensor-789"), deviceId,
                sensorModelName);
        assertNotEquals(sensor, anotherSensor);
    }

    /**
     * Tests that equals returns false when compared with null.
     */
    @Test
    void testEqualsWithNullShouldReturnFalse() {
        //Act
        boolean result = sensor.equals(null);
        //Assert
        assertFalse(result);
    }

    /**
     * Tests that equals returns false when compared with an object of a different class.
     */
    @Test
    void testEqualsWithDifferentClassShouldReturnFalse() {
        //Assert
        assertNotEquals(sensor, new Object());
    }

    /**
     * Tests that hashCode returns the same value when called on the same object multiple times.
     */
    @Test
    void testHashCodeWithSameObjectShouldReturnSameHashCode() {
        int expectedHashCode = sensor.hashCode();
        assertEquals(expectedHashCode, sensor.hashCode());
    }

    /**
     * Tests that hashCode returns the same value for two different SensorOfPowerConsumption
     * objects that have the same SensorId.
     */
    @Test
    void testHashCodeWith2DifferentSensorsOfPowerConsumptionWithSameSensorIdShouldReturnSameHashCode() {
        SensorOfPowerConsumption anotherSensor = new SensorOfPowerConsumption(sensorId, deviceId, sensorModelName);
        assertEquals(sensor.hashCode(), anotherSensor.hashCode());
    }

    /**
     * Tests that hashCode returns different values for two SensorOfPowerConsumption objects with different SensorIds.
     */
    @Test
    void testHashCodeWith2DifferentSensorsOfPowerConsumptionWithDifferentSensorIdShouldReturnDifferentHashCode() {
        SensorOfPowerConsumption anotherSensor = new SensorOfPowerConsumption(new SensorId("sensor-789"), deviceId,
                sensorModelName);
        assertNotEquals(sensor.hashCode(), anotherSensor.hashCode());
    }
}
