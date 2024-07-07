package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.persistence.datamodel.SensorTypeDataModel;
import smarthome.persistence.datamodel.mapper.SensorTypeDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * This class is a JPA implementation of the ISensorTypeRepository interface.
 * It provides methods to interact with the SensorType entities in the database.
 */
public class SensorTypeRepositoryJPAImpl implements ISensorTypeRepository {

    private final SensorTypeDataModelMapper sensorTypeDataModelMapper;

    /**
     * Constructor for the SensorTypeRepositoryJPAImpl class.
     *
     * @param sensorTypeDataModelMapper The sensor type data model mapper.
     */
    public SensorTypeRepositoryJPAImpl(SensorTypeDataModelMapper sensorTypeDataModelMapper) {
        this.sensorTypeDataModelMapper = sensorTypeDataModelMapper;
    }

    /**
     * This method saves a SensorType entity to the database.
     *
     * @param sensorType The SensorType entity to be saved.
     * @return The saved SensorType entity.
     * @throws IllegalArgumentException if the SensorType entity is null
     *
     */
    @Override
    public SensorType save(SensorType sensorType) {
        if (sensorType == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);
            transaction.begin();
            manager.persist(sensorTypeDataModel);
            transaction.commit();
            return sensorType;
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
     * This method retrieves all SensorType entities from the database.
     *
     * @return A list of all SensorType entities.
     */
    @Override
    public Iterable<SensorType> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM SensorTypeDataModel e");
            List<SensorTypeDataModel> listDataModel = query.getResultList();
            return sensorTypeDataModelMapper.toSensorTypesDomain(listDataModel);
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
     * This method retrieves a SensorType entity from the database by its ID.
     *
     * @param id The ID of the SensorType entity to be retrieved.
     * @return The SensorType entity with the provided ID.
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public Optional<SensorType> findByIdentity(SensorTypeId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            SensorTypeDataModel sensorTypeDataModel = manager.find(SensorTypeDataModel.class, id.getSensorTypeId());
            if (sensorTypeDataModel == null) {
                return Optional.empty();
            }
            return Optional.of(sensorTypeDataModelMapper.toSensorTypeDomain(sensorTypeDataModel));
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
     * This method checks if a SensorType entity with the provided ID exists in the database.
     *
     * @param id The ID of the SensorType entity to be checked.
     * @return True if the SensorType entity exists, false otherwise.
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public boolean containsIdentity(SensorTypeId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return findByIdentity(id).isPresent();
    }

    /**
     * Retrieves all sensor type IDs from the database.
     *
     * This method uses JPA to interact with the database. It creates a query to select all sensor type IDs from the
     * SensorTypeDataModel entities. The result is a list of strings, which are then converted into SensorTypeId objects.
     * The method returns an Iterable of these SensorTypeId objects.
     *
     * @return an Iterable of SensorTypeId objects representing all sensor type IDs in the database.
     */
    @Override
    public Iterable<SensorTypeId> findSensorTypeIds() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e.sensorTypeId FROM SensorTypeDataModel e");
            List<String> sensorTypeIds = query.getResultList();
            return sensorTypeIds.stream().map(SensorTypeId::new).toList();
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
