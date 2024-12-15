import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function GymMembershipForm() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    membershipType: "BASIC",
    startDate: "",
    endDate: "",
    price: 0,
    status: "ACTIVE",
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
    const userId = localStorage.getItem("userId");

    try {
      const response = await fetch(`http://localhost:8081/api/memberships/${userId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Membership created successfully!");
        navigate("/dashboard");
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
    <div>
      <h2>Create Gym Membership</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Membership Type</label>
          <select
            name="membershipType"
            value={formData.membershipType}
            onChange={handleChange}
            required
          >
            <option value="BASIC">Basic</option>
            <option value="PREMIUM">Premium</option>
            <option value="FAMILY">Family</option>
          </select>
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
          <label>Price</label>
          <input
            type="number"
            name="price"
            value={formData.price}
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
            <option value="INACTIVE">Inactive</option>
          </select>
        </div>

        <button
          type="submit"
          disabled={loading}
        >
          {loading ? "Saving..." : "Save Membership"}
        </button>

        {errorMessage && <div>{errorMessage}</div>}
      </form>
    </div>
  );
}

export default GymMembershipForm;
