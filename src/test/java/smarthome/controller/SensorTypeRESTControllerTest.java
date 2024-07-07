package smarthome.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.SensorTypeIdDTO;
import smarthome.mapper.mapper.SensorTypeMapper;
import smarthome.service.ISensorTypeService;
import smarthome.service.impl.SensorTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for SensorTypeRESTController.
 * It tests the behavior of SensorTypeRESTController methods.
 */
class SensorTypeRESTControllerTest {

    private ISensorTypeRepository mockSensorTypeRepository;

    private SensorTypeMapper sensorTypeMapper;

    private ISensorTypeService sensorTypeService;

    private String uri;

    private MockMvc mvc;

    private ObjectMapper objectMapper;

    private SensorTypeRESTController sensorTypeRESTController;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        mockSensorTypeRepository = mock(ISensorTypeRepository.class);

        sensorTypeService = new SensorTypeServiceImpl(mockSensorTypeRepository);

        sensorTypeMapper = new SensorTypeMapper();

        sensorTypeRESTController = new SensorTypeRESTController(sensorTypeService, sensorTypeMapper);

        uri = "/sensortypes";

        mvc = MockMvcBuilders.standaloneSetup(sensorTypeRESTController).build();

        objectMapper = new ObjectMapper();
    }

    /**
     * Test the constructor of SensorTypeRESTController.
     */
    @Test
    void testSensorTypeRESTControllerConstructor() {
        //Arrange + Act
        SensorTypeRESTController sensorTypeRESTController = new SensorTypeRESTController(sensorTypeService,
                sensorTypeMapper);
        //Assert
        assertNotNull(sensorTypeRESTController, "The constructor of SensorTypeRESTController should work correctly.");
    }

    /**
     * Test the HTTP status when a sensor type does not exist.
     */
    @Test
    void testGetSensorTypeByIdentityDoesNotExistHttpStatus() throws Exception {
        //Arrange
        String sensorTypeId = "123";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorTypeId}", sensorTypeId)).andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The HTTP status for a sensor type that does" +
                " not exist should be NOT_FOUND.");
    }

    /**
     * Test the HTTP status when a sensor type exists.
     */
    @Test
    void testGetSensorTypeByIdentityExistsHttpStatus() throws Exception {
        //Arrange
        String sensorTypeId = "123";
        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        SensorType sensorType = new SensorType(sensorTypeIdVO, new SensorTypeName("name"), new SensorTypeUnit("unit"));
        when(mockSensorTypeRepository.findByIdentity(sensorTypeIdVO)).thenReturn(Optional.of(sensorType));
        HttpStatus expected = HttpStatus.OK;
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorTypeId}", sensorTypeId)).andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The HTTP status for a sensor type that " +
                "exists should be OK.");
    }

    /**
     * Test the HTTP status when the list of sensor types is empty.
     */
    @Test
    void testGetSensorTypesEmptyListHttpStatusCode200() throws Exception {
        //Arrange
        List<SensorTypeId> emptyList = new ArrayList<>();
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(emptyList);

        int expected = HttpStatus.OK.value();
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json")).andReturn();

        //Assert
        assertEquals(expected, result.getResponse().getStatus(), "Should be http code 200");
    }

    /**
     * Test the response content type when getting the list of sensor types.
     */
    @Test
    void testGetSensorTypesResponseContentType() throws Exception {
        //Arrange
        List<SensorTypeId> emptyList = new ArrayList<>();
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(emptyList);

        String expected = "application/hal+json";

        //Act and Assert
        mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json")).andExpect(MockMvcResultMatchers.content().contentType(expected));
    }

    /**
     * Test the response content when the list of sensor types is empty.
     */
    @Test
    void testGetSensorTypesEmptyList() throws Exception {
        //Arrange
        List<SensorTypeId> emptyList = new ArrayList<>();
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(emptyList);

        ObjectMapper objectMapper = new ObjectMapper();

        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json")).andReturn();

        //Assert
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode sensorTypeListNode = jsonNode.get("content");

        assertTrue(sensorTypeListNode.isEmpty(), "Should be an empty list");
    }

    /**
     * Test the response content when the list of sensor types contains sensor types.
     */
    @Test
    void testGetSensorTypesListWithSensorTypes() throws Exception {
        //Arrange
        SensorTypeId sensorTypeId1 = new SensorTypeId("123");
        SensorTypeId sensorTypeId2 = new SensorTypeId("456");

        List<SensorTypeId> sensorTypeIds = List.of(sensorTypeId1, sensorTypeId2);
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(sensorTypeIds);

        SensorTypeIdDTO sensorTypeIdDTO1 = new SensorTypeIdDTO("123");
        SensorTypeIdDTO sensorTypeIdDTO2 = new SensorTypeIdDTO("456");
        List<SensorTypeIdDTO> expected = List.of(sensorTypeIdDTO1, sensorTypeIdDTO2);
        ObjectMapper objectMapper = new ObjectMapper();

        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json")).andReturn();

        //Assert
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode sensorTypeListNode = jsonNode.get("content");

        List<SensorTypeIdDTO> resultContent = objectMapper.readValue(sensorTypeListNode.toString(),
                new TypeReference<>() {
        });

        assertEquals(expected.size(), resultContent.size(), "Should be equal");
    }

    /**
     * Test if the response body is not null when the HTTP status is OK.
     * This test arranges a SensorType with a specific SensorTypeId, and mocks the SensorTypeRepository to return
     * this SensorType.
     * It then performs a GET request to the SensorTypeRESTController with the SensorTypeId.
     * The test asserts that the response body is not null.
     */
    @Test
    void testGetSensorTypeByIdentityExistsResponseNotNull() throws Exception {
        //Arrange
        String sensorTypeId = "123";
        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        SensorType sensorType = new SensorType(sensorTypeIdVO, new SensorTypeName("name"), new SensorTypeUnit("unit"));
        when(mockSensorTypeRepository.findByIdentity(sensorTypeIdVO)).thenReturn(Optional.of(sensorType));

        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorTypeId}", sensorTypeId)).andReturn();

        //Assert
        assertNotNull(result.getResponse().getContentAsString(), "The response body should not be null when the HTTP " +
                "status is OK.");
    }

    /**
     * Test if the response body is of type SensorTypeDTO when the HTTP status is OK.
     * This test arranges a SensorType with a specific SensorTypeId, and mocks the SensorTypeRepository to return
     * this SensorType.
     * It then performs a GET request to the SensorTypeRESTController with the SensorTypeId.
     * The test asserts that the response body is of type SensorTypeDTO.
     */
    @Test
    void testGetSensorTypeByIdentityExistsResponseBodyType() throws Exception {
        //Arrange
        String sensorTypeId = "123";
        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        SensorType sensorType = new SensorType(sensorTypeIdVO, new SensorTypeName("name"), new SensorTypeUnit("unit"));
        when(mockSensorTypeRepository.findByIdentity(sensorTypeIdVO)).thenReturn(Optional.of(sensorType));

        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{sensorTypeId}", sensorTypeId)).andReturn();

        //Assert
        String content = result.getResponse().getContentAsString();
        EntityModel<SensorTypeDTO> sensorTypeModel = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(SensorTypeDTO.class, sensorTypeModel.getContent().getClass(), "The response body should be of " +
                "type SensorTypeDTO when the HTTP status is OK.");
    }

    /**
     * Test for the getSensorTypeByIdentity method when the sensor type exists.
     * It checks if the returned sensor type id in the response body matches the expected sensor type id.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorTypeByIdentityReturnsCorrectIdWhenTypeExists() throws Exception {
        // Arrange
        String sensorTypeId = "123";
        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        SensorType sensorType = new SensorType(sensorTypeIdVO, new SensorTypeName("name"), new SensorTypeUnit("unit"));
        when(mockSensorTypeRepository.findByIdentity(sensorTypeIdVO)).thenReturn(Optional.of(sensorType));
        SensorTypeIdDTO sensorTypeIdDTO = new SensorTypeIdDTO(sensorTypeId);

        // Act
        ResponseEntity<EntityModel<SensorTypeDTO>> response =
                sensorTypeRESTController.getSensorTypeByIdentity(sensorTypeId);

        // Assert
        assertEquals(sensorTypeIdDTO.getSensorTypeId(), response.getBody().getContent().getSensorTypeId(),
                "The sensor type id should be the same as the one in the response.");
    }
}