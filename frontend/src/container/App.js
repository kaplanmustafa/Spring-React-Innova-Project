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
import UserProfilePage from "../pages/UserProfilePage";
import AdminLoginPage from "../pages/AdminLoginPage";
import AdminPage from "../pages/AdminPage";

function App() {
  const { isLoggedIn, role } = useSelector((store) => ({
    isLoggedIn: store.isLoggedIn,
    role: store.role,
  }));

  return (
    <HashRouter>
      <Navbar />
      <Switch>
        {isLoggedIn && role === "user" && (
          <Route exact path="/" component={UserPage} />
        )}
        {isLoggedIn && role === "admin" && (
          <Route exact path="/" component={AdminPage} />
        )}
        {!isLoggedIn && <Route exact path="/" component={LoginPage} />}
        {!isLoggedIn && <Route path="/signup" component={UserSignupPage} />}
        {!isLoggedIn && <Route path="/login" component={LoginPage} />}
        {!isLoggedIn && <Route path="/admin" component={AdminLoginPage} />}
        {isLoggedIn && role === "user" && (
          <Route path="/addNote" component={AddNotePage} />
        )}
        {isLoggedIn && role === "user" && (
          <Route exact path="/user" component={UserProfilePage} />
        )}
        {isLoggedIn && role === "user" && (
          <Route path="/note/:noteId" component={NoteDetailPage} />
        )}
        <Route path="/error" component={ErrorPage} />
        {isLoggedIn && <Redirect to="/" />}
        <Redirect to="/error" />
      </Switch>
    </HashRouter>
  );
}

export default App;
