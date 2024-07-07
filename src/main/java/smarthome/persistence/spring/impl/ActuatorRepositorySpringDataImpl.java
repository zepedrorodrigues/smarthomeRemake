package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.persistence.datamodel.ActuatorDataModel;
import smarthome.persistence.datamodel.mapper.ActuatorDataModelMapper;
import smarthome.persistence.spring.IActuatorRepositorySpringData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Actuator repository implementation using Spring Data
 */
@Repository
public class ActuatorRepositorySpringDataImpl implements IActuatorRepository {

    private final IActuatorRepositorySpringData actuatorRepositorySpringData;
    private final ActuatorDataModelMapper actuatorDataModelMapper;

    /**
     * Constructor of the ActuatorRepositorySpringDataImpl class.
     *
     * @param actuatorRepositorySpringData the Spring Data repository for ActuatorDataModel entities.
     * @param actuatorDataModelMapper      the mapper between ActuatorDataModel and Actuator domain objects.
     */
    public ActuatorRepositorySpringDataImpl(IActuatorRepositorySpringData actuatorRepositorySpringData,
                                            ActuatorDataModelMapper actuatorDataModelMapper) {
        this.actuatorRepositorySpringData = actuatorRepositorySpringData;
        this.actuatorDataModelMapper = actuatorDataModelMapper;
    }

    /**
     * method that saves an actuator.
     *
     * @param entity the actuator to save.
     * @return the saved actuator.
     */
    @Override
    public Actuator save(Actuator entity) {
        if (entity == null)
            throw new IllegalArgumentException();
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(entity);
        actuatorRepositorySpringData.save(actuatorDataModel);
        return entity;
    }

    /**
     * method that returns all actuators.
     *
     * @return all actuators.
     */
    @Override
    public Iterable<Actuator> findAll() {
        List<ActuatorDataModel> actuators = actuatorRepositorySpringData.findAll();
        return actuatorDataModelMapper.toActuatorsDomain(actuators);
    }

    /**
     * method that returns an actuator given its id.
     *
     * @param id the actuator id.
     * @return the actuator if it exists, empty otherwise.
     */
    @Override
    public Optional<Actuator> findByIdentity(ActuatorId id) {
        if (id == null)
            throw new IllegalArgumentException();
        Optional<ActuatorDataModel> actuatorDataModel = actuatorRepositorySpringData.findById(id.getActuatorId());
        return actuatorDataModel.map(actuatorDataModelMapper::toActuatorDomain);
    }

    /**
     * method that checks if an actuator exists given its id.
     *
     * @param id the actuator id.
     * @return true if the actuator exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(ActuatorId id) {
        if (id == null)
            throw new IllegalArgumentException();
        return findByIdentity(id).isPresent();
    }

    /**
     * Find all actuators ids by device id.
     *
     * @param deviceId the device id to search for
     * @return the actuators ids associated with the device id
     */
    @Override
    public Iterable<ActuatorId> findActuatorIdsByDeviceId(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }

        List<String> actuatorIds = actuatorRepositorySpringData.findActuatorIdsByDeviceId(deviceId.getIdentity());

        List<ActuatorId> actuatorIdsVO = new ArrayList<>();
        for (String id : actuatorIds) {
            actuatorIdsVO.add(new ActuatorId(id));
        }

        return actuatorIdsVO;
    }
}
