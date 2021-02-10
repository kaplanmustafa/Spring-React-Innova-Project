import * as ACTIONS from "./Constants";

const defaultState = {
  isLoggedIn: false,
  username: undefined,
  fullName: undefined,
  role: undefined,
};

const authReducer = (state = { ...defaultState }, action) => {
  if (action.type === ACTIONS.LOGIN_SUCCESS) {
    return {
      ...action.payload,
      isLoggedIn: true,
    };
  }
  return state;
};

export default authReducer;
