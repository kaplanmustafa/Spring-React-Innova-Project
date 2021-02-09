import React, { useState } from "react";
import { signup } from "../api/ApiCalls";
import Input from "../components/Input";
import { useApiProgress } from "../shared/ApiProgress";

const UserSignupPage = () => {
  const [form, setForm] = useState({
    username: null,
    fullName: null,
    password: null,
    passwordRepeat: null,
  });

  const [errors, setErrors] = useState({});

  const onChange = (event) => {
    const { name, value } = event.target;

    setErrors((previousErrors) => ({ ...previousErrors, [name]: undefined }));
    setForm((previousForm) => ({ ...previousForm, [name]: value }));
  };

  const onClickSignup = async (event) => {
    event.preventDefault();

    const { username, fullName, password } = form;

    const body = {
      username,
      fullName,
      password,
    };

    try {
      const response = await signup(body);
    } catch (error) {
      if (error.response.data.validationErrors) {
        setErrors(error.response.data.validationErrors);
      }
    }
  };

  const {
    username: usernameError,
    fullName: fullNameError,
    password: passwordError,
  } = errors;

  const pendingApiCallSignup = useApiProgress("post", "/api/1.0/users");

  return (
    <div className="container w-25">
      <form>
        <h1 className="text-center mt-4 mb-4">Sign Up</h1>
        <Input
          name="username"
          label="Username"
          onChange={onChange}
          error={usernameError}
        />
        <Input
          name="fullName"
          label="Full Name"
          onChange={onChange}
          error={fullNameError}
        />
        <Input
          name="password"
          label="Password"
          onChange={onChange}
          error={passwordError}
        />
        <Input
          name="passwordRepeat"
          label="Password Repeat"
          onChange={onChange}
        />
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
