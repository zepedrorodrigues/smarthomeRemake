// src/context/AppContext.js
import React, {createContext, useEffect, useState} from 'react';

/*
ReactDOM.render(
    <AppProvider>
        <App />
    </AppProvider>,
    document.getElementById('root')
);
*/

export const AppContext = createContext();

const initialWeatherState = {temperature: 0, sunrise: '', sunset: ''};

export const AppProvider = ({children}) => {
    const [pageTitle, setPageTitle] = useState('Home');
    const [weather, setWeather] = useState(initialWeatherState);

    useEffect(() => {
        const configureWeatherService = async () => {
            try {
                const response = await fetch('http://10.9.24.170:8080/WeatherServiceConfiguration', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        groupNumber: 6,
                        latitude: 40.00,
                        longitude: 80.00,
                    }),
                });

                const data = await response.json();
                if (data.groupNumber !== -2147483648) {
                    fetchTemperature();
                } else {
                    console.error('Configuration failed: Invalid parameters.');
                }
            } catch (error) {
                console.error('Error configuring Weather Service:', error);
            }
        };

        const fetchTemperature = async () => {
            try {
                const response = await fetch('http://10.9.24.170:8080/InstantaneousTemperature?groupNumber=5&hour=15');
                const data = await response.json();

                if (data.measurement !== 'NaN') {
                    setWeather((prevState) => ({
                        ...prevState,
                        temperature: data.measurement,
                    }));
                } else {
                    console.error('Failed to fetch temperature:', data.info);
                }
            } catch (error) {
                console.error('Error fetching temperature:', error);
            }
        };

        const fetchSunriseSunset = async () => {
            try {
                const response = await fetch('http://10.9.24.170:8080/SunriseSunset');
                const data = await response.json();
            } catch (error) {
                console.error('Error fetching sunrise and sunset:', error);
            }
        };

        configureWeatherService();
    }, []);

    const resetState = () => {
        setPageTitle('Home');
        setWeather(initialWeatherState);
    };

    return (
        <AppContext.Provider value={{pageTitle, setPageTitle, weather, resetState}}>
            {children}
        </AppContext.Provider>
    );
};