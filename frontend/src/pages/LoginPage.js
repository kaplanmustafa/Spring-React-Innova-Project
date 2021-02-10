import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";
import { loginHandler } from "../redux/authActions";
import { useApiProgress } from "../shared/ApiProgress";

const LoginPage = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [error, setError] = useState();

  const dispatch = useDispatch();

  useEffect(() => {
    setError(undefined);
  }, [username, password]);

  const onClickLogin = async (event) => {
    event.preventDefault();

    const creds = {
      username,
      password,
    };

    setError(undefined);

    try {
      await dispatch(loginHandler(creds));
    } catch (apiError) {
      setError("Incorrect username or password");
    }
  };

  const pendingApiCall = useApiProgress("post", "/api/1.0/auth");
  const buttonEnabled = username && password;

  return (
    <div className="container w-25 mt-5">
      <form>
        <h1 className="text-center mt-5 mb-4">{"Login"}</h1>
        <Input
          label={"Username"}
          onChange={(event) => {
            setUsername(event.target.value);
          }}
        />
        <Input
          label={"Password"}
          onChange={(event) => {
            setPassword(event.target.value);
          }}
          type="password"
        />
        {error && <div className="alert alert-danger">{error}</div>}
        <div className="text-center">
          <ButtonWithProgress
            disabled={!buttonEnabled || pendingApiCall}
            onClick={onClickLogin}
            text={"Login"}
            pendingApiCall={pendingApiCall}
          ></ButtonWithProgress>
        </div>
      </form>
    </div>
  );
};

export default LoginPage;
