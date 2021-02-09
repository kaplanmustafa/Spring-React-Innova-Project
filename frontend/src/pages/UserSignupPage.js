import axios from "axios";
import React, { useState } from "react";
import { useApiProgress } from "../shared/ApiProgress";

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

  const onClickSignup = (event) => {
    event.preventDefault();

    const { username, fullName, password } = form;

    const body = {
      username,
      fullName,
      password,
    };

    axios.post("/api/1.0/users", body);
  };

  const pendingApiCallSignup = useApiProgress("post", "/api/1.0/users");

  return (
    <div className="container w-25">
      <form>
        <h1 className="text-center mt-4 mb-4">Sign Up</h1>
        <div className="form-group">
          <label>Username</label>
          <input className="form-control" name="username" onChange={onChange} />
        </div>
        <div className="form-group">
          <label>Full Name</label>
          <input className="form-control" name="fullName" onChange={onChange} />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input
            className="form-control"
            name="password"
            type="password"
            onChange={onChange}
          />
        </div>
        <div className="form-group">
          <label>Password Repeat</label>
          <input
            className="form-control"
            name="passwordRepeat"
            type="password"
            onChange={onChange}
          />
        </div>
        <div className="text-center">
          <button
            className="btn btn-primary"
            onClick={onClickSignup}
            disabled={pendingApiCallSignup}
          >
            {pendingApiCallSignup && (
              <span className="spinner-border spinner-border-sm"></span>
            )}
            Sign Up
          </button>
        </div>
      </form>
    </div>
  );
};

export default UserSignupPage;
