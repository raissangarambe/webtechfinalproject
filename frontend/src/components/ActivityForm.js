import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import for navigation

function ActivityForm() {
  const navigate = useNavigate(); // Initialize the navigation function

  const [formData, setFormData] = useState({
    activityType: "RUNNING",
    startTime: "",
    endTime: "",
    distance: 0,
    caloriesBurned: 0,
    intensity: "BEGINNER",
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
      const response = await fetch(`http://localhost:8081/api/activities/${userId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Activity saved successfully!");
        navigate("/fetchActivities"); 
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
      <h2>Log Activity</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Activity Type</label>
          <select
            name="activityType"
            value={formData.activityType}
            onChange={handleChange}
            required
          >
            <option value="RUNNING">Running</option>
            <option value="WALKING">Walking</option>
            <option value="CYCLING">Cycling</option>
            <option value="SWIMMING">Swimming</option>
            <option value="HIKING">Hiking</option>
            <option value="WORKOUTS">Workouts</option>
            <option value="YOGA">Yoga</option>
          </select>
        </div>

        <div>
          <label>Start Time</label>
          <input
            type="datetime-local"
            name="startTime"
            value={formData.startTime}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>End Time</label>
          <input
            type="datetime-local"
            name="endTime"
            value={formData.endTime}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Distance (km)</label>
          <input
            type="number"
            name="distance"
            value={formData.distance}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Calories Burned</label>
          <input
            type="number"
            name="caloriesBurned"
            value={formData.caloriesBurned}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Intensity</label>
          <select
            name="intensity"
            value={formData.intensity}
            onChange={handleChange}
            required
          >
            <option value="BEGINNER">Beginner</option>
            <option value="INTERMEDIATE">Intermediate</option>
            <option value="ADVANCED">Advanced</option>
          </select>
        </div>

        <button
          type="submit"
          disabled={loading}
        >
          {loading ? "Saving..." : "Save Activity"}
        </button>

        {errorMessage && <div>{errorMessage}</div>}
      </form>
    </div>
  );
}

export default ActivityForm;
