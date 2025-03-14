import { useState } from "react";
import axios from "axios";

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    dob: "",
    gender: "",
    country: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:3000/api/central/user/register", {
        ...formData,
        phoneNumber: Number(formData.phoneNumber), // Convert to number
        dob: formData.dob ? new Date(formData.dob).toISOString().split("T")[0] : null, // Convert date
      });

      alert("User registered successfully!");
    } catch (error) {
      console.error("Error registering user:", error);
      alert("Failed to register user");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="bg-white shadow-lg rounded-lg p-8 max-w-md w-full">
        <h2 className="text-2xl font-semibold text-center mb-6">Register</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            name="name"
            placeholder="Full Name"
            className="w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email Address"
            className="w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            onChange={handleChange}
            required
          />
          <input
            type="tel"
            name="phoneNumber"
            placeholder="Phone Number"
            className="w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            onChange={handleChange}
            required
          />
          <input
            type="date"
            name="dob"
            className="w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            onChange={handleChange}
            required
          />
          <select
            name="gender"
            className="w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            onChange={handleChange}
            required
          >
            <option value="">Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
          </select>
          <input
            type="text"
            name="country"
            placeholder="Country"
            className="w-full p-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            onChange={handleChange}
            required
          />
          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 transition"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default RegisterForm;
