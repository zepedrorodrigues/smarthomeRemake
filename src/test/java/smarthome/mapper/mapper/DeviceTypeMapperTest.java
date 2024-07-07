package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceTypeDTO;
import smarthome.mapper.DeviceTypeNameDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DeviceTypeMapper class
 */
class DeviceTypeMapperTest {

    DeviceTypeMapper deviceTypeMapper;
    DeviceTypeName deviceTypeName;
    DeviceTypeNameDTO deviceTypeNameDTO;
    DeviceType deviceType;
    DeviceTypeDTO deviceTypeDTO;
    DeviceDTO deviceDTO;


    /**
     * Set up before each test
     */
    @BeforeEach
    void setUp() {
        deviceTypeMapper = new DeviceTypeMapper();

        deviceTypeNameDTO = mock(DeviceTypeNameDTO.class);
        when(deviceTypeNameDTO.getDeviceTypeName()).thenReturn("deviceTypeName");

        deviceTypeName = mock(DeviceTypeName.class);
        when(deviceTypeName.getDeviceTypeName()).thenReturn("deviceTypeName");

        deviceType = mock(DeviceType.class);
        when(deviceType.getIdentity()).thenReturn(deviceTypeName);

        deviceTypeDTO = mock(DeviceTypeDTO.class);
        when(deviceTypeDTO.getDeviceTypeName()).thenReturn("deviceTypeName");

        deviceDTO = mock(DeviceDTO.class);
        when(deviceDTO.getDeviceTypeName()).thenReturn("deviceTypeName");
        when(deviceDTO.getDeviceId()).thenReturn("1");
        when(deviceDTO.getDeviceName()).thenReturn("deviceName");
        when(deviceDTO.getDeviceStatus()).thenReturn(true);
        when(deviceDTO.getRoomId()).thenReturn("1");
    }

    /**
     * Tests that the toDeviceTypeNamesDTO method returns a list of DeviceTypeNameDTOs
     */
    @Test
    void toDeviceTypeNamesDTO() {
        //Arrange
        Iterable<DeviceTypeName> deviceTypes = List.of(deviceTypeName);
        int expectedSize = 1;
        //Act
        List<DeviceTypeNameDTO> deviceTypeNameDTOs = deviceTypeMapper.toDeviceTypeNamesDTO(deviceTypes);
        //Assert
        assertEquals(expectedSize, deviceTypeNameDTOs.size(), "The list should have one element");
    }

    /**
     * Tests that the toDeviceTypeName method returns the correct DeviceTypeName.
     */
    @Test
    void toDeviceTypeName() {
        //Arrange
        String expectedDeviceTypeName = "deviceTypeName";
        //Act
        DeviceTypeName result = deviceTypeMapper.toDeviceTypeName(deviceDTO);
        //Assert
        assertEquals(expectedDeviceTypeName, result.getDeviceTypeName(),
                "The device type name should be the expected one");
    }

    /**
     * Tests that the toDeviceTypeNameDTO method returns the correct DeviceTypeNameDTO.
     */
    @Test
    void toDeviceTypeNameDTO() {
        //Arrange
        String expectedDeviceTypeName = "deviceTypeName";
        //Act
        DeviceTypeNameDTO result = deviceTypeMapper.toDeviceTypeNameDTO(deviceTypeName);
        //Assert
        assertEquals(expectedDeviceTypeName, result.getDeviceTypeName(),
                "The device type name should be the expected one");
    }

    /**
     * Tests that the toDeviceTypeDTO method returns the correct DeviceTypeDTO.
     */
    @Test
    void toDeviceTypeDTO() {
        //Arrange
        String expectedDeviceTypeName = "deviceTypeName";
        //Act
        DeviceTypeDTO result = deviceTypeMapper.toDeviceTypeDTO(deviceType);
        //Assert
        assertEquals(expectedDeviceTypeName, result.getDeviceTypeName(),
                "The device type name should be the expected one");
    }
}