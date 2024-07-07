package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.persistence.datamodel.SensorDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between SensorDataModel and Sensor domain entities.
 * It uses a SensorFactory object to create Sensor domain entities.
 */
@Component
public class SensorDataModelMapper {

    private final SensorFactory sensorFactory;

    /**
     * Constructor for the SensorDataModelMapper class.
     * This constructor initializes the SensorDataModelMapper object with a SensorFactory object.
     *
     * @param sensorFactory The SensorFactory object to be used for creating Sensor domain entities.
     */
    public SensorDataModelMapper(SensorFactory sensorFactory) {
        this.sensorFactory = sensorFactory;
    }

    /**
     * This method converts a SensorDataModel object to a Sensor domain object.
     * It uses the SensorFactory to create a new Sensor object.
     * @param sensorDataModel The SensorDataModel object to be converted
     * @return A Sensor domain object
     */
    public Sensor toDomain(SensorDataModel sensorDataModel) {
        SensorId sensorId = new SensorId(sensorDataModel.getSensorId());
        DeviceId deviceId = new DeviceId(sensorDataModel.getDeviceId());
        SensorModelName sensorModelName = new SensorModelName(sensorDataModel.getSensorModelName());

        return sensorFactory.createSensor(sensorId, sensorModelName, deviceId);

    }

    /**
     * This method converts a list of SensorDataModel objects to a list of Sensor domain objects.
     * It uses the SensorFactory to create new Sensor objects.
     * @param listSensorDataModel The list of SensorDataModel objects to be converted
     * @return A list of Sensor domain objects
     */
    public Iterable<Sensor> toDomain(Iterable<SensorDataModel> listSensorDataModel) {

        List<Sensor> listDomain = new ArrayList<>();

        listSensorDataModel.forEach(sensorDataModel -> {
            Sensor sensorDomain = toDomain(sensorDataModel);
            listDomain.add(sensorDomain);
        });

        return listDomain;
    }
}
