import React, { Fragment } from 'react';
import './item-list.css';
import Header from '../components/header';
import List from '../components/list';

class ItemList extends React.Component {

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
        <Header/>
        {/* 등록된 물품 리스트 섹션 */}
        <section className="item-list">
          <div className="container">
            <div className="row">

              <div className="col-12">
                <h1 className="list-title">등록된 상품 보기</h1>
              </div>     

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
          </div>
        </section>
      </Fragment>
    )
  }
}

export default ItemList;