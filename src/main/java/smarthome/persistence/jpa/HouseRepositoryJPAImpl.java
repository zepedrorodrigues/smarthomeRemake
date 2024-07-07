package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.house.House;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IHouseRepository;
import smarthome.persistence.PersistenceUnitName;
import smarthome.persistence.datamodel.HouseDataModel;
import smarthome.persistence.datamodel.mapper.HouseDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * JPA implementation of the New House Repository.
 * Provides methods to perform CRUD operations on House entities using JPA.
 */
public class HouseRepositoryJPAImpl implements IHouseRepository {
    /**
     * Mapper to convert between House and HouseDataModel objects.
     */
    private final HouseDataModelMapper houseDataModelMapper;

    /**
     * Constructs a new repository with the specified data model mapper.
     *
     * @param houseDataModelMapper the mapper to convert between domain and data model objects.
     */
    public HouseRepositoryJPAImpl(HouseDataModelMapper houseDataModelMapper) {
        this.houseDataModelMapper = houseDataModelMapper;
    }

    /**
     * Updates an existing house in the repository.
     *
     * @param theHouse the house to update.
     * @return the updated house.
     * @throws IllegalArgumentException if the given house is null.
     */
    @Override
    public House update(House theHouse) {
        if (theHouse == null) {
            throw new IllegalArgumentException("House cannot be null.");
        }
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PersistenceUnitName.PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(theHouse);
            em.getTransaction().commit();
            return theHouse;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    /**
     * Get all house ids.
     *
     * @return a list of all house ids.
     */
    @Override
    public List<HouseName> findHouseIds() {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            manager = factory.createEntityManager();
            Query query = manager.createQuery("SELECT h.houseName FROM HouseDataModel h");

            List<String> houseIdsList = query.getResultList();
            return houseIdsList.stream().map(HouseName::new).toList();
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
     * Saves a new house to the repository.
     *
     * @param entity the house to save.
     * @return the saved house.
     * @throws IllegalArgumentException if the given house is null.
     */
    @Override
    public House save(House entity) {
        if (entity == null) {
            throw new IllegalArgumentException("House cannot be null.");
        }
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PersistenceUnitName.PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    /**
     * Finds all houses in the repository.
     *
     * @return an iterable collection of all houses.
     */
    @Override
    public Iterable<House> findAll() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PersistenceUnitName.PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            Query query = em.createQuery("SELECT e FROM HouseDataModel e");
            List<HouseDataModel> houseDataModels = query.getResultList();
            return houseDataModelMapper.toHousesDomain(houseDataModels);
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    /**
     * Retrieves a house by its identity.
     *
     * @param id the identity of the house.
     * @return an optional containing the house if found, otherwise empty.
     * @throws IllegalArgumentException if the given identity is null.
     */
    @Override
    public Optional<House> findByIdentity(HouseName id) {
        if (id == null) {
            throw new IllegalArgumentException("House identity cannot be null.");
        }
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PersistenceUnitName.PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            HouseDataModel houseDataModel = em.find(HouseDataModel.class, id);
            if (houseDataModel == null) {
                return Optional.empty();
            }
            return Optional.of(houseDataModelMapper.toHouseDomain(houseDataModel));
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    /**
     * Checks if a house with the given identity exists in the repository.
     *
     * @param id the identity of the house.
     * @return true if the house exists, false otherwise.
     * @throws IllegalArgumentException if the given identity is null.
     */
    @Override
    public boolean containsIdentity(HouseName id) {
        if (id == null) {
            throw new IllegalArgumentException("House identity cannot be null.");
        }
        return findByIdentity(id).isPresent();
    }
}
