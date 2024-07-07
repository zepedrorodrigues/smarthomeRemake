package smarthome.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.ActuatorModelFactory;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.DeviceTypeFactory;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.*;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.SensorModelFactory;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;

import java.io.File;
import java.util.List;

/**
 * The RepositoryLoader class is responsible for loading the repositories with the data from the configuration file.
 */
public class RepositoryLoader {

    Configuration configuration;

    SensorTypeFactory sensorTypeFactory;
    SensorModelFactory sensorModelFactory;

    ActuatorTypeFactory actuatorTypeFactory;
    ActuatorModelFactory actuatorModelFactory;
    DeviceTypeFactory deviceTypeFactory;


    /**
     * Constructs a RepositoryLoader object with the specified file path name, sensor type factory,
     * sensor model factory, actuator type factory, actuator model factory and device type factory.
     *
     * @param filePathName            the file path name
     * @param sensorTypeFactory    the sensor type factory
     * @param sensorModelFactory   the sensor model factory
     * @param actuatorTypeFactory  the actuator type factory
     * @param actuatorModelFactory the actuator model factory
     * @param deviceTypeFactory the device type factory
     * @throws InstantiationException if the file path name is invalid or if any of the factories is null
     */
    public RepositoryLoader(String filePathName, SensorTypeFactory sensorTypeFactory,
                            SensorModelFactory sensorModelFactory,
                            ActuatorTypeFactory actuatorTypeFactory,
                            ActuatorModelFactory actuatorModelFactory,
                            DeviceTypeFactory deviceTypeFactory) throws InstantiationException {

        Configurations configurations = new Configurations();

        try {
            configuration = configurations.properties(new File(filePathName));

            if (sensorTypeFactory == null || sensorModelFactory == null || actuatorTypeFactory == null ||
                    actuatorModelFactory == null || deviceTypeFactory == null) { throw new NullPointerException();
            }

        } catch (ConfigurationException | NullPointerException e) {
            throw new InstantiationException();
        }

        this.sensorTypeFactory = sensorTypeFactory;
        this.sensorModelFactory = sensorModelFactory;
        this.actuatorTypeFactory = actuatorTypeFactory;
        this.actuatorModelFactory = actuatorModelFactory;
        this.deviceTypeFactory = deviceTypeFactory;
    }

    /**
     * Loads the sensor type repository with the data from the configuration file.
     *
     * @param sensorTypeRepository the sensor type repository
     */
    public void loadSensorTypeRepository(ISensorTypeRepository sensorTypeRepository) {

        List<String> sensorTypeConfigs = List.of(configuration.getStringArray("sensorType"));

        for (String sensorTypeConfig : sensorTypeConfigs) {
            String[] sensorTypeConfigArr = sensorTypeConfig.split("\\.");
            SensorTypeName sensorTypeName = new SensorTypeName(sensorTypeConfigArr[0]);
            SensorTypeUnit sensorTypeUnit = new SensorTypeUnit(sensorTypeConfigArr[1]);
            SensorType sensorType = sensorTypeFactory.createSensorType(sensorTypeName, sensorTypeUnit);
            sensorTypeRepository.save(sensorType);
        }
    }

    /**
     * Loads the sensor model repository with the data from the configuration file.
     *
     * @param sensorModelRepository the sensor model repository
     */
    public void loadSensorModelRepository(ISensorModelRepository sensorModelRepository) {

        String[] arrayStringSensorModel = configuration.getStringArray("sensorModel");
        List<String> sensorModelConfigs = List.of(arrayStringSensorModel);

        for (String sensorModelConfig : sensorModelConfigs) {
            String[] sensorModelConfigArr = sensorModelConfig.split("\\.");
            SensorModelName sensorModelName = new SensorModelName(sensorModelConfigArr[0]);
            SensorTypeId sensorTypeId = new SensorTypeId(sensorModelConfigArr[1]);
            SensorModel sensorModel = sensorModelFactory.createSensorModel(sensorModelName, sensorTypeId);
            sensorModelRepository.save(sensorModel);
        }
    }

    /**
     * Loads the actuator type repository with the data from the configuration file.
     *
     * @param actuatorTypeRepository the actuator type repository
     */
    public void loadActuatorTypeRepository(IActuatorTypeRepository actuatorTypeRepository) {
        List<String> actuatorTypeConfigs = List.of(configuration.getStringArray("actuatorType"));

        for (String actuatorTypeConfig : actuatorTypeConfigs) {
            ActuatorTypeName actuatorTypeName = new ActuatorTypeName(actuatorTypeConfig);
            ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeName);
            actuatorTypeRepository.save(actuatorType);
        }
    }

    /**
     * Loads the actuator model repository with the data from the configuration file.
     *
     * @param actuatorModelRepository the actuator model repository
     */
    public void loadActuatorModelRepository(IActuatorModelRepository actuatorModelRepository) {
        List<String> actuatorModelConfigs = List.of(configuration.getStringArray("actuatorModel"));

        for (String actuatorModelConfig : actuatorModelConfigs) {
            String[] actuatorModelConfigArr = actuatorModelConfig.split("\\.");
            ActuatorModelName actuatorTModelName = new ActuatorModelName(actuatorModelConfigArr[0]);
            ActuatorTypeName actuatorTypeName = new ActuatorTypeName(actuatorModelConfigArr[1]);
            ActuatorModel actuatorModel = actuatorModelFactory.createActuatorModel(actuatorTModelName,
                    actuatorTypeName);
            actuatorModelRepository.save(actuatorModel);
        }
    }

    /**
     * Loads the device type repository with the data from the configuration file.
     *
     * @param deviceTypeRepository the device type repository
     */
    public void loadDeviceTypeRepository(IDeviceTypeRepository deviceTypeRepository) {
        List<String> deviceTypeConfigs = List.of(configuration.getStringArray("deviceType"));

        for(String deviceTypeConfig : deviceTypeConfigs) {
            DeviceTypeName deviceTypeName = new DeviceTypeName(deviceTypeConfig);
            DeviceType deviceType = deviceTypeFactory.createDeviceType(deviceTypeName);
            deviceTypeRepository.save(deviceType);
        }
    }

}
