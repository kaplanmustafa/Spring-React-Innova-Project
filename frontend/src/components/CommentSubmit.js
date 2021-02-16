import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { saveComment } from "../api/ApiCalls";
import { useApiProgress } from "../shared/ApiProgress";
import ButtonWithProgress from "./toolbox/ButtonWithProgress";

const CommentSubmit = (props) => {
  const [focused, setFocused] = useState(false);
  const [comment, setComment] = useState("");

  const { onChange } = props;

  useEffect(() => {
    if (!focused) {
      setComment("");
    }
  }, [focused]);

  const { noteId } = useParams();

  const pendingApiCall = useApiProgress(
    "post",
    `/api/1.0/comments/${noteId}`,
    true
  );

  const onClickComment = async () => {
    const body = {
      comment,
    };

    try {
      await saveComment(body, noteId);
      setFocused(false);
      onChange();
    } catch (error) {}
  };

  let textAreaClass = "form-control";

  return (
    <div className="container mt-3 mb-2">
      <div className="card p-1 flex-row col-10">
        <div className="flex-fill">
          <textarea
            className={textAreaClass}
            rows={focused ? "3" : "1"}
            onFocus={() => setFocused(true)}
            placeholder={"Comment..."}
            onChange={(event) => setComment(event.target.value)}
            value={comment}
          />
          {focused && (
            <>
              <div className="text-right mt-1">
                <ButtonWithProgress
                  className="btn btn-primary"
                  onClick={onClickComment}
                  text="Comment"
                  disabled={pendingApiCall}
                  pendingApiCall={pendingApiCall}
                />
                <button
                  className="btn btn-danger d-inline-flex ml-1"
                  onClick={() => setFocused(false)}
                  disabled={pendingApiCall}
                >
                  Cancel
                </button>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default CommentSubmit;
