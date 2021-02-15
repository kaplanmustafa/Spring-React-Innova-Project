import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getComments } from "../api/ApiCalls";
import CommentView from "./CommentView";

const Comments = () => {
  const [page, setPage] = useState({
    content: [],
  });

  const { noteId } = useParams();

  useEffect(() => {
    loadComments(noteId);
  }, [noteId]);

  const loadComments = async (noteId) => {
    try {
      const response = await getComments(noteId);
      setPage(response.data);
    } catch (error) {}
  };

  const { content } = page;

  return (
    <div className="col-10 mb-5">
      {content.map((item) => {
        return (
          <CommentView
            key={item.id}
            comment={item.comment}
          />
        );
      })}
    </div>
  );
};

export default Comments;
