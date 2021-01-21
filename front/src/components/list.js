import React from 'react';
import {Link} from "react-router-dom";
import './list.css'

function List({itemId, itemName, itemDescription, itemCategory, itemLocation, itemPrice}){
  return (
      <div className="col-12 col-md-3 list-item-div">
        <Link to={{
          pathname: `/itemlist/detail/${itemId}`,
          state: {
            itemId: itemId,
            itemName: itemName,
            itemDescription: itemDescription,
            itemCategory: itemCategory,
            itemLocation: itemLocation,
            itemPrice: itemPrice
          }
        }}>
          <a className="list-item" href="www.naver.com">
            <div>
              <img 
                className="list-item-img" 
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTraEhh9z3iVmkBeMZAG7wnWVUr7ep9vE1Lmw&usqp=CAU" alt={itemName}
              />
            </div>
        
            <div className="list-item-desc">
              <div className="list-item-name">
                {itemName}
              </div>
              <div className="list-item-price">
                {itemPrice}
              </div>
            </div>
          </a>
        </Link>
      </div>
  )
}

export default List;
