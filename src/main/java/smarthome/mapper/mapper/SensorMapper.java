package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorIdDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for SensorDTO
 */
@Component
public class SensorMapper {

    /**
     * SensorMapper constructor
     */
    public SensorMapper() {
        // Empty constructor
    }

    /**
     * Converts the sensor data to a DeviceId value object
     *
     * @param sensorDTO the SensorDTO object
     * @return the Sensor value object
     */
    public DeviceId toDeviceId(SensorDTO sensorDTO) {
        return new DeviceId(sensorDTO.getDeviceId());
    }

    /**
     * Converts the sensor data to a Sensor object.
     *
     * @param sensorDTO the SensorDTO object
     * @return the Sensor value object
     */
    public SensorModelName toSensorModelName(SensorDTO sensorDTO) {
        return new SensorModelName(sensorDTO.getSensorModelName());
    }

    /**
     * Converts a Sensor object to a SensorDTO object.
     *
     * @param sensor the Sensor object
     * @return the SensorDTO object
     */
    public SensorDTO toSensorDTO(Sensor sensor) {
        String sensorID = sensor.getIdentity().getSensorId();
        String deviceID = sensor.getDeviceId().getIdentity();
        String sensorModelName = sensor.getSensorModelName().getSensorModelName();
        String value = sensor.getValue().valueToString();
        return new SensorDTO(sensorID, deviceID, sensorModelName, value);
    }

    /**
     * Converts a list of Sensor objects to a list of SensorDTO objects.
     * @param sensorIds
     * @return
     */
    public List<SensorIdDTO> toSensorIdsDTO(Iterable<SensorId> sensorIds) {
        List<SensorIdDTO> sensorIdDTOList = new ArrayList<>();
        for (SensorId sensorId : sensorIds) {
            sensorIdDTOList.add(new SensorIdDTO(sensorId.getSensorId()));
        }
        return sensorIdDTOList;
    }
}
