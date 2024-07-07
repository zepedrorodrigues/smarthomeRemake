package smarthome.service.impl;

import org.springframework.stereotype.Service;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.service.IRoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing rooms.
 */
@Service
public class RoomServiceImpl implements IRoomService {

    private final IHouseRepository houseRepository;
    private final IRoomRepository roomRepository;
    private final RoomFactory roomFactory;

    /**
     * Constructor.
     *
     * @param houseRepository the house repository
     * @param roomRepository  the room repository
     * @param roomFactory     the room factory
     */
    public RoomServiceImpl(IHouseRepository houseRepository, IRoomRepository roomRepository,
                           RoomFactory roomFactory) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.roomFactory = roomFactory;
    }

    /**
     * Adds a new room to the house.
     *
     * @param houseId    the identity of the house
     * @param roomName   the name of the room
     * @param floor      the floor of the room
     * @param dimensions the dimensions of the room
     * @return the newly added room
     */
    @Override
    public Room addRoom(HouseName houseId, RoomName roomName, Floor floor, Dimensions dimensions) {
        try {
            if (!houseRepository.containsIdentity(houseId)) {
                return null;
            }
            Room room = roomFactory.createRoom(roomName, houseId, floor, dimensions);
            return roomRepository.save(room);

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
    public List<Room> getRooms() {
        Iterable<Room> rooms = roomRepository.findAll();
        List<Room> roomList = new ArrayList<>();

        rooms.forEach(roomList::add);

        return roomList;
    }

    /**
     * Get a Room entity by its identity.
     *
     * @param roomId the identity of the Room entity
     * @return the Room entity
     */
    public Optional<Room> getRoomById(RoomId roomId){
        return roomRepository.findByIdentity(roomId);
    }

    /**
     * Get the identity of all Room entities in the repository by house name.
     *
     * @param houseName The name of the house.
     * @return A list of all Room identities.
     */
    @Override
    public List<RoomId> getRoomIdsByHouseName(HouseName houseName) {
        Iterable<RoomId> roomIds = roomRepository.findRoomIdsByHouseName(houseName);
        List<RoomId> roomIdList = new ArrayList<>();

        roomIds.forEach(roomIdList::add);

        return roomIdList;
    }

    /**
     * Get the identity of all Room entities in the repository.
     *
     * @return An Iterable of all Room identities.
     */
    @Override
    public List<RoomId> getRoomIds() {
        Iterable<RoomId> roomIds = roomRepository.findRoomIds();
        List<RoomId> roomIdList = new ArrayList<>();

        roomIds.forEach(roomIdList::add);

        return roomIdList;
    }

}
