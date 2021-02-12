import React, { useState } from "react";

const NoteDetailPage = () => {
  const [inEditMode, setInEditMode] = useState(false);

  return (
    <div className="container">
      <div className="container">
        <form>
          <div className="row">
            <div className="col-10 form-group">
              <label>Title</label>
              <textarea
                readOnly={!inEditMode}
                className="form-control"
                rows="1"
              >
                TITLE
              </textarea>
            </div>
          </div>
          <div className="row">
            <div className="col-10 form-group">
              <label>Your Note</label>
              <textarea
                readOnly={!inEditMode}
                className="form-control"
                rows="15"
              >
                CONTENT
              </textarea>
            </div>
            {inEditMode && (
              <div className="col-2">
                <div className="row mt-5">
                  <button className="btn btn-outline-success">Save</button>
                </div>
                <div className="row mt-3">
                  <button
                    className="btn btn-outline-danger"
                    onClick={() => setInEditMode(false)}
                  >
                    Cancel
                  </button>
                </div>
              </div>
            )}
            {!inEditMode && (
              <div className="col-2">
                <div className="row mt-5">
                  <button
                    className="btn btn-outline-success"
                    onClick={() => setInEditMode(true)}
                  >
                    Edit
                  </button>
                </div>
                <div className="row mt-3">
                  <button className="btn btn-outline-danger">Delete</button>
                </div>
              </div>
            )}
          </div>
        </form>
      </div>
    </div>
  );
};

export default NoteDetailPage;
