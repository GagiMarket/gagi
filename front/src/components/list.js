import React from 'react';
import './list.css'

function List({itemId, itemName, itemImage, itemPrice}){
  return (
    <div className="col-12 col-md-3 list-item-div">
      <a className="list-item" href="www.naver.com">
        <div>
          <img 
            className="list-item-img" 
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTraEhh9z3iVmkBeMZAG7wnWVUr7ep9vE1Lmw&usqp=CAU" alt="아이폰 12"
          />
        </div>

        <div className="list-item-desc">
          <div className="list-item-name">
            맛있는 가지 1.5kg
          </div>
          <div className="list-item-price">
            13000 원
          </div>
        </div>
      </a>
    </div>
  )
}

export default List;
