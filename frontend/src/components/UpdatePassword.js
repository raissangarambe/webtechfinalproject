import React, { useState } from 'react';

const UpdatePassword = () => {
  const [email, setEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false); 

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(`http://localhost:8081/api/users/update-password?email=${email}&newPassword=${newPassword}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const result = await response.json();

      if (response.ok) {
        setMessage('Password updated successfully!');
        setIsError(false); 
      } else {
        setMessage('Error: ' + result.message);
        setIsError(true); 
      }
    } catch (error) {
      setMessage('Error: ' + error.message);
      setIsError(true); 
    }
  };

  return (
    <div className="update-password-container">
      <h2>Update Password</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="newPassword">New Password</label>
          <input
            type="password"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Update Password</button>
      </form>
      {message && (
        <p style={{ color: isError ? 'red' : 'green' }}>
          {message}
        </p>
      )}
    </div>
  );
};

export default UpdatePassword;
