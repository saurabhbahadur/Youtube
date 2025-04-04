import React, { useState } from "react";
import axios from "axios";
import { FaTimes } from "react-icons/fa";

const LoginForm = ({ onClose }) => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:3000/api/central/user/login",
        formData
      );

      console.log(response);
      if (response.data) {
        localStorage.setItem("authToken", response.data);
        alert("User logged in successfully!");
      } else {
        alert("Login successful, but no token received.");
      }
    } catch (error) {
      console.error("Error logging in:", error);
      alert("Failed to log in");
    }
  };

  return (
    <div className="fixed z-10 top-0 right-0 h-full w-80 bg-white shadow-2xl py-16 px-6 overflow-auto transition-transform transform translate-x-0">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-blue-600">Login</h2>
        <button onClick={onClose} className="text-gray-600 hover:text-gray-800">
          <FaTimes size={24} />
        </button>
      </div>
      <form onSubmit={handleSubmit} className="space-y-4">
        {[
          { name: "email", type: "email", placeholder: "Email" },
          { name: "password", type: "password", placeholder: "Password" },
        ].map((field) => (
          <input
            key={field.name}
            type={field.type}
            name={field.name}
            placeholder={field.placeholder}
            className="w-full p-2 border rounded-md focus:ring-2 focus:ring-blue-400"
            onChange={handleChange}
            required
          />
        ))}
        <button
          type="submit"
          className="w-full bg-blue-600 text-white p-2 rounded-md hover:bg-blue-700 transition"
        >
          Login
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
