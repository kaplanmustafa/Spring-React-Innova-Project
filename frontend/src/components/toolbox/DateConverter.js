import React from "react";

const DateConverter = (props) => {
  const { timestamp, horizontal } = props;

  const date = new Date(timestamp);

  let noteDate = "";
  if (date.getDate < 10) {
    noteDate += "0";
  }
  noteDate += date.getDate() + "/";
  if (date.getMonth() < 10) {
    noteDate += "0";
  }
  noteDate += date.getMonth() + "/";
  noteDate += date.getFullYear();

  let noteTime = "";
  if (date.getHours < 10) {
    noteTime += "0";
  }
  noteTime += noteTime = date.getHours() + ":";
  if (date.getMinutes() < 10) {
    noteTime += "0";
  }
  noteTime += date.getMinutes() + ":";
  if (date.getSeconds() < 10) {
    noteTime += "0";
  }
  noteTime += date.getSeconds();

  if (horizontal) {
    return (
      <div>
        <p className="card-text">
          {noteDate} {noteTime}
        </p>
      </div>
    );
  }

  return (
    <div>
      <p className="card-text">{noteDate}</p>
      <p className="card-text">{noteTime}</p>
    </div>
  );
};

export default DateConverter;
