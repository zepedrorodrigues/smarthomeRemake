import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import React from 'react';
import './App.css';
import Header from './components/Header';
import Home from './Home';
import Rooms from './Rooms';
import RoomDetails from './components/RoomDetails';
import { useTheme } from './ThemeContext';
import AnimatedBackground from './components/AnimatedBackground';
import DeviceDetails from "./components/DeviceDetails";
import ReadingList from "./components/ReadingList";

const App = () => {
    const { theme, toggleTheme } = useTheme();
    return (
        <div>
            <Router>
                <AnimatedBackground />
                <Header />
                <div className="home-container">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/rooms" element={<Rooms />} />
                        <Route path="/rooms/:roomName" element={<RoomDetails />} />
                        <Route path="/rooms/:roomName/:deviceName" element={<DeviceDetails />} />
                        <Route path="/rooms/:roomName/:deviceName/readings" element={<ReadingList />} />
                    </Routes>
                </div>
            </Router>
        </div>
    );
};

export default App;
