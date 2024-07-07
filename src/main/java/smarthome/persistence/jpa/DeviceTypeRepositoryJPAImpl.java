package smarthome.persistence.jpa;

import jakarta.persistence.*;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.persistence.datamodel.DeviceTypeDataModel;
import smarthome.persistence.datamodel.mapper.DeviceTypeDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * This class implements the IDeviceTypeRepository interface and provides JPA-based persistence operations.
 */
public class DeviceTypeRepositoryJPAImpl implements IDeviceTypeRepository {

    private final DeviceTypeDataModelMapper deviceTypeDataModelMapper;

    /**
     * Constructor for the DeviceTypeRepositoryJPAImpl class.
     * @param deviceTypeDataModelMapper The mapper to convert between the DeviceType domain model and the
     *                                  DeviceTypeDataModel persistence model.
     */
    public DeviceTypeRepositoryJPAImpl(DeviceTypeDataModelMapper deviceTypeDataModelMapper) {
        this.deviceTypeDataModelMapper = deviceTypeDataModelMapper;
    }

    /**
     * Save a DeviceType.
     *
     * @param deviceType The DeviceType to save.
     * @return The saved DeviceType.
     */
    @Override
    public DeviceType save(DeviceType deviceType) {
        if (deviceType == null || containsIdentity(deviceType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            DeviceTypeDataModel deviceTypeDataModel = new DeviceTypeDataModel(deviceType);
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(deviceTypeDataModel);
            transaction.commit();
            return deviceType;
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
     * Find all DeviceTypes.
     *
     * @return All DeviceTypes.
     */
    @Override
    public Iterable<DeviceType> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM DeviceTypeDataModel e");

            List<DeviceTypeDataModel> listDataModel = query.getResultList();

            return deviceTypeDataModelMapper.toDomain(listDataModel);
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
     * Get all DeviceTypeNames.
     *
     * @return All DeviceTypeNames.
     */
    @Override
    public Iterable<DeviceTypeName> findDeviceTypeNames() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e.deviceTypeName FROM DeviceTypeDataModel e");

            List<String> deviceTypeNames = query.getResultList();
            return deviceTypeNames.stream().map(DeviceTypeName::new).toList();
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
     * Get a DeviceType by its identity.
     *
     * @param deviceTypeName The identity of the DeviceType to get.
     * @return The DeviceType with the given identity.
     */
    @Override
    public Optional<DeviceType> findByIdentity(DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            DeviceTypeDataModel deviceTypeDataModel = manager.find(DeviceTypeDataModel.class,
                    deviceTypeName.getDeviceTypeName());
            if (deviceTypeDataModel != null) {
                DeviceType deviceType = deviceTypeDataModelMapper.toDomain(deviceTypeDataModel);
                return Optional.of(deviceType);
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
     * Check if a DeviceType with the given identity exists.
     *
     * @param deviceTypeName The identity of the DeviceType to check.
     * @return True if the DeviceType exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null) {
            throw new IllegalArgumentException();
        }
        Optional<DeviceType> deviceType = findByIdentity(deviceTypeName);
        return deviceType.isPresent();
    }

}
