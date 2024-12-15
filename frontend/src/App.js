import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Signup from './components/Signup';
import Login from './components/Login';
import ActivityForm from './components/ActivityForm';
import GoalForm from './components/GoalForm';
import MealForm from './components/Mealform';
import GymMembershipForm from './components/MembershipForm';
import DashboardPage from './components/Dashboard';
import UserGoalsPage from './components/UserGoalsPage';
import UpdatePassword from './components/UpdatePassword';
import AdminDashboard from './components/AdminDashboard';

function App() {
  return (
    <Router>
      <Routes>
        {/* Route for Home */}
        <Route path="/" element={<Home />} />

        {/* Route for Signup */}
        <Route path="/signup" element={<Signup />} />

        {/* Route for Login */}
        <Route path="/login" element={<Login />} />

        {/* Route for activity */}
        <Route path="/activities" element={<ActivityForm />} />

        {/* Route for goals */}
        <Route path="/goals" element={<GoalForm />} />

        {/* Route for meals */}
        <Route path="/meals" element={<MealForm />} />

         {/* Route for membership */}
         <Route path="/memberships" element={<GymMembershipForm />} />

         {/* Route for dashboard */}
         <Route path="/dashboard" element={<DashboardPage />} />

         {/* Route for fetchGoals */}
         <Route path="/fetchGoals" element={<UserGoalsPage />} />

         {/* Route for reset */}
         <Route path="/updatepassword" element={<UpdatePassword />} />

         {/* Route for allgoal */}
         <Route path="/admindashboard" element={<AdminDashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
