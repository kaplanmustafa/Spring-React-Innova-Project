import React from "react";
import { HashRouter, Redirect, Route, Switch } from "react-router-dom";
import UserSignupPage from "../pages/UserSignupPage";

function App() {
  return (
    <HashRouter>
      <Switch>
        <Route path="/signup" component={UserSignupPage} />
      </Switch>
    </HashRouter>
  );
}

export default App;
