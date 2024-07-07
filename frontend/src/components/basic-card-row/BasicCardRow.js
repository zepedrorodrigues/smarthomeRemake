import React from 'react';
import './BasicCardRow.css';

function BasicCardRow({text}) {

    return (
        <div className='basic-card-row'>
            <span>{text}</span>
        </div>
    );
}

export default BasicCardRow;