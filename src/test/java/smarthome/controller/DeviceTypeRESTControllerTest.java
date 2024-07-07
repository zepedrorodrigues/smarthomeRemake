package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.DeviceTypeFactory;
import smarthome.domain.deviceType.DeviceTypeFactoryImpl;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.mapper.DeviceTypeDTO;
import smarthome.mapper.DeviceTypeNameDTO;
import smarthome.mapper.mapper.DeviceTypeMapper;
import smarthome.service.IDeviceTypeService;
import smarthome.service.impl.DeviceTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Test class for the DeviceTypeRESTController.
 */
class DeviceTypeRESTControllerTest {

    DeviceTypeRESTController deviceTypeRESTController;
    DeviceTypeMapper deviceTypeMapper;
    IDeviceTypeService deviceTypeService;
    IDeviceTypeRepository mockDeviceTypeRepository;
    DeviceType deviceType;
    DeviceTypeFactory deviceTypeFactory;
    DeviceTypeName deviceTypeName;
    DeviceTypeDTO deviceTypeDTO;
    String deviceTypeNameString;

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    String uri;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        // Initialize the DeviceTypeRESTController and its dependencies
        mockDeviceTypeRepository = mock(IDeviceTypeRepository.class);
        deviceTypeService = new DeviceTypeServiceImpl(mockDeviceTypeRepository);
        deviceTypeMapper = new DeviceTypeMapper();
        deviceTypeRESTController = new DeviceTypeRESTController(deviceTypeService, deviceTypeMapper);

        // Initialize the DeviceType and its dependencies
        deviceTypeNameString = "deviceTypeName";
        deviceTypeName = new DeviceTypeName(deviceTypeNameString);
        deviceTypeFactory = new DeviceTypeFactoryImpl();
        deviceType = deviceTypeFactory.createDeviceType(deviceTypeName);
        deviceTypeDTO = new DeviceTypeDTO(deviceTypeNameString);

        // Set up the MockMvc and ObjectMapper
        mockMvc = MockMvcBuilders.standaloneSetup(deviceTypeRESTController).build();
        objectMapper = new ObjectMapper();
        uri = "/devicetypes";
    }

    /**
     * Test the DeviceTypeRESTController constructor.
     * This asserts that the DeviceTypeRESTController is not null.
     */
    @Test
    void testDeviceTypeRESTController() {
        // Assert
        assertNotNull(deviceTypeRESTController);
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts that the method returns null in the response body when there are no
     * device types available.
     */
    @Test
    void testGetDeviceTypesWhenNoDeviceTypesCheckResponseBody() {
        // Arrange
        List<DeviceTypeName> deviceTypeNames = new ArrayList<>();
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(deviceTypeNames);

        // Act
        ResponseEntity<List<DeviceTypeNameDTO>> responseEntity = deviceTypeRESTController.getDeviceTypes();

        // Assert
        assertNull(responseEntity.getBody(), "The response body should be null.");
    }

    /**
     * Test if the getDeviceTypes method.
     * This test asserts that the controller correctly handles a GET request when there are no device types and returns
     * HTTP code status NOT_FOUND.
     */
    @Test
    void testGetDeviceTypesWhenNoDeviceTypesCheckResponseStatusCode() {
        // Arrange
        List<DeviceTypeName> deviceTypeNames = new ArrayList<>();
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(deviceTypeNames);
        HttpStatus expectedCode = HttpStatus.NOT_FOUND;

        // Act
        ResponseEntity<List<DeviceTypeNameDTO>> responseEntity = deviceTypeRESTController.getDeviceTypes();

        // Assert
        HttpStatusCode resultCode = responseEntity.getStatusCode();
        assertEquals(expectedCode, resultCode, "The status code should be NOT_FOUND.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts that the method returns a list of device types when there are device types available.
     */
    @Test
    void testGetDeviceTypesWhenDeviceTypesAvailableCheckResponseBody() {
        // Arrange
        List<DeviceTypeName> deviceTypeNames = new ArrayList<>();
        deviceTypeNames.add(deviceTypeName);
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(deviceTypeNames);

        // Act
        ResponseEntity<List<DeviceTypeNameDTO>> responseEntity = deviceTypeRESTController.getDeviceTypes();

        // Assert
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).stream().anyMatch(dto -> dto.getDeviceTypeName()
                .equals(deviceTypeNameString)), "The response body should contain the device type name.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts that the controller correctly handles a GET request when there device types available and
     * returns
     * HTTP code status OK.
     */
    @Test
    void testGetDeviceTypesWhenDeviceTypesAvailableCheckResponseStatusCode() {
        // Arrange
        List<DeviceTypeName> deviceTypeNames = new ArrayList<>();
        deviceTypeNames.add(deviceTypeName);
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(deviceTypeNames);
        HttpStatus expectedCode = HttpStatus.OK;

        // Act
        ResponseEntity<List<DeviceTypeNameDTO>> responseEntity = deviceTypeRESTController.getDeviceTypes();

        // Assert
        HttpStatusCode resultCode = responseEntity.getStatusCode();
        assertEquals(expectedCode, resultCode, "The status code should be OK.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts if the controller correctly handles a GET request when an exception is thrown and returns
     * null in the response body.
     */
    @Test
    void testGetDeviceTypesWhenExceptionThrownCheckResponseBody() {
        // Arrange
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<DeviceTypeNameDTO>> responseEntity = deviceTypeRESTController.getDeviceTypes();

        // Assert
        assertNull(responseEntity.getBody(), "The response body should be null.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts that the controller correctly handles a GET request when an exception is thrown and returns
     * HTTP code status BAD_REQUEST.
     */
    @Test
    void testGetDeviceTypesWhenExceptionThrownCheckResponseStatusCode() {
        // Arrange
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenThrow(new RuntimeException());
        HttpStatus expectedCode = HttpStatus.BAD_REQUEST;

        // Act
        ResponseEntity<List<DeviceTypeNameDTO>> responseEntity = deviceTypeRESTController.getDeviceTypes();

        // Assert
        HttpStatusCode resultCode = responseEntity.getStatusCode();
        assertEquals(expectedCode, resultCode, "The status code should be BAD_REQUEST.");
    }

    /**
     * Test if the getDeviceTypes method.
     * This test asserts if the controller correctly handles a GET request when there are device types available and
     * returns a list of device types names in the response body.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypesWhenDeviceTypesAvailableCorrectlyHandlesGetRequestOkStatus() throws Exception {
        // Arrange
        String uri = "/devicetypes";
        int expectedStatus = HttpStatus.OK.value();
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(List.of(deviceTypeName));

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();
        int actualStatus = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, actualStatus, "The status code should be OK.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts if the controller correctly handles a GET request when there are device types available and
     * returns a list of device types names in the response body.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypesWhenDeviceTypesAvailableCorrectlyHandlesGetRequestCheckResponseBody() throws Exception {
        // Arrange
        DeviceTypeNameDTO deviceTypeNameDTO = new DeviceTypeNameDTO(deviceTypeNameString).add(Link.of("http" +
                "://localhost/devicetypes/" + deviceTypeName.getDeviceTypeName()));
        String expectedJson = objectMapper.writeValueAsString(List.of(deviceTypeNameDTO));
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(List.of(deviceTypeName));

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        String actualJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson, "The response body should contain the device type name.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts if the controller correctly handles a GET request when there are no device types available and
     * returns HTTP code status NOT_FOUND.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypesWhenNoDeviceTypesCorrectlyHandlesGetRequestNotFoundStatus() throws Exception {
        // Arrange
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenReturn(new ArrayList<>());

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int actualStatus = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The status code should be NOT_FOUND.");
    }

    /**
     * Test the getDeviceTypes method.
     * This test asserts if the controller correctly handles a GET request when an exception is thrown and
     * returns HTTP code status BAD_REQUEST.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypesWhenExceptionThrownCorrectlyHandlesGetRequestBadRequestStatus() throws Exception {
        // Arrange
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        when(mockDeviceTypeRepository.findDeviceTypeNames()).thenThrow(new RuntimeException());

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int actualStatus = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The status code should be BAD_REQUEST.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts that the method returns a response body with the correct device type data when the device
     * type name exists.
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeExistsCheckResponseBody() {
        // Arrange
        DeviceTypeDTO expected =
                deviceTypeDTO.add(linkTo(methodOn(DeviceTypeRESTController.class)
                        .getDeviceTypeById(deviceTypeNameString)).withSelfRel());
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.of(deviceType));

        // Act
        ResponseEntity<DeviceTypeDTO> result = deviceTypeRESTController.getDeviceTypeById(deviceTypeNameString);

        // Assert
        DeviceTypeDTO actual = result.getBody();
        assertEquals(expected, actual, "The response body should contain the expected device type data.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts that the method returns the status code OK when the device type name exists.
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeExistsCheckResponseStatusCode() {
        // Arrange
        HttpStatus expected = HttpStatus.OK;
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.of(deviceType));

        // Act
        ResponseEntity<DeviceTypeDTO> result = deviceTypeRESTController.getDeviceTypeById(deviceTypeNameString);

        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The status code should be OK.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts that the method returns null in the response body when the device type name does not exist.
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeDoesNotExistCheckResponseBody() {
        // Arrange
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<DeviceTypeDTO> result = deviceTypeRESTController.getDeviceTypeById(deviceTypeNameString);

        // Assert
        DeviceTypeDTO actual = result.getBody();
        assertNull(actual, "The response body should be null.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts that the method returns the status code NOT_FOUND when the device type name does not exist.
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeDoesNotExistCheckResponseStatusCode() {
        // Arrange
        HttpStatus expected = HttpStatus.NOT_FOUND;
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<DeviceTypeDTO> result = deviceTypeRESTController.getDeviceTypeById(deviceTypeNameString);

        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The status code should be NOT_FOUND.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts that the method returns null in the response body when an exception is thrown.
     */
    @Test
    void testGetDeviceTypeByIdWhenExceptionThrownCheckResponseBody() {
        // Arrange
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<DeviceTypeDTO> result = deviceTypeRESTController.getDeviceTypeById(deviceTypeNameString);

        // Assert
        DeviceTypeDTO actual = result.getBody();
        assertNull(actual, "The response body should be null.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts that the method returns the status code BAD_REQUEST when an exception is thrown.
     */
    @Test
    void testGetDeviceTypeByIdWhenExceptionThrownCheckResponseStatusCode() {
        // Arrange
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<DeviceTypeDTO> result = deviceTypeRESTController.getDeviceTypeById(deviceTypeNameString);

        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The status code should be BAD_REQUEST.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts if the controller correctly handles a GET request when the device type name exists and
     * returns the device type data in the response body.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeExistsCorrectlyHandlesGetRequestOkStatus() throws Exception {
        // Arrange
        uri += "/" + deviceTypeNameString;
        int expectedStatus = HttpStatus.OK.value();
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.of(deviceType));

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int actualStatus = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The status code should be OK.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts if the controller correctly handles a GET request when the device type name does not exist and
     * returns HTTP code status NOT_FOUND.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeDoesNotExistCorrectlyHandlesGetRequestNotFoundStatus() throws Exception {
        // Arrange
        uri += "/" + deviceTypeNameString;
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int actualStatus = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The status code should be NOT_FOUND.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts if the controller correctly handles a GET request when an exception is thrown and
     * returns HTTP code status BAD_REQUEST.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypeByIdWhenExceptionThrownCorrectlyHandlesGetRequestBadRequestStatus() throws Exception {
        // Arrange
        uri += "/" + deviceTypeNameString;
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenThrow(new RuntimeException());

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int actualStatus = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The status code should be BAD_REQUEST.");
    }

    /**
     * Test the getDeviceTypeById method.
     * This test asserts if the controller correctly handles a GET request when the device type name exists and
     * returns the device type data in the response body with the correct links.
     * It uses MockMvc to test the controller.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testGetDeviceTypeByIdWhenDeviceTypeExistsCorrectlyHandlesGetRequestCheckResponseBody() throws Exception {
        // Arrange
        DeviceTypeDTO expectedDTO =
                deviceTypeDTO.add(Link.of("http://localhost/devicetypes/" + deviceTypeName.getDeviceTypeName()));
        String expectedJson = objectMapper.writeValueAsString(expectedDTO);
        uri += "/" + deviceTypeNameString;
        when(mockDeviceTypeRepository.findByIdentity(deviceTypeName)).thenReturn(Optional.of(deviceType));

        // Act
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        String actualJson = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson, "The response body should contain the expected device type data.");
    }
}