import React from "react";

const AddNotePage = () => {
  return (
    <div className="container">
      <div className="container">
        <form>
          <div className="row">
            <div className="col-10 form-group">
              <label>Title</label>
              <textarea className="form-control" rows="1"></textarea>
            </div>
          </div>
          <div className="row">
            <div className="col-10 form-group">
              <label>Your Note</label>
              <textarea className="form-control" rows="15"></textarea>
            </div>
            <div className="col-2">
              <div className="row mt-5">
                <button className="btn btn-outline-success">Save</button>
              </div>
              <div className="row mt-3">
                <button className="btn btn-outline-danger">Cancel</button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddNotePage;
