import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate, useParams} from 'react-router-dom';
import './RoomDetails.css';
import DeviceDetails from "./DeviceDetails";
import AppButton from "./buttons/AppButton";
import CardRow from "./card-row/CardRow";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGear} from "@fortawesome/free-solid-svg-icons";

const RoomDetails = () => {
    const { roomName } = useParams();
    const location = useLocation();
    const navigate = useNavigate();
    const roomId = location.state?.roomId;
    const [room, setRoom] = useState(null);
    const [selectedDevice, setSelectedDevice] = useState(null);
    const [devices, setDevices] = useState([]);
    const [showAddDeviceForm, setShowAddDeviceForm] = useState(false);
    const [newDeviceName, setNewDeviceName] = useState('');
    const [newDeviceType, setNewDeviceType] = useState('');
    const [deviceTypes, setDeviceTypes] = useState([]);
    const [nameErrorMessage, setNameErrorMessage] = useState('');
    const [typeErrorMessage, setTypeErrorMessage] = useState('');

    useEffect(() => {
        const fetchRoom = async () => {
            try {
                const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/rooms/${roomId}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const roomData = await response.json();
                setRoom(roomData);
            } catch (error) {
                console.error('Error fetching room:', error);
            }
        };

        if (roomId) {
            fetchRoom();
        } else {
            console.error('Room ID not found in location state');
        }
    }, [roomId]);

    const handleAddDevice = async () => {
        let valid = true;
        if (newDeviceName.trim() === '') {
            setNameErrorMessage('Please enter a valid device name.');
            valid = false;
        } else {
            setNameErrorMessage('');
        }

        if (newDeviceType === '') {
            setTypeErrorMessage('Please select a device type.');
            valid = false;
        } else {
            setTypeErrorMessage('');
        }

        if (!valid) {
            return;
        }

        const newDevice = {
            deviceName: newDeviceName,
            deviceTypeName: newDeviceType,
        };

        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/devices/room/${roomId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newDevice)
            });

            if (response.ok) {
                const addedDevice = await response.json();
                setDevices([...devices, addedDevice]);
                setNewDeviceName('');
                setNewDeviceType('');
                setShowAddDeviceForm(false);
                setNameErrorMessage('');
                setTypeErrorMessage('');
            } else if (response.status === 422) {
                setNameErrorMessage('Please enter a valid device name.');
            } else {
                console.error('Failed to add device:', response.statusText);
            }
        } catch (error) {
            console.error('Error adding device:', error);
        }
    };

    const handleDeviceSelect = (device) => {
        navigate(`/rooms/${roomName}/${device.deviceName}`,  { state: { device, roomName: room.roomName } });
    };

    const handleDeviceBack = () => {
        setSelectedDevice(null);
        fetchDevicesForRoom(); // Re-fetch devices when navigating back
    };

    const fetchDeviceTypes = async () => {
        try {
            const response = await fetch('http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/devicetypes');

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const deviceTypes = data.map(deviceTypeDTO => ({
                    raw: deviceTypeDTO.deviceTypeName,
                    transformed: transformDeviceTypeName(deviceTypeDTO.deviceTypeName)
                }));
                console.log('Device types fetched successfully:', deviceTypes);
                setDeviceTypes(deviceTypes);
            } else {
                console.error('Unexpected response format:', data);
            }
        } catch (error) {
            console.error('Error fetching device types:', error);
        }
    };

    useEffect(() => {
        fetchDeviceTypes();
    }, []);

    const transformDeviceTypeName = (deviceTypeName) => {
        switch (deviceTypeName) {
            case 'GridPowerMeter':
                return 'Grid Power Meter';
            case 'PowerSourcePowerMeter':
                return 'Power Source Power Meter';
            default:
                return 'Default';
        }
    };

    const displayDeviceTypes = () => {
        return deviceTypes.map((type, index) => (
            <option key={index} value={type.raw}>{type.transformed}</option>
        ));
    };

    const fetchDevicesForRoom = async () => {
        try {
            const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/devices/room/${room.roomId}`);

            if (!response.ok) {
                throw new Error(`HTTP error status: ${response.status}`);
            }

            const data = await response.json();

            if (Array.isArray(data)) {
                const detailedDevices = await Promise.all(data.map(async (device) => {
                    const deviceDetailsResponse = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/devices/${device.deviceId}`);

                    if (!deviceDetailsResponse.ok) {
                        throw new Error(`HTTP error status: ${deviceDetailsResponse.status}`);
                    }

                    const deviceDetailsData = await deviceDetailsResponse.json();
                    return {...device, ...deviceDetailsData};
                }));

                setDevices(detailedDevices);
                console.log('Detailed devices fetched successfully:', detailedDevices);
            } else {
                console.error('Failed to fetch devices: Unexpected response format', data);
            }
        } catch (error) {
            console.error('Error fetching devices:', error);
        }
    };

    useEffect(() => {
        if (room) {
            fetchDevicesForRoom();
        }
    }, [room]);

    const resetDeviceForm = () => {
        setNewDeviceType('');
        setNewDeviceName('');
    };

    const handleShowAddDeviceForm = () => {
        if (showAddDeviceForm) {
            resetDeviceForm(); // Reset the form when closing it
        }
        setShowAddDeviceForm(!showAddDeviceForm);
    };

    return (
        <div className="cards-container">
            <div className="room-details">
                {selectedDevice ? (
                    <DeviceDetails device={selectedDevice} roomName={room.roomName} onBack={handleDeviceBack}/>
                ) : (
                    room && (
                        <>
                            <div className="room-details-header">
                                <h2>{room.roomName}</h2>
                                <AppButton type={'warning'} onClick={() => navigate(-1)} children={'Back'}/>
                            </div>
                            <div className="room-details-info">
                                <p><strong>Length:</strong> {room.length}m</p>
                                <p><strong>Height:</strong> {room.height}m</p>
                                <p><strong>Width:</strong> {room.width}m</p>
                                <p><strong>Floor:</strong> {room.floor}</p>
                                <div className="device-list">
                                    <h3>Devices:</h3>
                                    {devices && devices.map((device, index) => (
                                        <CardRow
                                            key={index}
                                            text={device.deviceName}
                                            onButtonClick={() => handleDeviceSelect(device)}
                                            buttonContent={<FontAwesomeIcon icon={faGear} />}
                                        />
                                    ))}
                                    <AppButton type={showAddDeviceForm ? 'warning' : 'info'}
                                               onClick={handleShowAddDeviceForm}>
                                        {showAddDeviceForm ? 'Cancel' : 'Add Device'}
                                    </AppButton>
                                    {showAddDeviceForm && (
                                        <div className="add-device-form">
                                            <input
                                                type="text"
                                                placeholder="Device Name"
                                                value={newDeviceName}
                                                onChange={(e) => {
                                                    setNewDeviceName(e.target.value);
                                                    setNameErrorMessage(''); // Remove a mensagem de erro
                                                }}
                                            />
                                            {nameErrorMessage && <p className="error-message">{nameErrorMessage}</p>}
                                            <select
                                                value={newDeviceType}
                                                onChange={(e) => {
                                                    setNewDeviceType(e.target.value);
                                                    setTypeErrorMessage(''); // Remove a mensagem de erro
                                                }}
                                            >
                                                <option value="">Select Type</option>
                                                {displayDeviceTypes()}
                                            </select>
                                            {typeErrorMessage && <p className="error-message">{typeErrorMessage}</p>}

                                            <AppButton type={'info'} onClick={handleAddDevice} children={'Save'}/>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </>
                    )
                )}
            </div>
        </div>
    );
};

export default RoomDetails;
