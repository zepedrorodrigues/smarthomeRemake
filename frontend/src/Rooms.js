import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import RoomView from './components/RoomView';
import './Home.css';

const Rooms = () => {
    const [rooms, setRooms] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

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

    const handleRoomSelect = (room) => {
        navigate(`/rooms/${room.roomName}`, { state: { roomId: room.roomId } });
    };

    return (
        <>
            {error && <div className="error">{error}</div>}
            <div className="cards-container">
                <RoomView rooms={rooms} onRoomSelect={handleRoomSelect}/>
            </div>
        </>
    );
};

export default Rooms;
