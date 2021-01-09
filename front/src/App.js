import React, { Fragment } from 'react';
import './App.css';
import './reset.css';
import './grid.min.css';

import Post from './components/post';
import List from './components/list';

class App extends React.Component {

  // state = {
  //   list-items: []
  // }

  // getPostListItem = async() =>{    
  //   const {data : {data : {items}}} = await axios.get("https://yts.mx/api/v2/list_movies.json?sort_by=rating");
  //   this.setState({list-items:items});
  // }

  // async componentDidMount(){
  //   this.getPostListItem();
  // }

  render()
  {
    // const {list-items} = this.state;

    return (
      <Fragment>
        {/* 물품 등록 섹션 */}
        <Post/>

        {/* 등록된 물품 리스트 섹션 */}
        <section className="item-list container">
          <div className="row">

              {/* {movies.map(movie => (
                <List 
                  key={movie.id}
                  id={movie.id}
                  year={movie.year}
                  title={movie.title}
                  summary={movie.summary}
                  poster={movie.medium_cover_image}
                  genres={movie.genres}
                />
              ))} */}
              
              <List/>
              <List/>
              <List/>
              <List/>
              <List/>
              <List/>
              <List/>
              <List/>
            </div>
        </section>
      </Fragment>
    )
  }
}

export default App;