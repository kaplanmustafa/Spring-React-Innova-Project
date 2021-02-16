import { login, signup } from "../api/ApiCalls";
import * as ACTIONS from "./Constants";

export const loginSuccess = (authState) => {
  return {
    type: ACTIONS.LOGIN_SUCCESS,
    payload: authState,
  };
};

export const logoutSuccess = () => {
  return async function (dispatch) {
    dispatch({
      type: ACTIONS.LOGOUT_SUCCESS,
    });
  };
};

export const updateSuccess = ({ username, fullName }) => {
  return {
    type: ACTIONS.UPDATE_SUCCESS,
    payload: {
      username,
      fullName,
    },
  };
};

export const loginHandler = (credentials) => {
  return async function (dispatch) {
    const response = await login(credentials);

    const authState = {
      ...response.data.user,
      token: response.data.token,
    };

    dispatch(loginSuccess(authState));

    return response;
  };
};

export const signupHandler = (user) => {
  return async function (dispatch) {
    const response = await signup(user);
    return response;
  };
};
