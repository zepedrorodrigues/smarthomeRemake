package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.datamodel.SensorDataModel;

import java.util.List;

/**
 * This is a Spring Data JPA repository for SensorDataModel entities.
 * It extends JpaRepository, which provides JPA related methods like save(), findOne(), findAll(), count(),
 * delete() etc.
 * It can be used to perform database operations on SensorDataModel entities.
 *
 * <p> SensorDataModel is the entity that this repository works with.
 * String is the type of the primary key of the SensorDataModel entity.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ISensorRepositorySpringData extends JpaRepository<SensorDataModel, String> {

    /**
     * This method retrieves all SensorDataModel entities associated with a specific DeviceId from the repository.
     * It takes the identity of a DeviceId as a parameter and returns a list of SensorDataModel entities associated
     * with that DeviceId.
     * The identity is a string representation of the DeviceId.
     *
     * @param identity The device id.
     * @return A list of SensorDataModel entities associated with the provided DeviceId.
     */
    List<SensorDataModel> findSensorsByDeviceId(String identity);

    /**
     * This method retrieves all SensorDataModel entities associated with a given DeviceId and SensorModelName
     * from the repository.
     *
     * @param deviceId        The device id.
     * @param sensorModelName The name of the SensorModel.
     * @return A list of SensorDataModel entities associated with the provided DeviceId and SensorModelName.
     */
    @Query("SELECT s FROM SensorDataModel s WHERE s.deviceId = :deviceId AND s.sensorModelName = :sensorModelName")
    List<SensorDataModel> findSensorsByDeviceIdAndSensorModelName(@Param("deviceId") String deviceId, @Param("sensorModelName") String sensorModelName);

    /**
     * This method retrieves all SensorDataModel entities associated with a given DeviceId and SensorModelName from the
     * repository.
     * @param deviceId        The device id.
     * @param sensorModelName The name of the SensorModel.
     * @return A list of SensorDataModel entities associated with the provided DeviceId and SensorModelName.
     */
    @Query("SELECT s.sensorId FROM SensorDataModel s WHERE s.deviceId = :deviceId AND s.sensorModelName = " +
            ":sensorModelName")
    List<String> findSensorIdsByDeviceIdAndSensorModelName(@Param("deviceId") String deviceId, @Param("sensorModelName") String sensorModelName);

    /**
     * This method retrieves all sensor ids associated with a specific device from the database.
     * It uses a JPA query to select all SensorDataModel entities where the deviceId matches the provided
     * deviceId. The result is then converted to a list of sensor ids.
     *
     * @param deviceId The identity of the device whose associated sensor ids are to be retrieved.
     * @return A list of sensor ids associated with the specified device.
     */
    @Query("SELECT s.sensorId FROM SensorDataModel s WHERE s.deviceId = :deviceId")
    List<String> findSensorIdsByDeviceId(@Param("deviceId") String deviceId);
}
