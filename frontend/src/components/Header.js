import React from "react";
import { Link } from 'react-router-dom';
import "./Header.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouseChimneyUser } from "@fortawesome/free-solid-svg-icons";

const Header = () => {
    return (
        <div className="header">
            <Link to="/" className="home-button" aria-label="Home">
                <FontAwesomeIcon icon={faHouseChimneyUser} size="2x" className="icon" />
            </Link>
            <span className="header-text">SmartHome</span>
        </div>
    );
};

export default Header;