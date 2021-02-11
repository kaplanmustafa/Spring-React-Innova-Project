import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { logoutSuccess } from "../../redux/authActions";
import { Link, useHistory } from "react-router-dom";

const Navbar = (props) => {
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
      <nav className="navbar navbar-expand-lg container navbar-light">
        <div className="navbar-brand">
          {isLoggedIn ? "My Notes | " + fullName : "My Notes"}
        </div>
        <div className="collapse navbar-collapse">
          {isLoggedIn && (
            <button
              className="btn btn-outline-danger ml-auto"
              onClick={onLogoutSuccess}
            >
              Logout
            </button>
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
