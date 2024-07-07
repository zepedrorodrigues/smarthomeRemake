package smarthome.domain.sensormodel.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The SensorModelNameTest class contains unit tests for the SensorModelName class.
 * It tests the constructor, getter method, and the equals method of the SensorModelName class.
 */
class SensorModelNameTest {

    /**
     * Test to verify that the constructor of the SensorModelName class creates a valid object when
     * provided with a valid sensor model name.
     */
    @Test
    void testConstructorWithValidSensorModelName() {
        // Arrange
        String sensorModelName = "Valid Sensor Model Name";

        // Act
        SensorModelName sensorModel = new SensorModelName(sensorModelName);

        // Assert
        assertNotNull(sensorModel, "SensorModel object should not be null");
    }

    /**
     * Test to verify that the constructor of the SensorModelName class throws an IllegalArgumentException when
     * provided with a null sensor model name.
     */
    @Test
    void testConstructorThrowsExceptionWhenSensorModelNameIsNull() {
        // Arrange
        String sensorModelName = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorModelName(sensorModelName), "SensorModelName " +
                "should not be null");
    }

    /**
     * Test to verify that the constructor of the SensorModelName class throws an IllegalArgumentException when
     * provided with a blank sensor model name.
     */
    @Test
    void testConstructorThrowsExceptionWhenSensorModelNameIsBlank() {
        // Arrange
        String sensorModelName = "";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorModelName(sensorModelName), "SensorModelName " +
                "should not be blank");
    }

    /**
     * Test to verify that the getSensorModelName method of the SensorModelName class returns
     * the correct sensor model name.
     */
    @Test
    void testGetSensorModelName() {
        // Arrange
        String sensorModelName = "Valid Sensor Model Name";
        SensorModelName sensorModel = new SensorModelName(sensorModelName);

        // Act
        String result = sensorModel.getSensorModelName();

        // Assert
        assertEquals(sensorModelName, result, "SensorModelName should be equal" + " to the provided sensor model name");
    }

    /**
     * Test to verify that the equals method of the SensorModelName class returns true when
     * compared with the same object.
     */
    @Test
    void testEqualsReturnsTrueWhenSameObject() {
        // Arrange
        String sensorModelName = "Valid Sensor Model Name";
        SensorModelName sensorModel = new SensorModelName(sensorModelName);

        // Act
        boolean result = sensorModel.equals(sensorModel);

        // Assert
        assertTrue(result, "SensorModelName should be equal to itself");
    }

    /**
     * Test to verify that the equals method of the SensorModelName class returns true when
     * the sensor model names are the same.
     */
    @Test
    void testEqualsReturnsTrueWhenSameSensorModelName() {
        // Arrange
        String sensorModelName = "Valid Sensor Model Name";
        SensorModelName sensorModel1 = new SensorModelName(sensorModelName);
        SensorModelName sensorModel2 = new SensorModelName(sensorModelName);

        // Act
        boolean result = sensorModel1.equals(sensorModel2);

        // Assert
        assertTrue(result, "SensorModelName should be equal to another SensorModelName object with" + " the same " +
                "sensor model name");
    }

    /**
     * Test to verify that the equals method of the SensorModelName class returns false when
     * the sensor model names are different.
     */
    @Test
    void testEqualsReturnsFalseWhenDifferentSensorModelName() {
        // Arrange
        String sensorModelName1 = "Valid Sensor Model Name 1";
        String sensorModelName2 = "Valid Sensor Model Name 2";
        SensorModelName sensorModel1 = new SensorModelName(sensorModelName1);
        SensorModelName sensorModel2 = new SensorModelName(sensorModelName2);

        // Act
        boolean result = sensorModel1.equals(sensorModel2);

        // Assert
        assertFalse(result, "SensorModelName should not be equal to another SensorModelName object with" + " a " +
                "different sensor model name");
    }

    /**
     * Test to verify that the equals method of the SensorModelName class returns false when compared with
     * an object of a different class.
     */
    @Test
    void testEqualsReturnsFalseWhenDifferentObject() {
        // Arrange
        String sensorModelName = "Valid Sensor Model Name";
        SensorModelName sensorModel = new SensorModelName(sensorModelName);

        // Act
        boolean result = sensorModel.equals(new Object());

        // Assert
        assertFalse(result, "SensorModelName should not be equal to an object of a different class");
    }

    /**
     * Test to verify that the equals method of the SensorModelName class returns false when compared with null.
     */
    @Test
    void testEqualsReturnsFalseWhenObjectIsNull() {
        // Arrange
        String sensorModelName = "Valid Sensor Model Name";
        SensorModelName sensorModel = new SensorModelName(sensorModelName);

        // Act
        boolean result = sensorModel.equals(null);

        // Assert
        assertFalse(result, "SensorModelName should not be equal to null");
    }

    /**
     * Test to verify that the hashCode method of the SensorModelName class returns
     * the same hash code for the same sensor model name.
     */
    @Test
    void testHashCodeForSameSensorModelName() {
        // Arrange
        SensorModelName sensorModelName1 = new SensorModelName("Valid Sensor Model Name");
        SensorModelName sensorModelName2 = new SensorModelName("Valid Sensor Model Name");
        // Act
        int hashCode1 = sensorModelName1.hashCode();
        int hashCode2 = sensorModelName2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "Hash code should be equal for the same sensor model name");
    }

    /*
     * Test to verify that the hashCode method of the SensorModelName class returns
     *  different hash codes for different sensor model names.
     */
    @Test
    void testHashCodeForDifferentSensorModelName() {
        // Arrange
        SensorModelName sensorModelName1 = new SensorModelName("Valid Sensor Model Name 1");
        SensorModelName sensorModelName2 = new SensorModelName("Valid Sensor Model Name 2");
        // Act
        int hashCode1 = sensorModelName1.hashCode();
        int hashCode2 = sensorModelName2.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "Hash code should be different for different sensor model names");
    }
}