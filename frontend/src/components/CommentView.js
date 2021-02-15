import React, { useState } from "react";
import { deleteComment } from "../api/ApiCalls";
import { useApiProgress } from "../shared/ApiProgress";
import DateConverter from "./toolbox/DateConverter";
import Modal from "./toolbox/Modal";

const CommentView = (props) => {
  const { comment, commentId, timestamp } = props;

  const [modalVisible, setModalVisible] = useState(false);

  const pendingDeleteCommentApiCall = useApiProgress(
    "delete",
    `/api/1.0/comments/${commentId}`,
    true
  );

  const onClickDelete = async () => {
    await deleteComment(commentId);
    setModalVisible(false);
  };

  const onClickCancelModal = () => {
    setModalVisible(false);
  };

  return (
    <div className="card container mt-4">
      <div className="row">
        <DateConverter timestamp={timestamp} horizontal={true} />
        <textarea
          className="form-control bg-white"
          value={comment}
          disabled={true}
          rows={5}
        ></textarea>
      </div>
      <div className="container text-right">
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
