import React, { useState } from "react";
import axios from "axios";
import { FaTimes } from "react-icons/fa";

const RegisterForm = ({ onClose }) => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    dob: "",
    gender: "",
    country: "",
    password: "", 
  });

  const handleChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:3000/api/central/user/register",
        {
          ...formData,
          phoneNumber: Number(formData.phoneNumber),
          dob: formData.dob
            ? new Date(formData.dob).toISOString().split("T")[0]
            : null,
        }
      );

      
      console.log(response);
      if (response.data) {
        localStorage.setItem("authToken", response.data);
        alert("User registered successfully!");
      } else {
        alert("Registration successful, but no token received.");
      }
    } catch (error) {
      console.error("Error registering user:", error);
      alert("Failed to register user");
    }
  };

  return (
    <div className="fixed z-10 top-0 right-0 h-full w-80 bg-white shadow-2xl py-16 px-6 overflow-auto transition-transform transform translate-x-0">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-red-600">Signup</h2>
        <button onClick={onClose} className="text-gray-600 hover:text-gray-800">
          <FaTimes size={24} />
        </button>
      </div>
      <form onSubmit={handleSubmit} className="space-y-4">
        {["name", "email", "phoneNumber", "dob", "country", "password"].map(
          (field) => (
            <input
              key={field}
              type={
                field === "dob"
                  ? "date"
                  : field === "phoneNumber"
                  ? "tel"
                  : field === "password"
                  ? "password"
                  : "text"
              }
              name={field}
              placeholder={field
                .charAt(0)
                .toUpperCase()
                .concat(field.slice(1).replace(/([A-Z])/g, " $1"))}
              className="w-full p-2 border rounded-md focus:ring-2 focus:ring-red-400"
              onChange={handleChange}
              required
            />
          )
        )}
        <select
          name="gender"
          className="w-full p-2 border rounded-md focus:ring-2 focus:ring-red-400"
          onChange={handleChange}
          required
        >
          <option value="">Select Gender</option>
          <option value="Male">Male</option>
          <option value="Female">Female</option>
          <option value="Other">Other</option>
        </select>
        <button
          type="submit"
          className="w-full bg-red-600 text-white p-2 rounded-md hover:bg-red-700 transition"
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default RegisterForm;
