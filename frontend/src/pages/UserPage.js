import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getNotes, getOldNotes } from "../api/ApiCalls";
import NoteFeed from "../components/NoteFeed";

const UserPage = () => {
  const [page, setPage] = useState({
    content: [],
  });

  const { username } = useSelector((store) => ({
    username: store.username,
  }));

  let lastId = 0;
  let firstId = 0;
  if (page.content.length > 0) {
    firstId = page.content[0].id;

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
    } catch (error) {}
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

  const { content, last } = page;

  return (
    <div className="container">
      <NoteFeed notes={content} />

      {!last && (
        <div className="container text-center mt-5">
          <button className="btn btn-primary w-25" onClick={loadOldNotes}>
            Load More
          </button>
        </div>
      )}
    </div>
  );
};

export default UserPage;
