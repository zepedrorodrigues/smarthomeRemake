package smarthome.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorModelNameDTO;
import smarthome.mapper.mapper.SensorModelMapper;
import smarthome.service.ISensorModelService;
import smarthome.service.impl.SensorModelServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for SensorModelRESTController.
 * It uses JUnit and Mockito to test the functionality of the REST controller.
 */
class SensorModelRESTControllerTest {

    private ISensorModelRepository mockSensorModelRepository;

    private SensorModelMapper sensorModelMapper;

    private ISensorModelService sensorModelService;

    private SensorModelName sensorModelName;

    private SensorTypeId sensorTypeId;

    private String uri;

    private MockMvc mvc;

    private ObjectMapper objectMapper;

    private SensorModelRESTController sensorModelRESTController;

    /**
     * Sets up the test environment before each test method.
     * It initializes the necessary objects and mocks the required dependencies.
     */
    @BeforeEach
    void setUp() {
        mockSensorModelRepository = mock(ISensorModelRepository.class);

        sensorModelService = new SensorModelServiceImpl(mockSensorModelRepository);

        sensorModelMapper = new SensorModelMapper();

        sensorModelName = new SensorModelName("sensorModelName");

        sensorTypeId = new SensorTypeId("123");

        sensorModelRESTController = new SensorModelRESTController(sensorModelService, sensorModelMapper);

        uri = "/sensormodels";

        mvc = MockMvcBuilders.standaloneSetup(sensorModelRESTController).build();

        objectMapper = new ObjectMapper();
    }

    /**
     * Test for the SensorModelRESTController constructor.
     * It checks if the constructor creates a non-null SensorModelRESTController object.
     */
    @Test
    void testSensorModelRESTController() {
        // Arrange + Act
        SensorModelRESTController sensorModelRESTController = new SensorModelRESTController(sensorModelService,
                sensorModelMapper);
        // Assert
        assertNotNull(sensorModelRESTController,
                "The constructor should create a non-null SensorModelRESTController object.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model exists.
     * It checks if the HTTP status for a sensor model that exists is OK.
     */
    @Test
    void testGetSensorModelByNameExists() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        SensorModel sensorModel = new SensorModel(sensorModelNameVO, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.of(sensorModel));

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorModelName}", sensorModelName))
                .andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(),
                "The HTTP status for a sensor model that exists should be OK.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model does not exist.
     * It checks if the HTTP status for a sensor model that does not exist is NOT_FOUND.
     */
    @Test
    void testGetSensorModelByNameDoesNotExist() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorModelName}", sensorModelName))
                .andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus(),
                "The HTTP status for a sensor model that does not exist should be NOT_FOUND.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model exists.
     * It checks if the response body is of type SensorModelDTO when the HTTP status is OK.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameExistsResponseBody() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        SensorModel sensorModel = new SensorModel(sensorModelNameVO, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.of(sensorModel));

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorModelName}", sensorModelName))
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        EntityModel<SensorModelDTO> sensorModelEntity = objectMapper.readValue(content, new TypeReference<>() {});
        assertEquals(SensorModelDTO.class, sensorModelEntity.getContent().getClass(),
                "The response body should be of type SensorModelDTO when the HTTP status is OK.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model exists.
     * It checks if the response content matches the expected sensor model name.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameExistsResponseContent() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        SensorModel sensorModel = new SensorModel(sensorModelNameVO, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.of(sensorModel));

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorModelName}", sensorModelName))
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        EntityModel<SensorModelDTO> sensorModelEntity = objectMapper.readValue(content, new TypeReference<>() {});
        assertEquals(sensorModelName, sensorModelEntity.getContent().getSensorModelName(),
                "The response content should match the expected sensor model name.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model does not exist.
     * It checks if the response content is empty when the sensor model does not exist.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameDoesNotExistResponseContent() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorModelName}", sensorModelName))
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        assertTrue(content.isEmpty(),
                "The response content should be empty when the sensor model does not exist.");
    }

    /**
     * Test for the getSensorModelByName method.
     * It checks if the response content type is "application/hal+json".
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameResponseContentType() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        SensorModel sensorModel = new SensorModel(sensorModelNameVO, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.of(sensorModel));

        // Act and Assert
        mvc.perform(MockMvcRequestBuilders.get(uri+"/{sensorModelName}", sensorModelName))
                .andExpect(MockMvcResultMatchers.content().contentType("application/hal+json"));
    }

    /**
     * Test for the getSensorModelsBySensorTypeIdentity method.
     * It checks if the response content type is "application/json".
     * The test arranges a scenario where the SensorModelRepository is mocked to return a list of SensorModelNames
     * when the getSensorModelBySensorTypeId method is called with a specific SensorTypeId.
     * The test then performs a GET request to the "/sensormodels/type/{sensorTypeId}" endpoint and asserts that
     * the content type of the response is "application/json".
     *
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityResponseContentType() throws Exception {
        // Arrange
        String sensorTypeId = "123";
        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        SensorModelName sensorModelName1 = new SensorModelName("sensorModelName1");
        SensorModelName sensorModelName2 = new SensorModelName("sensorModelName2");
        List<SensorModelName> sensorModelNames = List.of(sensorModelName1, sensorModelName2);
        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(sensorTypeIdVO)).thenReturn(sensorModelNames);

        // Act and Assert
        mvc.perform(MockMvcRequestBuilders.get(uri+"/type/{sensorTypeId}", sensorTypeId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    /**
     * Test for the getSensorModelByName method when the sensor model does not exist.
     * It checks if the HTTP status for a sensor model that does not exist is NOT_FOUND.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameDoesNotExistHttpStatus() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        when(mockSensorModelRepository.findByIdentity(this.sensorModelName)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorModelName}",
                sensorModelName)).andReturn();

        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status should be NOT_FOUND.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model exists.
     * It checks if the HTTP status for a sensor model that exists is OK.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameExistsHttpStatus() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        HttpStatus expected = HttpStatus.OK;
        SensorModel sensorModel = new SensorModel(this.sensorModelName, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(this.sensorModelName)).thenReturn(Optional.of(sensorModel));

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorModelName}", sensorModelName)).andReturn();

        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status should be OK.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model exists.
     * It checks if the response content matches the expected sensor model.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameExist() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModel sensorModel = new SensorModel(this.sensorModelName, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(this.sensorModelName)).thenReturn(Optional.of(sensorModel));

        SensorModelDTO sensorModelDTO = sensorModelMapper.toSensorModelDto(sensorModel);
        Link link = Link.of("http://localhost" + uri + "/" + sensorModelName);
        EntityModel<SensorModelDTO> expected = EntityModel.of(sensorModelDTO, link);

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorModelName}", sensorModelName)).andReturn();

        // Assert
        EntityModel<SensorModelDTO> resultContent = objectMapper.readValue(result.getResponse()
                .getContentAsString(), new TypeReference<>() {});
        assertEquals(expected, resultContent, "The response content should match the expected sensor model.");
    }

    /**
     * Test for the getSensorModelsBySensorTypeIdentity method when the sensor type does not exist.
     * It checks if the HTTP status for a sensor type that does not exist is NOT_FOUND.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityDoesNotExistHttpStatus() throws Exception {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("sensorTypeId");
        HttpStatus expected = HttpStatus.NOT_FOUND;
        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(sensorTypeId)).thenReturn(List.of());

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/type/{sensorTypeId}",
                sensorTypeId)).andReturn();

        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status should be NOT_FOUND.");
    }

    /**
     * Test for the getSensorModelsBySensorTypeIdentity method when the sensor type exists.
     * It checks if the response content is not null.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityExistsNotNull() throws Exception {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("sensorTypeId");
        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(sensorTypeId)).thenReturn(List.of(sensorModelName));

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/type/{sensorTypeId}",
                sensorTypeId)).andReturn();

        // Assert
        assertNotNull(result.getResponse().getContentAsString(),
                "The response content should not be null.");
    }

    /**
     * Test for the getSensorModelByName method when the sensor model exists.
     * It checks if the returned sensor model name in the response body matches the expected sensor model name.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorModelByNameReturnsCorrectNameWhenModelExists() throws Exception {
        // Arrange
        String sensorModelName = "sensorModelName";
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        SensorModel sensorModel = new SensorModel(sensorModelNameVO, sensorTypeId);
        when(mockSensorModelRepository.findByIdentity(sensorModelNameVO)).thenReturn(Optional.of(sensorModel));
        SensorModelNameDTO sensorModelNameDTO = new SensorModelNameDTO(sensorModelName);

        // Act
        ResponseEntity<EntityModel<SensorModelDTO>> response = sensorModelRESTController
                .getSensorModelByName(sensorModelName);
        // Assert
        assertEquals(sensorModelNameDTO.getSensorModelName(), response.getBody().getContent().getSensorModelName(),
                "The response body should match the expected sensor model name.");
    }
}