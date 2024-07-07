package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smarthome.persistence.datamodel.RoomDataModel;

import java.util.List;

/**
 * Spring Data repository for RoomDataModel
 */
public interface IRoomRepositorySpringData extends JpaRepository<RoomDataModel, String> {

    /**
     * Get the identity of all Room entities in the repository.
     *
     * @return An Iterable of all Room identities.
     */
    @Query("SELECT r.roomId FROM RoomDataModel r")
    List<String> findRoomIds();

    /**
     * Retrieve all Room identities with a given house name.
     *
     * @param houseName The house name to filter by.
     * @return An Iterable of Room identities.
     */
    @Query("SELECT r.roomId FROM RoomDataModel r WHERE r.houseName = :houseName")
    List<String> findRoomIdsByHouseName(String houseName);
}
