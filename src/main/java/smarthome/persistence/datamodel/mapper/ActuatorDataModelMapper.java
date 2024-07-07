package smarthome.persistence.datamodel.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.persistence.datamodel.ActuatorDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping actuator data models (database entities)
 * to actuator domain objects (core business logic entities).
 * It utilizes an {@link ActuatorFactory} for creating domain objects from {@link ActuatorMap}
 * which is a detailed, structured representation of actuator data.
 * An {@link ActuatorMapper} is used for intermediate transformations between {@link ActuatorDTO}
 * and {@link ActuatorMap}.
 */
@Component
public class ActuatorDataModelMapper {

    private final ActuatorFactory actuatorFactory;
    private final ActuatorMapper actuatorMapper;

    /**
     * Constructs an instance of ActuatorDataModelMapper with necessary dependencies.
     *
     * @param actuatorFactory the factory used for creating actuator domain objects.
     * @param actuatorMapper the mapper used for converting between ActuatorDTO and ActuatorMap.
     */
    @Autowired
    public ActuatorDataModelMapper(@Qualifier ("actuatorFactoryImpl") ActuatorFactory actuatorFactory, ActuatorMapper actuatorMapper) {
        this.actuatorFactory = actuatorFactory;
        this.actuatorMapper = actuatorMapper;
    }

    /**
     * Converts an iterable collection of {@link ActuatorDataModel} to a list of {@link Actuator} domain objects.
     *
     * @param actuatorDataModels the iterable collection of actuator data models to convert.
     * @return a list of converted {@link Actuator} domain objects.
     */
    public Iterable<Actuator> toActuatorsDomain(List<ActuatorDataModel> actuatorDataModels) {
        List<Actuator> actuators = new ArrayList<>();
        for (ActuatorDataModel actuatorDataModel : actuatorDataModels) {
            actuators.add(toActuatorDomain(actuatorDataModel));
        }
        return actuators;
    }

    /**
     * Converts a single {@link ActuatorDataModel} to an {@link Actuator} domain object.
     *
     * @param actuatorDataModel the actuator data model to convert.
     * @return the converted {@link Actuator} domain object.
     * via reflection (if used) or other instantiation issues.
     */
    public Actuator toActuatorDomain(ActuatorDataModel actuatorDataModel) {
        ActuatorDTO actuatorDTO = new ActuatorDTO(
                actuatorDataModel.getActuatorId(),
                actuatorDataModel.getDeviceId(),
                actuatorDataModel.getActuatorModelName(),
                actuatorDataModel.getIntegerUpperLimit(),
                actuatorDataModel.getIntegerLowerLimit(),
                actuatorDataModel.getDoubleUpperLimit(),
                actuatorDataModel.getDoubleLowerLimit(),
                actuatorDataModel.getDoubleLimitPrecision()
        );

        ActuatorMap actuatorMap = actuatorMapper.toActuatorMap(actuatorDTO);
        return actuatorFactory.createActuator(actuatorMap);
    }
}
