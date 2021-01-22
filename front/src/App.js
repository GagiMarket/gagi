import React from 'react';
import './App.css';
import './reset.css';
import './grid.min.css';
import {HashRouter, Route} from "react-router-dom";

import Home from "./routes/home";
import Post from "./routes/post";
import ItemList from "./routes/item-list";
import Detail from "./routes/item-detail";
import Login from "./routes/login";

class App extends React.Component {
  
  render()
  {
    return (
      <HashRouter>
        <Route path="/" exact={true} component={Home}/>
        <Route path="/post" component={Post}/>
        <Route path="/login" component={Login}/>
        <Route path="/itemlist" exact={true} component={ItemList}/>
        <Route path="/itemlist/detail/:itemId" component={Detail}/>
      </HashRouter>
    )
  }
}

export default App;
