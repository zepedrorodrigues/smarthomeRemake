import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDoorClosed, faDoorOpen } from '@fortawesome/free-solid-svg-icons';
import './Buttons.css';
import { useNavigate } from 'react-router-dom';

const RoomsButton = () => {
    const navigate = useNavigate();
    const [icon, setIcon] = useState(faDoorClosed);

    const handleNavigateRooms = () => {
        navigate('/rooms');
    };

    const handleMouseEnter = () => {
        setIcon(faDoorOpen);
    };

    const handleMouseLeave = () => {
        setIcon(faDoorClosed);
    };

    return (
        <button
            className='button rooms-button'
            onClick={handleNavigateRooms}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
        >

            <div className="text-container">
                Rooms <div className="icon-container">
                    <FontAwesomeIcon icon={icon} className="icon"/>
                </div>
            </div>

        </button>
    );
};

export default RoomsButton;
