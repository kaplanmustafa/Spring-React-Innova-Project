import React from "react";
import NoteCard from "./NoteCard";

const NoteFeed = (props) => {
  const { notes } = props;

  return (
    <div className="row">
      {notes.map((note) => {
        return (
          <div className="col-4 mt-3" key={note.id}>
            <NoteCard note={note} />
          </div>
        );
      })}
    </div>
  );
};

export default NoteFeed;
