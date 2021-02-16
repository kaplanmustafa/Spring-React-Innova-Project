import React, { useState } from "react";
import { useSelector } from "react-redux";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";
import Modal from "../components/toolbox/Modal";

const UserProfilePage = () => {
  const [inEditMode, setInEditMode] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);

  const { username, fullName } = useSelector((store) => ({
    username: store.username,
    fullName: store.fullName,
  }));

  const onClickCancelModal = () => {
    setModalVisible(false);
  };

  return (
    <div className="container mt-5">
      <div className="card text-center">
        <div className="card-body">
          {!inEditMode && (
            <>
              <h3>{fullName}</h3>
              <button
                className="btn btn-success d-inline-flex"
                onClick={() => setInEditMode(true)}
              >
                Edit
              </button>
              <div className="pt-2">
                <button
                  className="btn btn-danger d-inline-flex"
                  onClick={() => setModalVisible(true)}
                >
                  Delete My Account
                </button>
              </div>
            </>
          )}
          {inEditMode && (
            <div className="container row">
              <div className="container col-6 text-left">
                <Input label={"Username"} defaultValue={username} />
                <Input label={"Full Name"} defaultValue={fullName} />
                <div>
                  <ButtonWithProgress
                    className="btn btn-primary d-inline-flex"
                    text={"Save"}
                  />
                  <button
                    className="btn btn-danger d-inline-flex ml-1"
                    onClick={() => setInEditMode(false)}
                  >
                    Cancel
                  </button>
                </div>
              </div>
              <div className="container col-6 text-left border-left">
                <Input
                  label={"Current Password"}
                  onChange={(event) => {}}
                  type="password"
                />
                <Input
                  label={"New Password"}
                  onChange={(event) => {}}
                  type="password"
                />
                <Input
                  label={"New Password Repeat"}
                  onChange={(event) => {}}
                  type="password"
                />
                <div className="text-right">
                  <ButtonWithProgress
                    className="btn btn-primary d-inline-flex"
                    text={"Update"}
                  />
                </div>
              </div>
            </div>
          )}
        </div>
        <Modal
          title={"Delete My Account"}
          okButton={"Delete My Account"}
          visible={modalVisible}
          onClickCancel={onClickCancelModal}
          message={"Are you sure to delete your account?"}
        />
      </div>
    </div>
  );
};

export default UserProfilePage;
