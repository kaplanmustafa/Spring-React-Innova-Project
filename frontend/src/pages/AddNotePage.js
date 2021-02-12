import React, { useState } from "react";
import { useSelector } from "react-redux";
import { useHistory } from "react-router";
import { saveNote } from "../api/ApiCalls";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import { useApiProgress } from "../shared/ApiProgress";

const AddNotePage = () => {
  const [content, setContent] = useState("");
  const [title, setTitle] = useState("");

  const onClickSaveNote = async () => {
    const body = {
      content,
      title,
    };

    try {
      await saveNote(body);
      history.push(`/mynotes/${username}`);
    } catch (error) {}
  };

  const { username } = useSelector((store) => ({
    username: store.username,
  }));

  let history = useHistory();

  const pendingApiCall = useApiProgress("post", "/api/1.0/notes");
  const buttonEnabled = content;

  return (
    <div className="container">
      <div className="container">
        <form>
          <div className="row">
            <div className="col-10 form-group">
              <label>Title</label>
              <textarea
                className="form-control"
                rows="1"
                onChange={(event) => {
                  setTitle(event.target.value);
                }}
              ></textarea>
            </div>
          </div>
          <div className="row">
            <div className="col-10 form-group">
              <label>Your Note</label>
              <textarea
                className="form-control"
                rows="15"
                onChange={(event) => {
                  setContent(event.target.value);
                }}
              ></textarea>
            </div>
            <div className="col-2">
              <div className="row mt-5">
                <ButtonWithProgress
                  className="btn btn-outline-success"
                  onClick={onClickSaveNote}
                  text="Save"
                  disabled={pendingApiCall || !buttonEnabled}
                  pendingApiCall={pendingApiCall}
                />
              </div>
              <div className="row mt-3">
                <button
                  className="btn btn-outline-danger"
                  onClick={() => {
                    history.push(`/mynotes/${username}`);
                  }}
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddNotePage;
