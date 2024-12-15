import React from 'react';
import '../static/css/Home.css';


function Home() {
  return (
    <div className="home-container">
      <div className="overlay"></div>
      <header className="top-links">
        <a href="signup" className="join-link">Join</a>
      </header>
      <main className="center-content">
        <h1 className="title">Fitness Tracker</h1>
      </main>
      <footer className="bottom-links">
        <a href="#contact" className="contact-link">
          <span className="icon">📞</span> Contact
        </a>
        <a href="#gallery" className="gallery-link">
          <span className="icon">📷</span> Gallery
        </a>
      </footer>
    </div>
  );
}

export default Home;
