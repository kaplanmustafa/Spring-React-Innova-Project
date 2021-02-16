import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";
import Modal from "../components/toolbox/Modal";
import { updateUser } from "../api/ApiCalls";
import { updateSuccess } from "../redux/authActions";
import { useApiProgress } from "../shared/ApiProgress";

const UserProfilePage = () => {
  const { username, fullName } = useSelector((store) => ({
    username: store.username,
    fullName: store.fullName,
  }));

  const [inEditMode, setInEditMode] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [updatedUsername, setUpdatedUsername] = useState(username);
  const [updatedFullName, setUpdatedFullName] = useState(fullName);
  const [currentPassword, setCurrentPassword] = useState();
  const [updatedPassword, setUpdatedPassword] = useState();
  const [updatedPasswordRepeat, setUpdatedPasswordRepeat] = useState();
  const [validationErrors, setValidationErrors] = useState({});

  const dispatch = useDispatch();

  const onClickSaveUser = async () => {
    const body = {
      username: updatedUsername,
      fullName: updatedFullName,
    };

    try {
      const response = await updateUser(username, body);
      setInEditMode(false);
      dispatch(updateSuccess(response.data));
    } catch (error) {
      setValidationErrors(error.response.data.validationErrors);
    }
  };

  const onClickCancel = () => {
    setInEditMode(false);
    setValidationErrors({});
    setUpdatedUsername(username);
    setUpdatedFullName(fullName);
  };

  const onClickCancelModal = () => {
    setModalVisible(false);
  };

  const pendingApiCall = useApiProgress("put", "/api/1.0/users/" + username);
  const { username: usernameError, fullName: fullNameError } = validationErrors;
  const saveButtonEnabled =
    username === updatedUsername && fullName === updatedFullName;

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
                <Input
                  label={"Username"}
                  defaultValue={username}
                  onChange={(event) => {
                    setUpdatedUsername(event.target.value);
                    setValidationErrors((previousValidationErrors) => {
                      return {
                        ...previousValidationErrors,
                        username: undefined,
                      };
                    });
                  }}
                  error={usernameError}
                />
                <Input
                  label={"Full Name"}
                  defaultValue={fullName}
                  onChange={(event) => {
                    setUpdatedFullName(event.target.value);
                    setValidationErrors((previousValidationErrors) => {
                      return {
                        ...previousValidationErrors,
                        fullName: undefined,
                      };
                    });
                  }}
                  error={fullNameError}
                />
                <div>
                  <ButtonWithProgress
                    className="btn btn-primary d-inline-flex"
                    text={"Save"}
                    onClick={onClickSaveUser}
                    disabled={pendingApiCall || saveButtonEnabled}
                    pendingApiCall={pendingApiCall}
                  />
                  <button
                    className="btn btn-danger d-inline-flex ml-1"
                    onClick={onClickCancel}
                    disabled={pendingApiCall}
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
                  onChange={(event) => {
                    setCurrentPassword(event.target.value);
                  }}
                />
                <Input
                  label={"New Password"}
                  onChange={(event) => {}}
                  type="password"
                  onChange={(event) => {
                    setUpdatedPassword(event.target.value);
                  }}
                />
                <Input
                  label={"New Password Repeat"}
                  onChange={(event) => {}}
                  type="password"
                  onChange={(event) => {
                    setUpdatedPasswordRepeat(event.target.value);
                  }}
                />
                <div className="text-right">
                  <ButtonWithProgress
                    className="btn btn-primary d-inline-flex"
                    text={"Update"}
                    disabled={pendingApiCall}
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
