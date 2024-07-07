package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.persistence.datamodel.ActuatorTypeDataModel;
import smarthome.persistence.datamodel.mapper.ActuatorTypeDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * This class implements the IActuatorTypeRepository interface and provides JPA-based persistence operations.
 */
public class ActuatorTypeRepositoryJPAImpl implements IActuatorTypeRepository {
    private final ActuatorTypeDataModelMapper actuatorTypeDataModelMapper;

    /**
     * Constructor for the ActuatorTypeRepositoryJPAImpl class.
     * @param actuatorTypeDataModelMapper The mapper to convert between the ActuatorType domain model and the
     *                                    ActuatorTypeDataModel persistence model.
     */
    public ActuatorTypeRepositoryJPAImpl(ActuatorTypeDataModelMapper actuatorTypeDataModelMapper) {
        this.actuatorTypeDataModelMapper = actuatorTypeDataModelMapper;
    }

    /**
     * Save an ActuatorType to the database.
     * @param actuatorType The ActuatorType to save.
     * @return The saved ActuatorType.
     */
    @Override
    public ActuatorType save(ActuatorType actuatorType) {
        if (actuatorType == null || containsIdentity(actuatorType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(actuatorTypeDataModel);
            transaction.commit();
            return actuatorType;
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
     * Retrieve all ActuatorTypes from the database.
     * @return An Iterable of all ActuatorTypes.
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM ActuatorTypeDataModel e");

            List<ActuatorTypeDataModel> listDataModel = query.getResultList();

            return actuatorTypeDataModelMapper.toDomain(listDataModel);
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
     * Get all the actuator type ids.
     *
     * @return the list of actuator type ids.
     */
    @Override
    public Iterable<ActuatorTypeName> findActuatorTypeNames() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e.actuatorTypeName FROM ActuatorTypeDataModel e");

            List<String> actuatorTypeIds = query.getResultList();
            return actuatorTypeIds.stream().map(ActuatorTypeName::new).toList();
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
     * Retrieve an ActuatorType by its identity.
     * @param actuatorTypeName The identity of the ActuatorType to retrieve.
     * @return An Optional containing the ActuatorType if found, or empty if not found.
     */
    @Override
    public Optional<ActuatorType> findByIdentity(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            ActuatorTypeDataModel actuatorTypeDataModel = manager.find(ActuatorTypeDataModel.class,
                    actuatorTypeName.getActuatorTypeName());
            if (actuatorTypeDataModel != null) {
                ActuatorType actuatorType = actuatorTypeDataModelMapper.toDomain(actuatorTypeDataModel);
                return Optional.of(actuatorType);
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
     * Check if an ActuatorType with the given identity exists in the database.
     * @param actuatorTypeName The identity of the ActuatorType to check.
     * @return true if the ActuatorType exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        Optional<ActuatorType> actuatorType = findByIdentity(actuatorTypeName);
        return actuatorType.isPresent();
    }
}