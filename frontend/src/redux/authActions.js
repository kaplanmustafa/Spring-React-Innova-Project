import { signup } from "../api/ApiCalls";

export const signupHandler = (user) => {
  return async function (dispatch) {
    const response = await signup(user);
    return response;
  };
};
