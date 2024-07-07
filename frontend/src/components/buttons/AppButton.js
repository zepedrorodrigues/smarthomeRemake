// src/components/AppButton.js
import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './Buttons.css';

const AppButton = ({type = 'info', onClick, children}) => {
    const buttonClass = classNames('button', {
        'blue-button': type === 'info',
        'red-button': type === 'warning',
        'grey-button': type === 'default',
    });

    return (
        <button
            className={buttonClass}
            onClick={onClick}
            disabled={type === 'default'}
        >
            {children}
        </button>
    );
};

AppButton.propTypes = {
    type: PropTypes.oneOf(['info', 'warning', 'default']).isRequired,
    onClick: PropTypes.func,
    children: PropTypes.node.isRequired,
};

export default AppButton;