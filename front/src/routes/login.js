import React, { Fragment } from 'react';
import './login.css';
import Header from '../components/header';

class Login extends React.Component {

  render()
  {
    return (
      <Fragment>
        <Header/>
        <section className="login">
          <div className="container">
            <div className="row">

              <div className="col-12">
                
                <form className="login-form" action="" method="POST">
                  <h3>로그인</h3>

                  <input type="email" placeholder="example@naver.com"/>
                  <input type="password" placeholder="****"/>

                  <button type="submit">Log In</button>
                </form>
              </div>    
            </div>
          </div>
        </section>
      </Fragment>
    )
  }
}

export default Login;