import React from 'react';
import './Buttons.css';
import {useNavigate} from 'react-router-dom';
import {GiMushroomHouse} from "react-icons/gi";

const HomeButton = () => {
    const navigate = useNavigate();

    const handleNavigateHome = () => {
        navigate('/');
    };

    return (
        <button className='home-button' onClick={handleNavigateHome}>
            <GiMushroomHouse size={30} className="icon"/>
        </button>
    );
};

export default HomeButton;
