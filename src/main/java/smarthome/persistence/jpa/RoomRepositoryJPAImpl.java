package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.RoomId;
import smarthome.persistence.datamodel.RoomDataModel;
import smarthome.persistence.datamodel.mapper.RoomDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * Implementation of IRoomRepository using JPA
 */
public class RoomRepositoryJPAImpl implements IRoomRepository {

    private final RoomDataModelMapper roomDataModelMapper;

    /**
     * Instantiates a new Room repository JPA implementation.
     *
     * @param roomDataModelMapper The room data model mapper.
     */
    public RoomRepositoryJPAImpl(RoomDataModelMapper roomDataModelMapper) {
        this.roomDataModelMapper = roomDataModelMapper;
    }

    /**
     * Save a Room entity to the repository.
     *
     * @param room The Room entity to be saved.
     * @return The saved Room entity.
     * @throws IllegalArgumentException if the Room is null or if a Room with the same
     *                                  identity already exists.
     */
    @Override
    public Room save(Room room) {
        if (room == null || containsIdentity(room.getIdentity())) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            RoomDataModel roomDataModel = new RoomDataModel(room);
            transaction.begin();
            manager.persist(roomDataModel);
            transaction.commit();
            return room;
        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }

    /**
     * Find all Room entities in the repository.
     *
     * @return An Iterable of all Room entities.
     */
    @Override
    public Iterable<Room> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM RoomDataModel e");

            List<RoomDataModel> roomDataModels = query.getResultList();
            return roomDataModelMapper.toRoomsDomain(roomDataModels);
        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }

    /**
     * Get the identity of all Room entities in the repository.
     *
     * @return An Iterable of all Room identities.
     */
    @Override
    public Iterable<RoomId> findRoomIds() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e.roomId FROM RoomDataModel e");

            List<String> roomIds = query.getResultList();
            return roomIds.stream().map(RoomId::new).toList();
        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }

    /**
     * Retrieve all Room identities with a given house name.
     *
     * @param houseName The house name to filter by.
     * @return An Iterable of Room identities.
     */
    @Override
    public Iterable<RoomId> findRoomIdsByHouseName(HouseName houseName) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.
                    createQuery("SELECT e.roomId FROM RoomDataModel e WHERE e.houseName = :houseName");

            List<String> roomIds = query.getResultList();
            return roomIds.stream().map(RoomId::new).toList();
        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
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

        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            RoomDataModel roomDataModel = manager.find(RoomDataModel.class, roomId.getRoomId());
            if (roomDataModel != null) {
                Room room = roomDataModelMapper.toRoomDomain(roomDataModel);
                return Optional.of(room);
            } else {
                return Optional.empty();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
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
        Optional<Room> room = findByIdentity(roomId);
        return room.isPresent();
    }

}
