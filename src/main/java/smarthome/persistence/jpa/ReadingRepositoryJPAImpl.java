package smarthome.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.sensor.vo.SensorId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static smarthome.persistence.PersistenceUnitName.PERSISTENCE_UNIT_NAME;


/**
 * Implementation of IReadingRepository using JPA
 */
public class ReadingRepositoryJPAImpl implements IReadingRepository {
    /**
     * Save a Reading entity to the repository.
     *
     * @param entity The Reading entity to be saved.
     * @return The saved Reading entity.
     */
    @Override
    public Reading save(Reading entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
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
     * find All Reading entities in the repository.
     *
     * @return All Reading entities in the repository.
     */
    @Override
    public Iterable<Reading> findAll() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            return em.createQuery("SELECT r FROM ReadingDataModel r", Reading.class).getResultList();
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
     * Find a Reading entity by its identity.
     *
     * @param id The Reading identity.
     * @return The Reading entity with the given identity, if it exists.
     */
    @Override
    public Optional<Reading> findByIdentity(ReadingId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = jakarta.persistence.Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            return Optional.ofNullable(em.find(Reading.class, id));
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
     * Check if a Reading entity with the given identity exists in the repository.
     *
     * @param id The Reading identity.
     * @return true if the Reading entity exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(ReadingId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return findByIdentity(id).isPresent();
    }

    /**
     * Finds all readings for a given sensor in a given period.
     *
     * @param sensorId the identity of the sensor
     * @param start    the start of the period
     * @param end      the end of the period
     * @return all readings for the sensor in the given period
     */
    @Override
    public Iterable<Reading> findReadingsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<Reading> results = new ArrayList<>();
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            String jpql = "SELECT r FROM ReadingDataModel r WHERE r.sensorId = :sensorId " + "AND r.timeStamp >= " +
                    ":start " + "AND r.timeStamp <= :end";
            TypedQuery<Reading> query = em.createQuery(jpql, Reading.class);
            query.setParameter("sensorId", sensorId);
            query.setParameter("start", start);
            query.setParameter("end", end);
            results = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return results;
    }

    /**
     * Finds all reading identities for a given sensor in a given period.
     *
     * @param sensorId the identity of the sensor
     * @param start    the start of the period
     * @param end      the end of the period
     * @return all reading identities for the sensor in the given period
     */
    public Iterable<ReadingId> findReadingIdsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        List<ReadingId> results = new ArrayList<>();
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            String jpql = "SELECT r.id FROM ReadingDataModel r WHERE r.sensorId = :sensorId " + "AND r.timeStamp >= " +
                    ":start " + "AND r.timeStamp <= :end";
            TypedQuery<ReadingId> query = em.createQuery(jpql, ReadingId.class);
            query.setParameter("sensorId", sensorId);
            query.setParameter("start", start);
            query.setParameter("end", end);
            results = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
        return results;
    }

    /**
     * Find the latest reading for a given sensor.
     *
     * @param sensorId the sensor ID to search for.
     * @return an optional containing the latest reading with the given sensor ID if it exists in the repository.
     */
    @Override
    public Optional<Reading> findLastReadingBySensorId(SensorId sensorId) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME.getPersistenceUnitName());
            em = emf.createEntityManager();
            String jpql = "SELECT r FROM ReadingDataModel r WHERE r.sensorId = :sensorId ORDER BY r.timeStamp DESC";
            TypedQuery<Reading> query = em.createQuery(jpql, Reading.class);
            query.setParameter("sensorId", sensorId);
            query.setMaxResults(1);
            return Optional.ofNullable(query.getSingleResult());
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
