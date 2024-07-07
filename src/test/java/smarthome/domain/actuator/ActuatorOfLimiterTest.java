package smarthome.domain.actuator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuator.vo.IntLimit;
import smarthome.domain.actuator.vo.IntegerValue;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.Value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the ActuatorOfLimiter class.
 * Each method in this class corresponds to a method in the ActuatorOfLimiter class.
 */
class ActuatorOfLimiterTest {

    private DeviceId deviceId;

    private ActuatorId actuatorId;

    private IntLimit lowerLimit;

    private IntLimit upperLimit;

    private ActuatorModelName actuatorModelName;

    private ActuatorOfLimiter actuatorOfLimiter;

    private ActuatorOfLimiterFactory actuatorOfLimiterFactory;

    private ActuatorMap actuatorMap;


    /**
     * This method is executed before each test. It sets up the test environment by initializing
     * the actuatorId, deviceId, actuatorModelName, lowerLimit, upperLimit, and actuatorOfLimiter.
     */
    @BeforeEach
    void setUp() {
        actuatorId = new ActuatorId("actuatorId");
        deviceId = new DeviceId("deviceId");
        actuatorModelName = new ActuatorModelName("actuatorModelName");
        lowerLimit = new IntLimit(10);
        upperLimit = new IntLimit(200);
        actuatorMap = new ActuatorMap(actuatorId, deviceId, actuatorModelName, upperLimit, lowerLimit,
                null, null, null);
        actuatorOfLimiterFactory = new ActuatorOfLimiterFactory();
        actuatorOfLimiter = (ActuatorOfLimiter) actuatorOfLimiterFactory.createActuator(actuatorMap);
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter does not throw an exception
     * when valid parameters are provided and the actuatorId is not specified.
     */
    @Test
    void testConstructorWithoutActuatorIdDoesNotThrowExceptionValidParameters() {
        // Act + Assert
        assertDoesNotThrow(() -> new ActuatorOfLimiter(lowerLimit, upperLimit, actuatorModelName, deviceId),
                "Constructor should not throw an exception when valid parameters are provided.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the lowerLimit is null and the actuatorId is not specified.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionNullLowerLimit() {
        // Arrange
        lowerLimit = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the" + " lowerLimit is null" +
                ".");
    }

    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionNullUpperLimit() {
        // Arrange
        upperLimit = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the" + " upperLimit is null" +
                ".");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the upperLimit is null and the actuatorId is not specified.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionNullActuatorModelName() {
        // Arrange
        actuatorModelName = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the" + " actuatorModelName " +
                "is null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the deviceId is null and the actuatorId is not specified.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionNullDeviceId() {
        // Arrange
        deviceId = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the deviceId is null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the lowerLimit is not less than the upperLimit and the actuatorId is not specified.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionLowerLimitNotLessThanUpperLimit() {
        // Arrange
        lowerLimit = new IntLimit(200);
        upperLimit = new IntLimit(10);
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the" + " lowerLimit is not " +
                "less than the upperLimit.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter does not throw an exception
     * when valid parameters are provided and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdDoesNotThrowExceptionValidParameters() {
        // Act + Assert
        assertDoesNotThrow(() -> new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit, actuatorModelName,
                deviceId), "Constructor should not throw an exception when" + " valid parameters are provided.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the lowerLimit and upperLimit are the same and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForSameLimits() {
        // Arrange
        lowerLimit = new IntLimit(10);
        upperLimit = new IntLimit(10);
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the" + " lowerLimit and " +
                "upperLimit are the same.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter does not throw an exception
     * when the actuatorId is null and the other parameters are valid.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionNullActuatorId() {
        // Arrange
        ActuatorId nullActuatorId = null;
        // Act + Assert
        assertDoesNotThrow(() -> new ActuatorOfLimiter(nullActuatorId, lowerLimit, upperLimit, actuatorModelName,
                deviceId), "Constructor should not throw an exception when" + " the actuatorId is null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the lowerLimit is null and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullDeviceId(){
        //Arrange
        DeviceId nullDeviceId = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit,
                actuatorModelName, nullDeviceId), "Constructor should throw an exception when the" + " deviceId is " +
                "null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the actuatorModelName is null and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullActuatorModelName(){
        //Arrange
        ActuatorModelName nullActuatorModelName = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit,
                nullActuatorModelName, deviceId), "Constructor should throw an exception when the" + " " +
                "actuatorModelName is null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the lowerLimit is null and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullLowerLimit(){
        //Arrange
        IntLimit nullLowerLimit = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(actuatorId, nullLowerLimit,
                upperLimit, actuatorModelName, deviceId), "Constructor should throw an exception when the" + " " +
                "lowerLimit is null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the upperLimit is null and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullUpperLimit(){
        //Arrange
        IntLimit nullUpperLimit = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(actuatorId, lowerLimit,
                nullUpperLimit, actuatorModelName, deviceId), "Constructor should throw an exception when the" + " " +
                "upperLimit is null.");
    }

    /**
     * This test verifies that the constructor of ActuatorOfLimiter throws an IllegalArgumentException
     * when the lowerLimit is not less than the upperLimit and the actuatorId is specified.
     */
    @Test
    void testConstructorWithActuatorIdThrowsExceptionForInvalidLimits(){
        //Arrange
        lowerLimit = new IntLimit(200);
        upperLimit = new IntLimit(10);
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit,
                actuatorModelName, deviceId), "Constructor should throw an exception when the" + " lowerLimit is not " +
                "less than the upperLimit.");
    }

    /**
     * This test verifies that the getIdentity method of ActuatorOfLimiter returns the correct ActuatorId.
     */
    @Test
    void testGetIdentity() {
        //Arrange
        ActuatorId expected = actuatorId;
        // Act
        ActuatorId result = actuatorOfLimiter.getIdentity();
        // Assert
        assertEquals(expected, result, "getIdentity should return the correct ActuatorId.");
    }

    /**
     * This test verifies that the getIdentity method of ActuatorOfLimiter returns a non-null ActuatorId
     * when the ActuatorOfLimiter is created without specifying an ActuatorId.
     */
    @Test
    void testGetIdentityForRandomActuatorId(){
        //Arrange
        ActuatorOfLimiter actuatorOfLimiter = new ActuatorOfLimiter(lowerLimit, upperLimit, actuatorModelName, deviceId);
        //Act
        ActuatorId result = actuatorOfLimiter.getIdentity();
        //Assert
        assertNotNull(result, "getIdentity should return a non-null ActuatorId.");
    }

    /**
     * This test verifies that the getActuatorModelName method of ActuatorOfLimiter returns the correct ActuatorModelName.
     */
    @Test
    void testGetActuatorModelName() {
        //Arrange
        ActuatorModelName expected = actuatorModelName;
        // Act
        ActuatorModelName result = actuatorOfLimiter.getActuatorModelName();
        // Assert
        assertEquals(expected, result, "getActuatorModelName should return the correct ActuatorModelName.");
    }

    /**
     * This test verifies that the getDeviceId method of ActuatorOfLimiter returns the correct DeviceId.
     */
    @Test
    void testGetDeviceId() {
        //Arrange
        DeviceId expected = deviceId;
        // Act
        DeviceId result = actuatorOfLimiter.getDeviceId();
        // Assert
        assertEquals(expected, result, "getDeviceId should return the correct DeviceId.");
    }

    /**
     * This test verifies that the getLowerLimit method of ActuatorOfLimiter returns the correct IntLimit.
     */
    @Test
    void testGetLowerLimit() {
        //Arrange
        IntLimit expected = lowerLimit;
        // Act
        IntLimit result = actuatorOfLimiter.getLowerLimit();
        // Assert
        assertEquals(expected, result, "getLowerLimit should return the correct IntLimit.");
    }

    /**
     * This test verifies that the getUpperLimit method of ActuatorOfLimiter returns the correct IntLimit.
     */
    @Test
    void testGetUpperLimit() {
        //Arrange
        IntLimit expected = upperLimit;
        // Act
        IntLimit result = actuatorOfLimiter.getUpperLimit();
        // Assert
        assertEquals(expected, result, "getUpperLimit should return the correct IntLimit.");
    }

    /**
     * This test verifies that the equals method of ActuatorOfLimiter returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        //Act
        boolean result = actuatorOfLimiter.equals(actuatorOfLimiter);
        //Assert
        assertTrue(result, "equals should return true for the same object.");
    }

    /**
     * This test verifies that the equals method of ActuatorOfLimiter returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        ActuatorOfLimiter nullActuatorOfLimiter = null;
        //Act
        boolean result = actuatorOfLimiter.equals(nullActuatorOfLimiter);
        //Assert
        assertFalse(result, "equals should return false for a null object.");
    }

    /**
     * This test verifies that the equals method of ActuatorOfLimiter returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        //Act
        boolean result = actuatorOfLimiter.equals(new Object());
        //Assert
        assertFalse(result, "equals should return false for a different class object.");
    }

    /**
     * This test verifies that the equals method of ActuatorOfLimiter returns false for different objects with the same actuatorId.
     */
    @Test
    void testEqualsForDifferentActuatorId() {
        //Arrange
        ActuatorOfLimiter differentActuatorOfLimiter = new ActuatorOfLimiter(new ActuatorId("differentActuatorId"),
                lowerLimit, upperLimit, actuatorModelName, deviceId);
        //Act
        boolean result = actuatorOfLimiter.equals(differentActuatorOfLimiter);
        //Assert
        assertFalse(result, "equals should return false for different objects with the same actuatorId.");
    }

    /**
     * This test verifies that the equals method of ActuatorOfLimiter returns true for different objects with the same actuatorId.
     */
    @Test
    void testEqualsForSameActuatorId() {
        //Arrange
        ActuatorOfLimiter sameActuatorOfLimiter = new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit,
                actuatorModelName, deviceId);
        //Act
        boolean result = actuatorOfLimiter.equals(sameActuatorOfLimiter);
        //Assert
        assertTrue(result, "equals should return true for different objects with the same actuatorId.");
    }

    /**
     * This test verifies that the hashCode method of ActuatorOfLimiter returns the same hash code for the same object.
     */
    @Test
    void testHashCodeForDifferentActuatorId() {
        // Arrange
        ActuatorOfLimiter differentActuatorOfLimiter = new ActuatorOfLimiter(new ActuatorId("differentActuatorId"),
                lowerLimit, upperLimit, actuatorModelName, deviceId);
        // Act
        int hashCode1 = actuatorOfLimiter.hashCode();
        int hashCode2 = differentActuatorOfLimiter.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "hashCode should return the same hash code for the same object.");
    }

    /**
     * This test verifies that the hashCode method of ActuatorOfLimiter returns the same hash code for the same object.
     */
    @Test
    void testHashCodeForSameActuatorId() {
        // Arrange
        ActuatorOfLimiter sameActuatorOfLimiter = new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit,
                actuatorModelName, deviceId);
        // Act
        int hashCode1 = actuatorOfLimiter.hashCode();
        int hashCode2 = sameActuatorOfLimiter.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "hashCode should return the same hash code for the same object.");
    }

    /**
     * This test verifies that the hashCode method of ActuatorOfLimiter returns the same hash code for the same object.
     */
    @Test
    void testOperateWhitoutSettingsLimitsInvalid() {
        // Arrange
        Value expectedValue = new IntegerValue(10);
        IntLimit lowerLimit = new IntLimit(1);
        IntLimit upperLimit = new IntLimit(50);
        ActuatorOfLimiter actuatorOfLimiter = new ActuatorOfLimiter(lowerLimit, upperLimit, actuatorModelName, deviceId);
        // Act
        Value result = actuatorOfLimiter.operate(expectedValue);
        // Assert
        assertEquals(expectedValue, result, "operate should return the same value when" + " the limits are not set " +
                "and the value is valid.");
    }

    /**
     * This test verifies that the operate method of ActuatorOfLimiter returns the expected value when
     * the value is within the set limits.
     */
    @Test
    void testOperateWhitoutSettingsLimitsValid() {
        // Arrange
        Value expectedValue = new IntegerValue(10);
        IntLimit lowerLimit = new IntLimit(1);
        IntLimit upperLimit = new IntLimit(50);
        ActuatorOfLimiter actuatorOfLimiter = new ActuatorOfLimiter(lowerLimit, upperLimit, actuatorModelName, deviceId);
        // Act
        Value result = actuatorOfLimiter.operate(expectedValue);
        // Assert
        assertEquals(expectedValue, result, "operate should return the same value when" + " the limits are not set " +
                "and the value is valid.");
    }

    /**
     * This test verifies that the operate method of ActuatorOfLimiter returns the expected value when
     * the value is equal to the upper limit.
     */
    @Test
    void testOperateValidValueUpperLimit() {
        // Arrange
        Value expectedValue = new IntegerValue(200);
        // Act
        Value result = actuatorOfLimiter.operate(expectedValue);
        // Assert
        assertEquals(expectedValue, result, "operate should return the same value when" + " the limits are not set " +
                "and the value is valid.");
    }

    /**
     * This test verifies that the operate method of ActuatorOfLimiter returns the expected value when
     * the value is equal to the lower limit.
     */
    @Test
    void testOperateValidValueLowerLimit() {
        // Arrange
        Value expectedValue = new IntegerValue(10);
        // Act
        Value result = actuatorOfLimiter.operate(expectedValue);
        // Assert
        assertEquals(expectedValue, result, "operate should return the same value when" + " the limits are not set " +
                "and the value is valid.");
    }

    /**
     * This test verifies that the operate method of ActuatorOfLimiter returns null when the value
     * is above the upper limit.
     */
    @Test
    void testOperateInvalidValueUpperLimit() {
        // Arrange
        Value expectedValue = new IntegerValue(201);
        // Act
        Value result = actuatorOfLimiter.operate(expectedValue);
        // Assert
        assertNull(result, "operate should return null when the limits are not set and the value is invalid.");
    }

    /**
     * This test verifies that the operate method of ActuatorOfLimiter returns null when the value
     * is below the lower limit.
     */
    @Test
    void testOperateInvalidValueLowerLimit() {
        // Arrange
        Value expectedValue = new IntegerValue(9);
        // Act
        Value result = actuatorOfLimiter.operate(expectedValue);
        // Assert
        assertNull(result, "operate should return null when the limits are not set and the value is invalid.");
    }













}