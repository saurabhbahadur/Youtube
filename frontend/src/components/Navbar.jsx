import React, { useState } from "react";
import { FaTimes, FaBars, FaUserPlus } from "react-icons/fa";
import { Link } from "react-router-dom";
import RegisterForm from "./RegisterForm";
import ChannelForm from "./ChannelForm";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [isChannelOpen, setIsChannelOpen] = useState(false);
  const [isSignedUp, setIsSignedUp] = useState(false); // Track signup status

  const YouTube_Logo = "https://cdn-icons-png.flaticon.com/128/1384/1384060.png";

  return (
    <>
      <nav className="bg-red-600 text-white px-6 md:px-16 lg:px-24 fixed top-0 w-full z-50 shadow-md">
        <div className="container py-3 flex justify-between items-center">
          {/* Logo */}
          <div className="flex items-center space-x-2">
            <img src={YouTube_Logo} className="w-10 h-10" alt="YouTube Logo" />
            <div className="text-3xl font-bold">YouTube</div>
          </div>

          {/* Desktop Navigation */}
          <div className="hidden md:flex space-x-6">
            <Link to="/" className="hover:text-gray-300">Home</Link>
            <Link to="/channels" className="hover:text-gray-300">Channels</Link>
          </div>

          <div className="flex items-center space-x-4">
            {/* Signup Button */}
            {!isSignedUp ? (
              <button 
                className="hidden md:block bg-white text-red-600 px-4 py-2 rounded-lg font-semibold hover:bg-gray-200 transition" 
                onClick={() => setIsRegisterOpen(true)}
              >
                Signup
              </button>
            ) : (
              // Show plus icon after signup
              <button 
                className="hidden md:block text-white hover:text-gray-300 transition" 
                onClick={() => setIsChannelOpen(true)}
              >
                <FaUserPlus size={24} />
              </button>
            )}

            {/* Mobile Menu Button */}
            <button className="md:hidden" onClick={() => setIsOpen(!isOpen)}>
              {isOpen ? <FaTimes size={28} /> : <FaBars size={28} />}
            </button>
          </div>
        </div>

        {/* Mobile Menu */}
        {isOpen && (
          <div className="md:hidden bg-red-600 py-4 absolute top-full left-0 w-full shadow-lg">
            <Link to="/" className="block py-2 px-6 hover:text-gray-300" onClick={() => setIsOpen(false)}>Home</Link>
            <Link to="/channels" className="block py-2 px-6 hover:text-gray-300" onClick={() => setIsOpen(false)}>Channels</Link>
            {!isSignedUp ? (
              <button className="block w-full text-left py-2 px-6 text-white hover:text-gray-300" 
                onClick={() => setIsRegisterOpen(true)}
              >
                Signup
              </button>
            ) : (
              <button className="block w-full text-left py-2 px-6 text-white hover:text-gray-300" 
                onClick={() => setIsChannelOpen(true)}
              >
                <FaUserPlus size={20} className="inline-block mr-2" />
                Create Channel
              </button>
            )}
          </div>
        )}
      </nav>
      
      {/* Register Form Sidebar */}
      {isRegisterOpen && <RegisterForm onClose={() => { setIsRegisterOpen(false); setIsSignedUp(true); }} />}

      {/* Channel Form Sidebar */}
      {isChannelOpen && <ChannelForm onClose={() => setIsChannelOpen(false)} />}
    </>
  );
};

export default Navbar;
