package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceTypeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the DeviceTypeServiceImpl class.
 * It uses Mockito to mock the IDeviceTypeRepository interface.
 */
class DeviceTypeServiceImplTest {

    private IDeviceTypeRepository mockDeviceTypeRepository;
    private DeviceTypeServiceImpl deviceTypeService;
    private DeviceTypeName mockDeviceTypeName;
    private DeviceType mockDeviceType;

    /**
     * Set up the test environment.
     * It initializes the mock objects and the DeviceTypeServiceImpl.
     */
    @BeforeEach
    void setUp() {
        mockDeviceTypeRepository = mock(IDeviceTypeRepository.class);
        deviceTypeService = new DeviceTypeServiceImpl(mockDeviceTypeRepository);
        mockDeviceTypeName = mock(DeviceTypeName.class);
        mockDeviceType = mock(DeviceType.class);
    }

    /**
     * Test the DeviceTypeServiceImpl constructor.
     * This test asserts that the constructor is not null.
     */
    @Test
    void testDeviceTypeServiceImplConstructor() {
        // Act & Assert
        assertNotNull(deviceTypeService, "The constructor should return a non-null object.");
    }

    /**
     * Test the getDeviceTypeNames method.
     * This test asserts that the method returns an empty list when the repository is empty.
     */
    @Test
    void testGetDeviceTypeNamesReturnsNotNullList() {
        // Arrange
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(List.of(mockDeviceTypeName));
        // Act
        List<DeviceTypeName> deviceTypeNames = deviceTypeService.getDeviceTypeNames();
        // Assert
        assertNotNull(deviceTypeNames, "The method should return a non-null list.");
    }

    /**
     * Test the getDeviceTypeNames method.
     * This test asserts that the method returns a list with one element when the repository contains one element.
     */
    @Test
    void testGetDeviceTypeNamesReturnsListWithOneElement() {
        // Arrange
        int expectedSize = 1;
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(List.of(mockDeviceTypeName));
        // Act
        List<DeviceTypeName> deviceTypeNames = deviceTypeService.getDeviceTypeNames();
        // Assert
        int actualSize = deviceTypeNames.size();
        assertEquals(expectedSize, actualSize, "The method should return a list with one element.");
    }

    /**
     * Test the getDeviceTypeNames method.
     * This test asserts that the method returns an empty list when the repository is empty.
     */
    @Test
    void testGetDeviceTypeNamesReturnsEmptyList() {
        // Arrange
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(List.of());
        // Act
        List<DeviceTypeName> deviceTypeNames = deviceTypeService.getDeviceTypeNames();
        // Assert
        boolean isEmpty = deviceTypeNames.isEmpty();
        assertTrue(isEmpty, "The method should return an empty list.");
    }

    /**
     * Test the getDeviceTypeNameById method.
     * This test asserts that the method returns an empty optional when the repository is empty.
     */
    @Test
    void testGetDeviceTypeNameByIdReturnsEmptyOptional() {
        // Arrange
        when(mockDeviceTypeRepository.findByIdentity(mockDeviceTypeName)).thenReturn(Optional.empty());
        // Act
        var deviceType = deviceTypeService.getDeviceTypeById(mockDeviceTypeName);
        // Assert
        boolean isEmpty = deviceType.isEmpty();
        assertTrue(isEmpty, "The method should return an empty optional.");
    }

    /**
     * Test the getDeviceTypeNameById method.
     * This test asserts that the method returns the correct optional when the repository contains the device type.
     */
    @Test
    void testGetDeviceTypeNameByIdReturnsCorrectOptional() {
        // Arrange
        when(mockDeviceTypeRepository.findByIdentity(mockDeviceTypeName)).thenReturn(Optional.of(mockDeviceType));
        // Act
        var deviceType = deviceTypeService.getDeviceTypeById(mockDeviceTypeName);
        // Assert
        boolean isPresent = deviceType.isPresent();
        assertTrue(isPresent, "The method should return a non-empty optional.");
    }
}