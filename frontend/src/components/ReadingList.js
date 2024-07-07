import React, { useEffect, useState } from 'react';
import './ReadingList.css';
import AppButton from "./buttons/AppButton";

const ReadingList = ({ deviceName, readings, startDate, endDate, onBack, noReadings, transformSensorModelName }) => {
    const [sensorDetails, setSensorDetails] = useState({});
    const [order, setOrder] = useState('asc');

    useEffect(() => {
        const fetchSensorDetails = async (sensorId) => {
            try {
                const response = await fetch(`http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/sensors/${sensorId}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (!response.ok) {
                    throw new Error(`HTTP error status: ${response.status}`);
                }

                const sensorData = await response.json();
                const transformedSensorName = transformSensorModelName(sensorData.sensorModelName);
                setSensorDetails(prevDetails => ({
                    ...prevDetails,
                    [sensorId]: transformedSensorName
                }));
            } catch (error) {
                console.error('Error fetching sensor details:', error);
            }
        };

        const sensorIds = [...new Set(readings.map(reading => reading.sensorId))];
        sensorIds.forEach(sensorId => {
            if (!sensorDetails[sensorId]) {
                fetchSensorDetails(sensorId);
            }
        });
    }, [readings, transformSensorModelName]);

    const formatTimestamp = (timestamp) => {
        const [date, timeWithMicroseconds] = timestamp.split('T');
        const time = timeWithMicroseconds.split('.')[0];
        return (
            <>
                <strong>Date:</strong> {date} <strong>Time:</strong> {time}
            </>
        );
    };

    // Ordenar leituras por timestamp baseado no estado 'order'
    const sortedReadings = readings.sort((a, b) => {
        return order === 'asc'
            ? new Date(a.timestamp) - new Date(b.timestamp)
            : new Date(b.timestamp) - new Date(a.timestamp);
    });

    return (
        <div className="readings-list">
            <div className="custom-readings-card">
                <div className="readings-list-header">
                    <h2>{deviceName}</h2>
                    <AppButton type={'warning'} onClick={onBack} children={'Back'} />
                </div>
                <div className="readings-list-info">
                    <p className="formatted-date"><strong>Start Date:</strong> <br /> {formatTimestamp(startDate)}</p>
                    <p className="formatted-date"><strong>End Date:</strong> <br /> {formatTimestamp(endDate)}</p>
                    <div className="order-selector">
                        <label>Order by Timestamp: </label>
                        <select value={order} onChange={(e) => setOrder(e.target.value)}>
                            <option value="asc">Ascending</option>
                            <option value="desc">Descending</option>
                        </select>
                    </div>
                    {noReadings ? (
                        <p>There are no readings available.</p>
                    ) : (
                        <table className="readings-table">
                            <thead>
                            <tr>
                                <th>Sensor Model</th>
                                <th>Value</th>
                                <th>Timestamp</th>
                            </tr>
                            </thead>
                            <tbody>
                            {sortedReadings.map((reading) => (
                                <tr key={reading.id} className="reading-item">
                                    <td>{sensorDetails[reading.sensorId] || reading.sensorId}</td>
                                    <td>{reading.readingValue}</td>
                                    <td>{formatTimestamp(reading.timestamp)}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ReadingList;
