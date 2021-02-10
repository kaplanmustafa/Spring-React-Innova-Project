import React from "react";
import { HashRouter, Redirect, Route, Switch } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import UserSignupPage from "../pages/UserSignupPage";
import UserPage from "../pages/UserPage";
import ErrorPage from "../pages/ErrorPage";
import { useSelector } from "react-redux";

function App() {
  const { isLoggedIn, role } = useSelector((store) => ({
    isLoggedIn: store.isLoggedIn,
    role: store.role,
  }));

  return (
    <HashRouter>
      <Switch>
        {!isLoggedIn && <Route path="/signup" component={UserSignupPage} />}
        {!isLoggedIn && <Route path="/login" component={LoginPage} />}
        {isLoggedIn && <Route path="/user" component={UserPage} />}
        <Route path="/error" component={ErrorPage} />
        <Redirect to="/error" />
      </Switch>
    </HashRouter>
  );
}

export default App;
