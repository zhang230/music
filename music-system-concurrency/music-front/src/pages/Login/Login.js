import React, { Component } from 'react';
import { Form, Input, Button, Modal } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { withRouter} from 'react-router-dom';

import './Login.css'
import $ from 'jquery'
import { Link } from 'react-router-dom/cjs/react-router-dom.min';




class Login extends Component {
  constructor(props) {
    super(props);
    // 创建状态来存储输入框的值
    this.state = {
      username: '',
      userpwd: '',
      data: ''
    };
  }

  // 处理输入框值的变化
  handleInputChange = (e) => {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }


  // 处理提交事件
  handleSubmit = () => {
    const { username, userpwd } = this.state;
    let _this = this;
    
    // 进行其他处理或者发起登录请求
    $.ajax({
      url: 'http://127.0.0.1:8080/music_war_exploded/login',
      method: 'GET',
      data: {
        user_name: username,
        user_pwd: userpwd
      },
      success: function (tdata) {
        // console.log("login");
        // console.log(tdata);
        sessionStorage.setItem("user", JSON.stringify(tdata));
        // console.log(sessionStorage.getItem("user"));
        if(tdata.role === 0) {
          Modal.success({
            title: 'Login Success',
            content: '',
            className: 'custom-error-modal', // 自定义类名
          });
          
          _this.props.history.push('/admin');
          
        } else if(tdata.role === 1){
          Modal.success({
            title: 'Login Success',
            content: '',
            className: 'custom-error-modal', // 自定义类名
          });
          _this.props.history.push('/eventplanner');

        } else if(tdata.role === 2){
          Modal.success({
            title: 'Login Success',
            content: '',
            className: 'custom-error-modal', // 自定义类名
          });
          _this.props.history.push('/customer');
        } else {
          Modal.error({
            title: 'Login Fail',
            content: 'UserName or PassWord not right，please try again！',
            className: 'custom-error-modal', // 自定义类名
          });
        }
        // 在这里处理数据
      },
      error: function (error) {
        Modal.error({
          title: 'Login Fail',
          content: 'UserName or PassWord not right，please try again！',
          className: 'custom-error-modal', // 自定义类名
        });
      }
    });
  }

  resetValues = () => {
    this.setState({
      username: '',
      userpwd: '',
    })
  }

  render() {
    return (
      <div className="loginpage">
      <h1 className="login-title">Book Your Music Moment Here!</h1>
      <div className="login">
        <Form name="normal_login" className="login-form">
        <Form.Item
          rules={[
            {
              required: true,
              message: 'Please input your Username!',
            },
          ]}
        >
          <Input
            id="username"
            name="username"
            className="username"
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Username"
            value={this.state.username}
            onChange={this.handleInputChange}
          />
        </Form.Item>
        <Form.Item
          rules={[
            {
              required: true,
              message: 'Please input your Password!',
            },
          ]}
        >
          <Input
            id="userpwd"
            name="userpwd"
            className="userpwd"
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
            value={this.state.userpwd}
            onChange={this.handleInputChange}
          />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" className="login-form-submit-button" onClick={this.handleSubmit}>
            Log in
          </Button>
          <Button type="primary" htmlType="reset" className="login-form-reset-button" onClick={this.resetValues}>
            Reset
          </Button>
          &nbsp;Or <Link to="/Register">register now!</Link>
        </Form.Item>
      </Form>
      </div>
      </div>
    );
  }
}

export default withRouter(Login);
