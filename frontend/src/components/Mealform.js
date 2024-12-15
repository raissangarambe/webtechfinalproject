import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import for navigation

function MealForm() {
  const navigate = useNavigate(); // Initialize the navigation function

  const [formData, setFormData] = useState({
    mealName: "",
    mealTime: "",
    calories: 0,
    protein: 0,
    carbs: 0,
    fats: 0,
    fiber: 0,
    sugar: 0,
    vitamins: 0,
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

    const userId = localStorage.getItem("userId");

    try {
      const response = await fetch(`http://localhost:8081/api/meals/${userId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Meal saved successfully!");
        navigate("/dashboard"); // Redirect to dashboard or any other page
      } else {
        setErrorMessage("Error saving meal. Please try again.");
      }
    } catch (error) {
      setErrorMessage("An error occurred while saving the meal.");
    } finally {
      setLoading(false); // End loading
    }
  };

  return (
    <div className="meal-form-container">
      <h2>Create a New Meal</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="mealName">Meal Name</label>
          <input
            type="text"
            id="mealName"
            name="mealName"
            value={formData.mealName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="mealTime">Meal Time</label>
          <input
            type="datetime-local"
            id="mealTime"
            name="mealTime"
            value={formData.mealTime}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="calories">Calories</label>
          <input
            type="number"
            id="calories"
            name="calories"
            value={formData.calories}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="protein">Protein (g)</label>
          <input
            type="number"
            id="protein"
            name="protein"
            value={formData.protein}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="carbs">Carbs (g)</label>
          <input
            type="number"
            id="carbs"
            name="carbs"
            value={formData.carbs}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="fats">Fats (g)</label>
          <input
            type="number"
            id="fats"
            name="fats"
            value={formData.fats}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="fiber">Fiber (g)</label>
          <input
            type="number"
            id="fiber"
            name="fiber"
            value={formData.fiber}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="sugar">Sugar (g)</label>
          <input
            type="number"
            id="sugar"
            name="sugar"
            value={formData.sugar}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="vitamins">Vitamins (mg)</label>
          <input
            type="number"
            id="vitamins"
            name="vitamins"
            value={formData.vitamins}
            onChange={handleChange}
            required
          />
        </div>

        {errorMessage && <p className="error-message">{errorMessage}</p>}

        <button type="submit" disabled={loading}>
          {loading ? "Saving..." : "Save Meal"}
        </button>
      </form>
    </div>
  );
}

export default MealForm;
