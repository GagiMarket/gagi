import React from 'react';
import './item-detail.css'
import Header from '../components/header';

import eggplant from '../assets/free-icon-eggplant-1147561.svg'

function Detail({itemId, itemName, itemImage, itemPrice}){
  return (
    <>
      <Header/>
      <div class="container">
        <div className="row">
          <section class="col-12 product">
            <div class="product-image-viewer">
              <div class="main-image">
                <img
                  src={eggplant}
                  // srcset="./assets/img-product-1.jpg 1x, ./assets/img-product-1@2x.jpg 2x"
                  alt=""
                />
              </div>
              {/* <ul class="view-list">
                <li class="view-item selected">
                  <button type="button">
                    <img
                      src="./assets/img-product-1.jpg"
                      srcset="./assets/img-product-1.jpg 1x, ./assets/img-product-1@2x.jpg 2x"
                      alt=""
                    />
                  </button>
                </li>
                <li class="view-item">
                  <button type="button">
                    <img
                      src="./assets/img-product-1.jpg"
                      srcset="./assets/img-product-2.jpg 1x, ./assets/img-product-2@2x.jpg 2x"
                      alt=""
                    />
                  </button>
                </li>
                <li class="view-item">
                  <button type="button">
                    <img
                      src="./assets/img-product-1.jpg"
                      srcset="./assets/img-product-3.jpg 1x, ./assets/img-product-3@2x.jpg 2x"
                      alt=""
                    />
                  </button>
                </li>
              </ul> */}
            </div>

            <div class="product-content">
              <header class="product-header">
                <h1 class="product-title">
                  맛있는 가지 1.5kg            
                </h1>
              </header>

              <strong class="product-price">
                13,000 원
              </strong>

              <p class="product-desc">
                아주 맛있는 가지가 1.5kg에 단돈 13000!! 이보다 파격적인 가격은 더이상 없다.
                아주 맛있는 가지가 1.5kg에 단돈 13000!! 이보다 파격적인 가격은 더이상 없다.
              </p>

              <dl class="product-detail">
                <div>
                  <dt>
                    카테고리
                  </dt>
                  <dd>
                    식품
                  </dd>
                </div>
                <div>
                  <dt>
                    지역
                  </dt>
                  <dd>
                    서울
                  </dd>
                </div>
                <div>
                  <dt>
                    택배비
                  </dt>
                  <dd>
                    미포함
                  </dd>
                </div>
              </dl>

              <form action="" method="POST" class="product-form">
                <div class="form-options">
                  <div class="form-group form-quantity">
                    <label for="quantity">
                      수량
                    </label>
                    <div class="combo-box">
                      <button type="button" id="minus-button" aria-label="Add"></button>
                      <input type="number" name="quantity" id="quantity" min="0" max="10" value="1" />
                      <button type="button" id="plus-button" aria-label="Remove"></button>
                    </div>
                  </div>

                  <div class="form-group form-size">
                    <label for="size">
                      옵션
                    </label>
                    <div class="radio-buttons">
                      <div class="radio-button">
                        <input type="radio" name="size" id="uk-9" />
                        <label for="uk-9">
                          1.5kg
                        </label>
                      </div>
                      <div class="radio-button">
                        <input type="radio" name="size" id="uk-10" />
                        <label for="uk-10">
                          4kg
                        </label>
                      </div>
                      <div class="radio-button">
                        <input type="radio" name="size" id="uk-11" />
                        <label for="uk-11">
                          10kg
                        </label>
                      </div>
                    </div>
                  </div>
                </div>

                <button type="submit" class="form-submit">
                  주문하기
                </button>
              </form>
            </div>
          </section>
        </div>
      </div>
    </>
  )
}

export default Detail;
