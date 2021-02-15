import React from "react";

const CommentView = (props) => {
  const { comment } = props;

  return (
    <div className="card container mt-2">
      <div className="row">
        <textarea
          className="form-control bg-white"
          value={comment}
          disabled={true}
          rows={5}
        ></textarea>
      </div>
      <div className=" container text-right">
        <button className="btn btn-outline-danger mt-1 mb-1">Delete</button>
      </div>
    </div>
  );
};

export default CommentView;
