import {FaMoon, FaSun} from 'react-icons/fa';
import './SunInfoCard.css';
import React, {useEffect, useState} from 'react';

const SunInfoCard = ({type, location }) => {
    const isSunset = type === 'sunset';
    const title = isSunset ? 'Sunset' : 'Sunrise';
    const Icon = isSunset ? FaMoon : FaSun;

    const [time, setTime] = useState('Loading...');

    useEffect(() => {
        fetch(`http://10.9.24.170:8080/SunriseOrSunsetTime?groupNumber=6&latitude=${location.latitude}&longitude=${location.longitude}&option=${title.toLowerCase()}`)
            .then(response => response.json())
            .then(data => {
                const measurement = data.measurement.toString();
                const formattedTime = formatApiMeasure(measurement);
                setTime(formattedTime);
            })
            .catch(error => {
                console.error('Error fetching time:', error);
            });
    }, [type, location]);

    const formatApiMeasure = (apiMeasurement) => {
        const measurement = apiMeasurement.toString();
        let hour = measurement.substring(0, measurement.indexOf('.'));
        let minute = measurement.substring(measurement.indexOf('.') + 1);
        return  `${hour.padStart(2, '0')}:${minute.padEnd(2, '0')}`;
    }

    return (
        <div className="card sun-info-card">
            <Icon size={60} className="icon"/>
            <div className="text-container">
                <h2>{title}</h2>
                <p>{time}</p>
            </div>
        </div>
    );
}

export default SunInfoCard;
