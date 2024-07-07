package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import smarthome.domain.house.House;
import smarthome.domain.repository.IHouseRepository;
import smarthome.persistence.datamodel.HouseDataModel;
import smarthome.persistence.datamodel.mapper.HouseDataModelMapper;

import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;

/**
 * House Repository JPA
 */
public class HouseRepositoryJPAImpl implements IHouseRepository {


    private final HouseDataModelMapper houseDataModelMapper;

    /**
     * Constructor of the House Repository JPA
     *
     * @param houseDataModelMapper the house data model mapper
     */
    public HouseRepositoryJPAImpl(HouseDataModelMapper houseDataModelMapper) {
        this.houseDataModelMapper = houseDataModelMapper;
    }

    /**
     * Get the house entity from the database
     *
     * @return the house entity
     */
    @Override
    public Optional<House> getEntity() {
        EntityManagerFactory fac = null;
        EntityManager em = null;
        try {
            fac = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = fac.createEntityManager();
            Query query = em.createQuery("SELECT h FROM HouseDataModel h");
            List<HouseDataModel> results = query.getResultList();
            if (results.size() == 1) {
                HouseDataModel houseDataModel = results.get(0);
                House house = houseDataModelMapper.toHouseDomain(houseDataModel);
                return Optional.of(house);
            } else {
                return Optional.empty();
            }
        } finally {
            assert em != null;
            em.close();
            fac.close();
        }
    }

    /**
     * Save the house entity
     *
     * @param house the house entity
     * @return the saved house entity
     */
    @Override
    public House save(House house) {
        Optional<House> houseOptional = getEntity();
        if (house == null || (houseOptional.isPresent() &&
                !house.getIdentity().equals(houseOptional.get().getIdentity()))) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory fac = null;
        EntityManager em = null;
        try {
            fac = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = fac.createEntityManager();
            HouseDataModel houseDatamodel = new HouseDataModel(house);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(houseDatamodel);
            tx.commit();
            return house;
        } finally {
            assert em != null;
            em.close();
            fac.close();
        }
    }

    /**
     * Update the house entity
     *
     * @param house the house entity
     * @return the updated house entity
     */
    @Override
    public House update(House house) {
        if (house == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory fac = null;
        EntityManager em = null;
        try {
            fac = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = fac.createEntityManager();
            HouseDataModel houseDataModel = em.find(HouseDataModel.class, house.getIdentity().toString());
            if (houseDataModel != null) {
                boolean isUpdated = houseDataModel.updateFromDomain(house);
                if (isUpdated) {
                    EntityTransaction tx = em.getTransaction();
                    tx.begin();
                    em.merge(houseDataModel);
                    tx.commit();
                    return house;
                } else {
                    throw new IllegalStateException();
                }
            } else {
                throw new IllegalStateException();
            }
        } finally {
            assert em != null;
            em.close();
            fac.close();
        }
    }

}
