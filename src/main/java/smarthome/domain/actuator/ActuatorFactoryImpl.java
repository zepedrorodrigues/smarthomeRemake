package smarthome.domain.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;

import java.util.HashMap;
import java.util.Map;

@Component("actuatorFactoryImpl")
public class ActuatorFactoryImpl implements ActuatorFactory {

    private final Map<String, ActuatorFactory> actuatorFactories;

    @Autowired
    public ActuatorFactoryImpl(@Qualifier("actuatorOfBlindRollerFactory") ActuatorFactory blindRollerFactory,
                               @Qualifier("actuatorOfDecimalLimiterFactory") ActuatorFactory decimalLimiterFactory,
                               @Qualifier("actuatorOfLimiterFactory") ActuatorFactory limiterFactory,
                               @Qualifier("actuatorOfOnOffSwitchFactory") ActuatorFactory onOffSwitchFactory) {
        actuatorFactories = new HashMap<>();

        // Register the known factories
        register("ActuatorOfBlindRoller", blindRollerFactory);
        register("ActuatorOfDecimalLimiter", decimalLimiterFactory);
        register("ActuatorOfLimiter", limiterFactory);
        register("ActuatorOfOnOffSwitch", onOffSwitchFactory);
    }

    private void register(String actuatorModel, ActuatorFactory creator) {
        actuatorFactories.put(actuatorModel, creator);
    }

    @Override
    public Actuator createActuator(ActuatorMap actuatorDataMap) {
        if (actuatorDataMap == null) {
            throw new IllegalArgumentException("Actuator data map cannot be null");
        }
        ActuatorModelName model = actuatorDataMap.getActuatorModelName();
        ActuatorFactory creatorOfActuator = actuatorFactories.get(model.getActuatorModelName());
        if (creatorOfActuator == null) {
            throw new IllegalArgumentException("No ActuatorFactory registered for model: " + model.getActuatorModelName());
        }
        return creatorOfActuator.createActuator(actuatorDataMap);
    }
}
