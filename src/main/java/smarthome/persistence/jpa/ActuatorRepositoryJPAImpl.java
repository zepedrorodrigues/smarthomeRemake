package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.persistence.datamodel.ActuatorDataModel;
import smarthome.persistence.datamodel.mapper.ActuatorDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * JPA implementation of the IActuatorRepository for managing Actuator persistence.
 */
public class ActuatorRepositoryJPAImpl implements IActuatorRepository {
    private final ActuatorDataModelMapper actuatorDataModelMapper;

    /**
     * Constructor for ActuatorRepositoryJPAImpl.
     *
     * @param actuatorDataModelMapper The data model mapper for actuator objects.
     */
    public ActuatorRepositoryJPAImpl(ActuatorDataModelMapper actuatorDataModelMapper) {
        this.actuatorDataModelMapper = actuatorDataModelMapper;
    }

    /**
     * Saves an actuator entity to the database.
     *
     * @param entity The actuator entity to save.
     * @return The saved actuator entity.
     * @throws IllegalArgumentException if the actuator entity is null.
     */

    @Override
    public Actuator save(Actuator entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory fac = null;
        EntityManager manager = null;
        try {
            fac = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = fac.createEntityManager();
            ActuatorDataModel actuatorDataModel = new ActuatorDataModel(entity);
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(actuatorDataModel);
            transaction.commit();
            return entity;
        } finally {
            assert manager != null;
            manager.close();
            assert fac != null;
            fac.close();
        }
    }

    /**
     * Retrieves all actuator entities from the database.
     *
     * @return An iterable collection of actuator entities.
     */
    @Override
    public Iterable<Actuator> findAll() {
        EntityManagerFactory fac = null;
        EntityManager manager = null;
        try {
            fac = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = fac.createEntityManager();
            Query query = manager.createQuery("SELECT a FROM ActuatorDataModel a");
            List<ActuatorDataModel> actuatorDataModels = query.getResultList();
            return actuatorDataModelMapper.toActuatorsDomain(actuatorDataModels);
        } finally {
            assert manager != null;
            manager.close();
            assert fac != null;
            fac.close();
        }
    }

    /**
     * Retrieves an actuator by its identity.
     *
     * @param id The identity of the actuator to retrieve.
     * @return An Optional containing the found actuator, or an empty Optional if no actuator is found.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<Actuator> findByIdentity(ActuatorId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory fac = null;
        EntityManager manager = null;
        try {
            fac = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = fac.createEntityManager();
            Query query = manager.createQuery("SELECT a FROM ActuatorDataModel a WHERE a.actuatorId = :id");
            query.setParameter("id", id.getActuatorId());
            ActuatorDataModel actuatorDataModel = (ActuatorDataModel) query.getSingleResult();
            return Optional.of(actuatorDataModelMapper.toActuatorDomain(actuatorDataModel));
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            assert manager != null;
            manager.close();
            assert fac != null;
            fac.close();
        }
    }

    /**
     * Checks if an actuator with a specific identity exists in the database.
     * @param id  The identity of the actuator to check for.
     * @return True if the actuator exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(ActuatorId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return findByIdentity(id).isPresent();
    }

    /**
     * Finds all actuators ids by device id.
     *
     * @param deviceId the device id to search for
     * @return the actuators ids associated with the device id
     */
    @Override
    public Iterable<ActuatorId> findActuatorIdsByDeviceId(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery(
                    "SELECT a.actuatorId FROM ActuatorDataModel a WHERE a.deviceId = :deviceId");
            query.setParameter("deviceId", deviceId.getIdentity());
            return query.getResultList();
        }
        finally {
            if (manager != null) {
                manager.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }
}
