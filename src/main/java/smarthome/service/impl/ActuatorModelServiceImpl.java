package smarthome.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.service.IActuatorModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is a service that provides methods to interact with ActuatorModel entities.
 */
@Service
public class ActuatorModelServiceImpl implements IActuatorModelService {

    private final IActuatorModelRepository actuatorModelRepository;

    /**
     * Constructor for the ActuatorModelServiceImpl.
     *
     * @param actuatorModelRepository The ActuatorModelRepository to use.
     */
    @Autowired
    public ActuatorModelServiceImpl(IActuatorModelRepository actuatorModelRepository) {
        this.actuatorModelRepository = actuatorModelRepository;
    }

    @Override
    public Iterable<ActuatorModel> getActuatorModels(ActuatorTypeName actuatorTypeName) {
        return actuatorModelRepository.findActuatorModelsByActuatorTypeName(actuatorTypeName);
    }

    /* Retrieves an ActuatorModel entity from the repository by its identity.
     *
     * @param actuatorModelName The identity of the ActuatorModel entity to retrieve.
     * @return An Optional containing the ActuatorModel entity if found, otherwise empty.
     */
    @Override
    public Optional<ActuatorModel> getActuatorModelByName(ActuatorModelName actuatorModelName) {
        return actuatorModelRepository.findByIdentity(actuatorModelName);
    }

    /* Retrieves all ActuatorModel entities stored in the repository.
     *
     * @return An Iterable containing all ActuatorModel entities.
     */
    @Override
    public List<ActuatorModelName> getActuatorModelsByActuatorTypeIdentity(ActuatorTypeName actuatorTypeName) {
        Iterable<ActuatorModelName> actuatorModels = actuatorModelRepository.findActuatorModelNamesByActuatorTypeName(actuatorTypeName);
        List<ActuatorModelName> actuatorModelNames = new ArrayList<>();
        for (ActuatorModelName actuatorModelName : actuatorModels) {
            actuatorModelNames.add(actuatorModelName);
        }
        return actuatorModelNames;

    }
}
