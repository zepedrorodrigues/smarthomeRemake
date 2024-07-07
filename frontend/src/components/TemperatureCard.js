import React, {useEffect, useState} from 'react';
import {FaTemperatureHigh} from 'react-icons/fa';
import './TemperatureCard.css';

const TemperatureCard = ({location}) => {
    const [temperature, setTemperature] = useState(null);
    const [timeoutId, setTimeoutId] = useState(null);
    const [intervalId, setIntervalId] = useState(null);

    useEffect(() => {
        const fetchTemperature = async () => {
            try {
                const date = new Date();
                const currentHour = date.getHours();
                const response = await fetch(`http://10.9.24.170:8080/InstantaneousTemperature?groupNumber=6&hour=${currentHour}&latitude=${location.latitude}&longitude=${location.longitude}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const data = await response.json();

                if (data.hasOwnProperty('measurement') && !isNaN(data.measurement)) {
                    setTemperature(data.measurement);
                    console.log('Temperature fetched successfully:', data.measurement);
                } else {
                    console.error('Failed to fetch temperature: Invalid measurement', data.info);
                }
            } catch (error) {
                console.error('Error fetching temperature:', error);
            }
        };
        fetchTemperature();
        // Calculate the milliseconds until next 15-minute mark
        const now = new Date();
        const minutes = now.getMinutes();
        const seconds = now.getSeconds();
        const milliseconds = now.getMilliseconds();
        const msUntilNextQuarterHour = ((15 - (minutes % 15)) * 60 * 1000) - (seconds * 1000) - milliseconds;

        // Set a timeout until the next 15-minute mark
        const timeout = setTimeout(() => {
            // Fetch the temperature immediately at the next 15-minute mark
            fetchTemperature();

            // Then set an interval to fetch the temperature every 15 minutes
            const interval = setInterval(fetchTemperature, 15 * 60 * 1000);

            // Save the intervalId in the state so it can be cleared later
            setIntervalId(interval);
        }, msUntilNextQuarterHour);

        // Save the timeoutId in the state so it can be cleared later
        setTimeoutId(timeout);

        // This function will be called when the component unmounts
        return () => {
            clearTimeout(timeoutId);
            clearInterval(intervalId);
        };
    }, [location]);

    const getTemperatureColor = (temp) => {
        if (temp === null) return '#c2c2c2'; // Default color if temperature is not yet available
        return temp > 30 ? '#FF6347' : temp > 15 ? '#FFD700' : '#4c74af';
    };

    return (
        <div className="card temperature-card" style={{backgroundColor: getTemperatureColor(temperature)}}>
            <FaTemperatureHigh size={60} className="icon"/>
            <div className="text-container">
                <h2>Current Temperature</h2>
                <p>{temperature !== null ? `${temperature}°C` : 'Loading...'}</p>
            </div>
        </div>
    );
}

export default TemperatureCard;
