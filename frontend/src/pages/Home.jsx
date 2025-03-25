// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// const Home = () => {
//   const [videos, setVideos] = useState([]);

//   useEffect(() => {
//     const fetchVideos = async () => {
//       try {
//         const response = await axios.get(''); // Adjust API endpoint as needed
//         setVideos(response.data);
//       } catch (error) {
//         console.error('Error fetching videos:', error);
//       }
//     };
//     fetchVideos();
//   }, []);

//   return (
//     <div className="pt-16 grid grid-cols-1 h-screen bg-secondary_text md:grid-cols-2 lg:grid-cols-3 gap-6">
//       {videos.map((video) => (
//         <div key={video.id} className="bg-white shadow-md rounded-lg overflow-hidden">
//           <div className="w-full h-48 bg-gray-200 flex items-center justify-center">
//             <video className="w-full h-full object-cover" src={video.url} controls />
//           </div>
//           <div className="p-4">
//             <h2 className="text-lg font-semibold">{video.title}</h2>
//             <p className="text-gray-600">{video.channelName}</p>
//           </div>
//         </div>
//       ))}
//     </div>
//   );
// };

// export default Home;

import React from 'react'

const Home = () => {
  return (
    <div>Home</div>
  )
}

export default Home