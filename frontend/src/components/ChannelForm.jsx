import React, { useState } from "react";
import axios from "axios";
import { FaTimes } from "react-icons/fa";

const ChannelForm = ({ onClose }) => {
  const [formData, setFormData] = useState({
    channelName: "",
    channelEmail: "",
    description: "",
  });

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:3000/api/central/channel/create", formData);
      alert("Channel created successfully!");
    } catch (error) {
      console.error("Error creating channel:", error);
      alert("Failed to create channel");
    }
  };

  return (
    <div className="fixed top-0 right-0 h-full w-80 bg-white shadow-2xl py-16 px-6 overflow-auto transition-transform transform translate-x-0">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-red-600">Create Channel</h2>
        <button onClick={onClose} className="text-gray-600 hover:text-gray-800">
          <FaTimes size={24} />
        </button>
      </div>
      <form onSubmit={handleSubmit} className="space-y-4">
        {["channelName", "channelEmail", "description"].map((field) => (
          <input
            key={field}
            type={field === "channelEmail" ? "email" : "text"}
            name={field}
            placeholder={field.charAt(0).toUpperCase() + field.slice(1).replace(/([A-Z])/g, " $1")}
            className="w-full p-2 border rounded-md focus:ring-2 focus:ring-red-400"
            onChange={handleChange}
            required
          />
        ))}
        <button type="submit" className="w-full bg-red-600 text-white p-2 rounded-md hover:bg-red-700 transition">
          Create Channel
        </button>
      </form>
    </div>
  );
};

export default ChannelForm;
