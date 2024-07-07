package smarthome.service.impl;

import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomName;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IRoomService;

import java.util.List;
import java.util.Optional;

/**
 * The RoomServiceImpl class is a service class that provides methods for adding a room and getting the list of
 * rooms in a house.
 */
public class RoomServiceImpl implements IRoomService {

    private final IHouseRepository houseRepository;
    private final IRoomRepository roomRepository;
    private final RoomFactory roomFactory;
    private final RoomMapper roomMapper;

    /**
     * Constructs a new RoomServiceImpl.
     *
     * @param houseRepository the house repository to use
     * @param roomRepository  the room repository to use
     * @param roomFactory     the room factory to use
     * @param roomMapper      the room mapper to use
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public RoomServiceImpl(IHouseRepository houseRepository, IRoomRepository roomRepository, RoomFactory roomFactory,
                           RoomMapper roomMapper) {
        if (houseRepository == null || roomRepository == null || roomFactory == null || roomMapper == null) {
            throw new IllegalArgumentException();
        }

        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.roomFactory = roomFactory;
        this.roomMapper = roomMapper;
    }

    /**
     * Adds a new room to the house based on the provided RoomDTO.
     *
     * @param roomDTO the RoomDTO containing the room information
     * @return the RoomDTO of the newly added room, or null if the room name already exists
     * or any parameter is invalid
     */
    @Override
    public RoomDTO addRoom(RoomDTO roomDTO) {
        try {
            // Get House Name
            Optional<House> house = houseRepository.getEntity();
            if (house.isEmpty()) {
                return null;
            }
            HouseName houseName = house.get().getIdentity();
            // Create Room
            RoomName roomName = roomMapper.toRoomName(roomDTO);
            Floor floor = roomMapper.toFloor(roomDTO);
            Dimensions dimensions = roomMapper.toDimensions(roomDTO);
            Room room = roomFactory.createRoom(roomName, houseName, floor, dimensions);
            // Save the room
            Room savedRoom = roomRepository.save(room);
            return roomMapper.toRoomDTO(savedRoom);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the list of rooms in the house.
     *
     * @return the list of rooms in the house
     */
    @Override
    public List<RoomDTO> getRooms() {
        Iterable<Room> rooms = roomRepository.findAll();

        return roomMapper.toRoomsDTO(rooms);
    }
}
