import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import for navigation

function GoalForm() {
  const navigate = useNavigate(); // Initialize the navigation function

  const [formData, setFormData] = useState({
    goalType: "Fitness", // Default goal type
    targetValue: 0,
    currentValue: 0,
    startDate: "",
    endDate: "",
    status: "ACTIVE", // Initial status can be "ACTIVE"
  });

  const [errorMessage, setErrorMessage] = useState(""); // For displaying error messages
  const [loading, setLoading] = useState(false); // For showing a loading state

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true); // Start loading
    setErrorMessage(""); // Clear any previous error messages

  // Retrieve userId from localStorage
  const userId = localStorage.getItem("userId");

    try {
      const response = await fetch(`http://localhost:8081/api/goals/${userId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Goal created successfully!");
        navigate("/fetchgoals"); // Redirect to dashboard
      } else {
        const error = await response.text();
        setErrorMessage(error); // Set error message
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage("An error occurred. Please try again later.");
    } finally {
      setLoading(false); // End loading
    }
  };

  return (
    <div>
      <h2>Create a Goal</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Goal Type</label>
          <select
            name="goalType"
            value={formData.goalType}
            onChange={handleChange}
            required
          >
            <option value="Fitness">Fitness</option>
            <option value="Weight Loss">Weight Loss</option>
            <option value="Muscle Gain">Muscle Gain</option>
            <option value="Endurance">Endurance</option>
          </select>
        </div>
        <div>
          <label>Target Value</label>
          <input
            type="number"
            name="targetValue"
            value={formData.targetValue}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Current Value</label>
          <input
            type="number"
            name="currentValue"
            value={formData.currentValue}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Start Date</label>
          <input
            type="date"
            name="startDate"
            value={formData.startDate}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>End Date</label>
          <input
            type="date"
            name="endDate"
            value={formData.endDate}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Status</label>
          <select
            name="status"
            value={formData.status}
            onChange={handleChange}
            required
          >
            <option value="ACTIVE">Active</option>
            <option value="COMPLETED">Completed</option>
            <option value="FAILED">Failed</option>
          </select>
        </div>

        {errorMessage && <p>{errorMessage}</p>}

        <div>
          <button type="submit" disabled={loading}>
            {loading ? "Creating Goal..." : "Create Goal"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default GoalForm;
