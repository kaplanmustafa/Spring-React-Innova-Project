import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router";
import { getNoteById } from "../api/ApiCalls";
import { updateNote } from "../api/ApiCalls";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import { useApiProgress } from "../shared/ApiProgress";

const NoteDetailPage = () => {
  const [inEditMode, setInEditMode] = useState(false);
  const [note, setNote] = useState({});
  const [updatedContent, setUpdatedContent] = useState("");
  const [updatedTitle, setUpdatedTitle] = useState("");

  const { content, title } = note;

  const { username } = useSelector((store) => ({
    username: store.username,
  }));

  const { noteId } = useParams();

  useEffect(() => {
    loadNote(noteId);
  }, [noteId, content]);

  const loadNote = async (noteId) => {
    try {
      const response = await getNoteById(noteId);
      setNote(response.data);
      setUpdatedContent(response.data.content);
      setUpdatedTitle(response.data.title);
    } catch (error) {}
  };

  const onClickSaveNote = async () => {
    const body = {
      content: updatedContent,
      title: updatedTitle,
    };

    try {
      const response = await updateNote(username, noteId, body);
      setInEditMode(false);
      setNote(response.data);
    } catch (error) {}
  };

  const onClickCancel = () => {
    setInEditMode(false);
    setUpdatedTitle(title);
    setUpdatedContent(content);
  };

  const pendingApiCall = useApiProgress(
    "put",
    `/api/1.0/notes/${username}/${noteId}`
  );
  const buttonEnabled = content !== updatedContent || title !== updatedTitle;

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
                value={updatedTitle}
                disabled={!inEditMode}
                maxLength={255}
                onChange={(event) => {
                  setUpdatedTitle(event.target.value);
                }}
              ></textarea>
            </div>
          </div>
          <div className="row">
            <div className="col-10 form-group">
              <label>Your Note</label>
              <textarea
                className="form-control fixedTextArea"
                rows="15"
                value={updatedContent}
                disabled={!inEditMode}
                maxLength={1000}
                onChange={(event) => {
                  setUpdatedContent(event.target.value);
                }}
              ></textarea>
            </div>
            {inEditMode && (
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
                    onClick={onClickCancel}
                    disabled={pendingApiCall}
                  >
                    Cancel
                  </button>
                </div>
              </div>
            )}
            {!inEditMode && (
              <div className="col-2">
                <div className="row mt-5">
                  <button
                    className="btn btn-outline-success"
                    onClick={() => setInEditMode(true)}
                  >
                    Edit
                  </button>
                </div>
                <div className="row mt-3">
                  <button className="btn btn-outline-danger">Delete</button>
                </div>
              </div>
            )}
          </div>
        </form>
      </div>
    </div>
  );
};

export default NoteDetailPage;
