import React, { useEffect, useState } from 'react';
import './DeviceDetails.css';
import { MdBlinds, MdBlindsClosed } from 'react-icons/md';
import ReadingList from "../../src/components/ReadingList";
import AppButton from "./buttons/AppButton";
import ToggleSwitch from "./toggle-switch/ToggleSwitch";
import TabMenu from "./tab-menu/TabMenu";
import { useParams, useLocation, useNavigate } from 'react-router-dom';

const DeviceDetails = ({ device: propDevice, roomName: propRoomName, onBack: propOnBack }) => {
    const { roomName: paramRoomName, deviceName: paramDeviceName } = useParams();
    const location = useLocation();
    const navigate = useNavigate();
    const { device: stateDevice } = location.state || {};

    const device = propDevice || stateDevice;
    const roomName = propRoomName || paramRoomName;

    const [status, setStatus] = useState(device.deviceStatus);
    const [actuators, setActuators] = useState([]);
    const [actuatorTypes, setActuatorTypes] = useState([]);
    const [actuatorModels, setActuatorModels] = useState([]);
    const [showAddActuatorForm, setShowAddActuatorForm] = useState(false);
    const [newActuatorModel, setNewActuatorModel] = useState('');
    const [newActuatorType, setNewActuatorType] = useState('');
    const [newIntUpperLimit, setNewIntUpperLimit] = useState('');
    const [newIntLowerLimit, setNewIntLowerLimit] = useState('');
    const [newDoubleUpperLimit, setNewDoubleUpperLimit] = useState('');
    const [newDoubleLowerLimit, setNewDoubleLowerLimit] = useState('');
    const [newDoubleLimitPrecision, setNewDoubleLimitPrecision] = useState('');
    const [sensors, setSensors] = useState([]);
    const [newSensorType, setNewSensorType] = useState('');
    const [newSensorModel, setNewSensorModel] = useState('');
    const [showAddSensorForm, setShowAddSensorForm] = useState(false);
    const [sensorTypes, setSensorTypes] = useState([]);
    const [sensorModels, setSensorModels] = useState({});
    const [showReadingsForm, setShowReadingsForm] = useState(false);
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [getReadingsDisabled, setGetReadingsDisabled] = useState(false);
    const [showReadingsList, setShowReadingsList] = useState(false);
    const [readings, setReadings] = useState([]);
    const [actuatorValues, setActuatorValues] = useState({});
    const [initialActuatorValues, setInitialActuatorValues] = useState({});
    const [sliderChanged, setSliderChanged] = useState({});
    const [transformedSensorTypes, setTransformedSensorTypes] = useState([]);
    const [popupMessage, setPopupMessage] = useState('');
    const [showPopup, setShowPopup] = useState(false);
    const [showActuatorTab, setShowActuatorTab] = useState(true);
    const [showSensorTab, setShowSensorTab] = useState(false);
    const [sensorTypeErrorMessage, setSensorTypeErrorMessage] = useState('');
    const [sensorModelErrorMessage, setSensorModelErrorMessage] = useState('');
    const [actuatorTypeErrorMessage, setActuatorTypeErrorMessage] = useState('');
    const [actuatorModelErrorMessage, setActuatorModelErrorMessage] = useState('');
    const [dateErrorMessage, setDateErrorMessage] = useState('');
    const [dateRequiredMessage, setDateRequiredMessage] = useState('');
    const [actuatorErrorMessage, setActuatorErrorMessage] = useState('');

    useEffect(() => {
        const fetchUpdateActuatorValue = async (actuatorId) => {
            try {
                const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuators/${actuatorId}/current-percentage-value`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                });

                if (!response.ok) {
                    throw new Error(`HTTP error status: ${response.status}`);
                }

                const data = await response.json();
                setActuatorValues((prevValues) => ({ ...prevValues, [actuatorId]: data.value }));
                setInitialActuatorValues((prevValues) => ({ ...prevValues, [actuatorId]: data.value }));
                setSliderChanged((prevChanged) => ({ ...prevChanged, [actuatorId]: false }));
            } catch (error) {
                console.error('Error updating actuator value:', error);
            }
        };

        if (actuators.length > 0) {
            actuators.forEach((actuator) => {
                fetchUpdateActuatorValue(actuator.actuatorId);
            });
        }
    }, [actuators]);

    const fetchOperateActuator = async (actuatorId, value) => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuators/${actuatorId}/operate-blind-roller?percentage=${value}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
            });

            if (!response.ok) {
                if (response.status === 400) {
                    throw new Error('Required sensor is missing!');
                } else {
                    throw new Error(`HTTP error status: ${response.status}`);
                }
            }

            const data = await response.json();
            console.log('Operated actuator:', data); // Log the entire response for debugging
            setActuatorValues((prevValues) => ({ ...prevValues, [actuatorId]: value }));
            setSliderChanged((prevChanged) => ({ ...prevChanged, [actuatorId]: false }));

            setPopupMessage("Success!");
            setTimeout(() => setShowPopup(false), 2000); // Hide after an additional 2 seconds

        } catch (error) {
            console.error('Error operating actuator:', error);
            setPopupMessage(error.message);
            setShowPopup(true);
            setTimeout(() => setShowPopup(false), 2000); // Ocultar após 2 segundos
        }
    };

    const handleSliderChange = (actuatorId, value) => {
        const initialValue = initialActuatorValues[actuatorId];
        const isChanged = value !== initialValue;
        setActuatorValues((prevValues) => ({ ...prevValues, [actuatorId]: value }));
        setSliderChanged((prevChanged) => ({ ...prevChanged, [actuatorId]: isChanged }));
    };

    const handleOperateActuator = (actuatorId) => {
        if (!sliderChanged[actuatorId]) {
            alert("Data hasn't changed");
            return;
        }

        const newValue = actuatorValues[actuatorId];
        const initialValue = initialActuatorValues[actuatorId];

        const message = "Loading...";
        setPopupMessage(message);
        setShowPopup(true);

        setTimeout(() => {
            fetchOperateActuator(actuatorId, newValue);
        }, 2000);

        setInitialActuatorValues((prevValues) => ({ ...prevValues, [actuatorId]: newValue }));
        setSliderChanged((prevChanged) => ({ ...prevChanged, [actuatorId]: false }));
    };

    const fetchSensorOfDevice = async () => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/sensors/device/${device.deviceId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error status: ${response.status}`);
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const detailedSensors = await Promise.all(data.map(async (sensor) => {
                    const sensorDetailsResponse = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/sensors/${sensor.sensorId}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });

                    if (!sensorDetailsResponse.ok) {
                        throw new Error(`HTTP error status: ${sensorDetailsResponse.status}`);
                    }

                    const sensorDetailsData = await sensorDetailsResponse.json();
                    return { ...sensor, ...sensorDetailsData };
                }));

                setSensors(detailedSensors);
                console.log('Detailed sensor fetched successfully:', detailedSensors);
            } else {
                console.error('Failed to fetch sensors: Unexpected response format', data);
            }
        } catch (error) {
            console.error('Error fetching sensors:', error);
        }
    };

    useEffect(() => {
        fetchSensorOfDevice();
    }, [device.deviceId]);

    const handleAddSensor = async () => {
        let valid = true;

        if (newSensorType === '') {
            setSensorTypeErrorMessage('Please select a sensor type.');
            valid = false;
        } else {
            setSensorTypeErrorMessage('');
        }

        if (valid && newSensorModel === '') { // Só verificar o modelo se o tipo for válido
            setSensorModelErrorMessage('Please select a sensor model.');
            valid = false;
        } else {
            setSensorModelErrorMessage('');
        }

        if (!valid) {
            return;
        }

        const newSensor = {
            sensorModelName: newSensorModel,
        };

        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/sensors/device/${device.deviceId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newSensor)
            });

            if (response.ok) {
                const addedSensor = await response.json();
                setSensors([...sensors, addedSensor]);
                setNewSensorType('');
                setNewSensorModel('');
                setShowAddSensorForm(false);
                setSensorTypeErrorMessage('');
                setSensorModelErrorMessage('');
            } else {
                console.error('Failed to add sensor:', response.statusText);
            }
        } catch (error) {
            console.error('Error adding sensor:', error);
        }
    };

    const fetchSensorTypes = async () => {
        try {
            const response = await fetch('http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/sensortypes');

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            if (data._embedded && Array.isArray(data._embedded.sensorTypeIdDTOList)) {
                const sensorTypes = data._embedded.sensorTypeIdDTOList.map(sensorTypeDTO => ({
                    raw: sensorTypeDTO.sensorTypeId,
                    transformed: transformSensorTypeId(sensorTypeDTO.sensorTypeId),
                }));
                console.log('Sensor types fetched successfully:', sensorTypes);
                setSensorTypes(sensorTypes);
            } else {
                console.error('Unexpected response format:', data);
            }
        } catch (error) {
            console.error('Error fetching sensor types:', error);
        }
    };

    useEffect(() => {
        fetchSensorTypes();
    }, []);

    const transformSensorTypeId = (sensorTypeId) => {
        switch (sensorTypeId) {
            case 'TemperatureCelsius':
                return 'Temperature (ºC)';
            case 'HumidityPercent':
                return 'Humidity (%)';
            case 'OnOffNa':
                return 'On/Off (N/A)';
            case 'ScalePercentagePercentage':
                return 'Percentage (%)';
            case 'WindSpeedWindDirectionKmHrRadian':
                return 'Wind Speed (Km/h)';
            case 'SunriseTime':
                return 'Sunrise (Time)';
            case 'SunsetTime':
                return 'Sunset (Time)';
            case 'DewPointCelsius':
                return 'Dew Point (%RH)';
            case 'SolarIrradianceWm2':
                return 'Solar Irradiance (W/m^2)';
            case 'PowerConsumptionWatts':
                return 'Power Consumption (W)';
            case 'AveragePowerConsumptionWatts':
                return 'Average Power Consumption (W)';
            case 'ElectricEnergyConsumptionWh':
                return 'Electric Energy Consumption (Wh)';
            default:
                return 'Unknown Sensor Type';
        }
    };

    const displaySensorTypes = () => {
        return sensorTypes.map((type, index) => (
            <option key={index} value={type.raw}>{type.transformed}</option>
        ));
    };

    const fetchSensorModels = async (type) => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/sensormodels/type/${type}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            if (data._embedded && Array.isArray(data._embedded.sensorModelNameDTOList)) {
                const models = data._embedded.sensorModelNameDTOList.map(model => ({
                    raw: model.sensorModelName,
                    transformed: transformSensorModelName(model.sensorModelName),
                }));
                console.log('Sensor models fetched successfully:', models);
                setSensorModels((prevSensorModels) => ({ ...prevSensorModels, [type]: models }));
            } else {
                console.error('Unexpected response format:', data);
            }
        } catch (error) {
            console.error('Error fetching sensor models:', error);
        }
    };

    useEffect(() => {
        if (newSensorType) {
            fetchSensorModels(newSensorType);
        }
    }, [newSensorType]);

    const transformSensorModelName = (sensorModelName) => {
        switch (sensorModelName) {
            case 'SensorOfTemperature':
                return 'Temperature Sensor';
            case 'SensorOfHumidity':
                return 'Humidity Sensor';
            case 'SensorOfOnOff':
                return 'On/Off Sensor';
            case 'SensorOfScalePercentage':
                return 'Position Sensor';
            case 'SensorOfWind':
                return 'Wind Speed Sensor';
            case 'SensorOfSunrise':
                return 'Sunrise Sensor';
            case 'SensorOfSunset':
                return 'Sunset Sensor';
            case 'SensorOfDewPoint':
                return 'Dew Point Sensor';
            case 'SensorOfSolarIrradiance':
                return 'Solar Irradiance Sensor';
            case 'SensorOfPowerConsumption':
                return 'Power Consumption Sensor';
            case 'SensorOfAveragePowerConsumption':
                return 'Average Power Consumption Sensor';
            case 'SensorOfElectricEnergyConsumption':
                return 'Electric Energy Consumption Sensor';
            default:
                return 'Unknown Sensor Model';
        }
    };

    const displaySensorModels = () => {
        return sensorModels[newSensorType]?.map((model, index) => (
            <option key={index} value={model.raw}>{model.transformed}</option>
        ));
    };

    const resetSensorForm = () => {
        setNewSensorType('');
        setNewSensorModel('');
    };

    const handleShowAddSensorForm = () => {
        if (showAddSensorForm) {
            resetSensorForm(); // Reset the form when closing it
        }
        setShowAddSensorForm(!showAddSensorForm);
    };

    const handleAddActuator = async () => {
        let valid = true;

        if (newActuatorType === '') {
            setActuatorTypeErrorMessage('Please select an actuator type.');
            valid = false;
        } else {
            setActuatorTypeErrorMessage('');
        }

        if (valid && newActuatorModel === '') {
            setActuatorModelErrorMessage('Please select an actuator model.');
            valid = false;
        } else {
            setActuatorModelErrorMessage('');
        }

        if (newActuatorType === 'Limiter') {
            if (newIntUpperLimit === '' || newIntLowerLimit === '') {
                setActuatorErrorMessage('Please fill in all fields.');
                valid = false;
            } else {
                setActuatorErrorMessage('');
            }
        }

        if (newActuatorType === 'DecimalLimiter') {
            if (newDoubleUpperLimit === '' || newDoubleLowerLimit === '' || newDoubleLimitPrecision === '') {
                setActuatorErrorMessage('Please fill in all fields.');
                valid = false;
            } else {
                setActuatorErrorMessage('');
            }
        }

        if (!valid) {
            return;
        }

        const newActuator = {
            actuatorModelName: newActuatorModel,
            integerUpperLimit: newIntUpperLimit,
            integerLowerLimit: newIntLowerLimit,
            doubleUpperLimit: newDoubleUpperLimit,
            doubleLowerLimit: newDoubleLowerLimit,
            doubleLimitPrecision: newDoubleLimitPrecision,
        };

        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuators/device/${device.deviceId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newActuator)
            });

            if (response.ok) {
                const addedActuator = await response.json();
                setActuators([...actuators, addedActuator]);
                setNewActuatorType('');
                setNewActuatorModel('');
                setShowAddActuatorForm(false);
                setActuatorTypeErrorMessage('');
                setActuatorModelErrorMessage('');
                setActuatorErrorMessage('');
            } else if (response.status === 422 || response.status === 400) {
                setActuatorErrorMessage('Invalid input. Please check the values.');
            } else {
                console.error('Failed to add actuator:', response.statusText);
            }
        } catch (error) {
            console.error('Error adding actuator:', error);
        }
    };

    const handleDeactivate = async (deviceId) => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/devices/${deviceId}/deactivate`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                setStatus(false);
            } else {
                console.error('Failed to deactivate device:', response.statusText);
            }
        } catch (error) {
            console.error('Error deactivating device:', error);
        }
    };

    useEffect(() => {
        setStatus(device.deviceStatus);
    }, [device.deviceStatus]);

    const fetchActuatorOfDevice = async () => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuators/device/${device.deviceId}`);

            if (!response.ok) {
                throw new Error(`HTTP error status: ${response.status}`);
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const detailedActuators = await Promise.all(data.map(async (actuator) => {
                    const actuatorDetailsResponse = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuators/${actuator.actuatorId}`);

                    if (!actuatorDetailsResponse.ok) {
                        throw new Error(`HTTP error status: ${actuatorDetailsResponse.status}`);
                    }

                    const actuatorDetailsData = await actuatorDetailsResponse.json();
                    return { ...actuator, ...actuatorDetailsData };
                }));

                setActuators(detailedActuators);
                console.log('Detailed actuators fetched successfully:', detailedActuators);
            } else {
                console.error('Failed to fetch actuators: Unexpected response format', data);
            }
        } catch (error) {
            console.error('Error fetching actuators:', error);
        }
    };

    useEffect(() => {
        fetchActuatorOfDevice();
    }, [device.deviceId]);

    useEffect(() => {
        fetchActuatorTypes();
    }, []);

    const fetchActuatorTypes = async () => {
        try {
            const response = await fetch('http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuatortypes');

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const actuatorTypes = data.map(actuatorTypeDTO => actuatorTypeDTO.actuatorTypeName);
                console.log('Actuator types fetched successfully:', actuatorTypes);
                setActuatorTypes(actuatorTypes);
            } else {
                console.error('Unexpected response format:', data);
            }
        } catch (error) {
            console.error('Error fetching actuator types:', error);
        }
    };

    useEffect(() => {
        if (newActuatorType) {
            fetchActuatorModels(newActuatorType);
        }
    }, [newActuatorType]);

    const fetchActuatorModels = async (type) => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/actuatormodels/type/${type}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            if (data._embedded && Array.isArray(data._embedded.actuatorModelNameDTOList)) {
                const models = data._embedded.actuatorModelNameDTOList.map(model => ({
                    raw: model.actuatorModelName,
                    transformed: transformActuatorModelName(model.actuatorModelName),
                }));
                console.log('Actuator models fetched successfully:', models);
                setActuatorModels(prevState => ({
                    ...prevState,
                    [type]: models,
                }));
            } else {
                console.error('Unexpected response format:', data);
            }
        } catch (error) {
            console.error('Error fetching actuator models:', error);
        }
    };

    const transformActuatorTypeName = (actuatorTypeName) => {
        switch (actuatorTypeName) {
            case 'BlindsRoller':
                return 'Blinds Roller';
            case 'DecimalLimiter':
                return 'Decimal Limiter';
            case 'Limiter':
                return 'Limiter';
            case 'OnOffSwitch':
                return 'On/Off Switch';
            default:
                return 'Unknown Actuator Type';
        }
    };

    const transformActuatorModelName = (actuatorModelName) => {
        switch (actuatorModelName) {
            case 'ActuatorOfBlindRoller':
                return 'Blinds Roller Actuator';
            case 'ActuatorOfDecimalLimiter':
                return 'Decimal Limiter Actuator';
            case 'ActuatorOfLimiter':
                return 'Limiter Actuator';
            case 'ActuatorOfOnOffSwitch':
                return 'On/Off Switch';
            default:
                return 'Unknown Actuator Model';
        }
    };

    const displayActuatorTypes = () => {
        return actuatorTypes.map((type, index) => (
            <option key={index} value={type}>{transformActuatorTypeName(type)}</option>
        ));
    };

    const displayActuatorModels = () => {
        return actuatorModels[newActuatorType]?.map((model, index) => (
            <option key={index} value={model.raw}>{model.transformed}</option>
        ));
    };

    const resetActuatorForm = () => {
        setNewActuatorType('');
        setNewActuatorModel('');
        setNewIntUpperLimit('');
        setNewIntLowerLimit('');
        setNewDoubleUpperLimit('');
        setNewDoubleLowerLimit('');
        setNewDoubleLimitPrecision('');
    };

    const handleShowAddActuatorForm = () => {
        if (showAddActuatorForm) {
            resetActuatorForm(); // Reset the form when closing it
        }
        setShowAddActuatorForm(!showAddActuatorForm);
    };

    const handleGetReadings = () => {
        setShowReadingsForm(true);
        setGetReadingsDisabled(true);
    };

    const fetchReadingsOfDevice = async () => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/readings/device/${device.deviceId}?startPeriod=${startDate}&endPeriod=${endDate}`);

            if (!response.ok) {
                if (response.status === 400) {
                    throw new Error('Invalid date range.');
                } else if (response.status === 404) {
                    setPopupMessage("No readings found for the requested period.");
                    setShowPopup(true);
                    setTimeout(() => setShowPopup(false), 5000);
                    return;
                } else {
                    throw new Error(`HTTP error status: ${response.status}`);
                }
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const detailedReadings = await Promise.all(data.map(async (reading) => {
                    const readingDetailsResponse = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/readings/${reading.readingId}`);

                    if (!readingDetailsResponse.ok) {
                        throw new Error(`HTTP error status: ${readingDetailsResponse.status}`);
                    }

                    const readingDetailsData = await readingDetailsResponse.json();
                    return {
                        ...reading, ...readingDetailsData,
                        /*                        transformedActuatorModel: transformActuatorModelName(readingDetailsData.actuatorModelName),
                                                transformedSensorModel: transformSensorModelName(readingDetailsData.sensorModelName)*/
                    };
                }));

                setReadings(detailedReadings);
                setShowReadingsForm(false);
                setShowReadingsList(true);
                setDateErrorMessage(''); // Clear any previous error messages
                setDateRequiredMessage(''); // Clear any previous required messages
                console.log('Detailed readings fetched successfully:', detailedReadings);
            } else {
                console.error('Failed to fetch readings: Unexpected response format', data);
            }
        } catch (error) {
            console.error('Error fetching readings:', error);
            if (error.message === 'Invalid date range.') {
                setDateErrorMessage('Please enter a valid date range.');
            } else {
                setDateErrorMessage('An error occurred while fetching readings.');
            }
        }
    };



    const handleCancelReadings = () => {
        setShowReadingsForm(false);
        setGetReadingsDisabled(false);
    };

    const handleSubmitReadings = () => {
        if (!startDate || !endDate) {
            setDateRequiredMessage('Both start and end dates are required.');
            return;
        }
        fetchReadingsOfDevice();
    };

    const handleBackToDeviceDetails = () => {
        setShowReadingsList(false);
        setGetReadingsDisabled(false);
    };

    const handleClickTab = (tabId) => {
        switch (tabId) {
            case 1:
                setShowActuatorTab(true);
                setShowSensorTab(false);
                break;
            case 2:
                setShowActuatorTab(false);
                setShowSensorTab(true);
                break;
            default:
                setShowActuatorTab(true);
                setShowSensorTab(false);
                break;
        }
    };
    if (showReadingsList) {
        return (
            <ReadingList
                deviceName={device.deviceName}
                readings={readings}
                startDate={startDate}
                endDate={endDate}
                onBack={handleBackToDeviceDetails}
                transformSensorModelName={transformSensorModelName} // pass the function as a prop
            />
        );
    }

    return (
        <div className="cards-container">
            <div className="device-details">
                <div className="device-details-header">
                    <h2>{device.deviceName}</h2>
                    <AppButton type={'warning'} onClick={propOnBack ? propOnBack : () => navigate(-1)} children={'Back'} />
                </div>
                <div className="device-details-info">
                    <p><strong>Type:</strong> {device.deviceTypeName}</p>
                    <p><strong>Status:</strong> {status ? 'active' : 'inactive'}
                        <ToggleSwitch
                            isChecked={status}
                            isLocked={!status}
                            onClick={() => handleDeactivate(device.deviceId)}
                        />
                    </p>
                    <AppButton type={getReadingsDisabled ? 'default' : 'info'} onClick={handleGetReadings}
                               disabled={getReadingsDisabled}
                               children={'Measurements'} />
                    {showReadingsForm && (
                        <div className="readings-card">
                            <h3 className="readings-card-title">Select a period to get measurements for this device:</h3>
                            <div className="readings-form">
                                <div className="date-picker">
                                    <label>Start Date:</label>
                                    <input type="datetime-local" value={startDate}
                                           onChange={(e) => {
                                               setStartDate(e.target.value);
                                               setDateRequiredMessage(''); // Clear required message when user starts typing
                                               setDateErrorMessage(''); // Clear error message when user starts typing
                                           }} />
                                </div>
                                <div className="date-picker">
                                    <label>End Date:</label>
                                    <input type="datetime-local" value={endDate}
                                           onChange={(e) => {
                                               setEndDate(e.target.value);
                                               setDateRequiredMessage(''); // Clear required message when user starts typing
                                               setDateErrorMessage(''); // Clear error message when user starts typing
                                           }} />
                                </div>
                                {dateRequiredMessage && <p className="error-message">{dateRequiredMessage}</p>}
                                {dateErrorMessage && <p className="error-message">{dateErrorMessage}</p>}
                                <div className="buttons-container">
                                    <AppButton type={'info'} onClick={handleSubmitReadings} children={'Submit'} />
                                    <AppButton type={'warning'} onClick={handleCancelReadings} children={'Cancel'} />
                                </div>
                            </div>
                        </div>
                    )}
                </div>

                <TabMenu tabs={[            { id: 1, name: 'Actuators' },            { id: 2, name: 'Sensors' }        ]} onClick={handleClickTab} />

                {showActuatorTab && (
                    <div className="actuator-list">
                        {actuators && actuators.map((actuator, index) => (
                            <div key={index} className="device-item">
                                <span>{index + 1 + ": " + transformActuatorModelName(actuator.actuatorModelName)}</span>
                                {actuator.actuatorModelName === 'ActuatorOfBlindRoller' ? (
                                    <div className="slider-container">
                                        <div className="slider-wrapper">
                                            <MdBlindsClosed className="slider-icon" />
                                            <span className="slider-value">
                                            {status ? (actuatorValues[actuator.actuatorId] !== undefined ? `${actuatorValues[actuator.actuatorId]}%` : 'Loading...') : ''}
                                        </span>
                                            <input
                                                type="range"
                                                min="0"
                                                max="100"
                                                value={actuatorValues[actuator.actuatorId] || 0}
                                                onChange={(e) => handleSliderChange(actuator.actuatorId, e.target.value)}
                                                className="slider"
                                                disabled={!status}
                                            />
                                            <MdBlinds className="slider-icon" />
                                        </div>
                                        <AppButton
                                            type={sliderChanged[actuator.actuatorId] && status ? 'info' : 'default'}
                                            onClick={() => handleOperateActuator(actuator.actuatorId)}
                                            children={'Operate'}
                                            disabled={!sliderChanged[actuator.actuatorId] || !status}
                                        />
                                    </div>
                                ) : (
                                    <AppButton type={'default'} children={'Operate'} disabled={!status} />
                                )}
                            </div>
                        ))}
                        <AppButton type={showAddActuatorForm ? 'warning' : 'info'}
                                   onClick={handleShowAddActuatorForm}>
                            {showAddActuatorForm ? 'Cancel' : 'Add Actuator'}
                        </AppButton>
                        {showAddActuatorForm && (
                            <div className="add-actuator-form">
                                <select
                                    value={newActuatorType}
                                    onChange={(e) => {
                                        setNewActuatorType(e.target.value);
                                        setActuatorTypeErrorMessage(''); // Remover mensagem de erro do tipo
                                        setActuatorModelErrorMessage(''); // Remover mensagem de erro do modelo
                                        setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                    }}
                                >
                                    <option value="">Select Type</option>
                                    {displayActuatorTypes()}
                                </select>
                                {actuatorTypeErrorMessage && <p className="error-message">{actuatorTypeErrorMessage}</p>}

                                {newActuatorType && (
                                    <>
                                        <select
                                            value={newActuatorModel}
                                            onChange={(e) => {
                                                setNewActuatorModel(e.target.value);
                                                setActuatorModelErrorMessage(''); // Remover mensagem de erro do modelo
                                                setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                            }}
                                        >
                                            <option value="">Select Model</option>
                                            {displayActuatorModels()}
                                        </select>
                                        {actuatorModelErrorMessage && <p className="error-message">{actuatorModelErrorMessage}</p>}
                                    </>
                                )}
                                {newActuatorType === 'Limiter' && (
                                    <div>
                                        <input
                                            type="number"
                                            placeholder="Integer Upper Limit"
                                            value={newIntUpperLimit}
                                            onChange={(e) => {
                                                setNewIntUpperLimit(e.target.value);
                                                setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                            }}
                                        />
                                        <input
                                            type="number"
                                            placeholder="Integer Lower Limit"
                                            value={newIntLowerLimit}
                                            onChange={(e) => {
                                                setNewIntLowerLimit(e.target.value);
                                                setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                            }}
                                        />
                                        {actuatorErrorMessage && <p className="error-message">{actuatorErrorMessage}</p>}
                                    </div>
                                )}
                                {newActuatorType === 'DecimalLimiter' && (
                                    <div>
                                        <input
                                            type="number"
                                            placeholder="Double Upper Limit"
                                            value={newDoubleUpperLimit}
                                            onChange={(e) => {
                                                setNewDoubleUpperLimit(e.target.value);
                                                setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                            }}
                                        />
                                        <input
                                            type="number"
                                            placeholder="Double Lower Limit"
                                            value={newDoubleLowerLimit}
                                            onChange={(e) => {
                                                setNewDoubleLowerLimit(e.target.value);
                                                setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                            }}
                                        />
                                        <input
                                            type="number"
                                            min="0"
                                            placeholder="Double Limit Precision"
                                            value={newDoubleLimitPrecision}
                                            onChange={(e) => {
                                                setNewDoubleLimitPrecision(e.target.value);
                                                setActuatorErrorMessage(''); // Limpar mensagens de erro gerais
                                            }}
                                        />
                                        {actuatorErrorMessage && <p className="error-message">{actuatorErrorMessage}</p>}
                                    </div>
                                )}
                                <AppButton type={'info'} onClick={handleAddActuator} children={'Save'} />
                            </div>
                        )}
                    </div>
                )}
                {showSensorTab && (
                    <div className="sensor-list">
                        {sensors && sensors.map((sensor, index) => (
                            <div key={index} className="device-item">
                                <span>{index + 1 + ": " + transformSensorModelName(sensor.sensorModelName)}</span>
                            </div>
                        ))}
                        <AppButton type={showAddSensorForm ? 'warning' : 'info'}
                                   onClick={handleShowAddSensorForm}>
                            {showAddSensorForm ? 'Cancel' : 'Add Sensor'}
                        </AppButton>
                        {showAddSensorForm && (
                            <div className="add-sensor-form">
                                <select
                                    value={newSensorType}
                                    onChange={(e) => {
                                        setNewSensorType(e.target.value);
                                        setSensorTypeErrorMessage(''); // Remove a mensagem de erro
                                    }}
                                >
                                    <option value="">Select Type</option>
                                    {displaySensorTypes()}
                                </select>
                                {sensorTypeErrorMessage && <p className="error-message">{sensorTypeErrorMessage}</p>}

                                {newSensorType && (
                                    <>
                                        <select
                                            value={newSensorModel}
                                            onChange={(e) => {
                                                setNewSensorModel(e.target.value);
                                                setSensorModelErrorMessage(''); // Remove a mensagem de erro
                                            }}
                                        >
                                            <option value="">Select Model</option>
                                            {displaySensorModels()}
                                        </select>
                                        {sensorModelErrorMessage && <p className="error-message">{sensorModelErrorMessage}</p>}
                                    </>
                                )}

                                <AppButton type={'info'} onClick={handleAddSensor} children={'Save'} />
                            </div>
                        )}
                    </div>
                )}
                {showPopup && (
                    <div className="popup">
                        <div className="popup-content">
                            {popupMessage}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default DeviceDetails;