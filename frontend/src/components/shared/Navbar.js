import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { logoutSuccess } from "../../redux/authActions";
import { Link, useHistory } from "react-router-dom";

const Navbar = () => {
  const { isLoggedIn, fullName } = useSelector((store) => {
    return {
      isLoggedIn: store.isLoggedIn,
      fullName: store.fullName,
    };
  });

  const dispatch = useDispatch();

  let history = useHistory();

  const onLogoutSuccess = async () => {
    await dispatch(logoutSuccess());
    history.push("/login");
  };

  return (
    <div className="mb-3 shadow-sm">
      <nav className="navbar navbar-expand-lg navbar-light container">
        {isLoggedIn && (
          <Link to={"/"}>
            <div className="navbar-brand">My Notes | {fullName}</div>
          </Link>
        )}
        {!isLoggedIn && <div className="navbar-brand">My Notes</div>}
        <div className="container">
          {isLoggedIn && (
            <div className="ml-auto">
              <Link to={"/addNote"}>
                <button className="btn btn-outline-primary">New Note</button>
              </Link>
              <Link to={"/user"}>
                <button className="btn btn-outline-secondary ml-1">
                  Profile
                </button>
              </Link>
              <button
                className="btn btn-outline-danger ml-1"
                onClick={onLogoutSuccess}
              >
                Logout
              </button>
            </div>
          )}
          {!isLoggedIn && (
            <div className="ml-auto">
              <Link to={"/login"}>
                <button className="btn btn-outline-primary">Login</button>
              </Link>
              <Link to={"/signup"}>
                <button className="btn btn-outline-primary ml-1">Signup</button>
              </Link>
            </div>
          )}
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
