package smarthome.persistence.mem;

import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.RoomId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * RoomRepositoryMemImpl is a class that implements the IRoomRepository interface.
 * It provides methods to interact with the Room data.
 */
public class RoomRepositoryMemImpl implements IRoomRepository {

    private final Map<RoomId, Room> DATA = new HashMap<>();

    /**
     * Save a Room to the repository.
     *
     * @param room The Room to be saved.
     * @return The saved Room.
     * @throws IllegalArgumentException if the Room is null or if a Room with the same identity already exists.
     */
    @Override
    public Room save(Room room) {
        if (room == null || containsIdentity(room.getIdentity())) {
            throw new IllegalArgumentException();
        }

        DATA.put(room.getIdentity(), room);
        return room;
    }

    /**
     * Find all Room entities in the repository.
     *
     * @return An Iterable of all Room entities.
     */
    @Override
    public Iterable<Room> findAll() {
        return DATA.values();
    }

    /**
     * Get the identity of all Room entities in the repository.
     *
     * @return An Iterable of all Room identities.
     */
    @Override
    public Iterable<RoomId> findRoomIds() {
        return DATA.keySet();
    }

    /**
     * Retrieve all Room identities with a given house name.
     *
     * @param houseName The house name to filter by.
     * @return An Iterable of Room identities.
     */
    @Override
    public Iterable<RoomId> findRoomIdsByHouseName(HouseName houseName) {
        return DATA.values().stream()
                .filter(room -> room.getHouseName().equals(houseName))
                .map(Room::getIdentity)
                ::iterator;
    }

    /**
     * Get a Room entity by its identity.
     *
     * @param roomId The identity of the Room entity.
     * @return An Optional that may contain the Room entity if it exists.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<Room> findByIdentity(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }

        return Optional.ofNullable(DATA.get(roomId));
    }

    /**
     * Check if a Room entity with the given identity exists in the repository.
     *
     * @param roomId The identity of the Room entity.
     * @return true if the Room entity exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }
        return DATA.containsKey(roomId);
    }
}
