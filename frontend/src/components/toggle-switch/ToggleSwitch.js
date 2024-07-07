import React, {useState} from 'react';
import './ToggleSwitch.css';

function ToggleSwitch({ isChecked: initialIsChecked, isLocked, onClick }) {

    const [isChecked, setIsChecked] = useState(initialIsChecked);

    function handleClick() {
        if (!isLocked) {
            setIsChecked(!isChecked);
        }
    }

    return (
        <>
            <label className="toggle-switch" >
                <input type="checkbox"
                       checked={isChecked}
                       onChange={handleClick}
                       onClick={onClick}
                />
                <span className="toggle-slider round"></span>
            </label>
        </>
    );

}

export default ToggleSwitch;