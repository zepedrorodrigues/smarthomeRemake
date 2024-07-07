package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

/**
 * The IActuatorTypeRepository interface represents a repository for managing ActuatorType entities.
 */
public interface IActuatorTypeRepository extends IRepository<ActuatorTypeName, ActuatorType> {

    /**
     * Retrieve all ActuatorType names in the repository.
     * <p>
     * @return all ActuatorType names in the repository
     */
    Iterable<ActuatorTypeName> findActuatorTypeNames();
}
