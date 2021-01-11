import React from 'react';
import {Link} from "react-router-dom";
import './header.css'

function Header(){
  return (
    <header className="header-section">
      <div className="container">
        <div className="row">
          <div className="col-12 header">
            <div>
              <a className="header-title" href="/">가지마켓</a>
            </div>
            <div className="nav">
              <Link className="nav-btn" to="/">Home</Link>
              <Link className="nav-btn" to="/post">Post</Link>
              <Link className="nav-btn" to="/itemlist">ItemList</Link>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;