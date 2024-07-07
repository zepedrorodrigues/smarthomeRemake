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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.mapper.ActuatorTypeDTO;
import smarthome.mapper.mapper.ActuatorTypeMapper;
import smarthome.service.IActuatorTypeService;
import smarthome.service.impl.ActuatorTypeServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorTypeRESTControllerTest {

    IActuatorTypeService actuatorTypeService;
    ActuatorTypeMapper actuatorTypeMapper;
    IActuatorTypeRepository mockActuatorTypeRepository;
    ActuatorTypeName actuatorTypeName;
    ActuatorTypeRESTController actuatorTypeRESTController;

    private String uri;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockActuatorTypeRepository = mock(IActuatorTypeRepository.class);
        actuatorTypeService = new ActuatorTypeServiceImpl(mockActuatorTypeRepository);
        actuatorTypeMapper = new ActuatorTypeMapper();
        actuatorTypeName = new ActuatorTypeName("BlindRoller");
        actuatorTypeRESTController = new ActuatorTypeRESTController(actuatorTypeService, actuatorTypeMapper);

        uri = "/actuatortypes";
        mvc = MockMvcBuilders.standaloneSetup(actuatorTypeRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * This test checks if the ActuatorTypeRESTController constructor returns a new ActuatorTypeRESTController object.
     */

    @Test
    void testActuatorTypeRESTControllerConstructor() {
        //Arrange & Act
        ActuatorTypeRESTController actuatorTypeRESTController = new ActuatorTypeRESTController(actuatorTypeService,
                actuatorTypeMapper);
        //Assert
        assertNotNull(actuatorTypeRESTController,
                "The ActuatorTypeRESTController constructor should return a new " + "ActuatorTypeRESTController " +
                        "object");
    }

    /**
     * This test checks if the getActuatorTypes method of the ActuatorRESTController returns OK when the
     * ActuatorTypeRepository is not empty.
     */
    @Test
    void testGetActuatorTypesWhenRepositoryHasActuatorTypes() {
        //Arrange
        when(mockActuatorTypeRepository.findActuatorTypeNames()).thenReturn(List.of(actuatorTypeName));
        //Act
        ResponseEntity<List<ActuatorTypeDTO>> result = actuatorTypeRESTController.getActuatorTypes();
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode(),
                "The HTTP status for a non-empty ActuatorTypeRepository " + "should be OK");
    }

    /**
     * This test checks if the getActuatorTypes method of the ActuatorRESTController returns a non-empty list when the
     * ActuatorTypeRepository is not empty.
     */
    @Test
    void testGetActuatorTypesWhenRepositoryHasActuatorTypesWhenRepositoryHasActuatorTypes() throws Exception {
        //Arrange
        when(mockActuatorTypeRepository.findActuatorTypeNames()).thenReturn(List.of(actuatorTypeName));
        HttpStatus expected = HttpStatus.OK;
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus());
    }

    /**
     * This test checks if the getActuatorTypes method of the ActuatorRESTController returns NO_CONTENT when the
     * ActuatorTypeRepository is empty.
     */
    @Test
    void testGetActuatorTypesReturnsCorrectHTTPStatusNotFound() {
        //Arrange
        when(mockActuatorTypeRepository.findAll()).thenReturn(Collections.emptyList());
        //Act
        ResponseEntity<List<ActuatorTypeDTO>> result = actuatorTypeRESTController.getActuatorTypes();
        //Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(), "The HTTP status for an empty " +
                "ActuatorTypeRepository " + "should be NOT_FOUND");
    }

    /**
     * This test checks if the getActuatorTypes method of the ActuatorRESTController returns NOT_FOUND when the
     * ActuatorTypeRepository is empty.
     */
    @Test
    void testGetActuatorTypesEmptyList() throws Exception {
        //Arrange
        when(mockActuatorTypeRepository.findActuatorTypeNames()).thenReturn(Collections.emptyList());
        HttpStatus expected = HttpStatus.NOT_FOUND;
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus());
    }

    /*
     *Test if the getActuatorTypeById method returns a NOT_FOUND status when the ActuatorType does not exist.
     */
    @Test
    void testGetActuatorTypeByIdDoesNotExistHttpStatus() throws Exception {
        //Arrange
        String actuatorTypeName = "BlindRoller";
        when(mockActuatorTypeRepository.findByIdentity(new ActuatorTypeName(actuatorTypeName))).thenReturn(Optional.empty());
        HttpStatus expected = HttpStatus.NOT_FOUND;
        //Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorTypeId}", actuatorTypeName)).andReturn();
        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The getActuatorTypeById method should " +
                "return a NOT_FOUND status when the ActuatorType does not exist.");
    }


    /**
     * This test checks if the getActuatorTypeById method returns a OK status when the ActuatorType exists.
     */
    @Test
    void testGetActuatorTypeByIdExitsHttpStatus() throws Exception {
        //Arrange
        String actuatorTypeName = "BlindRoller";
        ActuatorTypeName actuatorTypeName1 = new ActuatorTypeName(actuatorTypeName);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeName1);
        when(mockActuatorTypeRepository.findByIdentity(actuatorTypeName1)).thenReturn(Optional.of(actuatorType));
        HttpStatus expected = HttpStatus.OK;
        //Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorTypeId}", actuatorTypeName)).andReturn();
        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The getActuatorTypeById method should " +
                "return a OK status when the ActuatorType exists.");
    }

    /**
     * This test checks if the getActuatorTypeById method returns a non-null response when the ActuatorType exists.
     */
    @Test
    void testGetActuatorTypeByIdExists() throws Exception {
        //Arrange
        String actuatorTypeName = "BlindRoller";
        ActuatorTypeName actuatorTypeName1 = new ActuatorTypeName(actuatorTypeName);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeName1);
        when(mockActuatorTypeRepository.findByIdentity(actuatorTypeName1)).thenReturn(Optional.of(actuatorType));
        Link link = Link.of("http://localhost/actuatortypes/BlindRoller");
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO("BlindRoller");
        EntityModel<ActuatorTypeDTO> expected = EntityModel.of(actuatorTypeDTO, link);
        //Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorTypeId}", actuatorTypeName)).andReturn();
        //Assert
        EntityModel<ActuatorTypeDTO> resultContent = objectMapper.readValue(result.getResponse().getContentAsString()
                , new TypeReference<>() {
        });
        assertEquals(expected, resultContent, "The getActuatorTypeById method should return the correct " +
                "ActuatorTypeDTO.");
    }

}