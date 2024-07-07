import React from 'react';
import './TabMenu.css';

const TabMenu = ({ tabs, onClick }) => {
    const [activeTab, setActiveTab] = React.useState(tabs[0].id);

    const handleClick = (tabId) => {
        if (tabId !== activeTab) {
            setActiveTab(tabId);
            onClick(tabId);
        }
    };

    return (
        <div className="tab">
            {tabs.map(tab => (
                <button
                    key={tab.id}
                    className={`tablinks ${tab.id === activeTab ? 'active' : ''}`}
                    onClick={() => handleClick(tab.id)}
                    disabled={tab.id === activeTab}
                >
                    {tab.name}
                </button>
            ))}
        </div>
    );
};

export default TabMenu;
