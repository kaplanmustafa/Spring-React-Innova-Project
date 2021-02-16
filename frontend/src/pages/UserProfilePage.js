import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Input from "../components/toolbox/Input";
import Modal from "../components/toolbox/Modal";
import { updateUser, updatePassword } from "../api/ApiCalls";
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
  const [currentPassword, setCurrentPassword] = useState("");
  const [updatedPassword, setUpdatedPassword] = useState("");
  const [updatedPasswordRepeat, setUpdatedPasswordRepeat] = useState("");
  const [validationErrors, setValidationErrors] = useState({});
  const [isUpdateSuccess, setIsUpdateSucces] = useState(false);

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
      setIsUpdateSucces(true);
    } catch (error) {
      setValidationErrors(error.response.data.validationErrors);
    }
  };

  const onClickUpdatePassword = async () => {
    const body = {
      currentPassword: currentPassword,
      newPassword: updatedPassword,
    };

    try {
      await updatePassword(username, body);
      setInEditMode(false);
      setIsUpdateSucces(true);
    } catch (error) {
      setValidationErrors(error.response.data.validationErrors);
    }
  };

  useEffect(() => {
    setValidationErrors({});
    setUpdatedUsername(username);
    setUpdatedFullName(fullName);
    setCurrentPassword();
    setUpdatedPassword();
    setUpdatedPasswordRepeat();

    if (inEditMode) {
      setIsUpdateSucces(false);
    }
  }, [inEditMode]);

  const onClickCancelModal = () => {
    setModalVisible(false);
  };

  const pendingApiCall = useApiProgress("put", "/api/1.0/users/" + username);
  const pendingApiCallPassword = useApiProgress(
    "put",
    "/api/1.0/users/password/" + username
  );

  const {
    username: usernameError,
    fullName: fullNameError,
    currentPassword: passwordError,
    newPassword: newPasswordError,
  } = validationErrors;
  const saveButtonEnabled =
    username === updatedUsername && fullName === updatedFullName;

  let passwordRepeatError;
  if (updatedPassword !== updatedPasswordRepeat) {
    passwordRepeatError = "Password mismatch";
  }
  const updateButtonEnabled =
    currentPassword === "" ||
    updatedPassword === "" ||
    updatedPasswordRepeat === "";

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
              {isUpdateSuccess && (
                <div className="container text-center mt-5">
                  <div className="alert alert-info">
                    <div className="mt-2 mb-2">Update Successful</div>
                  </div>
                </div>
              )}
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
                    disabled={
                      pendingApiCall ||
                      saveButtonEnabled ||
                      pendingApiCallPassword
                    }
                    pendingApiCall={pendingApiCall}
                  />
                  <button
                    className="btn btn-danger d-inline-flex ml-1"
                    onClick={() => setInEditMode(false)}
                    disabled={pendingApiCall || pendingApiCallPassword}
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
                  error={passwordError}
                  onChange={(event) => {
                    setCurrentPassword(event.target.value);
                    setValidationErrors((previousValidationErrors) => {
                      return {
                        ...previousValidationErrors,
                        currentPassword: undefined,
                      };
                    });
                  }}
                />
                <Input
                  label={"New Password"}
                  onChange={(event) => {}}
                  type="password"
                  error={newPasswordError}
                  onChange={(event) => {
                    setUpdatedPassword(event.target.value);
                    setValidationErrors((previousValidationErrors) => {
                      return {
                        ...previousValidationErrors,
                        newPassword: undefined,
                      };
                    });
                  }}
                />
                <Input
                  label={"New Password Repeat"}
                  onChange={(event) => {}}
                  type="password"
                  error={passwordRepeatError}
                  onChange={(event) => {
                    setUpdatedPasswordRepeat(event.target.value);
                  }}
                />
                <div className="text-right">
                  <ButtonWithProgress
                    className="btn btn-primary d-inline-flex"
                    text={"Update"}
                    pendingApiCall={pendingApiCallPassword}
                    disabled={
                      pendingApiCallPassword ||
                      pendingApiCall ||
                      passwordRepeatError !== undefined ||
                      updateButtonEnabled
                    }
                    onClick={onClickUpdatePassword}
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
