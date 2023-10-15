import React, { Component, Fragment } from 'react';
import './Aside.css'
import { Link } from 'react-router-dom';
import { Menu  } from 'antd';
// const { SubMenu } = Menu;
class Aside extends Component {
    constructor(props) {
        super(props);
        this.state = {};
      }

      componentDidMount() {
        // 找到第一个 Link 元素并触发点击
        const firstLink = document.getElementById('link1');
        if (firstLink) {
          firstLink.click();
        }
      }
  render() {
    return (
        <Fragment className="fragment">
            <h1 class="logo"><span class="logo-span">Customer</span></h1>
            <Menu
                theme='dark'
                mode="inline"
                defaultSelectedKeys={["1"]}
                defaultOpenKeys={['sub1']}
                style={{ height: '100%', borderRight: 0 }}
                >
                    <Menu.Item key="1">
                        <Link id="link1" to="/customer/Events"><span>Events</span></Link>
                    </Menu.Item>
                    <Menu.Item key="2">
                        <Link to="/customer/BookingEvents"><span>BookingEvents</span></Link>
                    </Menu.Item>
            </Menu>
        </Fragment>
    )
  }
  
}

export default Aside
