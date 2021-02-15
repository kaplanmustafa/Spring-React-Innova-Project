import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { getNotes, getOldNotes } from "../api/ApiCalls";
import NoteFeed from "../components/NoteFeed";
import ButtonWithProgress from "../components/toolbox/ButtonWithProgress";
import Spinner from "../components/toolbox/Spinner";
import { useApiProgress } from "../shared/ApiProgress";

const UserPage = () => {
  const [page, setPage] = useState({
    content: [],
  });

  const [notFound, setNotFound] = useState(false);

  const { username } = useSelector((store) => ({
    username: store.username,
  }));

  let lastId = 0;
  if (page.content.length > 0) {
    const lastIndex = page.content.length - 1;
    lastId = page.content[lastIndex].id;
  }

  useEffect(() => {
    loadNotes(username);
  }, [username]);

  const loadNotes = async (username) => {
    try {
      const response = await getNotes(username);
      setPage(response.data);
    } catch (error) {
      setNotFound(true);
    }
  };

  const loadOldNotes = async () => {
    try {
      const response = await getOldNotes(lastId, username);
      setPage((previousPage) => ({
        ...response.data,
        content: [...previousPage.content, ...response.data.content],
      }));
    } catch (error) {}
  };

  const { content, last, empty } = page;

  const loadOldNotesProgress = useApiProgress(
    "get",
    `/api/1.0/users/${username}/notes/${lastId}`
  );

  if (notFound) {
    return (
      <div className="container mt-5">
        <div className="alert alert-danger text-center p-5">
          <h1>Failed to Load Notes!</h1>
        </div>
      </div>
    );
  }

  if (last === undefined) {
    return <Spinner />;
  }

  return (
    <div className="container mt-5">
      <NoteFeed notes={content} />

      {!last && (
        <div className="container text-center mt-5">
          <ButtonWithProgress
            disabled={loadOldNotesProgress}
            onClick={loadOldNotes}
            text={"Load More"}
            pendingApiCall={loadOldNotesProgress}
            className={"btn btn-primary w-25"}
          ></ButtonWithProgress>
        </div>
      )}
      {empty && (
        <div className="container mt-5">
          <div className="alert alert-danger text-center">
            <h2>No Notes Yet!</h2>
            <Link to={"/addNote"}>
              <button className="btn btn-primary mt-4">New Note</button>
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserPage;
