import React from 'react';
import './home.css';
import Header from '../components/header';
import {Link} from "react-router-dom";

import img1 from '../assets/free-icon-box-2898040.svg';
import img2 from '../assets/free-icon-add-cart-2727416.svg';
import img3 from '../assets/free-icon-help-1660114.svg';

class Home extends React.Component {

  render()
  {
    return (
      <>
        <Header/>

        <section className="section program">
          <div className="container">

            <div className="row justify-content-center">

              <div className="col-12 col-md-10">
                <strong className="section-category">
                  중고거래의 혁신
                </strong>
                <h1 className="section-title">
                  Welcome to<br/>
                  Gagi Market
                </h1>
                <p className="section-desc">
                  마음대로 물건을 등록하여 팔 수 있고,<br/>
                  마음가는 물건을 연락하여 살 수 있는 보라보라한 이 곳<br/>
                  가지마켓에 오신 것을 환영합니다 :) 회원가입 후 마음껏 즐기세요!
                </p>
              </div>

            </div>

            <div className="row">
              <div className="col-12 col-md-4">
                <Link to="/post" className="program-card">
                  <img 
                    src={img1} 
                    alt=""/>
                  <strong>안 쓰는 물건 정리하기</strong>
                </Link>
              </div>

              <div className="col-12 col-md-4">
                <Link to="/itemlist" className="program-card">
                  <img 
                    src={img2} 
                    alt=""/>
                  <strong>따끈따끈한 물건 보러가기</strong>
                </Link>
              </div>

              <div className="col-12 col-md-4">
                <Link to="/" className="program-card">
                  <img 
                    src={img3} 
                    alt=""/>
                  <strong>가지마켓 도우미 부르기</strong>
                </Link>
              </div>
            </div>
          </div>
        </section>

        <section className="service">
          <div className="container">
            <div className="row">

              <div className="col-12 col-md-6">
                  <h2 className="service-title">
                    안전한 거래 문화 만들기
                  </h2>
                  <p className="service-desc">
                    우리 가지마켓은 판매자 연락처를 안심번호로 제공하여 판매자의 개인정보 유출을 방지합니다!
                  </p>
              </div>

              <div className="col-12 col-md-6">
                <h2 className="service-title">
                  선택! 연락! 거래! 끝!
                </h2>
                <p className="service-desc">
                  마음에 드는 물건을 고르고 연락만 하면 거래 완료! 번거로운 절차가 없으면서 안전합니다!
                </p>
              </div>

            </div>
          </div>
        </section>

        <footer class="footer">
          <div class="container">
            <div class="row">
              <div class="col-12">
                <ul class="footer-links">
                  <li class="footer-link">
                    <a href="/">Terms</a>
                  </li>
                  
                  <li class="footer-link">
                    <a href="/">Privacy</a>
                  </li>
                  
                  <li class="footer-link">
                    <a href="/">License</a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </footer>
      </>
    )
  }
}

export default Home;