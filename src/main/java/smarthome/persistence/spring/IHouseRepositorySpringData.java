package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smarthome.persistence.datamodel.HouseDataModel;

import java.util.List;

/**
 * This interface extends JpaRepository and provides CRUD operations for HouseDataModel. It is used by Spring
 * Data JPA to access data in a relational database.
 * The HouseDataModel is the entity that this repository works with.
 * The String is the type of the primary key of the HouseDataModel entity.
 */
public interface IHouseRepositorySpringData extends JpaRepository<HouseDataModel, String> {

    /**
     * Find all house ids.
     *
     * @return a list of all house ids
     */
    @Query("SELECT h.houseName FROM HouseDataModel h")
    List<String> findHouseIds();


}
