import React, { useEffect, useState } from 'react';
import '../static/css/GoalTable.css';  // Import your styles

const ReportPage = () => {
  const [goals, setGoals] = useState([]);
  const [activities, setActivities] = useState([]);
  const [memberships, setMemberships] = useState([]);
  const [meals, setMeals] = useState([]); // State for meals
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchQuery, setSearchQuery] = useState(''); // State for search query

  const pageSize = 3; // Number of items per page
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    const fetchReport = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/report');
        if (!response.ok) {
          throw new Error('Failed to fetch report data');
        }
        const data = await response.json();
        setGoals(data.goals);
        setActivities(data.activities);
        setMemberships(data.memberships);
        setMeals(data.meals);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchReport();
  }, []);

  // Handle search query change
  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value.toLowerCase());
  };

  // Filter data based on search query
  const filterData = (data) => {
    return data.filter(item => {
      return Object.values(item).some(value =>
        String(value).toLowerCase().includes(searchQuery)
      );
    });
  };

  // Filtered data with pagination
  const filteredGoals = filterData(goals);
  const filteredActivities = filterData(activities);
  const filteredMemberships = filterData(memberships);
  const filteredMeals = filterData(meals);

  // Paginate filtered data
  const paginateData = (data) => {
    const startIndex = (currentPage - 1) * pageSize;
    const endIndex = startIndex + pageSize;
    return data.slice(startIndex, endIndex);
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">Error: {error}</div>;
  }

  return (
    <div className="report-page-container">
      <h2 className="title">Report</h2>

      {/* Search Input */}
      <div className="search-container">
        <input
          type="text"
          className="search-input"
          placeholder="Search in table..."
          onChange={handleSearchChange}
        />
      </div>

      {/* Goals Table */}
      <div className="table-container">
        <h3>Goals List</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Goal ID</th>
              <th>User</th>
              <th>Goal Type</th>
              <th>Target Value</th>
              <th>Current Value</th>
              <th>Status</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Progress</th>
            </tr>
          </thead>
          <tbody>
            {paginateData(filteredGoals).map((goal) => (
              <tr key={goal.goalId}>
                <td>{goal.goalId}</td>
                <td>{goal.user.firstName} {goal.user.lastName}</td>
                <td>{goal.goalType}</td>
                <td>{goal.targetValue}</td>
                <td>{goal.currentValue}</td>
                <td>{goal.status}</td>
                <td>{new Date(goal.startDate).toLocaleDateString()}</td>
                <td>{new Date(goal.endDate).toLocaleDateString()}</td>
                <td>{((goal.currentValue / goal.targetValue) * 100).toFixed(2)}%</td>
              </tr>
            ))}
          </tbody>
        </table>
        {/* Pagination Controls */}
        <div className="pagination">
          <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
            Previous
          </button>
          <span>Page {currentPage}</span>
          <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage * pageSize >= filteredGoals.length}>
            Next
          </button>
        </div>
      </div>

      {/* Activities Table */}
      <div className="table-container">
        <h3>Activities List</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Activity ID</th>
              <th>User</th>
              <th>Activity Type</th>
              <th>Distance</th>
              <th>Calories Burned</th>
              <th>Duration (min)</th>
              <th>Intensity</th>
              <th>Start Time</th>
              <th>End Time</th>
            </tr>
          </thead>
          <tbody>
            {paginateData(filteredActivities).map((activity) => (
              <tr key={activity.activityId}>
                <td>{activity.activityId}</td>
                <td>{activity.user.firstName} {activity.user.lastName}</td>
                <td>{activity.activityType}</td>
                <td>{activity.distance}</td>
                <td>{activity.caloriesBurned}</td>
                <td>{activity.duration}</td>
                <td>{activity.intensity}</td>
                <td>{new Date(activity.startTime).toLocaleString()}</td>
                <td>{new Date(activity.endTime).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {/* Pagination Controls */}
        <div className="pagination">
          <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
            Previous
          </button>
          <span>Page {currentPage}</span>
          <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage * pageSize >= filteredActivities.length}>
            Next
          </button>
        </div>
      </div>

      {/* Memberships Table */}
      <div className="table-container">
        <h3>Memberships List</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Membership ID</th>
              <th>User</th>
              <th>Type</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
            {paginateData(filteredMemberships).map((membership) => (
              <tr key={membership.id}>
                <td>{membership.id}</td>
                <td>{membership.user.firstName} {membership.user.lastName}</td>
                <td>{membership.type}</td>
                <td>{new Date(membership.startDate).toLocaleDateString()}</td>
                <td>{new Date(membership.endDate).toLocaleDateString()}</td>
                <td>{membership.price}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {/* Pagination Controls */}
        <div className="pagination">
          <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
            Previous
          </button>
          <span>Page {currentPage}</span>
          <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage * pageSize >= filteredMemberships.length}>
            Next
          </button>
        </div>
      </div>

      {/* Meals Table */}
      <div className="table-container">
        <h3>Meals List</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Meal ID</th>
              <th>User</th>
              <th>Meal Name</th>
              <th>Meal Time</th>
              <th>Calories</th>
              <th>Protein (g)</th>
              <th>Carbs (g)</th>
              <th>Fats (g)</th>
              <th>Fiber (g)</th>
              <th>Sugar (g)</th>
              <th>Meal Description</th>
            </tr>
          </thead>
          <tbody>
            {paginateData(filteredMeals).map((meal) => (
              <tr key={meal.id}>
                <td>{meal.id}</td>
                <td>{meal.user.firstName} {meal.user.lastName}</td>
                <td>{meal.mealName}</td>
                <td>{new Date(meal.mealTime).toLocaleString()}</td>
                <td>{meal.calories}</td>
                <td>{meal.protein}</td>
                <td>{meal.carbs}</td>
                <td>{meal.fats}</td>
                <td>{meal.fiber}</td>
                <td>{meal.sugar}</td>
                <td>{meal.mealDescription}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {/* Pagination Controls */}
        <div className="pagination">
          <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
            Previous
          </button>
          <span>Page {currentPage}</span>
          <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage * pageSize >= filteredMeals.length}>
            Next
          </button>
        </div>
      </div>
    </div>
  );
};

export default ReportPage;
