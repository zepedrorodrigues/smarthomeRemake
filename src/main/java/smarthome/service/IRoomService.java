package smarthome.service;

import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;

import java.util.List;
import java.util.Optional;

/**
 * Service interface used to define the methods for managing rooms.
 */
public interface IRoomService {

    /**
     * Adds a new room to the house.
     *
     * @param houseName  the name of the house
     * @param roomName   the name of the room
     * @param floor      the floor of the room
     * @param dimensions the dimensions of the room
     * @return the newly added room
     */
    Room addRoom(HouseName houseName, RoomName roomName, Floor floor, Dimensions dimensions);

    /**
     * Gets the list of rooms in the house.
     *
     * @return the list of rooms in the house
     */
    List<Room> getRooms();

    /**
     * Get the identity of all Room entities in the repository.
     *
     * @return An Iterable of all Room identities.
     */
    List<RoomId> getRoomIds();

    /**
     * Get a Room entity by its identity.
     *
     * @param roomId The identity of the Room entity.
     * @return An Optional of the Room entity.
     */
    Optional<Room> getRoomById(RoomId roomId);

    /**
     * Get the identity of all Room entities in the repository by house name.
     *
     * @param houseName The name of the house.
     * @return An Iterable of all Room identities.
     */
    List<RoomId> getRoomIdsByHouseName(HouseName houseName);
}
