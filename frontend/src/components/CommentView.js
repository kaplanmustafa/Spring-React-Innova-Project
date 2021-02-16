import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { deleteComment, updateComment } from "../api/ApiCalls";
import { useApiProgress } from "../shared/ApiProgress";
import ButtonWithProgress from "./toolbox/ButtonWithProgress";
import DateConverter from "./toolbox/DateConverter";
import Modal from "./toolbox/Modal";

const CommentView = (props) => {
  const { comment, commentId, timestamp, onChange } = props;
  const [inEditMode, setInEditMode] = useState(false);
  const [updatedComment, setUpdatedComment] = useState("");
  const [modalVisible, setModalVisible] = useState(false);

  const { username } = useSelector((store) => ({
    username: store.username,
  }));

  const pendingDeleteCommentApiCall = useApiProgress(
    "delete",
    `/api/1.0/comments/${commentId}`,
    true
  );
  const pendingSaveCommentApiCall = useApiProgress(
    "put",
    `/api/1.0/comments/${username}/${commentId}`
  );
  const buttonEnabled = comment !== updatedComment;

  useEffect(() => {
    setUpdatedComment(comment);
  }, [comment]);

  const onClickSaveComment = async () => {
    const body = {
      comment: updatedComment,
    };

    try {
      const response = await updateComment(username, commentId, body);
      setInEditMode(false);
      onChange();
    } catch (error) {}
  };

  const onClickDelete = async () => {
    await deleteComment(commentId);
    setModalVisible(false);
    onChange();
  };

  const onClickCancelModal = () => {
    setModalVisible(false);
  };

  const onClickCancel = () => {
    setInEditMode(false);
    setUpdatedComment(comment);
  };

  return (
    <div className="card container mt-4">
      <div className="row">
        <DateConverter timestamp={timestamp} horizontal={true} />
        <textarea
          className="form-control bg-white"
          value={updatedComment}
          disabled={!inEditMode}
          rows={5}
          maxLength={255}
          onChange={(event) => {
            setUpdatedComment(event.target.value);
          }}
        ></textarea>
      </div>
      {inEditMode && (
        <div className="container text-right mt-1">
          <ButtonWithProgress
            className="btn btn-outline-success mr-2"
            onClick={onClickSaveComment}
            text="Save"
            disabled={pendingSaveCommentApiCall || !buttonEnabled}
            pendingApiCall={pendingSaveCommentApiCall}
          />
          <button
            className="btn btn-outline-danger"
            onClick={onClickCancel}
            disabled={pendingSaveCommentApiCall}
          >
            Cancel
          </button>
        </div>
      )}
      {!inEditMode && (
        <div className="container text-right">
          <button
            className="btn btn-outline-success mr-2"
            onClick={() => setInEditMode(true)}
          >
            Edit
          </button>
          <button
            className="btn btn-outline-danger mt-1 mb-1"
            onClick={(event) => {
              event.preventDefault();
              setModalVisible(true);
            }}
          >
            Delete
          </button>
        </div>
      )}
      <Modal
        title={"Delete Comment"}
        visible={modalVisible}
        onClickCancel={onClickCancelModal}
        onClickOk={onClickDelete}
        okButton={"Delete Comment"}
        message={"Are you sure to delete this comment?"}
        pendingApiCall={pendingDeleteCommentApiCall}
      />
    </div>
  );
};

export default CommentView;
