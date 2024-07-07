package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.persistence.datamodel.SensorModelDataModel;
import smarthome.persistence.datamodel.mapper.SensorModelDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * SensorModelRepositoryJPAImpl is a class that implements the ISensorModelRepository interface.
 * It provides methods to interact with the SensorModel data using JPA.
 */
public class SensorModelRepositoryJPAImpl implements ISensorModelRepository {

    private final SensorModelDataModelMapper sensorModelDataModelMapper;

    /**
     * Constructs a SensorModelRepositoryJPAImpl object with the given SensorModelDataModelMapper.
     *
     * @param sensorModelDataModelMapper
     */
    public SensorModelRepositoryJPAImpl(SensorModelDataModelMapper sensorModelDataModelMapper) {
        this.sensorModelDataModelMapper = sensorModelDataModelMapper;

    }

    /**
     * Finds all SensorModel entities in the repository that have a specific SensorTypeId.
     *
     * @param sensorTypeId The SensorTypeId to search for.
     * @return An Iterable of SensorModel entities that have the specified SensorTypeId.
     * @throws IllegalArgumentException if the SensorTypeId is null.
     */
    @Override
    public Iterable<SensorModel> findSensorModelsBySensorTypeId(SensorTypeId sensorTypeId) {
        if (sensorTypeId == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM SensorModelDataModel e" + " WHERE e.sensorTypeId = " +
                    ":sensorTypeId", SensorModelDataModel.class);
            query.setParameter("sensorTypeId", sensorTypeId.getSensorTypeId());
            List<SensorModelDataModel> sensorModelDataModels = query.getResultList();
            return sensorModelDataModelMapper.toSensorModelsDomain(sensorModelDataModels);
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
     * Retrieves a iterebla of SensorModelName objects by sensor type ID.
     *
     * @param sensorTypeId The ID of the sensor type.
     * @return List of SensorModelName objects that match the given sensor type ID.
     * @throws IllegalArgumentException if the sensorTypeId is null.
     */
    @Override
    public Iterable<SensorModelName> findSensorModelNamesBySensorTypeId(SensorTypeId sensorTypeId) {
        if (sensorTypeId == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM SensorModelDataModel e WHERE e.sensorTypeId = :sensorTypeId", SensorModelDataModel.class);
            query.setParameter("sensorTypeId", sensorTypeId);
            List<SensorModelDataModel> sensorModelDataModels = query.getResultList();
            return sensorModelDataModelMapper.toSensorModelNamesDomain(sensorModelDataModels);
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
     * Saves a SensorModel entity to the repository.
     *
     * @param sensorModel The SensorModel entity to be saved.
     * @return The saved SensorModel entity.
     * @throws IllegalArgumentException if the entity is null.
     */
    @Override
    public SensorModel save(SensorModel sensorModel) {
        if (sensorModel == null || containsIdentity(sensorModel.getIdentity())) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            SensorModelDataModel sensorModelDataModel = new SensorModelDataModel(sensorModel);
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(sensorModelDataModel);
            transaction.commit();
            return sensorModel;
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
     * Finds all SensorModel entities in the repository.
     *
     * @return An Iterable of all SensorModel entities.
     */
    @Override
    public Iterable<SensorModel> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM SensorModelDataModel e");

            List<SensorModelDataModel> sensorModelDataModels = query.getResultList();
            return sensorModelDataModelMapper.toSensorModelsDomain(sensorModelDataModels);
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
     * Gets a SensorModel entity by its identity.
     *
     * @param sensorModelName The identity of the SensorModel entity.
     * @return An Optional that may contain the SensorModel entity if it exists.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<SensorModel> findByIdentity(SensorModelName sensorModelName) {
        if (sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;


        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            SensorModelDataModel sensorModelDataModel = manager.find(SensorModelDataModel.class,
                    sensorModelName.getSensorModelName());
            if (sensorModelDataModel != null) {
                SensorModel sensorModel = sensorModelDataModelMapper.toSensorModelDomain(sensorModelDataModel);
                return Optional.of(sensorModel);
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
     * Checks if a SensorModel entity with the given identity exists in the repository.
     *
     * @param sensorModelName The identity of the SensorModel entity.
     * @return true if the SensorModel entity exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(SensorModelName sensorModelName) {
        if (sensorModelName == null) {
            throw new IllegalArgumentException();
        }

        Optional<SensorModel> optionalSensorModel = findByIdentity(sensorModelName);
        return optionalSensorModel.isPresent();
    }
}
