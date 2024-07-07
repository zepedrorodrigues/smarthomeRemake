import React from 'react';
import './CardRow.css';
import AppButton from "../buttons/AppButton";
import classNames from "classnames";
import { useLocation } from 'react-router-dom';

function CardRow({type, text, buttonContent, onButtonClick}) {
    const location = useLocation();

    let buttonType;

    if (!type) {
        type = 'info';
    }

    let cardRowClass = classNames('card-row', {
        'card-row-title': type === 'title',
        'card-row-info': type === 'info',
    });

    switch (type) {
        case 'title':
            buttonType = 'warning';
            break;
        default:
            buttonType = 'info';
    }

    if (!buttonContent) {
        buttonContent = 'View More';
    }

    return (
        <div className={cardRowClass}>
            <span>{text}</span>
            <AppButton type={buttonType}
                       onClick={onButtonClick}
                       children={buttonContent}
            />
        </div>
    );
}

export default CardRow;
