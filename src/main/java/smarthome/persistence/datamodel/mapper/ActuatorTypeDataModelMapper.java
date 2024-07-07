package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.persistence.datamodel.ActuatorTypeDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between the ActuatorType domain model and the ActuatorTypeDataModel.
 * It uses the ActuatorTypeFactory to create ActuatorType domain models.
 */
@Component
public class ActuatorTypeDataModelMapper {
    private final ActuatorTypeFactory actuatorTypeFactory;

    /**
     * Constructor for the ActuatorTypeDataModelMapper class.
     *
     * @param actuatorTypeFactory The factory to create ActuatorType domain models.
     */
    public ActuatorTypeDataModelMapper(ActuatorTypeFactory actuatorTypeFactory) {
        this.actuatorTypeFactory = actuatorTypeFactory;
    }

    /**
     * This method converts an ActuatorTypeDataModel to an ActuatorType domain model.
     *
     * @param actuatorTypeDataModel The ActuatorTypeDataModel to be converted.
     * @return The converted ActuatorType domain model.
     */
    public ActuatorType toDomain(ActuatorTypeDataModel actuatorTypeDataModel) {
        ActuatorTypeName actuatorTypeName = new ActuatorTypeName(actuatorTypeDataModel.getActuatorTypeName());
        return actuatorTypeFactory.createActuatorType(actuatorTypeName);
    }

    /**
     * This method converts an Iterable of ActuatorTypeDataModels to an Iterable of ActuatorType domain models.
     *
     * @param listActuatorTypeDataModel The Iterable of ActuatorTypeDataModels to be converted.
     * @return The converted Iterable of ActuatorType domain models.
     */
    public Iterable<ActuatorType> toDomain(Iterable<ActuatorTypeDataModel> listActuatorTypeDataModel) {
        List<ActuatorType> listDomain = new ArrayList<>();
        for (ActuatorTypeDataModel actuatorTypeDataModel : listActuatorTypeDataModel) {
            listDomain.add(toDomain(actuatorTypeDataModel));
        }
        return listDomain;
    }
}