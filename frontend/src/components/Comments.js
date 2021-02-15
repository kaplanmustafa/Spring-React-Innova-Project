import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router";
import { getComments, getOldComments } from "../api/ApiCalls";
import { useApiProgress } from "../shared/ApiProgress";
import CommentView from "./CommentView";
import ButtonWithProgress from "./toolbox/ButtonWithProgress";

const Comments = () => {
  const [page, setPage] = useState({
    content: [],
  });
  const [commentCount, setCommentCount] = useState();

  const { username } = useSelector((store) => ({
    username: store.username,
  }));

  const { noteId } = useParams();

  let lastId = 0;
  if (page.content.length > 0) {
    const lastIndex = page.content.length - 1;
    lastId = page.content[lastIndex].id;
  }

  useEffect(() => {
    loadComments();
  }, [noteId, commentCount]);

  useEffect(() => {
    let looper = setInterval(loadCommentsForDifference, 5000);

    return function cleanup() {
      clearInterval(looper);
    };
  }, [username]);

  const loadComments = async () => {
    try {
      const response = await getComments(noteId);
      setPage(response.data);
      setCommentCount(response.data.totalElements);
    } catch (error) {}
  };

  const loadCommentsForDifference = async () => {
    try {
      const response = await getComments(noteId);
      setCommentCount(response.data.totalElements);
    } catch (error) {}
  };

  const loadOldComments = async () => {
    try {
      const response = await getOldComments(lastId, noteId);
      setPage((previousPage) => ({
        ...response.data,
        content: [...previousPage.content, ...response.data.content],
      }));
    } catch (error) {}
  };

  const loadOldCommentsProgress = useApiProgress(
    "get",
    `/api/1.0/users/${noteId}/comments/${lastId}`
  );

  const { content, last } = page;

  return (
    <div className="col-10 mb-5">
      {content.map((item) => {
        return (
          <CommentView
            commentId={item.id}
            comment={item.comment}
            timestamp={item.timestamp}
            key={item.id}
          />
        );
      })}
      {!last && (
        <div className="container text-center mt-5">
          <ButtonWithProgress
            disabled={loadOldCommentsProgress}
            onClick={loadOldComments}
            text={"Load More"}
            pendingApiCall={loadOldCommentsProgress}
            className={"btn btn-primary w-25"}
          ></ButtonWithProgress>
        </div>
      )}
    </div>
  );
};

export default Comments;
