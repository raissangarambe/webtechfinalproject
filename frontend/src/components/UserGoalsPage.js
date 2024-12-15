import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; 

function UserGoalsPage() {
  const navigate = useNavigate();
  const [goals, setGoals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState(""); // For displaying error messages

  useEffect(() => {
    const fetchGoals = async () => {
      try {
        // Get the JWT token from localStorage or from wherever it's stored
        const token = localStorage.getItem("authToken");

        const response = await fetch("http://localhost:8081/api/goals", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`, // Add the token to the request header
            "Content-Type": "application/json",
          },
        });

        if (response.ok) {
          const data = await response.json();
          setGoals(data);
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

    fetchGoals();
  }, []);

  return (
    <div>
      <h2>User Goals</h2>

      {loading ? (
        <p>Loading goals...</p>
      ) : errorMessage ? (
        <p>{errorMessage}</p>
      ) : (
        <div>
          {goals.length === 0 ? (
            <p>No goals found for this user.</p>
          ) : (
            <ul>
              {goals.map((goal) => (
                <li key={goal.goalId}>
                  <h3>{goal.goalType}</h3>
                  <p>Status: {goal.status}</p>
                  <p>Target: {goal.targetValue}</p>
                  <p>Current Progress: {goal.currentValue}</p>
                  <p>Start Date: {new Date(goal.startDate).toLocaleDateString()}</p>
                  <p>End Date: {new Date(goal.endDate).toLocaleDateString()}</p>
                  <button
                    onClick={() => navigate(`/goal/${goal.goalId}`)} // Navigate to goal details page
                  >
                    View Details
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
}

export default UserGoalsPage;
