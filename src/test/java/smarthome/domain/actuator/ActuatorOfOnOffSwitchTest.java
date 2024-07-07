package smarthome.domain.actuator;

import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuator.vo.LoadState;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.OnOffValue;
import smarthome.domain.sensor.vo.values.Value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the ActuatorOfOnOffSwitch class.
 */
class ActuatorOfOnOffSwitchTest {

    /**
     * Test case for valid ActuatorOfOnOffSwitch constructor.
     */
    @Test
    void testConstructorWithoutActuatorIdDoesNotThrowExceptionForValidParameters() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        //Act & Assert
        assertDoesNotThrow(() -> new ActuatorOfOnOffSwitch(deviceId, actuatorModelName), "Constructor should not " +
                "throw an exception for valid parameters.");
    }

    /**
     * Test case for ActuatorOfOnOffSwitch constructor with null DeviceId.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullDeviceId() {
        //Arrange
        DeviceId invalidDeviceId = null;
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfOnOffSwitch(invalidDeviceId,
                actuatorModelName), "Constructor should throw an exception for null DeviceId.");
    }

    /**
     * Test case for ActuatorOfOnOffSwitch constructor with null ActuatorModelName.
     */
    @Test
    void testConstructorWithoutActuatorIdThrowsExceptionForNullActuatorModelName() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName invalidActuatorModelName = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfOnOffSwitch(deviceId,
                invalidActuatorModelName), "Constructor should throw an exception for null ActuatorModelName.");
    }

    @Test
    void testConstructorWithActuatorIdDoesNotThrowExceptionForValidParameters() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        //Act & Assert
        assertDoesNotThrow(() -> new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName),
                "Constructor " + "should not throw an exception for valid parameters.");
    }

    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullActuatorId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId invalidActuatorId = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfOnOffSwitch(invalidActuatorId, deviceId,
                actuatorModelName), "Constructor should throw an exception for null ActuatorId.");
    }

    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullDeviceId() {
        //Arrange
        DeviceId invalidDeviceId = null;
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfOnOffSwitch(actuatorId, invalidDeviceId,
                actuatorModelName), "Constructor should throw an exception for null DeviceId.");
    }

    @Test
    void testConstructorWithActuatorIdThrowsExceptionForNullActuatorModelName() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName invalidActuatorModelName = null;
        ActuatorId actuatorId = new ActuatorId("1");
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfOnOffSwitch(actuatorId, deviceId,
                invalidActuatorModelName), "Constructor should throw an exception for null ActuatorModelName.");
    }


    /**
     * Test case for operate method with valid value that turns on the switch.
     */
    @Test
    void testOperateWithValidValueTurnsOn() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        Value value = new OnOffValue(true);
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
        // Act
        Value result = onOffSwitch.operate(value);
        // Assert
        assertTrue(result instanceof OnOffValue, "The result should be an instance of OnOffValue.");
    }

    /**
     * Test case for operate method with valid value that turns off the switch.
     */
    @Test
    void testOperateWithValidValueTurnsOff() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        Value value = new OnOffValue(false);
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
        // Act
        Value result = onOffSwitch.operate(value);
        // Assert
        assertTrue(result instanceof OnOffValue, "The result should be an instance of OnOffValue.");
    }

    /**
     * Test case for operate method with invalid value.
     */
    @Test
    public void testOperateWithInvalidValue() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("testActuatorId");
        DeviceId deviceId = new DeviceId("testDeviceId");
        ActuatorModelName actuatorModelName = new ActuatorModelName("testModelName");
        ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        // Act
        Value invalidValue = new Value() {
            @Override
            public String valueToString() {
                return "invalid";
            }
        };
        // Assert
        assertThrows(IllegalArgumentException.class, () -> actuator.operate(invalidValue),
                "Operate should throw an " + "illegal argument exception for invalid value.");
    }

    /**
     * Test case for operate method with valid value that turns on the switch.
     */
    @Test
    void testOperateParsesValueCorrectly() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
        Value trueValue = new OnOffValue(true);
        // Act
        Value result = actuator.operate(trueValue);
        // Assert
        assertEquals(trueValue, result, "Operate should return the same value as the input value.");
    }

    /**
     * Test case for getIdentity method.
     */
    @Test
    void testGetIdentityReturnsCorrectActuatorId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        ActuatorId result = onOffSwitch.getIdentity();
        assertEquals(actuatorId, result, "GetIdentity should return the correct ActuatorId.");
    }


    /**
     * Test case for getActuatorModelName method.
     */
    @Test
    void testGetActuatorModelName() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        ActuatorModelName result = onOffSwitch.getActuatorModelName();
        //Assert
        assertEquals(actuatorModelName, result, "GetActuatorModelName should return the correct ActuatorModelName.");
    }

    /**
     * Test case for getDeviceId method.
     */
    @Test
    void testGetDeviceId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        DeviceId result = onOffSwitch.getDeviceId();

        //Assert
        assertEquals(deviceId, result, "GetDeviceId should return the correct DeviceId.");
    }

    /**
     * Test case for getLoadState method.
     * Test case for getLoadState method with the constructor that has ActuatorId.
     */
    @Test
    void testGetLoadStateConstructorWithActuatorIdInitialLoadState() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        LoadState expectedInitialLoadState = new LoadState(false);
        // Act
        LoadState initialLoadState = onOffSwitch.getLoadState();
        // Assert
        assertEquals(expectedInitialLoadState, initialLoadState, "GetLoadState should return the correct initial " +
                "LoadState.");
    }

    /**
     * Test case for getLoadState method.
     * Test case for getLoadState method with the constructor that does not have ActuatorId.
     */
    @Test
    void testGetLoadStateConstructorWithoutActuatorIdInitialLoadState() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
        LoadState expectedInitialLoadState = new LoadState(false);
        // Act
        LoadState initialLoadState = onOffSwitch.getLoadState();
        // Assert
        assertEquals(expectedInitialLoadState, initialLoadState, "GetLoadState should return the correct initial " +
                "LoadState.");
    }

    /**
     * Test case for operate method with valid value.
     */
    @Test
    void testOperateDoesNotThrowExceptionForValidValue() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        Value validValue = new OnOffValue(true);
        //Act & Assert
        assertDoesNotThrow(() -> onOffSwitch.operate(validValue),
                "Operate should not throw an exception for valid " + "value.");
    }

    /**
     * Test to check the operate method of ActuatorOfOnOffSwitch when the value is false.
     * It checks if the method returns an instance of OnOffValue with a value of false.
     */
    @Test
    void testOperateReturnsFalseOnOffValueWhenInputIsFalse() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
        Value falseValue = new OnOffValue(false);
        // Act
        Value result = actuator.operate(falseValue);
        // Assert
        assertEquals(falseValue, result);

    }

    /**
     * Test to check the operate method of ActuatorOfOnOffSwitch when the value is true.
     * It checks if the method returns an instance of OnOffValue with a value of true.
     */
    @Test
    void testOperateReturnsTrueOnOffValueWhenInputIsTrue() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
        Value trueValue = new OnOffValue(true);
        // Act
        Value result = actuator.operate(trueValue);
        // Assert
        assertEquals(trueValue, result);
    }

    /**
     * Test case for equals method with the same object.
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        boolean result = onOffSwitch.equals(onOffSwitch);
        //Assert
        assertTrue(result, "equals should return true for the same object.");
    }

    /**
     * Test case for equals method with null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        ActuatorOfOnOffSwitch nullOnOffSwitch = null;
        //Act
        boolean result = onOffSwitch.equals(nullOnOffSwitch);
        //Assert
        assertFalse(result, "equals should return false for null object.");

    }

    /**
     * Test case for equals method with different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        boolean result = onOffSwitch.equals(new Object());
        //Assert
        assertFalse(result, "equals should return false for different class object.");
    }


    /**
     * Test case for equals method with different ActuatorId.
     */
    @Test
    void testEqualsForDifferentActuatorId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorId differentActuatorId = new ActuatorId("2");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        ActuatorOfOnOffSwitch differentOnOffSwitch = new ActuatorOfOnOffSwitch(differentActuatorId, deviceId,
                actuatorModelName);
        //Act
        boolean result = onOffSwitch.equals(differentOnOffSwitch);
        //Assert
        assertFalse(result, "equals should return false for different ActuatorId.");
    }

    /**
     * Test case for equals method with the same ActuatorId.
     */
    @Test
    void testEqualsForSameActuatorId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        ActuatorOfOnOffSwitch sameOnOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        boolean result = onOffSwitch.equals(sameOnOffSwitch);
        //Assert
        assertTrue(result, "equals should return true for the same ActuatorId.");
    }

    /**
     * Test case for hashCode method with different ActuatorId.
     */
    @Test
    void testHashCodeForDifferentActuatorId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorId differentActuatorId = new ActuatorId("2");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        ActuatorOfOnOffSwitch differentOnOffSwitch = new ActuatorOfOnOffSwitch(differentActuatorId, deviceId,
                actuatorModelName);
        //Act
        int result = onOffSwitch.hashCode();
        int differentResult = differentOnOffSwitch.hashCode();
        //Assert
        assertNotEquals(result, differentResult, "hashCode should return different values for different ActuatorId.");
    }

    /**
     * Test case for hashCode method with the same ActuatorId.
     */
    @Test
    void testHashCodeForSameActuatorId() {
        //Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfOnOffSwitch onOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        ActuatorOfOnOffSwitch sameOnOffSwitch = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        //Act
        int result = onOffSwitch.hashCode();
        int sameResult = sameOnOffSwitch.hashCode();
        //Assert
        assertEquals(result, sameResult, "hashCode should return the same value for the same ActuatorId.");
    }

    /**
     * Test case for ActuatorOfOnOffSwitch constructor with ActuatorId and Factory.
     * This test verifies that the constructor of the ActuatorOfOnOffSwitch class and the createActuator method of
     * the ActuatorOfOnOffSwitchFactory class do not throw an exception when provided with valid parameters.
     */
    @Test
    void testConstructorWithActuatorIdAndFactoryDoesNotThrowExceptionForValidParameters() {
        //Arrange
        ActuatorOfOnOffSwitchFactory factory = new ActuatorOfOnOffSwitchFactory();
        ActuatorId actuatorId = new ActuatorId("1");
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorMap map = new ActuatorMap(actuatorId, deviceId, actuatorModelName, null, null, null, null, null);
        //Act
        ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        ActuatorOfOnOffSwitch result = (ActuatorOfOnOffSwitch) factory.createActuator(map);
        //Assert
        assertEquals(actuator, result, "The constructor and the factory method should create equivalent objects when " +
                "provided with valid parameters.");
    }
}
