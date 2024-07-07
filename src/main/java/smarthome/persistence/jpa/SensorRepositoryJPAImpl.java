package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.persistence.datamodel.SensorDataModel;
import smarthome.persistence.datamodel.mapper.SensorDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * This class implements the ISensorRepository interface and provides the implementation for the methods defined in
 * the interface.
 * It uses the Java Persistence API (JPA) to interact with the database.
 */
public class SensorRepositoryJPAImpl implements ISensorRepository {

    private final SensorDataModelMapper sensorDataModelMapper;


    /**
     * Constructor for the SensorRepositoryJPAImpl class.
     * This constructor initializes the SensorRepositoryJPAImpl object with a SensorDataModelMapper object.
     * The SensorDataModelMapper object is used to map between SensorDataModel and Sensor domain entities.
     *
     * @param sensorDataModelMapper The SensorDataModelMapper object to be used for entity mapping.
     */
    public SensorRepositoryJPAImpl(SensorDataModelMapper sensorDataModelMapper) {
        this.sensorDataModelMapper = sensorDataModelMapper;
    }

    /**
     * This method saves a Sensor object to the database.
     *
     * @param sensor The Sensor object to be saved
     * @return The saved Sensor object
     * @throws IllegalArgumentException if the Sensor object is null
     */
    @Override
    public Sensor save(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException();
        }

        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            SensorDataModel sensorDataModel = new SensorDataModel(sensor);
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(sensorDataModel);
            transaction.commit();
            return sensor;
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
     * This method retrieves all Sensor objects from the database.
     *
     * @return An Iterable of Sensor objects
     */
    @Override
    public Iterable<Sensor> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM SensorDataModel e");

            List<SensorDataModel> listDataModel = query.getResultList();

            return sensorDataModelMapper.toDomain(listDataModel);
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
     * This method retrieves a Sensor object from the database by its identity.
     *
     * @param id The identity of the Sensor object
     * @return An Optional of Sensor object
     */
    @Override
    public Optional<Sensor> findByIdentity(SensorId id) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();

            SensorDataModel sensorDataModel = manager.find(SensorDataModel.class, id.toString());

            if (sensorDataModel != null) {
                Sensor sensorDomain = sensorDataModelMapper.toDomain(sensorDataModel);
                return Optional.of(sensorDomain);
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
     * This method checks if a Sensor object with the given identity exists in the database.
     *
     * @param id The identity of the Sensor object
     * @return true if the Sensor object exists, false otherwise
     */
    @Override
    public boolean containsIdentity(SensorId id) {

        Optional<Sensor> optionalSensor = findByIdentity(id);

        return optionalSensor.isPresent();
    }

    /**
     * Retrieves all Sensor entities associated with a specific device from the database.
     * This method creates a JPA query to select all SensorDataModel entities where the deviceId matches the provided
     * deviceId.
     * The result is then converted to a list of SensorDataModel entities.
     * Finally, the list of SensorDataModel entities is converted to a list of Sensor domain entities using the
     * SensorDataModel's toDomain method.
     *
     * @param deviceId The identity of the device whose associated Sensor entities are to be retrieved.
     * @return An Iterable collection containing all Sensor entities associated with the specified device.
     */
    public Iterable<Sensor> findSensorsByDeviceId(DeviceId deviceId) {

        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM SensorDataModel e WHERE e.deviceId = :deviceId");

            List<SensorDataModel> listDataModel = query.getResultList();

            return sensorDataModelMapper.toDomain(listDataModel);
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
     * Retrieves all Sensor entities associated with a specific device and sensor model from the repository.
     *
     * @param deviceId        The identity of the device whose associated Sensor entities are to be retrieved.
     * @param sensorModelName The name of the sensor model whose associated Sensor entities are to be retrieved.
     * @return An Iterable collection containing all Sensor entities associated with the specified device and
     * sensor model.
     */
    @Override
    public Iterable<Sensor> findSensorsByDeviceIdAndSensorModelName(DeviceId deviceId, SensorModelName sensorModelName) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT e FROM SensorDataModel e WHERE e.deviceId = :deviceId " +
                            "AND e.sensorModelName = :sensorModelName");

            List<SensorDataModel> listDataModel = query.getResultList();

            return sensorDataModelMapper.toDomain(listDataModel);
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
     * Retrieves all Sensor entities associated with a specific device and sensor model from the repository.
     * @param deviceId        The device id.
     * @param sensorModelName The name of the SensorModel.
     * @return An Iterable collection containing all Sensor entities associated with the provided DeviceId and
     * SensorModelName.
     */
    @Override
    public Iterable<SensorId> findSensorIdsByDeviceIdAndSensorModelName(DeviceId deviceId,
                                                                        SensorModelName sensorModelName) {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e.sensorId FROM SensorDataModel e WHERE e.deviceId = :deviceId " + "AND e.sensorModelName" + " = :sensorModelName");

            List<SensorId> sensorIds = query.getResultList();

            return sensorIds;
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
     * Retrieves all SensorId entities associated with a specific device from the database.
     * This method creates a JPA query to select all SensorDataModel entities where the deviceId matches the provided
     * deviceId. The result is then converted to a list of SensorId entities.
     *
     * @param deviceId The identity of the device whose associated SensorId entities are to be retrieved.
     * @return An Iterable collection containing all SensorId entities associated with the specified device.
     * @throws IllegalArgumentException if the deviceId is null.
     */
    @Override
    public Iterable<SensorId> findSensorIdsByDeviceId(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e.sensorId FROM SensorDataModel e WHERE e.deviceId = :deviceId");

            query.setParameter("deviceId", deviceId.toString());

            List<SensorId> sensorIds = query.getResultList();

            return sensorIds;
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