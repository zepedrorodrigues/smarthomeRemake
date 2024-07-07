package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

/**
 * This interface represents a repository for ActuatorModel entities.
 * It extends the generic IRepository interface with ActuatorModelName as the ID type
 * and ActuatorModel as the entity type.
 * It provides a method to find ActuatorModels by their ActuatorType identity.
 */
public interface IActuatorModelRepository extends IRepository<ActuatorModelName, ActuatorModel> {

    /**
     * This method is used to find ActuatorModels by their ActuatorType identity.
     * <p>
     * @param actuatorTypeName The name of the ActuatorType to find ActuatorModels for.
     * @return An Iterable of ActuatorModels that have the specified ActuatorType identity.
     */
    Iterable<ActuatorModel> findActuatorModelsByActuatorTypeName(ActuatorTypeName actuatorTypeName);

    /**
     * This method is used to find ActuatorModel names by their ActuatorType identity.
     * <p>
     * @param actuatorTypeName The name of the ActuatorType to find ActuatorModel names for.
     * @return An Iterable of ActuatorModel names that have the specified ActuatorType identity.
     */
    Iterable<ActuatorModelName> findActuatorModelNamesByActuatorTypeName(ActuatorTypeName actuatorTypeName);
}
