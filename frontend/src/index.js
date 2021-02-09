import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import UserSignupPage from "./pages/UserSignupPage";
import "./bootstrap-override.scss";

ReactDOM.render(
  <React.StrictMode>
    <UserSignupPage />
  </React.StrictMode>,
  document.getElementById("root")
);

reportWebVitals();
