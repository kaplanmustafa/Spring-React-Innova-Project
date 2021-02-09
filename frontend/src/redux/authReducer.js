import * as ACTIONS from "./Constants";

const defaultState = {
  isLoggedIn: false,
  username: undefined,
  fullName: undefined,
  role: undefined,
};

const authReducer = (state = { ...defaultState }, action) => {
  return state;
};

export default authReducer;
