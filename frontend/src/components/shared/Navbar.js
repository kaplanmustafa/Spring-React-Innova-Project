import React from "react";
import { useDispatch } from "react-redux";
import { logoutSuccess } from "../../redux/authActions";
import { useHistory } from "react-router-dom";

const Navbar = (props) => {
  const dispatch = useDispatch();

  let history = useHistory();

  const onLogoutSuccess = async () => {
    await dispatch(logoutSuccess());
    history.push("/login");
  };

  return (
    <div className="mb-3 shadow-sm">
      <nav className="navbar navbar-expand-lg container navbar-light">
        <div className="navbar-brand">My Notes | Full Name</div>
        <div className="collapse navbar-collapse">
          <button
            className="btn btn-outline-danger ml-auto"
            onClick={onLogoutSuccess}
          >
            Logout
          </button>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
