import React from 'react';
import './post.css'

class Post extends React.Component {

  render()
  {

    return (
      <section className="post-item">
        <form className="container" action="" method="POST">
          <div className="row">

            <div className="col-12">
              <h1 className="post-item-title">상품 등록하기</h1>
            </div>     

            <div className="col-12">
              <div className="post-img">
                <label className="input-file-button" for="input-file">
                  상품 사진<br/>등록
                </label>
                <input type="file" id="input-file"/>
              </div>
            </div>
            
            <div className="col-12 col-md-6">
              <input className="item-name" type="text" name="itemName" id="" placeholder="상품 이름"/>
            </div>

            <div className="col-12 col-md-6">
              <select className="item-category" name="itemCategory" id="">
                <option value="의류">의류</option>
                <option value="전자기기">전자기기</option>
                <option value="식품">식품</option>
                <option value="악세서리">악세서리</option>
                <option value="기타">기타</option>
              </select>
            </div>

            <div className="col-12 col-md-6">
              <div className="item-price">
                <input className="item-price-input" type="text" name="itemPrice" id="" placeholder="가격"/>
                <input className="delivery-fee-chk" type="button" name="deliveryFee" value="택배비 포함"/>          
              </div>
            </div>

            <div className="col-12 col-md-6">
              <select className="item-location" name="itemLoacation" id="">
                <option value="서울">서울</option>
                <option value="서울">경기</option>
                <option value="서울">부산</option>
                <option value="서울">대구</option>
                <option value="서울">제주</option>
              </select>
            </div>
            
            <div className="col-12">        
              <input className="item-tag" type="text" name="itemTag" id="" placeholder="#태그"/>        
            </div>
            
            <div className="col-12">        
              <textarea className="item-desc" name="itemDescription" id="" cols="30" rows="10" placeholder="상품 설명 (200자 이내)"></textarea>
            </div>

            <div className="col-12">
              <button className="post-btn" type="submit">등록 완료</button>
            </div>

          </div>

        </form>
      </section>
    )
  }
}

export default Post;