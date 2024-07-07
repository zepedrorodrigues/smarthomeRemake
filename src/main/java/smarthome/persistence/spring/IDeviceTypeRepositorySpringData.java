package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smarthome.persistence.datamodel.DeviceTypeDataModel;

import java.util.List;

/**
 * This interface extends JpaRepository and provides CRUD operations for DeviceTypeDataModel. It is used by Spring
 * Data JPA to access data in a relational database.
 */
public interface IDeviceTypeRepositorySpringData extends JpaRepository<DeviceTypeDataModel, String> {

    /**
     * Retrieve all device type names from the database.
     * @return A list of all device type names.
     */
    @Query("SELECT dt.deviceTypeName FROM DeviceTypeDataModel dt")
    List<String> findDeviceTypeNames();
}
