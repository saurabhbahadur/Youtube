import React, { useEffect, useState } from 'react';
import ChannelForm from '../components/ChannelForm';
import axios from 'axios';

const Channel = () => {
  const [channels, setChannels] = useState([]);  
  const [loading, setLoading] = useState(true);  
  const [error, setError] = useState(null); 

  useEffect(() => {
   
    axios.get("http://localhost:3000/api/central/channel/getAllChannels")
      .then(response => {
        setChannels(response.data);  
        setLoading(false);  
      })
      .catch(err => {
        setError(err.message); 
        setLoading(false);
      });
  }, []);  

  return (
    <div className="py-16">
      <h1>Channels</h1>
      {loading && <p>Loading...</p>} 
      {error && <p>Error: {error}</p>}  
      <div>
       
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
      
    </div>
  );
};

export default Channel;
