import React from "react";
import "./Home.css";
import { Link } from "react-router-dom";

export default function Home() {
  const user = JSON.parse(localStorage.getItem("user") || "null");

  const handleLogout = () => {
    localStorage.removeItem("user");
    window.location.href = "/login";
  };

  return (
    <div className="home-container">
      <div className="home-banner">
        <h1>Welcome to LMS Platform</h1>
        <p>Learn. Practice. Succeed.</p>
      </div>

      {user ? (
        <div className="home-dashboard">
          <p className="greeting">Hello, <b>{user.name}</b> 👋</p>
          <p>Your role: <b>{user.role}</b></p>

          <div className="dashboard-cards">
            {user.role === "ADMIN" ? (
              <Link to="/admin" className="card">Admin Dashboard</Link>
            ) : (
              <Link to="/user" className="card">User Dashboard</Link>
            )}
          </div>

          <button className="logout-btn" onClick={handleLogout}>Logout</button>
        </div>
      ) : (
        <div className="login-prompt">
          <p>Please <Link to="/login">login</Link> to access your dashboard.</p>
        </div>
      )}
    </div>
  );
}