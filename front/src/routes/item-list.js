import React, { Fragment } from 'react';
import axios from 'axios';
import './item-list.css';
import Header from '../components/header';
import List from '../components/list';

class ItemList extends React.Component {

  state = {    
    isLoading: true,
    items: []
  }

  getPostListItem = async() =>{    
    // const {data : {data : {items}}} = await axios.get("https://yts.mx/api/v2/list_movies.json?sort_by=rating");
    const {data: {items}} = await axios.get("http://ec2-3-36-83-107.ap-northeast-2.compute.amazonaws.com:8080/api/items");
    this.setState({items:items, isLoading:false});
  }

  async componentDidMount(){
    this.getPostListItem();
  }

  render()
  {
    const {items} = this.state;

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

              {items.map(item => (
                <List 
                  key={item.itemId}
                  itemId={item.id}
                  itemNameear={item.itemName}
                  itemDescription={item.itemDescription}
                  itemCategory={item.itemCategory}
                  itemPrice={item.itemPrice}
                  itemLocation={item.itemLocation}
                />
              ))}
            </div>
          </div>
        </section>
      </Fragment>
    )
  }
}

export default ItemList;