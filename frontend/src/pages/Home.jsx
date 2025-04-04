import React from "react";


const Home = () => {
  const videos = [
    { id: 1, title: "Wahi Karn hu Mai", videoId: "qB2j5FhfOd4" },
    {
      id: 2,
      title: "Aaj Ki Raat -Full Song |Stree 2|",
      videoId: "hxMNYkLN7tI",
    },
    {
      id: 3,
      title: "Aayi Nai -Stree 2 | Shraddha Kapoor | Rajkummar Rao ",
      videoId: "nFgsBxw-zWQ",
    },
    {
      id: 4,
      title: "Chunnari Chunnari | Biwi No.1 | Salman Khan |",
      videoId: "6z1U-kJ3xJE",
    },
    { id: 5, title: "Yeh Parda Hata Do", videoId: "dGuUf_QnReY" },
    { id: 6, title: "DUVIDHA", videoId: "Y_29eVU2eyo" },
    { id: 7, title: "Tumhare Hi Rahenge Hum -Stree2", videoId: "cxKAtmvf-uM" },
  ];

  return (
    <div className="flex flex-wrap bg-black">
      {/* Sidebar */}
    

      {/* Video Section */}
      <div className="flex-1 py-8 px-4 md:px-8">
        <h1 className="text-3xl font-bold text-white mb-8">Home</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-8">
          {videos.map((video) => (
            <div
              key={video.id}
              className="max-w-full text-white rounded overflow-hidden shadow-lg"
            >
              {/* Embed YouTube video */}
              <div className="relative pb-[56.25%]">
                <iframe
                  className="absolute top-0 left-0 w-full h-full"
                  src={`https://www.youtube.com/embed/${video.videoId}`}
                  title={video.title}
                  frameBorder="0"
                  allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                  allowFullScreen
                />
              </div>
              <div className="px-6 py-4">
                <h2 className="font-semibold text-xl truncate">
                  {video.title}
                </h2>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Home;
