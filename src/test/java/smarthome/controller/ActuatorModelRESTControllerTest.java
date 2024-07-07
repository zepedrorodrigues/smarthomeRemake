package smarthome.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorModelNameDTO;
import smarthome.mapper.mapper.ActuatorModelMapper;
import smarthome.service.IActuatorModelService;
import smarthome.service.impl.ActuatorModelServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorModelRESTControllerTest {

    ActuatorModelRESTController actuatorModelRESTController;
    IActuatorModelService actuatorModelService;
    ActuatorModelMapper actuatorModelMapper;
    ActuatorModel actuatorModel;
    ActuatorModelName actuatorModelName;
    IActuatorModelRepository mockActuatorModelRepository;
    ActuatorTypeName actuatorTypeName;

    private String uri;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockActuatorModelRepository = mock(IActuatorModelRepository.class);
        actuatorModelService = new ActuatorModelServiceImpl(mockActuatorModelRepository);
        actuatorModelMapper = new ActuatorModelMapper();
        actuatorModelRESTController = new ActuatorModelRESTController(actuatorModelService, actuatorModelMapper);
        actuatorModelName = new ActuatorModelName("actuatorModelName");
        actuatorTypeName = new ActuatorTypeName("actuatorTypeName");
        actuatorModel = new ActuatorModel(actuatorModelName, actuatorTypeName);

        uri = "/actuatormodels";
        mvc = MockMvcBuilders.standaloneSetup(actuatorModelRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /*
    The constructor of the ActuatorModelRESTController class should work correctly.
     */
    @Test
    void testActuatorModelRESTControllerConstructor() {
        ActuatorModelRESTController actuatorModelRESTController =
                new ActuatorModelRESTController(actuatorModelService, actuatorModelMapper);
        assertNotNull(actuatorModelRESTController,
                "The constructor of the ActuatorModelRESTController class should " + "work correctly.");
    }

    /*
     *Test if the getActuatorModelByName method returns a NOT_FOUND status when the ActuatorModel does not exist.
     */
    @Test
    void testGetActuatorModelByNameDoesNotExistHttpStatus() throws Exception {
        // Arrange
        String actuatorModelName = "actuatorModelName";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorModelName}", actuatorModelName)).andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The getActuatorModelByName method should " + "return a NOT_FOUND status when the ActuatorModel does "
                        + "not exist.");
    }

    /*
     *Test if the getActuatorModelByName method returns an OK status when the ActuatorModel exists.
     */
    @Test
    void testGetActuatorModelByNameExistsHttpStatus() throws Exception {
        // Arrange
        String actuatorModelName = "BlindRoller";
        ActuatorModelName actuatorModelNameVo = new ActuatorModelName(actuatorModelName);
        when(mockActuatorModelRepository.findByIdentity(actuatorModelNameVo)).thenReturn(java.util.Optional.of(actuatorModel));
        HttpStatus expected = HttpStatus.OK;
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorModelName}", actuatorModelName)).andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The getActuatorModelByName method should " + "return an OK status when the ActuatorModel exists.");
    }

    /*
     *Test if the getActuatorModelByName method returns a non-null ActuatorModelDTO when the ActuatorModel exists.
     */
    @Test
    void testGetActuatorModelByNameExists() throws Exception {
        // Arrange
        String actuatorModelName = "actuatorModelName";
        ActuatorModelName actuatorModelNameVo = new ActuatorModelName(actuatorModelName);
        when(mockActuatorModelRepository.findByIdentity(actuatorModelNameVo)).thenReturn(java.util.Optional.of(actuatorModel));
        Link link = Link.of("http://localhost/actuatormodels/actuatorModelName");
        ActuatorModelDTO actuatorModelDTO = new ActuatorModelDTO("actuatorModelName", "actuatorTypeName");
        EntityModel<ActuatorModelDTO> expected = EntityModel.of(actuatorModelDTO, link);
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorModelName}", actuatorModelName)).andReturn();
        // Assert
        EntityModel<ActuatorModelDTO> resultContent =
                objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals(expected, resultContent, "The getActuatorModelByName method should return the correct " +
                "ActuatorModelDTO.");
    }

    /**
     * Test if the getActuatorModelByName method returns the correct ActuatorModelNameDTO when the ActuatorModel exists.
     */
    @Test
    void testGetActuatorModelByNameReturnsCorrectDTO() {
        //Arrange
        String actuatorModelName = "actuatorModelName";
        ActuatorModelName actuatorModelNameVo = new ActuatorModelName(actuatorModelName);
        when(mockActuatorModelRepository.findByIdentity(actuatorModelNameVo)).thenReturn(java.util.Optional.of(actuatorModel));
        ActuatorModelNameDTO actuatorModelNameDTO = new ActuatorModelNameDTO(actuatorModelName);
        //Act
        ResponseEntity<EntityModel<ActuatorModelDTO>> result =
                actuatorModelRESTController.getActuatorModelByName(actuatorModelName);
        //Assert
        assertEquals(actuatorModelNameDTO.getActuatorModelName(),
                result.getBody().getContent().getActuatorModelName(), "The getActuatorModelByName method should " +
                        "return the correct ActuatorModelNameDTO when the ActuatorModel exists.");
    }

    /*
     *Test if the getActuatorModelsByActuatorTypeIdentity method returns a NOT_FOUND status when the ActuatorModel does
     * not exist in the repository with the specified ActuatorType name.
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentityDoesNotExistHttpStatus() throws Exception {
        // Arrange
        String actuatorTypeName = "actuatorTypeName";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/type/{actuatorTypeName}", actuatorTypeName)).andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The getActuatorModelsByActuatorTypeIdentity"
                + " " + "method should return a NOT_FOUND status when the ActuatorModel does not exist in the " +
                "repository " + "with" + " " + "the specified ActuatorType name.");
    }

    /*
     *Test if the getActuatorModelsByActuatorTypeIdentity method returns an OK status when the ActuatorModel exists.
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentityExistsHttpStatus() throws Exception {
        // Arrange
        String actuatorTypeName = "actuatorTypeName";
        when(mockActuatorModelRepository.findActuatorModelNamesByActuatorTypeName(new ActuatorTypeName(actuatorTypeName))).thenReturn(List.of(actuatorModelName));
        HttpStatus expected = HttpStatus.OK;
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/type/{actuatorTypeName}", actuatorTypeName)).andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The getActuatorModelsByActuatorTypeIdentity"
                + " " + "method should return an OK status when the ActuatorModel exists.");
    }


    /*
     *Test if the getActuatorModelsByActuatorTypeIdentity method returns a non-null response when the ActuatorModel
     * exists.
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentityExistsNotNull() throws Exception {
        // Arrange
        ActuatorTypeName actuatorTypeName = new ActuatorTypeName("actuatorTypeName");
        when(mockActuatorModelRepository.findActuatorModelNamesByActuatorTypeName(actuatorTypeName)).thenReturn(List.of(actuatorModelName));
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/type/{actuatorTypeName}", actuatorTypeName)).andReturn();
        // Assert
        assertNotNull(result.getResponse().getContentAsString(), "The getActuatorModelsByActuatorTypeIdentity method "
                + "should not return null when the ActuatorModel exists.");
    }


    /*
     *Test if the getActuatorModelsByActuatorTypeIdentity method returns a BAD_REQUEST status when the ActuatorType
     * identity is blank.
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentityWithBlankIdentity() throws Exception {
        // Arrange
        String actuatorTypeName = "";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri + "/type/{actuatorTypeName}", actuatorTypeName)).andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The getActuatorModelsByActuatorTypeIdentity"
                + " " + "method should return a BAD_REQUEST status when the ActuatorType identity is blank.");
    }

    /**
     * Test if the getActuatorModelsByActuatorTypeIdentity method returns an OK status when the ActuatorModel exists.
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentityReturnsOk() {
        // Arrange
        String actuatorTypeName = "actuatorTypeName";
        ActuatorTypeName actuatorTypeNameVo = new ActuatorTypeName(actuatorTypeName);
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        when(mockActuatorModelRepository.findActuatorModelNamesByActuatorTypeName(actuatorTypeNameVo)).thenReturn(List.of(actuatorModelName));
        HttpStatus expected = HttpStatus.OK;
        // Act
        ResponseEntity<CollectionModel<ActuatorModelNameDTO>> result =
                actuatorModelRESTController.getActuatorModelsByActuatorTypeIdentity(actuatorTypeName);
        // Assert
        assertEquals(expected, result.getStatusCode(), "The getActuatorModelsByActuatorTypeIdentity" + " " + "method "
                + "should return an OK status when the ActuatorModel exists.");
    }
}