import React from 'react';

const Sidebar = () => {
  // Sample channel names (replace with actual channels you're subscribed to)
  const channels = ['Channel 1', 'Channel 2', 'Channel 3', 'Channel 4'];

  return (
    <div className="w-64 bg-gray-200 p-4">
      <h2 className="text-2xl font-semibold mb-4">Subscribed Channels</h2>
      <ul>
        {channels.map((channel, index) => (
          <li key={index} className="py-2 px-4 hover:bg-gray-300 rounded">
            {channel}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
