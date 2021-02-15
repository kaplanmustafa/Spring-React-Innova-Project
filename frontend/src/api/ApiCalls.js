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

export const getNotes = (username, page = 0, size = 6) => {
  return axios.get(
    `/api/1.0/users/${username}/notes?page=${page}&size=${size}`
  );
};

export const getOldNotes = (id, username, size = 6) => {
  return axios.get(`/api/1.0/users/${username}/notes/${id}?size=${size}`);
};

export const getNoteById = (id) => {
  return axios.get(`/api/1.0/users/notes/${id}`);
};

export const updateNote = (username, noteId, body) => {
  return axios.put(`/api/1.0/notes/${username}/${noteId}`, body);
};

export const deleteNote = (id) => {
  return axios.delete(`/api/1.0/notes/${id}`);
};

export const saveComment = (comment, noteId) => {
  return axios.post(`/api/1.0/comments/${noteId}`, comment);
};

export const getComments = (noteId, page = 0, size = 3) => {
  return axios.get(
    `/api/1.0/users/comments/${noteId}?page=${page}&size=${size}`
  );
};

export const getOldComments = (id, noteId, size = 3) => {
  return axios.get(`/api/1.0/users/${noteId}/comments/${id}?size=${size}`);
};

export const deleteComment = (id) => {
  return axios.delete(`/api/1.0/comments/${id}`);
};
