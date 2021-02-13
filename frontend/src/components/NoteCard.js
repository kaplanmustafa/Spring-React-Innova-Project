import React from "react";
import { Link } from "react-router-dom";

const NoteCard = (props) => {
  const { id, title, date, time } = props.note;

  return (
    <div>
      <div className="container card text-center">
        <div className="card-body">
          <h5 className="card-title">{title ? title : "No Title"}</h5>
          <p className="card-text">{date}</p>
          <p className="card-text">{time}</p>
          <Link to={`/note/${id}`} className="btn btn-warning">
            Open
          </Link>
        </div>
      </div>
    </div>
  );
};

export default NoteCard;
