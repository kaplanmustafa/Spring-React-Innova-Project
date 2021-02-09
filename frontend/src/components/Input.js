import React from "react";

const Input = (props) => {
  const { label, name, onChange, type } = props;
  let className = "form-control";

  return (
    <div className="form-group">
      <label>{label}</label>
      <input
        className={className}
        name={name}
        onChange={onChange}
        type={type}
      ></input>
    </div>
  );
};

export default Input;
