package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * REST controller for managing rooms.
 */
@RestController
@RequestMapping("/rooms")
public class RoomRESTController {

    private final IRoomService roomService;
    private final RoomMapper roomMapper;

    /**
     * Constructor.
     *
     * @param roomService the room service
     * @param roomMapper  the room mapper
     */
    @Autowired
    public RoomRESTController(IRoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    /**
     * Adds a new room to the house.
     *
     * @param houseId the id of the house
     * @param roomDTO the room to be created data
     * @return the response entity with the newly added room data
     */
    @PostMapping("/house/{houseId}")
    public ResponseEntity<RoomDTO> addRoom(@PathVariable("houseId") String houseId, @RequestBody RoomDTO roomDTO) {
        if (roomDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HouseName houseIdVO = new HouseName(houseId);
            RoomName roomName = roomMapper.toRoomName(roomDTO);
            Floor floor = roomMapper.toFloor(roomDTO);
            Dimensions dimensions = roomMapper.toDimensions(roomDTO);
            Room savedRoom = roomService.addRoom(houseIdVO, roomName, floor, dimensions);
            if (savedRoom != null) {
                RoomDTO savedRoomDTO = roomMapper.toRoomDTO(savedRoom);
                addLinksToRoomDTO(savedRoomDTO);
                return new ResponseEntity<>(savedRoomDTO, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Gets a room by its id.
     *
     * @param roomId the id of the room
     * @return the response entity with the room data
     */
    @GetMapping(value = "/{roomId}", produces = {"application/hal+json"})
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable("roomId") String roomId) {
        RoomId roomId1 = new RoomId(roomId);
        Optional<Room> roomOptional = roomService.getRoomById(roomId1);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            RoomDTO roomDto = roomMapper.toRoomDTO(room);

            addLinksToRoomDTO(roomDto);
            return new ResponseEntity<>(roomDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Gets all rooms.
     *
     * @return the response entity with the list of rooms
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<RoomDTO>> getRooms() {
        List<RoomId> roomIds = roomService.getRoomIds();
        List<RoomDTO> rooms = toRoomDTOs(roomIds);

        CollectionModel<RoomDTO> result = CollectionModel.of(rooms);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Gets all rooms in a house.
     *
     * @return the response entity with the list of rooms
     */
    @GetMapping(value = "/house/{houseId}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<RoomDTO>> getRoomsByHouseName(@PathVariable("houseId") String houseName) {
        HouseName houseName1 = new HouseName(houseName);
        List<RoomId> roomIds = roomService.getRoomIdsByHouseName(houseName1);
        List<RoomDTO> rooms = toRoomDTOs(roomIds);

        CollectionModel<RoomDTO> result = CollectionModel.of(rooms);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Converts a list of room ids to a list of room DTOs with links.
     *
     * @param roomIds the list of room ids
     * @return the list of room DTOs
     */
    private List<RoomDTO> toRoomDTOs(List<RoomId> roomIds) {
        List<RoomDTO> rooms = roomMapper.toRoomIdsDTO(roomIds);

        for (RoomDTO roomDTO : rooms) {
            String roomId = roomDTO.getRoomId();
            Link selflink = linkTo(methodOn(RoomRESTController.class).getRoomById(roomId)).withSelfRel();
            roomDTO.add(selflink);
        }
        return rooms;
    }

    /**
     * Adds HATEOAS links to the room DTO.
     *
     * @param roomDTO the room DTO to which the links will be added
     */
    private void addLinksToRoomDTO(RoomDTO roomDTO) {
        Link selflink = linkTo(methodOn(RoomRESTController.class).getRoomById(roomDTO.getRoomId())).withSelfRel();
        Link roomDevicesLink = linkTo(methodOn(DeviceRESTController.class).getDevicesInRoom(roomDTO.getRoomId()))
                .withRel("room-devices");
        roomDTO.add(selflink, roomDevicesLink);
    }
}
