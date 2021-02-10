import React, { useState } from "react";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";

const LoginPage = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  return (
    <div className="container w-25 mt-5">
      <form>
        <h1 className="text-center mb-4">Login</h1>
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
        <div className="text-center">
          <ButtonWithProgress text={"Login"}></ButtonWithProgress>
        </div>
      </form>
    </div>
  );
};

export default LoginPage;
