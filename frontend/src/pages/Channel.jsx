import React, { useEffect, useState } from 'react';
import ChannelForm from '../components/ChannelForm';
import axios from 'axios';

const Channel = () => {
  const [channels, setChannels] = useState([]);  // State to store channels
  const [loading, setLoading] = useState(true);  // State to manage loading state
  const [error, setError] = useState(null);  // State to manage errors

  useEffect(() => {
    // Fetching the channels from the API
    axios.get("http://localhost:3000/api/central/channel/getAllChannels")
      .then(response => {
        setChannels(response.data);  // Storing the channels in state
        setLoading(false);  // Setting loading to false
      })
      .catch(err => {
        setError(err.message);  // Handling any errors
        setLoading(false);
      });
  }, []);  // Empty dependency array ensures this runs only once when the component mounts

  return (
    <div className="py-16">
      <h1>Channels</h1>
      {loading && <p>Loading...</p>}  {/* Show loading message */}
      {error && <p>Error: {error}</p>}  {/* Show error message if there's an error */}
      <div>
        {/* Render the channels list */}
        <ul className=' '>
          {channels.length > 0 ? (
            channels.map((channel, index) => (
              <div key={index} className='hover:bg-slate-600 hover:text-white m-4 p-4' >
                <li>{channel.id}</li>
                <li>{channel.name}</li> 
                <li>{channel.user.email}</li>
                <li>{channel.description}</li> 
                <li>{channel.totalSubs}</li> 
                <li>{channel.createdAt}</li>
              </div>
            ))
          ) : (
            <p>No channels found.</p>
          )}
        </ul>
      </div>
      <ChannelForm />
    </div>
  );
};

export default Channel;
