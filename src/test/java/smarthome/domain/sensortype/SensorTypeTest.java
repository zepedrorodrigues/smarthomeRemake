package smarthome.domain.sensortype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the SensorType class.
 */
class SensorTypeTest {

    SensorTypeName sensorTypeName;
    SensorTypeUnit sensorTypeUnit;
    SensorTypeId sensorTypeId;
    SensorType sensorType;
    SensorTypeFactory sensorTypeFactory;

    /**
     * This method sets up the common test data and state for each test case.
     * It is annotated with @BeforeEach, so it runs before each test case.
     */
    @BeforeEach
    void setUp() {
        sensorTypeName = new SensorTypeName("123");
        sensorTypeUnit = new SensorTypeUnit("ABC");
        sensorTypeId = new SensorTypeId(sensorTypeName.getSensorTypeName() + sensorTypeUnit.getSensorTypeUnit());
        sensorType = new SensorType(sensorTypeName, sensorTypeUnit);
        sensorTypeFactory = new SensorTypeFactoryImpl();
    }

    /**
     * This test case checks if the SensorType constructor does not throw an exception when valid parameters are passed.
     */
    @Test
    void testConstructorWithoutSensorTypeIdValidParametersShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> new SensorType(sensorTypeName, sensorTypeUnit), "Constructor without SensorTypeId " + "should not throw an exception when valid parameters are " + "passed" + ".");
    }

    /**
     * This test case checks if the SensorType constructor throws an exception when the sensor type name is null.
     */
    @Test
    void testConstructorWithoutSensorTypeIDNullSensorTypeNameShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorType(null, sensorTypeUnit), "Constructor " +
                "without" + " SensorTypeId should throw an exception when the sensor type name is null.");
    }

    /**
     * This test case checks if the SensorType constructor throws an exception when the sensor type unit is null.
     */
    @Test
    void testConstructorWithoutSensorTypeIDNullSensorTypeUnitShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorType(sensorTypeName, null), "Constructor " +
                "without" + " SensorTypeId should throw an exception when the sensor type unit is null.");
    }

    /**
     * This test case checks if the SensorType constructor does not throw an exception when valid parameters are passed.
     */
    @Test
    void testConstructorWithSensorTypeIdValidParametersShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> new SensorType(sensorTypeId, sensorTypeName, sensorTypeUnit), "Constructor with " +
                "SensorTypeId should not throw an exception when valid parameters are passed.");
    }

    /**
     * This test case checks if the SensorType constructor throws an exception when the sensor type ID is null.
     */
    @Test
    void testConstructorWithSensorTypeIdNullSensorTypeIdShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorType(null, sensorTypeName, sensorTypeUnit),
                "Constructor with SensorTypeId should throw an exception when the sensor type ID is null.");
    }

    /**
     * This test case checks if the SensorType constructor throws an exception when the sensor type name is null.
     */
    @Test
    void testConstructorWithSensorTypeIdNullSensorTypeNameShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorType(sensorTypeId, null, sensorTypeUnit),
                "Constructor with SensorTypeId should throw an exception when the sensor type name is null.");
    }

    /**
     * This test case checks if the SensorType constructor throws an exception when the sensor type unit is null.
     */
    @Test
    void testConstructorWithSensorTypeIdNullSensorTypeUnitShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorType(sensorTypeId, sensorTypeName, null),
                "Constructor with SensorTypeId should throw an exception when the sensor type unit is null.");
    }

    /**
     * This test case checks if the getIdentity method returns a non-null value.
     */
    @Test
    void testGetIdentity() {
        //Act
        SensorTypeId result = sensorType.getIdentity();
        //Assert
        assertNotNull(result, "The identity should not be null.");

    }

    /**
     * This test case checks if the getSensorTypeName method returns the correct sensor type name.
     */
    @Test
    void testGetSensorTypeName() {
        //Act
        SensorTypeName result = sensorType.getSensorTypeName();
        //Assert
        assertEquals(sensorTypeName, result, "The sensor type name should be the same as the one passed in the " +
                "constructor.");
    }

    /**
     * This test case checks if the getSensorTypeUnit method returns the correct sensor type unit.
     */
    @Test
    void testGetSensorTypeUnit() {
        //Act
        SensorTypeUnit result = sensorType.getSensorTypeUnit();
        //Assert
        assertEquals(sensorTypeUnit, result, "The sensor type unit should be the same as the one passed in the " +
                "constructor.");
    }

    /**
     * This test case checks if the equals method returns true when the same sensor type is passed as a parameter.
     */
    @Test
    void testEqualsForSameSensorType() {
        //Arrange
        SensorType sensorTypeToCompare = new SensorType(sensorTypeName, sensorTypeUnit);
        //Act
        boolean result = sensorType.equals(sensorTypeToCompare);
        //Assert
        assertTrue(result, "The equals method should return true when the same sensor type is passed as a parameter.");

    }

    /**
     * This test case checks if the equals method returns true when the same object is passed as a parameter.
     */
    @Test
    void testEqualsForSameObject() {
        //Act
        boolean result = sensorType.equals(sensorType);
        //Assert
        assertTrue(result, "The equals method should return true when the same object is passed as a parameter.");
    }

    /**
     * This test case checks if the equals method returns false when null is passed as a parameter.
     */
    @Test
    void testEqualsForNullObject() {
        //Act
        boolean result = sensorType.equals(null);
        //Assert
        assertFalse(result, "The equals method should return false when null is passed as a parameter.");
    }

    /**
     * This test case checks if the equals method returns false when an object of a different class is passed as a
     * parameter.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        //Act
        boolean result = sensorType.equals(new Object());
        //Assert
        assertFalse(result,
                "The equals method should return false when an object of a different class is passed as " + "a" + " " + "parameter.");
    }

    /**
     * This test case checks if the equals method returns false when a different sensor type is passed as a parameter.
     */
    @Test
    void testEqualsForDifferentSensorType() {
        //Arrange
        SensorTypeName differentSensorTypeName = new SensorTypeName("differentSensorTypeName");
        SensorTypeUnit differentSensorTypeUnit = new SensorTypeUnit("differentSensorTypeUnit");
        SensorType differentSensorType = new SensorType(differentSensorTypeName, differentSensorTypeUnit);
        //Act
        boolean result = sensorType.equals(differentSensorType);
        //Assert
        assertFalse(result, "The equals method should return false when a different sensor type is passed as a " +
                "parameter.");
    }

    /**
     * This test case checks if the equals method returns false when a sensor type with the same name but different
     * unit is passed as a parameter.
     */
    @Test
    void testEqualsForDifferentSensorTypeWithSameSensorTypeName() {
        //Arrange
        SensorTypeUnit differentSensorTypeUnit = new SensorTypeUnit("differentSensorTypeUnit");
        SensorType differentSensorType = new SensorType(sensorTypeName, differentSensorTypeUnit);
        //Act
        boolean result = sensorType.equals(differentSensorType);
        //Assert
        assertFalse(result, "The equals method should return false when a sensor type with the same name but " +
                "different unit is passed as a parameter.");
    }

    /**
     * This test case checks if the equals method returns false when a sensor type with the same unit but different
     * name is passed as a parameter.
     */
    @Test
    void testHashCodeWhenDifferentObjectIsCompared() {
        //Arrange
        SensorTypeName differentSensorTypeName = new SensorTypeName("differentSensorTypeName");
        SensorType differentSensorType = new SensorType(differentSensorTypeName, sensorTypeUnit);
        //Act
        int result = sensorType.hashCode();
        int result2 = differentSensorType.hashCode();
        //Assert
        assertNotEquals(result, result2,
                "The hashCode method should return different values when different objects " + "are compared.");
    }

    /**
     * This test case checks if the hashCode method returns the same value for two equal sensor types.
     */
    @Test
    void testHashCodeWhenEqualObjectIsCompared() {
        //Arrange
        SensorType sensorTypeToCompare = new SensorType(sensorTypeName, sensorTypeUnit);
        //Act
        int result = sensorType.hashCode();
        int result2 = sensorTypeToCompare.hashCode();
        //Assert
        assertEquals(result, result2, "The hashCode method should return the same value for two equal sensor types.");
    }

    /**
     * This test case checks if the constructor for the DB returns a non-null object.
     */
    @Test
    void testConstructorForDBReturnsNotNull() {
        // Arrange
        SensorType result = sensorTypeFactory.createSensorType(sensorTypeId, sensorTypeName, sensorTypeUnit);
        // Act & Assert
        assertNotNull(result, "The constructor should return a non-null object.");
    }

}
