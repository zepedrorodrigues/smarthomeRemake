package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.datamodel.DeviceDataModel;

import java.util.List;

/**
 * Device Repository Spring Data
 * This interface extends the JpaRepository interface from Spring Data JPA to provide database operations on Device entities.
 * It provides methods to find devices by room identity, device type name, and device ID.
 * It also provides a method to retrieve all device IDs in the repository.
 */
public interface IDeviceRepositorySpringData extends JpaRepository<DeviceDataModel, String> {

    /**
     * Finds all devices located in the specified room.
     * <p>
     * @param roomIdentity the identity of the room to search for.
     * @return a list of devices located in the specified room.
     */
    List<DeviceDataModel> findDevicesByRoomIdentity(String roomIdentity);

    /**
     * Finds all device IDs located in the specified room.
     * <p>
     * @param roomIdentity the identity of the room to search for.
     * @return a list of device IDs located in the specified room.
     */
    @Query("SELECT d.deviceId FROM DeviceDataModel d WHERE d.roomIdentity = :roomIdentity")
    List<String> findDeviceIdsByRoomIdentity(@Param("roomIdentity") String roomIdentity);

    /**
     * Retrieves all device IDs of a specified type.
     * <p>
     * @param deviceTypeName the name of the device type to search for.
     * @return an iterable collection of device IDs of the specified type.
     */
    @Query("SELECT d.deviceId FROM DeviceDataModel d WHERE d.deviceTypeName = :deviceTypeName")
    List<String> findDeviceIdsByDeviceTypeName(@Param("deviceTypeName") String deviceTypeName);

    /**
     * Retrieves all device IDs in the repository.
     * <p>
     * @return an iterable collection of all device IDs.
     */
    @Query("SELECT d.deviceId FROM DeviceDataModel d")
    List<String> findDeviceIds();
}
