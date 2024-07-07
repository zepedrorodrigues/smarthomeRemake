import React from 'react';
import './RoomList.css';
import CardRow from "./card-row/CardRow";

const RoomList = ({rooms, onRoomSelect}) => {
    return (
        <div className="room-list-container">
            <div className="room-list">
                {rooms.map((room) =>
                    <CardRow
                        key={room.roomId}
                        text={room.roomName}
                        onButtonClick={() => onRoomSelect(room)}
                    />
                )}
            </div>
        </div>
    );
};

export default RoomList;
