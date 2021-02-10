import React, { useState } from "react";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";
import { useApiProgress } from "../shared/ApiProgress";
import { useDispatch } from "react-redux";
import { signupHandler } from "../redux/authActions";

const UserSignupPage = (props) => {
  const [form, setForm] = useState({
    username: null,
    fullName: null,
    password: null,
    passwordRepeat: null,
  });

  const [errors, setErrors] = useState({});

  const dispatch = useDispatch();

  const onChange = (event) => {
    const { name, value } = event.target;

    setErrors((previousErrors) => ({ ...previousErrors, [name]: undefined }));
    setForm((previousForm) => ({ ...previousForm, [name]: value }));
  };

  const onClickSignup = async (event) => {
    event.preventDefault();

    const { history } = props;
    const { push } = history;

    const { username, fullName, password } = form;

    const body = {
      username,
      fullName,
      password,
    };

    try {
      await dispatch(signupHandler(body));
      push("/login");
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
