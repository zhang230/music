import React, { Component } from 'react';
import { Layout, Button  } from 'antd';
import Aside from './components/Aside'

import './EventPlanner.css'
import PrivateRoute from '../../routes/PrivateRoute';
import BookingManagement from './components/BookingManagement';
import EventManagement from './components/EventManagement';
import { Switch } from 'react-router-dom/cjs/react-router-dom.min';

import { withRouter} from 'react-router-dom';
const { Header, Sider, Content} = Layout;




class EventPlanner extends Component {
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
              <PrivateRoute exact path="/eventplanner/EventManagement" component={EventManagement}></PrivateRoute>
              <PrivateRoute exact path="/eventplanner/BookingManagement" component={BookingManagement}></PrivateRoute>
            </Switch>
          </Content>
        </Layout>
      </Layout>

    )
  }
  
}

export default withRouter(EventPlanner);
