package smarthome.controller;

import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomName;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;

/**
 * The AddRoomController is a controller class that handles the addition of a new room to the system.
 * It uses a RoomService to add the new room.
 */
public class AddRoomController {

    private final IRoomService roomService;
    private final RoomMapper roomMapper;

    /**
     * Constructs a new AddRoomController.
     *
     * @param roomService the room service to use
     * @throws IllegalArgumentException if the room service is null
     */
    public AddRoomController(IRoomService roomService, RoomMapper roomMapper) {
        if (roomService == null || roomMapper == null) {
            throw new IllegalArgumentException();
        }
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    /**
     * Adds a new room to the house based on the provided RoomDTO.
     *
     * @param roomDTO the RoomDTO containing the room information
     * @return the RoomDTO of the newly added room, or null if the room name already exists
     * or any parameter is invalid
     */
    public RoomDTO addRoom(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }
        try {
            HouseName houseId = roomMapper.toHouseName(roomDTO);
            RoomName roomName = roomMapper.toRoomName(roomDTO);
            Floor floor = roomMapper.toFloor(roomDTO);
            Dimensions dimensions = roomMapper.toDimensions(roomDTO);
            Room savedRoom = roomService.addRoom(houseId, roomName, floor, dimensions);
            if (savedRoom != null) {
                return roomMapper.toRoomDTO(savedRoom);

            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
