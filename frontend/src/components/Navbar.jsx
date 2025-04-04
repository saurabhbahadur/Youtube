import React, { useState, useEffect } from "react";
import { FaTimes, FaBars, FaUserPlus, FaSignOutAlt, FaUserCircle } from "react-icons/fa";
import { Link } from "react-router-dom";
import RegisterForm from "./RegisterForm";
import LoginForm from "./LoginForm";
import ChannelForm from "./ChannelForm";
import Sidebar from "./Sidebar"; // Import Sidebar

const Navbar = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [isLoginOpen, setIsLoginOpen] = useState(false);
  const [isChannelOpen, setIsChannelOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const YouTube_Logo = "https://cdn-icons-png.flaticon.com/128/1384/1384060.png";

  // Check authentication status on component mount
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    setIsLoggedIn(!!token);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    setIsLoggedIn(false);
  };

  return (
    <>
      <nav className="bg-red-600 text-white px-6 md:px-16 lg:px-24 fixed top-0 w-full z-50 shadow-md">
        <div className="container py-3 flex justify-between items-center">
          {/* Hamburger Icon (Left Side) */}
          <button
            className="md:hidden text-white"
            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
          >
            <FaBars size={28} />
          </button>

          {/* Logo and Brand */}
          <div className="flex items-center space-x-2">
            <img src={YouTube_Logo} className="w-10 h-10" alt="YouTube Logo" />
            <div className="text-3xl font-bold">YouTube</div>
          </div>

          {/* Links for large screens */}
          <div className="hidden md:flex space-x-6">
            <Link to="/" className="hover:text-gray-300">Home</Link>
            <Link to="/channels" className="hover:text-gray-300">Channels</Link>
          </div>

          {/* Auth Buttons for large screens */}
          <div className="flex items-center space-x-4">
            {!isLoggedIn ? (
              <>
                <button
                  className="hidden md:block bg-white text-red-600 px-4 py-2 rounded-lg font-semibold hover:bg-gray-200 transition"
                  onClick={() => setIsRegisterOpen(true)}
                >
                  Signup
                </button>
                <button
                  className="hidden md:block bg-white text-red-600 px-4 py-2 rounded-lg font-semibold hover:bg-gray-200 transition"
                  onClick={() => setIsLoginOpen(true)}
                >
                  Login
                </button>
              </>
            ) : (
              <>
                <button
                  className="hidden md:block text-white hover:text-gray-300 transition"
                  onClick={() => setIsChannelOpen(true)}
                >
                  <FaUserPlus size={24} />
                </button>
                <button
                  className="hidden md:block bg-gray-800 text-white px-4 py-2 rounded-lg font-semibold hover:bg-gray-700 transition"
                  onClick={handleLogout}
                >
                  <FaSignOutAlt size={20} className="inline mr-2" /> Logout
                </button>
              </>
            )}
          </div>

          {/* Circle Icon for mobile */}
          {isLoggedIn ? (
            <div className="md:hidden">
              <button
                className="text-white rounded-full bg-gray-800 p-3"
                onClick={handleLogout}
              >
                <FaSignOutAlt size={20} />
              </button>
            </div>
          ) : (
            <div className="md:hidden">
              <button
                className="text-white rounded-full bg-gray-800 p-3"
                onClick={() => setIsLoginOpen(true)}
              >
                <FaUserCircle size={24} />
              </button>
            </div>
          )}
        </div>
      </nav>

      {/* Sidebar for Mobile */}
      {isSidebarOpen && (
        <div className="fixed inset-0 bg-gray-900 bg-opacity-50 z">
          <div className="w-64 bg-gray-200 p-4 absolute top-0 left-0 h-full z-50">
            <button
              className="text-red-600 text-2xl"
              onClick={() => setIsSidebarOpen(false)}
            >
              <FaTimes />
            </button>
            <Sidebar /> {/* Add your Sidebar component */}
          </div>
        </div>
      )}

      {/* Register Form Sidebar */}
      {isRegisterOpen && <RegisterForm onClose={() => setIsRegisterOpen(false)} />}

      {/* Login Form Sidebar */}
      {isLoginOpen && <LoginForm onClose={() => { setIsLoginOpen(false); setIsLoggedIn(!!localStorage.getItem("authToken")); }} />}

      {/* Channel Form Sidebar */}
      {isChannelOpen && <ChannelForm onClose={() => setIsChannelOpen(false)} />}
    </>
  );
};

export default Navbar;
