import React from "react";
import { HashRouter, Redirect, Route, Switch } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import UserSignupPage from "../pages/UserSignupPage";

function App() {
  return (
    <HashRouter>
      <Switch>
        <Route path="/signup" component={UserSignupPage} />
        <Route path="/login" component={LoginPage} />
      </Switch>
    </HashRouter>
  );
}

export default App;
