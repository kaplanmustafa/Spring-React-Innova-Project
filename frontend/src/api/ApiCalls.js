import axios from "axios";

export const signup = (body) => {
  return axios.post("/api/1.0/users", body);
};

export const login = (creds) => {
  return axios.post("/api/1.0/auth", creds);
};

export const setAuthorizationHeader = ({ token, isLoggedIn }) => {
  if (isLoggedIn) {
    const authorizationHeaderValue = `Bearer ${token}`;
    axios.defaults.headers["Authorization"] = authorizationHeaderValue;
  } else {
    delete axios.defaults.headers["Authorization"];
  }
};

export const saveNote = (note) => {
  return axios.post("/api/1.0/notes", note);
};

export const getNotes = (username, page = 0, size = 3) => {
  return axios.get(
    `/api/1.0/users/${username}/notes?page=${page}&size=${size}`
  );
};

export const getOldNotes = (id, username, size = 3) => {
  return axios.get(`/api/1.0/users/${username}/notes/${id}?size=${3}`);
};
