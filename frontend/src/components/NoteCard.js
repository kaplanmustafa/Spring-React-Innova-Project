import React from "react";
import { Link } from "react-router-dom";
import DateConverter from "./toolbox/DateConverter";

const NoteCard = (props) => {
  const { id, title, timestamp } = props.note;

  return (
    <div>
      <div className="container card text-center">
        <div className="card-body">
          <h5 className="card-title">{title ? title : "No Title"}</h5>
          <DateConverter timestamp={timestamp} />
          <Link to={`/note/${id}`} className="btn btn-warning mt-2">
            Open
          </Link>
        </div>
      </div>
    </div>
  );
};

export default NoteCard;
