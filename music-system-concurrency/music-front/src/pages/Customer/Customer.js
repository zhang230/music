import React, { Component } from 'react';
import { Layout, Button  } from 'antd';
import Aside from './components/Aside'

import './Customer.css'
import PrivateRoute from '../../routes/PrivateRoute';

import { Switch } from 'react-router-dom/cjs/react-router-dom.min';
import Events from './components/Events';
import BookingEvents from './components/BookingEvents';
import { withRouter} from 'react-router-dom';

const { Header, Sider, Content} = Layout;




class Customer extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  signOut() {
    sessionStorage.removeItem("user");
    this.props.history.push('/login');
  }
  render() {
    return (

      <Layout className='layout-wrap'>
        <Sider className="layout-menu"><Aside/></Sider>
        <Layout>
          <Header className="layout-header">
          <Button className="signout" type="primary" onClick={this.signOut.bind(this)}>
            Sign out  {JSON.parse(sessionStorage.getItem("user")).userName}
          </Button>
          </Header>
          <Content className="layout-content">
            <Switch>
              <PrivateRoute exact path="/customer/Events" component={Events}></PrivateRoute>
              <PrivateRoute exact path="/customer/BookingEvents" component={BookingEvents}></PrivateRoute>
            </Switch>
          </Content>
        </Layout>
      </Layout>

    )
  }
  
}

export default withRouter(Customer);
