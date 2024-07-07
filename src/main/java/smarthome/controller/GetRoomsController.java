package smarthome.controller;

import smarthome.domain.room.Room;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;

import java.util.List;

/**
 * Controller class responsible for fetching the list of rooms in a house.
 * It interacts with the RoomRepositoryMemImpl to fetch the rooms.
 */
public class GetRoomsController {

    private final IRoomService roomService;

    private final RoomMapper roomMapper;

    /**
     * Constructor for GetRoomsController.
     * Initializes the RoomService and RoomMapper.
     *
     * @throws IllegalArgumentException if the roomService or roomMapper is invalid.
     */
    public GetRoomsController(IRoomService roomService, RoomMapper roomMapper) {
        if (roomService == null || roomMapper == null) {
            throw new IllegalArgumentException();
        }
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    /**
     * Fetches the list of rooms from the room service.
     *
     * @return List of RoomDTO objects representing the rooms.
     * @throws IllegalAccessException if the current context does not have access to the room service.
     */
    public List<RoomDTO> getRooms() throws IllegalAccessException {
        List<Room> rooms = roomService.getRooms();

        return roomMapper.toRoomsDTO(rooms);
    }
}