import React, { Component } from 'react';
import { Button, Form, Input, Select, Modal} from 'antd';

import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { withRouter} from 'react-router-dom';

import './Register.css'
import $ from 'jquery'
const { Option } = Select;

class Register extends Component {
    constructor(props) {
        super(props);
        // 创建状态来存储输入框的值
        this.state = {
          username: '',
          userpwd: '',
          name: '',
          age: '',
          gender: '',
          email: '',
          role: '',
          data: ''
        };
        
      }
    
      // 处理输入框值的变化
      handleInputChange = (e) => {
        const { name, value } = e.target;
        this.setState({ [name]: value });
      }
    
      handleSelectInputChange = (value) => {
        this.setState({ role: value });
      }
      // 处理提交事件
      handleSubmit = () => {
        const { username, userpwd, name, age, gender, email, role } = this.state;

        // console.log(username+":"+userpwd+":"+age+":"+gender+":"+email+":"+role);

        let _this = this;
        
        // 进行其他处理或者发起登录请求
        $.ajax({
          url: 'http://127.0.0.1:8080/music_war_exploded/register',
          method: 'GET',
          data: {
            user_name: username,
            user_pwd: userpwd,
            name: name,
            age: age,
            gender: gender,
            email: email,
            role: role
          },
          success: function (tdata) {
            // console.log(tdata);
            if (tdata !== undefined) {
              Modal.success({
                title: 'Register Success',
                content: 'Return to Login',
                className: 'custom-error-modal', // 自定义类名
              });
              _this.props.history.push('/login');
            } else {
              Modal.error({
                title: 'Register Fail',
                content: 'please try again！',
                className: 'custom-error-modal', // 自定义类名
              });
            }
            // 在这里处理数据
          },
          error: function (error) {
            Modal.error({
                title: 'Register Fail',
                content: 'account repeat please try again！',
                className: 'custom-error-modal', // 自定义类名
              });
          }
        });
      }
    
      resetValues = () => {
        this.setState({
            username: '',
            userpwd: '',
            name: '',
            age: '',
            gender: '',
            email: '',
            role: ''
        })
      }
    render(){
        return (
            <div className="registerpage">
                <h1 className="register-title">Register</h1>
                <div className="register">
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
                
                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your name!',
                    },
                  ]}
                >
                  <Input
                    id="name"
                    name="name"
                    className="name"
                    
                    placeholder="name"
                    value={this.state.name}
                    onChange={this.handleInputChange}
                  />
                </Form.Item>
                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your age!',
                    },
                  ]}
                >
                  <Input
                    id="age"
                    name="age"
                    className="age"
                    
                    placeholder="age"
                    value={this.state.age}
                    onChange={this.handleInputChange}
                  />
                </Form.Item>
                
                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your gender!',
                    },
                  ]}
                >
                  <Input
                    id="gender"
                    name="gender"
                    className="gender"
                    
                    placeholder="gender"
                    value={this.state.gender}
                    onChange={this.handleInputChange}
                  />
                </Form.Item>

                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your email!',
                    },
                  ]}
                >
                  <Input
                    id="email"
                    name="email"
                    className="email"
                    
                    placeholder="email"
                    value={this.state.email}
                    onChange={this.handleInputChange}
                  />
                </Form.Item>

                <Form.Item
                  rules={[
                    {
                      required: true,
                    },
                  ]}
                  className='role'
                >
                  <Select
                    id="role"
                    
                    placeholder="Select Role"
                    onChange={this.handleSelectInputChange}
                    allowClear
                    >
                    <Option value="1">Event Planner</Option>
                    <Option value="2">Customer</Option>
                    </Select>
                </Form.Item>


                <Form.Item>
                  <Button type="primary" htmlType="submit" className="register-form-submit-button" onClick={this.handleSubmit}>
                    Register
                  </Button>
                  <Button type="primary" htmlType="reset" className="register-form-reset-button" onClick={this.resetValues}>
                    Reset
                  </Button>
             
                </Form.Item>
              </Form>
            </div>
            </div>
          )
    }
}

export default withRouter(Register);
