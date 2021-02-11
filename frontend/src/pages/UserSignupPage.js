import React, { useState } from "react";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";
import { useApiProgress } from "../shared/ApiProgress";
import { useDispatch } from "react-redux";
import { signupHandler } from "../redux/authActions";
import { Link } from "react-router-dom";

const UserSignupPage = () => {
  const [form, setForm] = useState({
    username: null,
    fullName: null,
    password: null,
    passwordRepeat: null,
  });

  const [errors, setErrors] = useState({});
  const [isSignupSuccess, setIsSignupSuccess] = useState(false);

  const dispatch = useDispatch();

  const onChange = (event) => {
    const { name, value } = event.target;

    setIsSignupSuccess(false);
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
      await dispatch(signupHandler(body));
      setIsSignupSuccess(true);
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
  const pendingApiCallLogin = useApiProgress("post", "/api/1.0/auth");

  const pendingApiCall = pendingApiCallSignup || pendingApiCallLogin;

  let passwordRepeatError;
  if (form.password !== form.passwordRepeat) {
    passwordRepeatError = "Password mismatch";
  }

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
          type={"password"}
        />
        <Input
          name="passwordRepeat"
          label="Password Repeat"
          onChange={onChange}
          error={passwordRepeatError}
          type={"password"}
        />
        {isSignupSuccess && (
          <div className="container text-center mt-2">
            <div className="alert alert-info">
              <Link to={"/login"}>
                <button className="btn btn-outline-primary">Go to Login</button>
              </Link>
              <div className="mt-2">Registration Successful</div>
            </div>
          </div>
        )}
        <div className="text-center">
          <ButtonWithProgress
            disabled={pendingApiCall || passwordRepeatError !== undefined}
            onClick={onClickSignup}
            pendingApiCall={pendingApiCall}
            text={"Sign Up"}
          ></ButtonWithProgress>
        </div>
      </form>
    </div>
  );
};

export default UserSignupPage;
