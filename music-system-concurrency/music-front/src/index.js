import React from 'react';
import ReactDOM from 'react-dom/client';
import "antd/dist/antd.css"

import './index.css';
// import App from './App';
import reportWebVitals from './reportWebVitals';
import { HashRouter as Router, Switch, Route } from 'react-router-dom';
import Login from './pages/Login/Login';
import Admin from './pages/Admin/Admin';
import Customer from './pages/Customer/Customer';
import EventPlanner from './pages/EventPlanner/EventPlanner';
import Register from './pages/Register/Register';
import PrivateRoute from './routes/PrivateRoute';

global.AdminChange='';
const isAuthenticated = sessionStorage.getItem("user") !== null;

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Router>
      <Switch>
        <Route path="/register" component={Register} />
        <Route path="/login" component={Login} />
        <PrivateRoute
          path="/admin"
          component={Admin}
          isAuthenticated={isAuthenticated}
        />
        <PrivateRoute
          path="/customer"
          component={Customer}
          isAuthenticated={isAuthenticated}
        />
        <PrivateRoute
          path="/eventplanner"
          component={EventPlanner}
          isAuthenticated={isAuthenticated}
        />
        <Route path="/" component={Login} />
      </Switch>
  </Router>
);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
