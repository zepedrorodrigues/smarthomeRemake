package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorIdDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for SensorMapper
 */
class SensorMapperTest {

    SensorMapper sensorMapper;
    SensorDTO sensorDTO;
    Sensor sensor;
    SensorId sensorId;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        sensorMapper = new SensorMapper();

        sensorDTO = mock(SensorDTO.class);
        when(sensorDTO.getDeviceId()).thenReturn("1");
        when(sensorDTO.getSensorModelName()).thenReturn("model");
        when(sensorDTO.getSensorId()).thenReturn("1");
        when(sensorDTO.getValue()).thenReturn("value");

        sensor = mock(Sensor.class);

        sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn("1");

        SensorId sensorId = mock(SensorId.class);
        when(sensorId.getSensorId()).thenReturn("1");
        when(sensor.getIdentity()).thenReturn(sensorId);

        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("1");
        when(sensor.getDeviceId()).thenReturn(deviceId);

        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("model");
        when(sensor.getSensorModelName()).thenReturn(sensorModelName);

        Value value = mock(Value.class);
        when(value.valueToString()).thenReturn("value");
        when(sensor.getValue()).thenReturn(value);


    }

    /**
     * Tests that the toDeviceId method returns the correct DeviceId.
     */
    @Test
    void toDeviceId() {
        //Arrange
        String expected = "1";
        //Act
        DeviceId result = sensorMapper.toDeviceId(sensorDTO);
        //Assert
        assertEquals(expected, result.getIdentity(), "The device id should be the expected one");
    }

    /**
     * Tests that the toSensorModelName method returns the correct SensorModelName.
     */
    @Test
    void toSensorModelName() {
        //Arrange
        String expected = "model";
        //Act
        SensorModelName result = sensorMapper.toSensorModelName(sensorDTO);
        //Assert
        assertEquals(expected, result.getSensorModelName(), "The sensor model name should be the expected one");
    }

    /**
     * Tests that the toSensorDTO method returns the Sensor with the correct sensor id.
     */
    @Test
    void toSensorDTOSensorId() {
        //Arrange
        String expected = "1";
        //Act
        SensorDTO result = sensorMapper.toSensorDTO(sensor);
        //Assert
        assertEquals(expected, result.getSensorId(), "The sensor id should be the expected one");
    }

    /**
     * Tests that the toSensorDTO method returns the Sensor with the correct device id.
     */
    @Test
    void toSensorDTODeviceId() {
        //Arrange
        String expected = "1";
        //Act
        SensorDTO result = sensorMapper.toSensorDTO(sensor);
        //Assert
        assertEquals(expected, result.getDeviceId(), "The device id should be the expected one");
    }

    /**
     * Tests that the toSensorDTO method returns the Sensor with the correct model name.
     */
    @Test
    void toSensorDTOSensorModelName() {
        //Arrange
        String expected = "model";
        //Act
        SensorDTO result = sensorMapper.toSensorDTO(sensor);
        //Assert
        assertEquals(expected, result.getSensorModelName(), "The sensor model name should be the expected one");
    }

    /**
     * This test verifies that the toSensorIdsDTO method in the SensorMapper class correctly converts an Iterable of SensorId
     * objects into a List of SensorIdDTO objects. The test sets up an Iterable of SensorId objects and expects the size of the
     * resulting List of SensorIdDTO objects to be 1. The test then calls the toSensorIdsDTO method with the Iterable of SensorId
     * objects and asserts that the size of the returned List of SensorIdDTO objects is as expected.
     */
    @Test
    void testToSensorIdsDTO() {
        //Arrange
        Iterable<SensorId> sensorIds = List.of(sensorId);
        int expectedSize = 1;
        //Act
        List<SensorIdDTO> result = sensorMapper.toSensorIdsDTO(sensorIds);
        //Assert
        assertEquals(expectedSize, result.size(),
                "The size of the list should be the expected one");
    }
}