import React from "react";

const Navbar = () => {
  return (
    <div className="mb-3 shadow-sm">
      <nav className="navbar navbar-expand-lg container navbar-light">
        <div className="navbar-brand">My Notes | Full Name</div>
        <div className="collapse navbar-collapse">
          <button className="btn btn-outline-danger ml-auto">Logout</button>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
