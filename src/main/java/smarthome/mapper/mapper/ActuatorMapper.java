package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorOfDecimalLimiter;
import smarthome.domain.actuator.ActuatorOfLimiter;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuator.vo.DecimalLimit;
import smarthome.domain.actuator.vo.IntLimit;
import smarthome.domain.actuator.vo.Precision;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorIdDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * ActuatorMapper is a class for mapping ActuatorDTO to ActuatorMap and vice versa.
 */
@Component
public class ActuatorMapper {

    /**
     * This method is used to convert an ActuatorDTO object into an ActuatorMap object.
     *
     * @param actuatorDTO he ActuatorDTO object that needs to be converted into an ActuatorMap object.
     * @return ActuatorMap object that contains the information extracted from the ActuatorDTO object
     */
    public ActuatorMap toActuatorMap(ActuatorDTO actuatorDTO) {
        ActuatorId actuatorId = toActuatorId(actuatorDTO);
        DeviceId deviceId = toDeviceId(actuatorDTO);
        ActuatorModelName actuatorModelName = toActuatorModelName(actuatorDTO);
        IntLimit integerUpperLimit = toIntUpperLimit(actuatorDTO);
        IntLimit integerLowerLimit = toIntLowerLimit(actuatorDTO);
        DecimalLimit decimalUpperLimit = toDecimalUpperLimit(actuatorDTO);
        DecimalLimit decimalLowerLimit = toDecimalLowerLimit(actuatorDTO);
        Precision precision = toPrecision(actuatorDTO);

        return new ActuatorMap(actuatorId, deviceId, actuatorModelName, integerUpperLimit, integerLowerLimit,
                decimalUpperLimit, decimalLowerLimit, precision);
    }

    /**
     * This method is used to convert an ActuatorDTO object into an ActuatorId object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into an ActuatorId object.
     * @return ActuatorId object that contains the information extracted from the ActuatorDTO object.
     */
    public ActuatorId toActuatorId(ActuatorDTO actuatorDTO) {
        try {
            String actuatorId = actuatorDTO.getActuatorId();
            return new ActuatorId(actuatorId);
        } catch (Exception e) {
            return null;

        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into a DeviceId object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into a DeviceId object.
     * @return DeviceId object that contains the information extracted from the ActuatorDTO object.
     */
    public DeviceId toDeviceId(ActuatorDTO actuatorDTO) {
        try {
            String deviceId = actuatorDTO.getDeviceId();
            return new DeviceId(deviceId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into an ActuatorModelName object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into an ActuatorModelName object.
     * @return ActuatorModelName object that contains the information extracted from the ActuatorDTO object.
     */
    public ActuatorModelName toActuatorModelName(ActuatorDTO actuatorDTO) {
        try {
            String actuatorModelName = actuatorDTO.getActuatorModelName();
            return new ActuatorModelName(actuatorModelName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into an IntLimit object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into an IntLimit object.
     * @return IntLimit object that contains the information extracted from the ActuatorDTO object.
     */
    public IntLimit toIntUpperLimit(ActuatorDTO actuatorDTO) {
        try {
            int integerUpperLimit = actuatorDTO.getIntegerUpperLimit();
            return new IntLimit(integerUpperLimit);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into an IntLimit object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into an IntLimit object.
     * @return IntLimit object that contains the information extracted from the ActuatorDTO object.
     */
    public IntLimit toIntLowerLimit(ActuatorDTO actuatorDTO) {
        try {
            int integerLowerLimit = actuatorDTO.getIntegerLowerLimit();
            return new IntLimit(integerLowerLimit);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into a DecimalLimit object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into a DecimalLimit object.
     * @return DecimalLimit object that contains the information extracted from the ActuatorDTO object.
     */
    public DecimalLimit toDecimalUpperLimit(ActuatorDTO actuatorDTO) {
        try {
            double doubleUpperLimit = actuatorDTO.getDoubleUpperLimit();
            return new DecimalLimit(doubleUpperLimit);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into a DecimalLimit object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into a DecimalLimit object.
     * @return DecimalLimit object that contains the information extracted from the ActuatorDTO object.
     */
    public DecimalLimit toDecimalLowerLimit(ActuatorDTO actuatorDTO) {
        try {
            double doubleLowerLimit = actuatorDTO.getDoubleLowerLimit();
            return new DecimalLimit(doubleLowerLimit);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an ActuatorDTO object into a Precision object.
     *
     * @param actuatorDTO The ActuatorDTO object that needs to be converted into a Precision object.
     * @return Precision object that contains the information extracted from the ActuatorDTO object.
     */
    public Precision toPrecision(ActuatorDTO actuatorDTO) {
        try {
            int doubleLimitPrecision = actuatorDTO.getDoubleLimitPrecision();
            return new Precision(doubleLimitPrecision);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method is used to convert an Actuator object into an ActuatorDTO object.
     *
     * @param actuator The Actuator object that needs to be converted into an ActuatorDTO object.
     * @return ActuatorDTO object that contains the information extracted from the Actuator object.
     */
    public ActuatorDTO actuatorToDTO(Actuator actuator) {

        DeviceId deviceId = actuator.getDeviceId();
        String deviceIdStr = deviceId.getIdentity();
        ActuatorModelName actuatorModelName = actuator.getActuatorModelName();
        String actuatorModelNameStr = actuatorModelName.getActuatorModelName();
        ActuatorId actuatorId = actuator.getIdentity();
        String actuatorIdStr = actuatorId.getActuatorId();


        if (actuator instanceof ActuatorOfLimiter) {
            IntLimit upperLimit = ((ActuatorOfLimiter) actuator).getUpperLimit();
            int upperLimitValue = upperLimit.getIntLimit();
            IntLimit lowerLimit = ((ActuatorOfLimiter) actuator).getLowerLimit();
            int lowerLimitValue = lowerLimit.getIntLimit();

            return new ActuatorDTO(actuatorIdStr, deviceIdStr, actuatorModelNameStr, upperLimitValue, lowerLimitValue
                    , null, null, null);
        } else if (actuator instanceof ActuatorOfDecimalLimiter) {
            DecimalLimit upperLimit = ((ActuatorOfDecimalLimiter) actuator).getUpperLimit();
            double upperLimitValue = upperLimit.getDecimalLimit();
            DecimalLimit lowerLimit = ((ActuatorOfDecimalLimiter) actuator).getLowerLimit();
            double lowerLimitValue = lowerLimit.getDecimalLimit();
            Precision precision = ((ActuatorOfDecimalLimiter) actuator).getPrecision();
            int precisionValue = precision.getPrecision();

            return new ActuatorDTO(actuatorIdStr, deviceIdStr, actuatorModelNameStr, null, null, upperLimitValue,
                    lowerLimitValue, precisionValue);
        } else {
            return new ActuatorDTO(actuatorIdStr, deviceIdStr, actuatorModelNameStr, null, null, null, null, null);
        }


    }

    /**
     * This method is used to convert a list of Actuator ids into a list of ActuatorIdDTO objects.
     *
     * @param actuatorIds The list of Actuator ids that needs to be converted into a list of ActuatorIdDTO objects.
     * @return List of ActuatorIdDTO objects that contains the information extracted from the Actuator ids.
     */
    public List<ActuatorIdDTO> toActuatorIdsDTO(Iterable<ActuatorId> actuatorIds) {
        List<ActuatorIdDTO> actuatorIdsDTO = new ArrayList<>();
        for (ActuatorId actuatorId : actuatorIds) {
            actuatorIdsDTO.add(new ActuatorIdDTO(actuatorId.getActuatorId()));
        }
        return actuatorIdsDTO;
    }
}
