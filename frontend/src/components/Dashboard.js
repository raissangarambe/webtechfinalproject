import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../static/css/Dashboard.css";

function DashboardPage() {
  const [summary, setSummary] = useState({
    activities: 0,
    completedGoals: 0,
    failedGoals: 0,
    meals: 0,
  });

  const [user, setUser] = useState({ first_name: "User" });

  useEffect(() => {
    // Fetch the summary data from the backend
    fetch("/api/dashboard/summary")
  .then((response) => {
    console.log(response);
    return response.json();
  })
  .then((data) => setSummary(data))
  .catch((error) => console.error("Error fetching dashboard summary:", error));

    // Fetch the user data from the backend
    fetch("/api/user")
      .then((response) => response.json())
      .then((userData) => setUser(userData))
      .catch((error) => console.error("Error fetching user data:", error));
  }, []);

  const menuItems = [
    { key: "/", label: "Home", route: "/", icon: "🏠" },
    { key: "goals", label: "Create New Goals", route: "/goals", icon: "🎯" },
    { key: "listgoals", label: "View Goals", route: "/fetchGoals", icon: "📋" },
    { key: "memberships", label: "Memberships", route: "/memberships", icon: "💳" },
    { key: "meals", label: "Log your Meals", route: "/meals", icon: "🍴" },
    { key: "activities", label: "Select New Activities", route: "/activities", icon: "🏋️‍♀️" },
    { key: "settings", label: "Settings", route: "/updatepassword", icon: "⚙️" },
  ];

  const userCards = [
    {
      id: 1,
      title: "Your Goals",
      description: `Completed: ${summary.completedGoals}, Failed: ${summary.failedGoals}`,
      buttonLabel: "Explore your Goals",
      route: "/fetchGoals",
    },
    {
      id: 2,
      title: "Your Activities",
      description: `Activities selected: ${summary.activities}`,
      buttonLabel: "Explore Activities",
      route: "/activities",
    },
    {
      id: 3,
      title: "Your Meals",
      description: `Meals logged: ${summary.meals}`,
      buttonLabel: "Explore Meals",
      route: "/meals",
    },
  ];

  return (
    <div className="dashboard-container">

      {/* Sidebar */}
      <aside className="sidebar">
        <div className="app-logo">
          <img src="../static/images/profile.png" alt=" " />
        </div>
        <h2>User Dashboard</h2>
        <ul>
          {menuItems.map((item) => (
            <li key={item.key}>
              <Link to={item.route} className="menu-link">
                <span className="menu-icon">{item.icon}</span> {item.label}
              </Link>
            </li>
          ))}
        </ul>
      </aside>

      {/* Main Content */}
      <main className="main-content">
        <header>
          <h1>Welcome, {user.first_name}</h1>
          <p>Select an option from the menu or view your progress below.</p>
        </header>

        {/* User Cards */}
        <section className="card-container">
          {userCards.map((card) => (
            <div key={card.id} className="card">
              <h3>{card.title}</h3>
              <p>{card.description}</p>
              <Link to={card.route}>
                <button className="explore-button">{card.buttonLabel}</button>
              </Link>
            </div>
          ))}
        </section>
      </main>
    </div>
  );
}

export default DashboardPage;
