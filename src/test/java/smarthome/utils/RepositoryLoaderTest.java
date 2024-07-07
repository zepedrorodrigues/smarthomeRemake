package smarthome.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatormodel.ActuatorModelFactory;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.deviceType.DeviceTypeFactory;
import smarthome.domain.repository.*;
import smarthome.domain.sensormodel.SensorModelFactory;
import smarthome.domain.sensortype.SensorTypeFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * The RepositoryLoaderTest class tests the RepositoryLoader class.
 */
class RepositoryLoaderTest {

    String filePathName;
    SensorTypeFactory sensorTypeFactory;
    SensorModelFactory sensorModelFactory;
    ActuatorTypeFactory actuatorTypeFactoryImp;
    ActuatorModelFactory actuatorModelFactory;

    DeviceTypeFactory deviceTypeFactory;
    RepositoryLoader repositoryLoader;

    /**
     * Sets up the RepositoryLoader instance for the tests.
     */
    @BeforeEach
    void setUp() throws InstantiationException {
        filePathName = "configTest.properties";
        sensorTypeFactory = mock(SensorTypeFactory.class);
        sensorModelFactory = mock(SensorModelFactory.class);
        actuatorTypeFactoryImp = mock(ActuatorTypeFactory.class);
        actuatorModelFactory = mock(ActuatorModelFactory.class);
        deviceTypeFactory = mock(DeviceTypeFactory.class);

        repositoryLoader = new RepositoryLoader(filePathName, sensorTypeFactory, sensorModelFactory,
                actuatorTypeFactoryImp, actuatorModelFactory, deviceTypeFactory);
    }

    /**
     * Tests the constructor of the RepositoryLoader class does not throw an exception
     * when valid parameters are provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new RepositoryLoader(filePathName, sensorTypeFactory, sensorModelFactory,
                actuatorTypeFactoryImp, actuatorModelFactory, deviceTypeFactory),
                "Constructor should not throw an exception");
    }

    /**
     * Tests the constructor of the RepositoryLoader class throws an exception
     * when an invalid FilePathName is provided.
     */
    @Test
    void testConstructorThrowsExceptionForInvalidFilePathName() {
        //Arrange
        String invalidFilePathName = "invalidPath.properties";
        //Act and Assert
        assertThrows(InstantiationException.class, () -> new RepositoryLoader(invalidFilePathName,
                sensorTypeFactory, sensorModelFactory, actuatorTypeFactoryImp, actuatorModelFactory,
                deviceTypeFactory), "Constructor should throw an exception");
    }

    /**
     * Tests the constructor of the RepositoryLoader class throws an exception
     * when a null SensorTypeFactory is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullSensorTypeFactory() {
        //Arrange
        SensorTypeFactory nullSensorTypeFactory = null;
        //Act and Assert
        assertThrows(InstantiationException.class, () -> new RepositoryLoader(filePathName, nullSensorTypeFactory,
                sensorModelFactory, actuatorTypeFactoryImp, actuatorModelFactory, deviceTypeFactory),
                "Constructor should throw an exception");
    }

    /**
     * Tests the constructor of the RepositoryLoader class throws an exception
     * when a null SensorModelFactory is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullSensorModelFactory() {
        //Arrange
        SensorModelFactory nullSensorModelFactory = null;
        //Act and Assert
        assertThrows(InstantiationException.class, () -> new RepositoryLoader(filePathName, sensorTypeFactory,
                nullSensorModelFactory, actuatorTypeFactoryImp, actuatorModelFactory, deviceTypeFactory),
                "Constructor should throw an exception");
    }

    /**
     * Tests the constructor of the RepositoryLoader class throws an exception
     * when a null ActuatorTypeFactoryImp is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullActuatorTypeFactory() {
        //Arrange
        ActuatorTypeFactory nullActuatorTypeFactoryImp = null;
        //Act and Assert
        assertThrows(InstantiationException.class, () -> new RepositoryLoader(filePathName, sensorTypeFactory,
                sensorModelFactory, nullActuatorTypeFactoryImp, actuatorModelFactory, deviceTypeFactory),
                "Constructor should throw an exception");
    }

    /**
     * Tests the constructor of the RepositoryLoader class throws an exception
     * when a null ActuatorModelFactory is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullActuatorModelFactory() {
        //Arrange
        ActuatorModelFactory nullActuatorModelFactory = null;
        //Act and Assert
        assertThrows(InstantiationException.class, () -> new RepositoryLoader(filePathName, sensorTypeFactory,
                sensorModelFactory, actuatorTypeFactoryImp, nullActuatorModelFactory, deviceTypeFactory),
                "Constructor should throw an exception");
    }

    /**
     * Tests the constructor of the RepositoryLoader class throws an exception
     * when a null DeviceTypeFactory is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullDeviceTypeFactory() {
        //Arrange
        DeviceTypeFactory nullDeviceTypeFactory = null;
        //Act and Assert
        assertThrows(InstantiationException.class, () -> new RepositoryLoader(filePathName, sensorTypeFactory,
                sensorModelFactory, actuatorTypeFactoryImp, actuatorModelFactory, nullDeviceTypeFactory),
                "Constructor should throw an exception");
    }

    /**
     * Tests the loadSensorTypeRepository method of the RepositoryLoader class,
     * verifying that the save method is called twice.
     */
    @Test
    void testLoadSensorTypeRepository() {
        //Arrange
        ISensorTypeRepository sensorTypeRepositoryMem = mock(ISensorTypeRepository.class);
        //Act
        repositoryLoader.loadSensorTypeRepository(sensorTypeRepositoryMem);
        //Assert
        verify(sensorTypeRepositoryMem, times(2)).save(any());
    }

    /**
     * Tests the loadSensorModelRepository method of the RepositoryLoader class,
     * verifying that the save method is called twice.
     */
    @Test
    void testLoadSensorModelRepository() {
        //Arrange
        ISensorModelRepository sensorModelRepositoryMem = mock(ISensorModelRepository.class);
        //Act
        repositoryLoader.loadSensorModelRepository(sensorModelRepositoryMem);
        //Assert
        verify(sensorModelRepositoryMem, times(2)).save(any());
    }

    /**
     * Tests the loadActuatorTypeRepository method of the RepositoryLoader class,
     * verifying that the save method is called twice.
     */
    @Test
    void testLoadActuatorTypeRepository() {
        //Arrange
        IActuatorTypeRepository actuatorTypeRepositoryMem = mock(IActuatorTypeRepository.class);
        //Act
        repositoryLoader.loadActuatorTypeRepository(actuatorTypeRepositoryMem);
        //Assert
        verify(actuatorTypeRepositoryMem, times(2)).save(any());
    }

    /**
     * Tests the loadActuatorModelRepository method of the RepositoryLoader class,
     * verifying that the save method is called twice.
     */
    @Test
    void testLoadActuatorModelRepository() {
        //Arrange
        IActuatorModelRepository actuatorModelRepositoryMem = mock(IActuatorModelRepository.class);
        //Act
        repositoryLoader.loadActuatorModelRepository(actuatorModelRepositoryMem);
        //Assert
        verify(actuatorModelRepositoryMem, times(4)).save(any());
    }

    /**
     * Tests the loadDeviceTypeRepository method of the RepositoryLoader class,
     * verifying that the save method is called three times.
     */
    @Test
    void testLoadDeviceTypeRepository() {
        //Arrange
        IDeviceTypeRepository deviceTypeRepository = mock((IDeviceTypeRepository.class));
        //Act
        repositoryLoader.loadDeviceTypeRepository(deviceTypeRepository);
        //Assert
        verify(deviceTypeRepository, times(3)).save(any());
    }

}