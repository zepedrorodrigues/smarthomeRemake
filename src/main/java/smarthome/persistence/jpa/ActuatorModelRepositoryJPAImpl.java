package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.persistence.datamodel.ActuatorModelDataModel;
import smarthome.persistence.datamodel.mapper.ActuatorModelDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * This class implements the IActuatorModelRepository interface.
 * It provides the functionality to interact with the database using JPA.
 */

public class ActuatorModelRepositoryJPAImpl implements IActuatorModelRepository {

    private final ActuatorModelDataModelMapper actuatorModelDataModelMapper;

    /**
     * Constructor for the ActuatorModelRepositoryJPAImpl class.
     *
     * @param actuatorModelDataModelMapper The mapper to convert between the ActuatorModel and ActuatorModelDataModel.
     */
    public ActuatorModelRepositoryJPAImpl(ActuatorModelDataModelMapper actuatorModelDataModelMapper) {
        this.actuatorModelDataModelMapper = actuatorModelDataModelMapper;
    }

    /**
     * This method is used to save an ActuatorModel.
     *
     * @param actuatorModel The ActuatorModel to save.
     * @return The saved ActuatorModel.
     * @throws IllegalArgumentException if the actuatorModel is null.
     */
    @Override
    public ActuatorModel save(ActuatorModel actuatorModel) {
        if (actuatorModel == null || containsIdentity(actuatorModel.getIdentity())) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            ActuatorModelDataModel actuatorModelDataModel = new ActuatorModelDataModel(actuatorModel);
            transaction.begin();
            manager.persist(actuatorModelDataModel);
            transaction.commit();
            return actuatorModel;
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
     * This method is used to find all ActuatorModels.
     *
     * @return An Iterable of all ActuatorModels.
     */
    @Override
    public Iterable<ActuatorModel> findAll() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM ActuatorModelDataModel  e");
            List<ActuatorModelDataModel> actuatorModelDataModels = query.getResultList();
            return actuatorModelDataModelMapper.toActuatorModelsDomain(actuatorModelDataModels);
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
     * This method is used to find an ActuatorModel by its identity.
     *
     * @param actuatorModelName The identity of the ActuatorModel to find.
     * @return An Optional containing the found ActuatorModel, or an empty Optional if no ActuatorModel was found.
     * @throws IllegalArgumentException if the actuatorModelName is null.
     */
    @Override
    public Optional<ActuatorModel> findByIdentity(ActuatorModelName actuatorModelName) {
        if (actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            ActuatorModelDataModel actuatorModelDataModel = manager.find(ActuatorModelDataModel.class,
                    actuatorModelName.getActuatorModelName());
            if (actuatorModelDataModel != null) {
                ActuatorModel actuatorModel =
                        actuatorModelDataModelMapper.toActuatorModelDomain(actuatorModelDataModel);
                return Optional.of(actuatorModel);
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
     * This method is used to check if an ActuatorModel with a certain identity exists.
     *
     * @param actuatorModelName The identity of the ActuatorModel to check for.
     * @return A boolean indicating whether an ActuatorModel with the specified identity exists.
     * @throws IllegalArgumentException if the actuatorModelName is null.
     */
    @Override
    public boolean containsIdentity(ActuatorModelName actuatorModelName) {
        if (actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        Optional<ActuatorModel> optionalActuatorModel = findByIdentity(actuatorModelName);
        return optionalActuatorModel.isPresent();
    }

    /**
     * This method is used to find ActuatorModels by their ActuatorType identity.
     *
     * @param actuatorTypeName The identity of the ActuatorType to find ActuatorModels for.
     * @return An Iterable of ActuatorModels that have the specified ActuatorType identity.
     * @throws IllegalArgumentException if the actuatorTypeName is null.
     */
    @Override
    public Iterable<ActuatorModel> findActuatorModelsByActuatorTypeName(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM ActuatorModelDataModel e WHERE e.actuatorTypeName = " +
                    ":typeName", ActuatorModelDataModel.class);
            query.setParameter("typeName", actuatorTypeName.getActuatorTypeName());
            List<ActuatorModelDataModel> actuatorModelDataModels = query.getResultList();
            return actuatorModelDataModelMapper.toActuatorModelsDomain(actuatorModelDataModels);
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
     * This method is used to find ActuatorModels by their ActuatorType identity.
     *
     * @param actuatorTypeName The identity of the ActuatorType to find ActuatorModels for.
     * @return An Iterable of ActuatorModels that have the specified ActuatorType identity.
     * @throws IllegalArgumentException if the actuatorTypeName is null.
     */
    @Override
    public Iterable<ActuatorModelName> findActuatorModelNamesByActuatorTypeName(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT e FROM ActuatorModelDataModel e WHERE e.actuatorTypeName = " +
                    ":typeName", ActuatorModelDataModel.class);
            query.setParameter("typeName", actuatorTypeName);
            List<ActuatorModelDataModel> actuatorModelDataModels = query.getResultList();
            return actuatorModelDataModelMapper.toActuatorModelNamesDomain(actuatorModelDataModels);
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
