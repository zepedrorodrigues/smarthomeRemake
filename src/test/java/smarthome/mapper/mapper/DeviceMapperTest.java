package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceIdDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for DeviceMapper
 */
class DeviceMapperTest {

    DeviceMapper deviceMapper;
    DeviceDTO deviceDTO;
    Device device;
    DeviceId deviceId;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        deviceMapper = new DeviceMapper();

        deviceDTO = mock(DeviceDTO.class);

        when(deviceDTO.getDeviceId()).thenReturn("1");
        when(deviceDTO.getDeviceName()).thenReturn("deviceName");
        when(deviceDTO.getDeviceTypeName()).thenReturn("deviceTypeName");
        when(deviceDTO.getRoomId()).thenReturn("1");
        when(deviceDTO.getDeviceStatus()).thenReturn(true);

        device = mock(Device.class);

        deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("1");

        DeviceName deviceName = mock(DeviceName.class);
        when(deviceName.getDeviceName()).thenReturn("deviceName");

        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        when(deviceTypeName.getDeviceTypeName()).thenReturn("deviceTypeName");

        RoomId roomId = mock(RoomId.class);
        when(roomId.getRoomId()).thenReturn("1");

        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(true);

        when(device.getIdentity()).thenReturn(deviceId);
        when(device.getDeviceName()).thenReturn(deviceName);
        when(device.getDeviceTypeName()).thenReturn(deviceTypeName);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);

    }

    /**
     * Tests that the toDeviceDTO method returns a DeviceDTO object with the correct device id
     */
    @Test
    void testToDeviceDTODeviceId() {
        //Arrange
        String expectedDeviceId = "1";
        //Act
        DeviceDTO result = deviceMapper.toDeviceDTO(device);
        //Assert
        assertEquals(expectedDeviceId, result.getDeviceId(), "The device id should be the expected one");
    }

    /**
     * Tests that the toDeviceDTO method returns a DeviceDTO object with the correct device status
     */
    @Test
    void testToDeviceDTODeviceStatus() {
        //Arrange
        boolean expectedDeviceStatus = true;
        //Act
        DeviceDTO result = deviceMapper.toDeviceDTO(device);
        //Assert
        assertEquals(expectedDeviceStatus, result.getDeviceStatus(), "The device status should be the expected one");
    }

    /**
     * Tests that the toDeviceDTO method returns a DeviceDTO object with the correct room id
     */
    @Test
    void testToDeviceDTORoomId() {
        //Arrange
        String expectedRoomId = "1";
        //Act
        DeviceDTO result = deviceMapper.toDeviceDTO(device);
        //Assert
        assertEquals(expectedRoomId, result.getDeviceId(), "The room id should be the expected one");
    }

    /**
     * Tests that the toDeviceId method returns the correct DeviceId
     */
    @Test
    void testToDeviceId() {
        //Arrange
        String expectedDeviceID = "1";
        //Act
        DeviceId result = deviceMapper.toDeviceId(deviceDTO);
        //Assert
        assertEquals(expectedDeviceID, result.getIdentity(), "The device id should be the expected one");
    }

    /**
     * Tests that the toDeviceName method returns the correct DeviceName
     */
    @Test
    void testToDeviceName() {
        //Arrange
        String expectedDeviceName = "deviceName";
        //Act
        DeviceName result = deviceMapper.toDeviceName(deviceDTO);
        //Assert
        assertEquals(expectedDeviceName, result.getDeviceName(), "The device name should be the expected one");
    }

    /**
     * Tests that the toRoomId method returns the correct RoomId
     */
    @Test
    void testToRoomId() {
        //Arrange
        String expectedRoomId = "1";
        //Act
        RoomId result = deviceMapper.toRoomId(deviceDTO);
        //Assert
        assertEquals(expectedRoomId, result.getRoomId(), "The room id should be the expected one");
    }

    /**
     * Tests that the toMapDTO method returns a map with the correct size
     */
    @Test
    void testToMapDTO() {
        //Arrange
        HashMap<String, List<Device>> map = new HashMap<>();
        map.put("key", List.of(device));

        int expectedSize = 1;
        //Act
        Map<String, List<DeviceDTO>> result = deviceMapper.toMapDTO(map);
        //Assert
        assertEquals(expectedSize, result.size(), "The map should have one key");
    }

    /**
     * Tests that the toDevicesDTO method returns a list with the correct size
     */
    @Test
    void testToDevicesDTO() {
        //Arrange
        Iterable<Device> devices = List.of(device);
        int expectedSize = 1;
        //Act
        List<DeviceDTO> result = deviceMapper.toDevicesDTO(devices);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }

    /**
     * Tests that the toDeviceIdsDTO method returns a list with the correct size
     */
    @Test
    void testToDeviceIdsDTO() {
        //Arrange
        Iterable<DeviceId> deviceIds = List.of(deviceId);
        int expectedSize = 1;
        //Act
        List<DeviceIdDTO> result = deviceMapper.toDeviceIdsDTO(deviceIds);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }
}