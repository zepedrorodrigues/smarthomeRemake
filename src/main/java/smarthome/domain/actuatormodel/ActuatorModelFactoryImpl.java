package smarthome.domain.actuatormodel;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

/**
 *  This class is an implementation of the ActuatorModelFactory interface.
 *  It is responsible for creating instances of ActuatorModel.
 */
@Component
public class ActuatorModelFactoryImpl implements ActuatorModelFactory {

    /**
     * Create a new ActuatorModel object
     * @param actuatorModelName The name of the ActuatorModel
     * @param actuatorTypeName The type of the ActuatorModel
     * @return The created ActuatorModel object
     */
    public ActuatorModel createActuatorModel(ActuatorModelName actuatorModelName, ActuatorTypeName actuatorTypeName){
        return new ActuatorModel(actuatorModelName, actuatorTypeName);
    }

}
