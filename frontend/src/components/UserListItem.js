import React from "react";

const UserListItem = (props) => {
  const { user } = props;
  const { username, fullName } = user;

  return (
    <div className="list-group-item list-group-item-action">
      <span className="pl-2">
        {fullName}@{username}
      </span>
    </div>
  );
};

export default UserListItem;
