package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.persistence.datamodel.DeviceDataModel;
import smarthome.persistence.datamodel.mapper.DeviceDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * Device Repository JPA
 * This class provides an implementation of the IDeviceRepository interface using Java Persistence API (JPA) for database operations.
 * It interacts with the database to perform CRUD operations on Device entities.
 */
public class DeviceRepositoryJPAImpl implements IDeviceRepository {

    private final DeviceDataModelMapper deviceDataModelMapper;

    /**
     * Constructor of the Device Repository JPA
     * Initializes the DeviceDataModelMapper.
     *
     * @param deviceDataModelMapper the device data model mapper
     */
    public DeviceRepositoryJPAImpl(DeviceDataModelMapper deviceDataModelMapper) {
        this.deviceDataModelMapper = deviceDataModelMapper;
    }

    /**
     * Save a Device entity to the database.
     * It creates a new DeviceDataModel from the Device domain object and persists it to the database.
     *
     * @param device the Device entity to save
     * @return the saved Device entity
     */
    @Override
    public Device save(Device device) {
        if (device == null || containsIdentity(device.getIdentity())) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            DeviceDataModel deviceDataModel = new DeviceDataModel(device);
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(deviceDataModel);
            transaction.commit();
            return device;
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
     * Retrieves all Device entities from the database.
     * It creates a query to select all DeviceDataModel entities from the database and maps them to Device domain objects.
     * The method returns an Iterable containing all Device entities.
     * <p>
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<Device> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT e FROM DeviceDataModel e");
            List<DeviceDataModel> deviceDataModels = query.getResultList();
            Iterable<Device> devices = deviceDataModelMapper.toDevicesDomain(deviceDataModels);
            return devices;
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
     * Retrieves a Device entity by its identity from the database.
     * It creates a query to select a DeviceDataModel entity by its identity from the database.
     * If the entity exists, it maps it to a Device domain object and returns it.
     * If the entity does not exist, it returns an empty Optional.
     * <p>
     * @param deviceId The identity of the Device entity to retrieve.
     * @return An Optional containing the Device entity if it exists, empty otherwise.
     */
    @Override
    public Optional<Device> findByIdentity(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            DeviceDataModel deviceDataModel = manager.find(DeviceDataModel.class, deviceId.getIdentity());
            if (deviceDataModel != null) {
                Device device = deviceDataModelMapper.toDeviceDomain(deviceDataModel);
                return Optional.of(device);
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
     * Check if a Device entity with the given identity exists in the repository.
     * If the entity exists, it returns true. Otherwise, it returns false.
     * <p>
     * @param deviceId The identity of the Device entity.
     * @return true if the Device entity exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        Optional<Device> device = findByIdentity(deviceId);
        return device.isPresent();
    }

    /**
     * Find all Device entities in the repository by room identity.
     * It creates a query to select all DeviceDataModel entities by room identity from the database.
     * The method returns an Iterable containing all Device entities.
     * <p>
     * @param roomId The identity of the Room entity.
     * @return An Iterable of all Device entities in the room.
     */
    @Override
    public Iterable<Device> findDevicesByRoomId(RoomId roomId) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT e FROM DeviceDataModel e WHERE e.roomIdentity = :roomId");
            query.setParameter("roomId", roomId.getRoomId());
            List<DeviceDataModel> deviceDataModels = query.getResultList();
            Iterable<Device> devices = deviceDataModelMapper.toDevicesDomain(deviceDataModels);
            return devices;
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
     * Find all Device entities in the repository by room identity.
     * It creates a query to select all DeviceDataModel entities by room identity from the database.
     * The method returns an Iterable containing all Device entities.
     * <p>
     *
     * @param roomId The identity of the Room entity.
     * @return An Iterable of all Device entities in the room.
     */
    @Override
    public Iterable<DeviceId> findDeviceIdsByRoomId(RoomId roomId) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT e.roomIdentity FROM DeviceDataModel e WHERE e.roomIdentity = :roomId");
            query.setParameter("roomId", roomId.getRoomId());
            List<DeviceId> deviceIds = query.getResultList();
            return deviceIds;
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
     * Updates a Device entity in the repository.
     * It retrieves the DeviceDataModel entity by its identity from the database.
     * If the entity exists, it updates the DeviceDataModel entity from the Device domain object and merges it to the database.
     * The method returns the updated Device entity.
     * If the entity does not exist, it returns null.
     *
     * @param device The Device entity to update.
     * @return The updated Device entity, or null if the entity does not exist.
     */
    @Override
    public Device update(Device device) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            DeviceDataModel deviceDataModel = manager.find(DeviceDataModel.class, device.getIdentity().getIdentity());
            if (deviceDataModel == null) {
                return null;
            }
            boolean isUpdated = deviceDataModel.updateDeviceFromDomain(device);
            if (!isUpdated) {
                return null;
            }
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.merge(deviceDataModel);
            transaction.commit();
            return device;
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
     * Retrieves all Device entities by device type name from the database.
     * @param deviceTypeName The name of the device type.
     * @return An Iterable containing all Device entities with the specified device type name.
     */
    @Override
    public Iterable<DeviceId> findDeviceIdsByDeviceTypeName(DeviceTypeName deviceTypeName) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT e.deviceId FROM DeviceDataModel e WHERE e.deviceTypeName = :deviceTypeName");
            query.setParameter("deviceTypeName", deviceTypeName.getDeviceTypeName());
            List<DeviceId> deviceIds = query.getResultList();
            return deviceIds;
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
     * Retrieves all Device entities in the repository.
     * @return An Iterable containing all Device entities.
     */
    @Override
    public Iterable<DeviceId> findDeviceIds() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT e.deviceId FROM DeviceDataModel e");
            List<String> deviceIds = query.getResultList();
            return deviceIds.stream().map(DeviceId::new).toList();
        } finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }
}
