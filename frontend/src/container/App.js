import React from "react";
import { HashRouter, Redirect, Route, Switch } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import UserSignupPage from "../pages/UserSignupPage";
import UserPage from "../pages/UserPage";
import ErrorPage from "../pages/ErrorPage";
import { useSelector } from "react-redux";
import Navbar from "../components/shared/Navbar";
import AddNotePage from "../pages/AddNotePage";
import NoteDetailPage from "../pages/NoteDetailPage";

function App() {
  const { isLoggedIn, role } = useSelector((store) => ({
    isLoggedIn: store.isLoggedIn,
    role: store.role,
  }));

  return (
    <HashRouter>
      <Navbar />
      <Switch>
        <Route exact path="/" component={isLoggedIn ? UserPage : LoginPage} />
        {!isLoggedIn && <Route path="/signup" component={UserSignupPage} />}
        {!isLoggedIn && <Route path="/login" component={LoginPage} />}
        {isLoggedIn && <Route path="/addNote" component={AddNotePage} />}
        {isLoggedIn && (
          <Route path="/note/:noteId" component={NoteDetailPage} />
        )}
        <Route path="/error" component={ErrorPage} />
        <Redirect to="/error" />
      </Switch>
    </HashRouter>
  );
}

export default App;
