import * as ACTIONS from "./Constants";
import { signup } from "../api/apiCalls";

export const signupHandler = (user) => {
  return async function (dispatch) {
    const response = await signup(user);
    return response;
  };
};
