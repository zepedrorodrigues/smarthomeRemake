package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.persistence.datamodel.SensorDataModel;
import smarthome.persistence.datamodel.mapper.SensorDataModelMapper;
import smarthome.persistence.spring.ISensorRepositorySpringData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This class provides a concrete implementation of the ISensorRepository interface using Spring Data JPA for
 * database operations.
 * It uses a SensorDataModelMapper to map between Sensor objects and SensorDataModel entities.
 */
@Repository
public class SensorRepositorySpringDataImpl implements ISensorRepository {

    ISensorRepositorySpringData sensorRepoSpringData;
    SensorDataModelMapper sensorDataModelMapper;

    /**
     * Constructor for the SensorRepositorySpringDataImpl class.
     * This constructor initializes the SensorDataModelMapper and ISensorRepositorySpringData instances that will be
     * used by this repository.
     *
     * @param sensorDataModelMapper An instance of SensorDataModelMapper.
     * @param sensorRepoSpringData An instance of ISensorRepositorySpringData.
     */
    public SensorRepositorySpringDataImpl(SensorDataModelMapper sensorDataModelMapper,
                                          ISensorRepositorySpringData sensorRepoSpringData) {
        this.sensorDataModelMapper = sensorDataModelMapper;
        this.sensorRepoSpringData = sensorRepoSpringData;
    }

    /**
     * This method saves a Sensor object to the database.
     * @param sensor The Sensor object to be saved
     * @return The saved Sensor object
     * @throws IllegalArgumentException if the Sensor object is null
     */
    @Override
    public Sensor save(Sensor sensor) {
        if(sensor == null) {
            throw new IllegalArgumentException();
        }

        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        this.sensorRepoSpringData.save(sensorDataModel);

        return sensor;
    }

    /**
     * This method retrieves all Sensor objects from the database.
     * @return An Iterable of Sensor objects
     */
    @Override
    public Iterable<Sensor> findAll() {

        List<SensorDataModel> lstSensorDataModelSaved = this.sensorRepoSpringData.findAll();

        return sensorDataModelMapper.toDomain(lstSensorDataModelSaved);
    }

    /**
     * This method retrieves a Sensor object from the database by its identity.
     * @param id The identity of the Sensor object
     * @return An Optional of Sensor object
     */
    @Override
    public Optional<Sensor> findByIdentity(SensorId id) {

        Optional<SensorDataModel> optSensorDataModelSaved = this.sensorRepoSpringData.findById(id.getSensorId());

        if(optSensorDataModelSaved.isPresent())
        {
            Sensor sensorToDomain = sensorDataModelMapper.toDomain(optSensorDataModelSaved.get());

            return Optional.of(sensorToDomain);
        }
        else
            return Optional.empty();

    }

    /**
     * This method checks if a Sensor object with the given identity exists in the database.
     * @param id The identity of the Sensor object
     * @return true if the Sensor object exists, false otherwise
     */
    @Override
    public boolean containsIdentity(SensorId id) {

        return sensorRepoSpringData.existsById(id.getSensorId());
    }

    /**
     * This method retrieves all Sensor objects associated with a specific DeviceId from the database.
     * It first calls the findByDeviceId() method of sensorRepositorySpringData, passing the identity of the provided
     * DeviceId.
     * This returns a list of SensorDataModel entities associated with the DeviceId.
     * The SensorDataModel entities are then converted to Sensor objects using the sensorDataModelMapper's toDomain()
     * method.
     *
     * @param deviceId The DeviceId of the Sensor objects to be retrieved.
     * @return An Iterable of Sensor objects associated with the provided DeviceId.
     */
    @Override
    public Iterable<Sensor> findSensorsByDeviceId(DeviceId deviceId) {

        List<SensorDataModel> lstSensorDataModelSaved = this.sensorRepoSpringData.
                findSensorsByDeviceId(deviceId.getIdentity());

        return sensorDataModelMapper.toDomain(lstSensorDataModelSaved);
    }

    /**
     * Retrieves all Sensor entities associated with a specific device and sensor model from the repository.
     *
     * @param deviceId        The identity of the device whose associated Sensor entities are to be retrieved.
     * @param sensorModelName The name of the sensor model whose associated Sensor entities are to be retrieved.
     * @return An Iterable collection containing all Sensor entities associated with the specified device and
     * sensor model.
     */
    @Override
    public Iterable<Sensor> findSensorsByDeviceIdAndSensorModelName(DeviceId deviceId, SensorModelName sensorModelName) {
        List<SensorDataModel> lstSensorDataModelSaved = this.sensorRepoSpringData.
                findSensorsByDeviceIdAndSensorModelName(deviceId.getIdentity(), sensorModelName.getSensorModelName());

        return sensorDataModelMapper.toDomain(lstSensorDataModelSaved);
    }

    /**
     * Retrieves a list of SensorId objects associated with a specific device and sensor model from the repository.
     *
     * @param deviceId The identity of the device whose associated SensorId objects are to be retrieved.
     * @param sensorModelName The name of the sensor model whose associated SensorId objects are to be retrieved.
     * @return An Iterable collection containing all SensorId objects associated with the specified device and
     * sensor model.
     * @throws IllegalArgumentException if either deviceId or sensorModelName is null.
     */
    @Override
    public Iterable<SensorId> findSensorIdsByDeviceIdAndSensorModelName(DeviceId deviceId,
                                                                        SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        List<String> sensorIdStr = sensorRepoSpringData.findSensorIdsByDeviceIdAndSensorModelName(deviceId.getIdentity(),
                sensorModelName.getSensorModelName());

        List<SensorId> sensorIds = new ArrayList<>();
        for (String id : sensorIdStr) {
            sensorIds.add(new SensorId(id));
        }
        return sensorIds;
    }

    /**
     * Retrieves all SensorId entities associated with a specific device from the repository.
     * This method first checks if the provided deviceId is null. If it is, it throws an IllegalArgumentException.
     * If the deviceId is not null, it retrieves a list of sensor ids associated with the device from the
     * sensorRepoSpringData.
     * The sensor ids are then converted to SensorId objects and returned.
     *
     * @param deviceId The identity of the device whose associated SensorId entities are to be retrieved.
     * @return An Iterable collection containing all SensorId entities associated with the specified device.
     * @throws IllegalArgumentException if the deviceId is null.
     */
    @Override
    public Iterable<SensorId> findSensorIdsByDeviceId(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        List<String> sensorIdStr = sensorRepoSpringData.findSensorIdsByDeviceId(deviceId.getIdentity());

        List<SensorId> sensorIdsVO = new ArrayList<>();
        for (String id : sensorIdStr) {
            sensorIdsVO.add(new SensorId(id));
        }
        return sensorIdsVO;
    }
}