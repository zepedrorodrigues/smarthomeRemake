package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.ActuatorModelFactory;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.persistence.datamodel.ActuatorModelDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between the ActuatorModelDataModel and the ActuatorModel domain object. It uses
 * the ActuatorModelFactory to create instances of ActuatorModel.
 */
@Component
public class ActuatorModelDataModelMapper {
    private final ActuatorModelFactory actuatorModelFactory;

    /**
     * Constructor for the ActuatorModelDataModelMapper.
     *
     * @param actuatorModelFactory The factory used to create instances of ActuatorModel.
     */
    public ActuatorModelDataModelMapper(ActuatorModelFactory actuatorModelFactory) {
        this.actuatorModelFactory = actuatorModelFactory;
    }

    /**
     * Converts a ActuatorModelDataModel to a ActuatorModel domain object.
     *
     * @param actuatorModelDataModel The ActuatorModelDataModel to convert.
     * @return The converted ActuatorModel domain object.
     */
    public ActuatorModel toActuatorModelDomain(ActuatorModelDataModel actuatorModelDataModel) {
        ActuatorModelName actuatorModelName = new ActuatorModelName(actuatorModelDataModel.getActuatorModelName());
        ActuatorTypeName actuatorTypeName = new ActuatorTypeName(actuatorModelDataModel.getActuatorTypeName());
        return actuatorModelFactory.createActuatorModel(actuatorModelName, actuatorTypeName);
    }

    /**
     * Converts a collection of ActuatorModelDataModels to a collection of ActuatorModel domain objects.
     *
     * @param actuatorsDataModel The collection of ActuatorModelDataModels to convert.
     * @return The converted collection of ActuatorModel domain objects.
     */
    public Iterable<ActuatorModel> toActuatorModelsDomain(Iterable<ActuatorModelDataModel> actuatorsDataModel) {
        List<ActuatorModel> actuators = new ArrayList<>();

        for (ActuatorModelDataModel actuatorModelDataModel : actuatorsDataModel) {
            actuators.add(toActuatorModelDomain(actuatorModelDataModel));
        }
        return actuators;
    }


    /**
     * Converts a ActuatorModelName to a ActuatorModelDataModel.
     *
     * @param actuatorsDataModel The ActuatorModelName to convert.
     * @return The converted ActuatorModelDataModel.
     */
    public Iterable<ActuatorModelName> toActuatorModelNamesDomain(Iterable<ActuatorModelDataModel> actuatorsDataModel) {
        List<ActuatorModelName> actuatorModelNames = new ArrayList<>();

        for (ActuatorModelDataModel actuatorModelDataModel : actuatorsDataModel) {
            actuatorModelNames.add(new ActuatorModelName(actuatorModelDataModel.getActuatorModelName()));
        }
        return actuatorModelNames;
    }
}