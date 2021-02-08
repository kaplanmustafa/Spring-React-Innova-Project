import React, { useState } from "react";

const UserSignupPage = () => {
  const [form, setForm] = useState({
    username: null,
    fullName: null,
    password: null,
    passwordRepeat: null,
  });

  const onChange = (event) => {
    const { name, value } = event.target;
    setForm((previousForm) => ({ ...previousForm, [name]: value }));
  };

  return (
    <div>
      <form>
        <h1>Sign Up</h1>
        <div>
          <label>Username</label>
          <input name="username" onChange={onChange} />
        </div>
        <div>
          <label>Full Name</label>
          <input name="fullName" onChange={onChange} />
        </div>
        <div>
          <label>Password</label>
          <input name="password" type="password" onChange={onChange} />
        </div>
        <div>
          <label>Password Repeat</label>
          <input name="passwordRepeat" type="password" onChange={onChange} />
        </div>
        <button>Sign Up</button>
      </form>
    </div>
  );
};

export default UserSignupPage;
