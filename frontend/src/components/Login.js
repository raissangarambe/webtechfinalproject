import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import '../static/css/Login.css';

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    otp: "", // For storing the OTP entered by the user
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [isOtpSent, setIsOtpSent] = useState(false); // To check if OTP is sent

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const sendOtp = async () => {
    try {
      const response = await fetch("http://localhost:8081/api/users/send-otp", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email: formData.email }),
      });
  
      const result = await response.json();
      if (response.ok) {
        alert(result.message); // Notify the user that the OTP was sent
        setIsOtpSent(true); // Show the OTP input field
      } else {
        setErrorMessage(result.message || JSON.stringify(result)); // Handle objects
      }
    } catch (error) {
      console.error("Error sending OTP:", error);
      setErrorMessage("Failed to send OTP. Please try again.");
    }
  };
  

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage("");

    if (!isOtpSent) {
      // Step 1: Validate login credentials and send OTP
      try {
        const response = await fetch("http://localhost:8081/api/users/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: formData.email, password: formData.password }),
        });

        const result = await response.json();
        if (response.ok) {
          sendOtp(); // Call the sendOtp function
        } else {
          setErrorMessage(result.message);
        }
      } catch (error) {
        console.error("Error logging in:", error);
        setErrorMessage("An error occurred during login.");
      }
    } else {
      // Step 2: Validate OTP
      try {
        const response = await fetch("http://localhost:8081/api/users/verify-otp", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: formData.email, otp: formData.otp }),
        });

        const result = await response.json();
        if (response.ok) {
          alert(result.message);
          localStorage.setItem("userRole", result.role);
          navigate(result.role === "ADMIN" ? "/admindashboard" : "/dashboard");
        } else {
          setErrorMessage(result.message);
        }
      } catch (error) {
        console.error("Error verifying OTP:", error);
        setErrorMessage("Failed to verify OTP. Please try again.");
      }
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Login</h2>

        {/* Step 1: Email and Password Input */}
        {!isOtpSent ? (
          <>
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
          </>
        ) : (
          // Step 2: OTP Input (Only shown after login credentials are validated)
          <div className="form-group">
            <label>Enter OTP</label>
            <input
              type="text"
              name="otp"
              value={formData.otp}
              onChange={handleChange}
              required
            />
          </div>
        )}

        <button
          type="submit"
          className="login-button"
          disabled={loading}
        >
          {loading ? (isOtpSent ? "Verifying OTP..." : "Logging in...") : (isOtpSent ? "Verify OTP" : "Login")}
        </button>
        
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div className="signup-redirect">
          <p>Don't have an account?</p>
          <button onClick={() => navigate("/signup")}>Sign Up</button>
        </div>
      </form>
    </div>
  );
}

export default Login;
