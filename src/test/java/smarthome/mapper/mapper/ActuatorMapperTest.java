package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorOfDecimalLimiter;
import smarthome.domain.actuator.ActuatorOfLimiter;
import smarthome.domain.actuator.vo.*;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorIdDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ActuatorMapperTest class contains the tests for the ActuatorMapper class.
 */
class ActuatorMapperTest {

    ActuatorMapper actuatorMapper;
    ActuatorDTO actuatorDTO;
    Actuator actuator;
    ActuatorId actuatorId;
    DeviceId deviceId;
    ActuatorModelName actuatorModelName;

    /**
     * This method sets up the necessary objects for the tests.
     */
    @BeforeEach
    void setUp() {
        actuatorMapper = new ActuatorMapper();

        actuatorDTO = mock(ActuatorDTO.class);
        when(actuatorDTO.getActuatorId()).thenReturn("1");
        when(actuatorDTO.getDeviceId()).thenReturn("3");
        when(actuatorDTO.getActuatorModelName()).thenReturn("actuatorModelName");
        when(actuatorDTO.getIntegerUpperLimit()).thenReturn(10);
        when(actuatorDTO.getIntegerLowerLimit()).thenReturn(1);
        when(actuatorDTO.getDoubleUpperLimit()).thenReturn(10.0);
        when(actuatorDTO.getDoubleLowerLimit()).thenReturn(1.0);
        when(actuatorDTO.getDoubleLimitPrecision()).thenReturn(1);

        actuator = mock(Actuator.class);

        actuatorId = mock(ActuatorId.class);
        when(actuatorId.getActuatorId()).thenReturn("1");

        deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("3");

        actuatorModelName = mock(ActuatorModelName.class);
        when(actuatorModelName.getActuatorModelName()).thenReturn("actuatorModelName");

        when(actuator.getIdentity()).thenReturn(actuatorId);
        when(actuator.getDeviceId()).thenReturn(deviceId);
        when(actuator.getActuatorModelName()).thenReturn(actuatorModelName);


    }

    /**
     * Tests that the toActuatorMap method returns an actuatorMap with the correct actuatorId.
     */
    @Test
    void testToActuatorMapActuatorId() {
        //Arrange
        String expectedActuatorId = "1";
        //Act
        ActuatorMap result = actuatorMapper.toActuatorMap(actuatorDTO);
        //Assert
        assertEquals(expectedActuatorId, result.getActuatorId().getActuatorId(),
                "The actuator id should be the expected one");
    }

    /**
     * Tests that the toActuatorMap method returns an actuatorMap with the correct deviceId.
     */
    @Test
    void testToActuatorMapDeviceId() {
        //Arrange
        String expectedDeviceId = "3";
        //Act
        ActuatorMap result = actuatorMapper.toActuatorMap(actuatorDTO);
        //Assert
        assertEquals(expectedDeviceId, result.getDeviceId().getIdentity(),
                "The device id should be the expected one");
    }

    /**
     * Tests that the toActuatorMap method returns an actuatorMap with the correct actuatorModelName.
     */
    @Test
    void testToActuatorMapActuatorModelName() {
        //Arrange
        String expectedActuatorModelName = "actuatorModelName";
        //Act
        ActuatorMap result = actuatorMapper.toActuatorMap(actuatorDTO);
        //Assert
        assertEquals(expectedActuatorModelName, result.getActuatorModelName().getActuatorModelName(),
                "The actuator model name should be the expected one");
    }

    /**
     * Tests that the toActuatorId method returns the correct ActuatorId.
     */
    @Test
    void testToActuatorId() {
        //Arrange
        String expected = "1";
        //Act
        ActuatorId result = actuatorMapper.toActuatorId(actuatorDTO);
        //Assert
        assertEquals(expected, result.getActuatorId(), "The actuator id should be the expected one");
    }

    /**
     * Tests that the toActuatorId method returns null when the actuator id is null.
     */
    @Test
    void testToActuatorIdNullActuatorId() {
        //Arrange
        when(actuatorDTO.getActuatorId()).thenReturn(null);
        //Act
        ActuatorId result = actuatorMapper.toActuatorId(actuatorDTO);
        //Assert
        assertNull(result, "The actuator id should be null");
    }

    /**
     * Tests that the toDeviceId method returns the correct DeviceId.
     */
    @Test
    void testToDeviceId() {
        //Arrange
        String expected = "3";
        //Act
        DeviceId result = actuatorMapper.toDeviceId(actuatorDTO);
        //Assert
        assertEquals(expected, result.getIdentity(), "The device id should be the expected one");
    }

    /**
     * Tests that the toDeviceId method returns null when the device id is null.
     */
    @Test
    void testToDeviceIdNullDeviceId() {
        //Arrange
        when(actuatorDTO.getDeviceId()).thenReturn(null);
        //Act
        DeviceId result = actuatorMapper.toDeviceId(actuatorDTO);
        //Assert
        assertNull(result, "The device id should be null");
    }

    /**
     * Tests that the toActuatorModelName method returns the correct ActuatorModelName.
     */
    @Test
    void testToActuatorModelName() {
        //Arrange
        String expected = "actuatorModelName";
        //Act
        ActuatorModelName result = actuatorMapper.toActuatorModelName(actuatorDTO);
        //Assert
        assertEquals(expected, result.getActuatorModelName(), "The actuator model name should be the expected one");
    }

    /**
     * Tests that the toActuatorModelName method returns null when the actuator model name is null.
     */
    @Test
    void testToActuatorModelNameNullActuatorModelName() {
        //Arrange
        when(actuatorDTO.getActuatorModelName()).thenReturn(null);
        //Act
        ActuatorModelName result = actuatorMapper.toActuatorModelName(actuatorDTO);
        //Assert
        assertNull(result, "The actuator model name should be null");
    }

    /**
     * Tests that the toIntUpperLimit method returns the correct IntLimit.
     */
    @Test
    void testToIntUpperLimit() {
        //Arrange
        int expected = 10;
        //Act
        IntLimit result = actuatorMapper.toIntUpperLimit(actuatorDTO);
        //Assert
        assertEquals(expected, result.getIntLimit(), "The int limit should be the expected one");
    }

    /**
     * Tests that the toIntUpperLimit method returns null when the integer upper limit is null.
     */
    @Test
    void testToIntUpperLimitNullIntegerUpperLimit() {
        //Arrange
        when(actuatorDTO.getIntegerUpperLimit()).thenReturn(null);
        //Act
        IntLimit result = actuatorMapper.toIntUpperLimit(actuatorDTO);
        //Assert
        assertNull(result, "The int limit should be null");
    }

    /**
     * Tests that the toIntLowerLimit method returns the correct IntLimit.
     */
    @Test
    void testToIntLowerLimit() {
        //Arrange
        int expected = 1;
        //Act
        IntLimit result = actuatorMapper.toIntLowerLimit(actuatorDTO);
        //Assert
        assertEquals(expected, result.getIntLimit(), "The int limit should be the expected one");
    }

    /**
     * Tests that the toIntLowerLimit method returns null when the integer lower limit is null.
     */
    @Test
    void testToIntLowerLimitNullIntegerLowerLimit() {
        //Arrange
        when(actuatorDTO.getIntegerLowerLimit()).thenReturn(null);
        //Act
        IntLimit result = actuatorMapper.toIntLowerLimit(actuatorDTO);
        //Assert
        assertNull(result, "The int limit should be null");
    }

    /**
     * Tests that the toDecimalUpperLimit method returns the correct DecimalLimit.
     */
    @Test
    void testToDecimalUpperLimit() {
        //Arrange
        double expected = 10.0;
        //Act
        DecimalLimit result = actuatorMapper.toDecimalUpperLimit(actuatorDTO);
        //Assert
        assertEquals(expected, result.getDecimalLimit(), "The decimal limit should be the expected one");
    }

    /**
     * Tests that the toDecimalUpperLimit method returns null when the double upper limit is null.
     */
    @Test
    void testToDecimalUpperLimitNullDoubleUpperLimit() {
        //Arrange
        when(actuatorDTO.getDoubleUpperLimit()).thenReturn(null);
        //Act
        DecimalLimit result = actuatorMapper.toDecimalUpperLimit(actuatorDTO);
        //Assert
        assertNull(result, "The decimal limit should be null");
    }

    /**
     * Tests that the toDecimalLowerLimit method returns the correct DecimalLimit.
     */
    @Test
    void testToDecimalLowerLimit() {
        //Arrange
        double expected = 1.0;
        //Act
        DecimalLimit result = actuatorMapper.toDecimalLowerLimit(actuatorDTO);
        //Assert
        assertEquals(expected, result.getDecimalLimit(), "The decimal limit should be the expected one");
    }

    /**
     * Tests that the toDecimalLowerLimit method returns null when the double lower limit is null.
     */
    @Test
    void testToDecimalLowerLimitNullDoubleLowerLimit() {
        //Arrange
        when(actuatorDTO.getDoubleLowerLimit()).thenReturn(null);
        //Act
        DecimalLimit result = actuatorMapper.toDecimalLowerLimit(actuatorDTO);
        //Assert
        assertNull(result, "The decimal limit should be null");
    }

    /**
     * Tests that the toPrecision method returns the correct Precision.
     */
    @Test
    void testToPrecision() {
        //Arrange
        int expected = 1;
        //Act
        Precision result = actuatorMapper.toPrecision(actuatorDTO);
        //Assert
        assertEquals(expected, result.getPrecision(), "The precision should be the expected one");
    }

    /**
     * Tests that the toPrecision method returns null when the double limit precision is null.
     */
    @Test
    void testToPrecisionNullPrecision() {
        //Arrange
        when(actuatorDTO.getDoubleLimitPrecision()).thenReturn(null);
        //Act
        Precision result = actuatorMapper.toPrecision(actuatorDTO);
        //Assert
        assertNull(result, "The precision should be null");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct actuator model name.
     */
    @Test
    void testActuatorToDTOActuatorDefaultActuatorModelName() {
        //Arrange
        String expectedActuatorModelName = "actuatorModelName";
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuator);
        //Assert
        assertEquals(expectedActuatorModelName, result.getActuatorModelName(),
                "The actuator model name should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct actuator id.
     */
    @Test
    void testActuatorToDTOActuatorDefaultActuatorId() {
        //Arrange
        String expectedActuatorId = "1";
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuator);
        //Assert
        assertEquals(expectedActuatorId, result.getActuatorId(), "The actuator id should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct device id for the ActuatorOfLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfLimiterActuatorId() {
        //Arrange
        ActuatorOfLimiter actuatorOfLimiter = mock(ActuatorOfLimiter.class);

        IntLimit integerUpperLimit = mock(IntLimit.class);
        when(integerUpperLimit.getIntLimit()).thenReturn(10);

        IntLimit integerLowerLimit = mock(IntLimit.class);
        when(integerLowerLimit.getIntLimit()).thenReturn(1);

        when(actuatorOfLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfLimiter.getUpperLimit()).thenReturn(integerUpperLimit);
        when(actuatorOfLimiter.getLowerLimit()).thenReturn(integerLowerLimit);

        String expectedActuatorId = "1";
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfLimiter);
        //Assert
        assertEquals(expectedActuatorId, result.getActuatorId(), "The actuator id should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct integer upper limit
     * for the ActuatorOfLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfLimiterUpperLimit() {
        //Arrange
        ActuatorOfLimiter actuatorOfLimiter = mock(ActuatorOfLimiter.class);

        IntLimit integerUpperLimit = mock(IntLimit.class);
        when(integerUpperLimit.getIntLimit()).thenReturn(10);

        IntLimit integerLowerLimit = mock(IntLimit.class);
        when(integerLowerLimit.getIntLimit()).thenReturn(1);

        when(actuatorOfLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfLimiter.getUpperLimit()).thenReturn(integerUpperLimit);
        when(actuatorOfLimiter.getLowerLimit()).thenReturn(integerLowerLimit);

        int expectedUpperLimit = 10;
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfLimiter);
        //Assert
        assertEquals(expectedUpperLimit, result.getIntegerUpperLimit(), "The upper limit should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct integer lower limit
     * for the ActuatorOfLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfLimiterLowerLimit() {
        //Arrange
        ActuatorOfLimiter actuatorOfLimiter = mock(ActuatorOfLimiter.class);

        IntLimit integerUpperLimit = mock(IntLimit.class);
        when(integerUpperLimit.getIntLimit()).thenReturn(10);

        IntLimit integerLowerLimit = mock(IntLimit.class);
        when(integerLowerLimit.getIntLimit()).thenReturn(1);

        when(actuatorOfLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfLimiter.getUpperLimit()).thenReturn(integerUpperLimit);
        when(actuatorOfLimiter.getLowerLimit()).thenReturn(integerLowerLimit);

        int expectedLowerLimit = 1;
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfLimiter);
        //Assert
        assertEquals(expectedLowerLimit, result.getIntegerLowerLimit(), "The lower limit should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct actuator id
     * for the ActuatorOfDecimalLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfDecimalLimiterActuatorId() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = mock(ActuatorOfDecimalLimiter.class);

        DecimalLimit decimalUpperLimit = mock(DecimalLimit.class);
        when(decimalUpperLimit.getDecimalLimit()).thenReturn(10.0);

        DecimalLimit decimalLowerLimit = mock(DecimalLimit.class);
        when(decimalLowerLimit.getDecimalLimit()).thenReturn(1.0);

        Precision precision = mock(Precision.class);
        when(precision.getPrecision()).thenReturn(1);

        when(actuatorOfDecimalLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfDecimalLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfDecimalLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfDecimalLimiter.getUpperLimit()).thenReturn(decimalUpperLimit);
        when(actuatorOfDecimalLimiter.getLowerLimit()).thenReturn(decimalLowerLimit);
        when(actuatorOfDecimalLimiter.getPrecision()).thenReturn(precision);

        String expectedActuatorId = "1";
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfDecimalLimiter);
        //Assert
        assertEquals(expectedActuatorId, result.getActuatorId(), "The actuator id should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct double upper limit
     * for the ActuatorOfDecimalLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfDecimalLimiterUpperLimit() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = mock(ActuatorOfDecimalLimiter.class);

        DecimalLimit decimalUpperLimit = mock(DecimalLimit.class);
        when(decimalUpperLimit.getDecimalLimit()).thenReturn(10.0);

        DecimalLimit decimalLowerLimit = mock(DecimalLimit.class);
        when(decimalLowerLimit.getDecimalLimit()).thenReturn(1.0);

        Precision precision = mock(Precision.class);
        when(precision.getPrecision()).thenReturn(1);

        when(actuatorOfDecimalLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfDecimalLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfDecimalLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfDecimalLimiter.getUpperLimit()).thenReturn(decimalUpperLimit);
        when(actuatorOfDecimalLimiter.getLowerLimit()).thenReturn(decimalLowerLimit);
        when(actuatorOfDecimalLimiter.getPrecision()).thenReturn(precision);

        double expectedUpperLimit = 10.0;
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfDecimalLimiter);
        //Assert
        assertEquals(expectedUpperLimit, result.getDoubleUpperLimit(), "The upper limit should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct double lower limit
     * for the ActuatorOfDecimalLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfDecimalLimiterLowerLimit() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = mock(ActuatorOfDecimalLimiter.class);

        DecimalLimit decimalUpperLimit = mock(DecimalLimit.class);
        when(decimalUpperLimit.getDecimalLimit()).thenReturn(10.0);

        DecimalLimit decimalLowerLimit = mock(DecimalLimit.class);
        when(decimalLowerLimit.getDecimalLimit()).thenReturn(1.0);

        Precision precision = mock(Precision.class);
        when(precision.getPrecision()).thenReturn(1);

        when(actuatorOfDecimalLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfDecimalLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfDecimalLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfDecimalLimiter.getUpperLimit()).thenReturn(decimalUpperLimit);
        when(actuatorOfDecimalLimiter.getLowerLimit()).thenReturn(decimalLowerLimit);
        when(actuatorOfDecimalLimiter.getPrecision()).thenReturn(precision);

        double expectedLowerLimit = 1.0;
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfDecimalLimiter);
        //Assert
        assertEquals(expectedLowerLimit, result.getDoubleLowerLimit(), "The lower limit should be the expected one");
    }

    /**
     * Tests that the actuatorToDTO method returns an ActuatorDTO with the correct precision
     * for the ActuatorOfDecimalLimiter.
     */
    @Test
    void testActuatorToDTOActuatorOfDecimalLimiterPrecision() {
        //Arrange
        ActuatorOfDecimalLimiter actuatorOfDecimalLimiter = mock(ActuatorOfDecimalLimiter.class);

        DecimalLimit decimalUpperLimit = mock(DecimalLimit.class);
        when(decimalUpperLimit.getDecimalLimit()).thenReturn(10.0);

        DecimalLimit decimalLowerLimit = mock(DecimalLimit.class);
        when(decimalLowerLimit.getDecimalLimit()).thenReturn(1.0);

        Precision precision = mock(Precision.class);
        when(precision.getPrecision()).thenReturn(1);

        when(actuatorOfDecimalLimiter.getIdentity()).thenReturn(actuatorId);
        when(actuatorOfDecimalLimiter.getDeviceId()).thenReturn(deviceId);
        when(actuatorOfDecimalLimiter.getActuatorModelName()).thenReturn(actuatorModelName);
        when(actuatorOfDecimalLimiter.getUpperLimit()).thenReturn(decimalUpperLimit);
        when(actuatorOfDecimalLimiter.getLowerLimit()).thenReturn(decimalLowerLimit);
        when(actuatorOfDecimalLimiter.getPrecision()).thenReturn(precision);

        int expectedPrecision = 1;
        //Act
        ActuatorDTO result = actuatorMapper.actuatorToDTO(actuatorOfDecimalLimiter);
        //Assert
        assertEquals(expectedPrecision, result.getDoubleLimitPrecision(), "The precision should be the expected one");
    }

    /**
     * Tests that the toActuatorIdsDTO method returns a list of ActuatorIdDTO with the correct size.
     */
    @Test
    void testToActuatorIdsDTO() {
        //Arrange
        Iterable<ActuatorId> actuatorIds = List.of(actuatorId);
        int expectedSize = 1;
        //Act
        List<ActuatorIdDTO> result = actuatorMapper.toActuatorIdsDTO(actuatorIds);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }

}