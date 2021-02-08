import React from "react";

const UserSignupPage = () => {
  return (
    <div>
      <form>
        <h1>Sign Up</h1>
        <div>
          <label>Username</label>
          <input />
        </div>
        <div>
          <label>Full Name</label>
          <input />
        </div>
        <div>
          <label>Password</label>
          <input type="password" />
        </div>
        <div>
          <label>Password Repeat</label>
          <input type="password" />
        </div>
        <button>Sign Up</button>
      </form>
    </div>
  );
};

export default UserSignupPage;
