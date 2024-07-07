package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.RoomId;

/**
 * Repository for rooms.
 */
public interface IRoomRepository extends IRepository<RoomId, Room> {

    /**
     * Retrieve the identity of all Room entities in the repository.
     * <p>
     * @return An Iterable of all Room identities.
     */
    Iterable<RoomId> findRoomIds();

    /**
     * Retrieve all Room identities with a given house name.
     * <p>
     *
     * @return An Iterable of Room identities.
     */
    Iterable<RoomId> findRoomIdsByHouseName(HouseName houseName);
}
