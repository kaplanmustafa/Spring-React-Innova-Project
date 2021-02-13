import React from "react";
import errorImage from "../assets/error.png";

const ErrorPage = () => {
  return (
    <div className="container text-center">
      <img src={errorImage} alt={"404-error"} />
    </div>
  );
};

export default ErrorPage;
