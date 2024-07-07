package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.RoomId;
import smarthome.persistence.datamodel.RoomDataModel;
import smarthome.persistence.datamodel.mapper.RoomDataModelMapper;
import smarthome.persistence.spring.IRoomRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of IRoomRepository using Spring Data
 */
@Repository
public class RoomRepositorySpringDataImpl implements IRoomRepository {

    private final RoomDataModelMapper roomDataModelMapper;
    private final IRoomRepositorySpringData roomSpringDataRepository;

    /**
     * Instantiates a new Room repository Spring Data implementation.
     *
     * @param roomDataModelMapper      The room data model mapper.
     * @param roomSpringDataRepository The room Spring Data repository.
     */
    public RoomRepositorySpringDataImpl(RoomDataModelMapper roomDataModelMapper,
                                        IRoomRepositorySpringData roomSpringDataRepository) {
        this.roomDataModelMapper = roomDataModelMapper;
        this.roomSpringDataRepository = roomSpringDataRepository;
    }

    /**
     * Save a Room entity to the repository.
     *
     * @param room The Room entity to be saved.
     * @return The saved Room entity.
     * @throws IllegalArgumentException if the Room is null or if a Room with the same identity already exists.
     */
    @Override
    public Room save(Room room) {
        if (room == null || containsIdentity(room.getIdentity())) {
            throw new IllegalArgumentException();
        }

        RoomDataModel roomDataModel = new RoomDataModel(room);
        roomSpringDataRepository.save(roomDataModel);
        return room;
    }

    /**
     * Retrieve all Room entities from the repository.
     *
     * @return An Iterable of Room entities.
     */
    @Override
    public Iterable<Room> findAll() {
        List<RoomDataModel> roomDataModels = roomSpringDataRepository.findAll();
        return roomDataModelMapper.toRoomsDomain(roomDataModels);
    }

    /**
     * Retrieve a Room entity by its identity.
     *
     * @param roomId The identity of the Room entity to retrieve.
     * @return An Optional of the Room entity with the provided identity.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<Room> findByIdentity(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }

        Optional<RoomDataModel> roomDataModel = roomSpringDataRepository.findById(roomId.getRoomId());
        return roomDataModel.map(roomDataModelMapper::toRoomDomain);
    }

    /**
     * Check if a Room entity with the provided identity exists in the repository.
     *
     * @param roomId The identity of the Room entity to check.
     * @return A boolean indicating if the Room entity with the provided identity exists in the repository.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }

        return roomSpringDataRepository.existsById(roomId.getRoomId());
    }

    /**
     * Retrieve the identity of all Room entities in the repository.
     *
     * @return An Iterable of all Room identities.
     */
    @Override
    public Iterable<RoomId> findRoomIds() {
        List<String> roomIds = roomSpringDataRepository.findRoomIds();
        return roomIds.stream().map(RoomId::new).toList();
    }

    /**
     * Retrieve all Room identities with a given house name.
     *
     * @param houseName The house name to filter by.
     * @return An Iterable of Room identities.
     */
    @Override
    public Iterable<RoomId> findRoomIdsByHouseName(HouseName houseName) {
        List<String> roomIds = roomSpringDataRepository.findRoomIdsByHouseName(houseName.getName());
        return roomIds.stream().map(RoomId::new).toList();
    }
}
