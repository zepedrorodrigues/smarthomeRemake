import React from 'react';
import {useNavigate} from 'react-router-dom';
import RoomList from './RoomList';
import CardRow from "./card-row/CardRow";


const RoomView = ({rooms, onRoomSelect}) => {
    const navigate = useNavigate();

    const handleBackClick = () => {
        navigate('/');
    };

    return (
        <div className="room-view">
            <CardRow type="title"
                     text="Rooms"
                     buttonContent="Back"
                     onButtonClick={handleBackClick}
            />
            <RoomList rooms={rooms}
                      onRoomSelect={onRoomSelect}
            />
        </div>
    );
};

export default RoomView;
