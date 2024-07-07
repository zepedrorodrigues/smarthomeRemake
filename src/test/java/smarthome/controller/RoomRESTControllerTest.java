package smarthome.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.Height;
import smarthome.domain.room.vo.Length;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.domain.room.vo.Width;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;
import smarthome.service.impl.RoomServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Integration tests for RoomRESTController.
 */
class RoomRESTControllerTest {

    private String houseId;
    private String houseName;
    private String roomName;
    private int floor;
    private double height;
    private double width;
    private double length;

    private IRoomService roomService;
    private IHouseRepository mockHouseRepository;
    private IRoomRepository mockRoomRepository;
    private RoomFactory roomFactory;
    private RoomMapper roomMapper;

    private RoomRESTController roomRESTController;

    private String uri;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    /**
     * Setup before each test.
     */
    @BeforeEach
    void setUp() {
        mockHouseRepository = mock(IHouseRepository.class);

        roomName = "testRoomName";
        houseId = "testHouseName";
        houseName = "testHouseName";
        floor = 1;
        height = 10.0;
        width = 10.0;
        length = 10.0;

        mockRoomRepository = mock(IRoomRepository.class);
        when(mockRoomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.<Room>getArgument(0));

        roomFactory = new RoomFactoryImpl();
        roomService = new RoomServiceImpl(mockHouseRepository, mockRoomRepository, roomFactory);
        roomMapper = new RoomMapper();

        roomRESTController = new RoomRESTController(roomService, roomMapper);

        uri = "/rooms";
        mvc = MockMvcBuilders.standaloneSetup(roomRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test that a RoomRESTController can be correctly constructed.
     */
    @Test
    void testRoomRESTControllerCanBeConstructed() {
        RoomRESTController roomRESTController = new RoomRESTController(roomService, roomMapper);
        assertNotNull(roomRESTController,
                "RoomRESTController should be successfully constructed.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the room dto is null
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenRoomDTOIsNull() {
        //Arrange
        RoomDTO nullRoomDTO = null;

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, nullRoomDTO);

        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The HTTP status for a null room dto should be BAD_REQUEST.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the house id is null
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenHouseIdIsNull() {
        //Arrange
        String nullHouseId = null;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);

        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(nullHouseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a null house id should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the house id is null
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenHouseIdIsBlank() {
        //Arrange
        String blankHouseId = " ";
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);

        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(blankHouseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a null house id should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the room name is null
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenRoomNameIsNull() {
        //Arrange
        String nullRoomName = null;
        RoomDTO roomDTO = new RoomDTO(nullRoomName, floor, height, width, length);

        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a null room name should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the room name is blank
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenRoomNameIsBlank() {
        //Arrange
        String blankRoomName = " ";
        RoomDTO roomDTO = new RoomDTO(blankRoomName, floor, height, width, length);

        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a blank room name should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the height is zero
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenHeightIsZero() {
        //Arrange
        double zeroHeight = 0.0;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, zeroHeight, width, length);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a zero height value should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the height is negative
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenHeightIsNegative() {
        //Arrange
        double negativeHeight = -1.0;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, negativeHeight, width, length);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a negative height value should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the width is zero
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenWidthIsZero() {
        //Arrange
        double zeroWidth = 0.0;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, zeroWidth, length);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a zero width value should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the width is negative
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenWidthIsNegative() {
        //Arrange
        double negativeWidth = -1.0;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, negativeWidth, length);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a negative width value should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the length is zero
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenLengthIsZero() {
        //Arrange
        double zeroLength = 0.0;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, zeroLength);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a zero length value should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the length is negative
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenLengthIsNegative() {
        //Arrange
        double negativeLength = -1.0;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, negativeLength);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a negative length value should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the house id is not present in the repository
     */
    @Test
    void testAddRoomReturnsCorrectHTTPStatusWhenHouseIdIsNotPresentInRepository() {
        //Arrange
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(false);

        //Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(),
                "The HTTP status for a house id that is not present in the repository " +
                        "should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method checks if the house id is present in the repository
     */
    @Test
    void testAddRoomChecksIfHouseIdIsPresentInTheRepository() {
        //Arrange
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);

        //Act
        roomRESTController.addRoom(houseId, roomDTO);

        //Assert
        verify(mockHouseRepository).containsIdentity(new HouseName(houseId));
    }

    /**
     * Test if the addRoom method returns correct HTTP status when the room is created and saved
     */
    @Test
    void testAddRoomCorrectlySavesARoomAndReturnsCorrectHTTPStatusCode() {
        // Arrange
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);

        // Act
        ResponseEntity<RoomDTO> result = roomRESTController.addRoom(houseId, roomDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode(),
                "The HTTP status for a successful room creation should be CREATED.");
    }

    /**
     * Test if the addRoom method returns correct response body when the room is created and saved
     */
    @Test
    void testAddRoomCorrectlySavesARoomAndReturnsCorrectResponseBody() {
        // Arrange
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);

        // Act
        ResponseEntity<RoomDTO> responseEntity = roomRESTController.addRoom(houseId, roomDTO);
        RoomDTO responseBody = Objects.requireNonNull(responseEntity.getBody());

        // Assert
        assertTrue(!responseBody.getRoomId().isBlank() &&
                        responseBody.getRoomName().equals(roomName) &&
                        responseBody.getHouseName().equals(houseId) &&
                        responseBody.getFloor() == floor &&
                        responseBody.getHeight() == height &&
                        responseBody.getWidth() == width &&
                        responseBody.getLength() == length,
                "The response body should contain the correct room data.");
    }

    /**
     * Test if the addRoom method correctly handles a post request with valid JSON data and returns correct HTTP
     * status code.
     */
    @Test
    void testAddRoomCorrectlyHandlesAPostRequestWithValidJSONDataAndReturnsCorrectHTTPStatusCode()
            throws Exception {
        //Arrange
        String addRoomUri = "/rooms/house/" + houseId;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        String dtoAsJSON = objectMapper.writeValueAsString(roomDTO);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);

        HttpStatus expected = HttpStatus.CREATED;

        //Act
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(addRoomUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status for a successful post request and correct room creation should be CREATED.");
    }

    /**
     * Test if the addRoom method correctly handles a post request with valid JSON data and returns correct
     * Response Body
     */
    @Test
    void testAddRoomCorrectlyHandlesAPostRequestWithValidJSONDataAndReturnsCorrectResponseBody()
            throws Exception {
        //Arrange
        String addRoomUri = "/rooms/house/" + houseId;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        String dtoAsJSON = objectMapper.writeValueAsString(roomDTO);

        Room room = roomFactory.createRoom(new RoomName(roomName), new HouseName(houseId), new Floor(floor),
                new Dimensions(new Width(width), new Height(height), new Length(length)));
        when(mockRoomRepository.save(any(Room.class))).thenReturn(room);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(true);

        RoomDTO expectedRoomDTO = roomMapper.toRoomDTO(room);
        expectedRoomDTO
                .add(Link.of("http://localhost/rooms/" + expectedRoomDTO.getRoomId()))
                .add(Link.of("http://localhost/devices/room/" + expectedRoomDTO.getRoomId())
                        .withRel("room-devices"));
        String expected = objectMapper.writeValueAsString(expectedRoomDTO);

        //Act
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(addRoomUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        //Assert
        assertEquals(expected, result.getResponse().getContentAsString(),
                "The response body should contain the correct room data.");
    }

    /**
     * Test if the addRoom method correctly handles a post request with invalid JSON data and returns correct HTTP
     * status code.
     * In this test the invalid data is a blank room name.
     */
    @Test
    void testAddRoomCorrectlyHandlesAPostRequestWithInvalidJSONDataAndReturnsCorrectHTTPStatusCode()
            throws Exception {
        //Arrange
        String addRoomUri = "/rooms/house/" + houseId;
        String invalidRoomName = " ";
        RoomDTO roomDTO = new RoomDTO(invalidRoomName, floor, height, width, length);
        String dtoAsJSON = objectMapper.writeValueAsString(roomDTO);

        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;

        //Act
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(addRoomUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status for a post request with invalid JSON data should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method correctly handles a post request with valid JSON data and returns correct HTTP
     * status code but the house id is not present in the repository.
     */
    @Test
    void testAddRoomCorrectlyHandlesAPostRequestWhenHouseIdIsNotPresentAndReturnsCorrectHTTPStatusCode()
            throws Exception {
        //Arrange
        String addRoomUri = "/rooms/house/" + houseId;
        RoomDTO roomDTO = new RoomDTO(roomName, floor, height, width, length);
        String dtoAsJSON = objectMapper.writeValueAsString(roomDTO);
        when(mockHouseRepository.containsIdentity(new HouseName(houseId))).thenReturn(false);

        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;

        //Act
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(addRoomUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status for a post request when the house id is not present in the repository " +
                        "should be UNPROCESSABLE_ENTITY.");
    }

    /**
     * Test if the addRoom method correctly handles a bad post request with invalid JSON and returns correct HTTP
     * status code.
     * JSON is null/blank.
     */
    @Test
    void testAddRoomCorrectlyHandlesAPostRequestWithInvalidJSONAndReturnsCorrectHTTPStatusCode()
            throws Exception {
        //Arrange
        String addRoomUri = "/rooms/house/" + houseId;
        RoomDTO roomDTO = null;
        String dtoAsJSON = objectMapper.writeValueAsString(roomDTO);
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        //Act
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(addRoomUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status for a post request with null/blank JSON should be BAD_REQUEST.");
    }

    /**
     * Test if the getRoomById method returns correct HTTP status when the room does not exist.
     */
    @Test
    void testGetRoomByIdDoesNotExistHttpStatus() throws Exception {
        //Arrange
        String roomId = "123";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        //Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri + "/{roomId}", roomId))
                .andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status for a room that does not exist should be NOT_FOUND.");
    }

    /**
     * Test to check that when a valid http get request is sent to the /rooms uri, the response http status code is 200.
     */
    @Test
    void testGetRoomByIdExistsHttpStatus() throws Exception {
        //Arrange
        String roomId = "123";
        RoomId roomId1 = new RoomId(roomId);
        RoomName roomName1 = new RoomName(roomName);
        HouseName houseName1 = new HouseName(houseName);
        Floor floor1 = new Floor(floor);
        Width width1 = new Width(width);
        Height height1 = new Height(height);
        Length length1 = new Length(length);
        Dimensions dimensions = new Dimensions(width1, height1, length1);
        Room room = roomFactory.createRoom(roomId1, roomName1, houseName1, floor1, dimensions);
        when(mockRoomRepository.findByIdentity(roomId1)).thenReturn(Optional.of(room));
        HttpStatus expected = HttpStatus.OK;
        //Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri + "/{roomId}", roomId))
                .andReturn();

        //Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "The HTTP status for a room that exists should be OK.");
    }

    /**
     * Test to check that when a valid http get request is sent to the /rooms uri,
     * the response body has the correct room data.
     */
    @Test
    void testGetRoomByIdExists() throws Exception {
        //Arrange
        String roomId = "123";
        RoomId roomId1 = new RoomId(roomId);
        RoomName roomName1 = new RoomName(roomName);
        HouseName houseName1 = new HouseName(houseName);
        Floor floor1 = new Floor(floor);
        Width width1 = new Width(width);
        Height height1 = new Height(height);
        Length length1 = new Length(length);
        Dimensions dimensions = new Dimensions(width1, height1, length1);
        Room room = roomFactory.createRoom(roomId1, roomName1, houseName1, floor1, dimensions);
        when(mockRoomRepository.findByIdentity(roomId1)).thenReturn(Optional.of(room));
        Link selfLink = Link.of("http://localhost/rooms/123", "self");
        Link devicesLink = Link.of("http://localhost/devices/room/123", "room-devices");
        RoomDTO roomDto = new RoomDTO(roomId, houseName, roomName, floor, height, width, length);
        EntityModel<RoomDTO> expected = EntityModel.of(roomDto, selfLink, devicesLink);

        //Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri + "/{roomId}", roomId))
                .andReturn();

        //Assert
        EntityModel<RoomDTO> resultContent = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(expected, resultContent,
                "The response body should contain the correct room data.");
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms uri, the response http status code is 200.
     */
    @Test
    void testGetRoomsEmptyListHttpStatusCode200() throws Exception {
        //Arrange
        List<RoomId> emptyList = new ArrayList<>();
        when(mockRoomRepository.findRoomIds()).thenReturn(emptyList);

        int expected = HttpStatus.OK.value();
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andReturn();

        //Assert
        assertEquals(expected, result.getResponse().getStatus(), "Should be http code 200");
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms uri, the response
     * content type is application/json.
     */
    @Test
    void testGetRoomsResponseContentType() throws Exception {
        //Arrange
        List<RoomId> emptyList = new ArrayList<>();
        when(mockRoomRepository.findRoomIds()).thenReturn(emptyList);

        String expected = "application/hal+json";

        //Act and Assert
        mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andExpect(MockMvcResultMatchers.content().contentType(expected));
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms uri, the response content is an empty list.
     */
    @Test
    void testGetRoomsEmptyList() throws Exception {
        //Arrange
        List<RoomId> emptyList = new ArrayList<>();
        when(mockRoomRepository.findRoomIds()).thenReturn(emptyList);

        ObjectMapper objectMapper = new ObjectMapper();

        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andReturn();

        //Assert
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString()); // root node - all json
        JsonNode roomListNode = jsonNode.get("content"); // content node / _embedded - list of rooms

        assertTrue(roomListNode.isEmpty(), "Should be an empty list");
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms uri, the response content
     * is the expected list of rooms.
     */
    @Test
    void testGetRoomsListWithRooms() throws Exception {
        //Arrange
        HouseName houseName = new HouseName("House");
        Floor floor = new Floor(1);

        RoomName roomName = new RoomName("Living Room");
        Width width = new Width(7);
        Height height = new Height(10);
        Length length = new Length(9);
        Dimensions dimensions = new Dimensions(width, height, length);
        Room room1 = roomFactory.createRoom(roomName, houseName, floor, dimensions);

        RoomName roomName2 = new RoomName("Bedroom");
        Width width2 = new Width(3);
        Height height2 = new Height(3);
        Length length2 = new Length(7);
        Dimensions dimensions2 = new Dimensions(width2, height2, length2);
        Room room2 = roomFactory.createRoom(roomName2, houseName, floor, dimensions2);

        List<RoomId> rooms = List.of(room1.getIdentity(), room2.getIdentity());
        when(mockRoomRepository.findRoomIds()).thenReturn(rooms);

        RoomDTO roomDTO1 = roomMapper.toRoomDTO(room1);
        RoomDTO roomDTO2 = roomMapper.toRoomDTO(room2);
        List<RoomDTO> expected = List.of(roomDTO1, roomDTO2);


        //Act + Assert
        mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/hal+json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].roomId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", expected.size()).exists());


    }

    /**
     * Test to check when a valid http get request is sent to the /rooms/house/{houseId} uri,
     * the response http status code is 200.
     */
    @Test
    void testGetRoomsByHouseNameEmptyListHttpStatusCode200() throws Exception {
        //Arrange
        HouseName houseName1 = new HouseName(houseName);
        List<RoomId> emptyList = new ArrayList<>();
        when(mockRoomRepository.findRoomIdsByHouseName(houseName1)).thenReturn(emptyList);

        int expected = HttpStatus.OK.value();

        String uri = "/rooms/house/" + houseName;
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andReturn();

        //Assert
        assertEquals(expected, result.getResponse().getStatus(), "Should be http code 200");
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms/house/{houseId} uri, the response
     * content type is application/json.
     */
    @Test
    void testGetRoomsByHouseNameResponseContentType() throws Exception {
        //Arrange
        HouseName houseName1 = new HouseName(houseName);
        List<RoomId> emptyList = new ArrayList<>();
        when(mockRoomRepository.findRoomIdsByHouseName(houseName1)).thenReturn(emptyList);

        String expected = "application/hal+json";

        String uri = "/rooms/house/" + houseName;
        //Act and Assert
        mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andExpect(MockMvcResultMatchers.content().contentType(expected));
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms/house/{houseId} uri,
     * the response content is an empty list.
     */
    @Test
    void testGetRoomsByHouseNameEmptyList() throws Exception {
        //Arrange
        HouseName houseName1 = new HouseName(houseName);
        List<RoomId> emptyList = new ArrayList<>();
        when(mockRoomRepository.findRoomIdsByHouseName(houseName1)).thenReturn(emptyList);

        ObjectMapper objectMapper = new ObjectMapper();

        String uri = "/rooms/house/" + houseName;
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andReturn();

        //Assert
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString()); // root node - all json
        JsonNode roomListNode = jsonNode.get("content"); // content node / _embedded - list of rooms

        assertTrue(roomListNode.isEmpty(), "Should be an empty list");
    }

    /**
     * Test to check when a valid http get request is sent to the /rooms/house/{houseId} uri, the response content
     * is the expected list of rooms.
     */
    @Test
    void testGetRoomsByHouseNameListWithRooms() throws Exception {
        //Arrange
        HouseName houseName = new HouseName("House");
        Floor floor = new Floor(1);

        RoomName roomName = new RoomName("Living Room");
        Width width = new Width(7);
        Height height = new Height(10);
        Length length = new Length(9);
        Dimensions dimensions = new Dimensions(width, height, length);
        Room room1 = roomFactory.createRoom(roomName, houseName, floor, dimensions);

        RoomName roomName2 = new RoomName("Bedroom");
        Width width2 = new Width(3);
        Height height2 = new Height(3);
        Length length2 = new Length(7);
        Dimensions dimensions2 = new Dimensions(width2, height2, length2);
        Room room2 = roomFactory.createRoom(roomName2, houseName, floor, dimensions2);

        List<RoomId> rooms = List.of(room1.getIdentity(), room2.getIdentity());
        when(mockRoomRepository.findRoomIdsByHouseName(houseName)).thenReturn(rooms);

        RoomDTO roomDTO1 = roomMapper.toRoomDTO(room1);
        RoomDTO roomDTO2 = roomMapper.toRoomDTO(room2);
        List<RoomDTO> expected = List.of(roomDTO1, roomDTO2);

        String uri = "/rooms/house/" + houseName.getName();
        //Act + Assert
        mvc.perform(MockMvcRequestBuilders.get(uri).accept("application/hal+json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/hal+json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].roomId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", expected.size()).exists());


    }
}
