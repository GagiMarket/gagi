import React, { Fragment } from 'react';
import './login.css';
import Header from '../components/header';

class Login extends React.Component {

  render()
  {
    return (
      <Fragment>
        <Header/>
        {/* 등록된 물품 리스트 섹션 */}
        <section className="login">
          <div className="container">
            <div className="row">

              <div className="col-12">
                

              </div>    
            </div>
          </div>
        </section>
      </Fragment>
    )
  }
}

export default Login;