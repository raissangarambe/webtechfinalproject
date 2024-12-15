import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import '../static/css/Signup.css';

function Signup() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    role: "", // Added role field back
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMessage("");

    try {
      const response = await fetch("http://localhost:8081/api/users/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("User registered successfully!");
        setFormData({
          firstName: "",
          lastName: "",
          email: "",
          password: "",
          role: "", // Reset role field
        });
      } else {
        const error = await response.text();
        setErrorMessage(error);
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage("An error occurred. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={handleSubmit}>
        <h1>Signup</h1>
        <div className="form-group">
          <label>First Name</label>
          <input
            type="text"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Last Name</label>
          <input
            type="text"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Role</label>
          <select
            name="role"
            value={formData.role}
            onChange={handleChange}
            required
          >
            <option value="">Select Role</option>
            <option value="ADMIN">Admin</option>
            <option value="TRAINER">Trainer</option>
            <option value="TRAINEE">Trainee</option>
          </select>
        </div>
        <button type="submit" disabled={loading}>
          {loading ? "Registering..." : "Signup"}
        </button>
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div className="login-redirect">
        <p>Already have an account?</p>
        <button onClick={() => navigate("/login")}>Login</button>
    </div>
      </form>
    </div>
  );
}

export default Signup;
