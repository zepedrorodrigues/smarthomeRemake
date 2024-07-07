import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import SunInfoCard from './components/SunInfoCard';
import TemperatureCard from './components/TemperatureCard';
import RoomDetails from './components/RoomDetails';
import RoomView from './components/RoomView';
import RoomsButton from './components/buttons/RoomsButton';
import './Home.css';

const Home = () => {
    const [showRooms, setShowRooms] = useState(false);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [house, setHouse] = useState([]);
    const [rooms, setRooms] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();


    useEffect(() => {
        const fetchHouse = async () => {
            const houseResp = await fetch('http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/houses/TheHouse');
            if (!houseResp.ok) {
                throw new Error(`HTTP error! status: ${houseResp.status}`);
            }

            let house = await houseResp.json();
            setHouse(house);
        }

        fetchHouse();
    }, []);

    useEffect(() => {
        const fetchRooms = async () => {
            try {
                const response = await fetch('http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/rooms');
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const data = await response.json();
                if (data._embedded && Array.isArray(data._embedded.rooms) && data._embedded.rooms.every(room => room.hasOwnProperty('roomId'))) {
                    const rooms = data._embedded.rooms;
                    console.log('Rooms fetched successfully:', rooms);

                    // Fetch details for each room
                    const detailedRooms = await Promise.all(rooms.map(async (room) => {
                        const roomDetailsResponse = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/rooms/${room.roomId}`);

                        if (!roomDetailsResponse.ok) {
                            throw new Error(`HTTP error! status: ${roomDetailsResponse.status}`);
                        }

                        const roomDetailsData = await roomDetailsResponse.json();
                        return {...room, ...roomDetailsData}; // Merge room with its details
                    }));

                    setRooms(detailedRooms);
                    console.log('Detailed rooms fetched successfully:', detailedRooms);
                } else {
                    console.error('Failed to fetch rooms: Invalid data format', data);
                }
            } catch (error) {
                setError('Error fetching rooms. Please try again later.');
                console.error('Error fetching rooms:', error);
            }
        };

        fetchRooms();
    }, []);

    const handleRoomsClick = () => setShowRooms(true);
    const handleBackClick = () => {
        if (selectedRoom) {
            setSelectedRoom(null);
        } else {
            setShowRooms(false);
            navigate('/');  // Navigate back to the home page
        }
    };
    const handleRoomSelect = room => setSelectedRoom(room);

    return (
        <>
            {error && <div className="error">{error}</div>}
            <div className="cards-container">
                {showRooms ? (
                    selectedRoom ? (
                        <RoomDetails room={selectedRoom} onBack={handleBackClick}/>
                    ) : (
                        <RoomView rooms={rooms} onRoomSelect={handleRoomSelect} onBack={handleBackClick}/>
                    )
                ) : (
                    <>
                        <div className="left-card main-card">
                            <RoomsButton onClick={handleRoomsClick}/>
                        </div>

                            <div className="right-cards">
                                <SunInfoCard type="sunrise" location={
                                    {latitude: house.latitude, longitude: house.longitude}
                                }/>
                                <SunInfoCard type="sunset" location={
                                    {latitude: house.latitude, longitude: house.longitude}
                                }/>
                                <TemperatureCard location={
                                    {latitude: house.latitude, longitude: house.longitude}
                                }/>
                            </div>
                    </>

                )}
            </div>
        </>
    );
};

export default Home;
