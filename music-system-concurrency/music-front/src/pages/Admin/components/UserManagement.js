import React, { Component } from 'react';
import { Table, Modal } from 'antd';
import './UserManagement.css'

import $ from 'jquery'


class UserManagement extends Component {
  constructor(props) {
    super(props);
    this.state = {
      columns : [
        {
          title: 'UserName',
          dataIndex: 'UserName',
          key: '1',
        },
        {
          title: 'Password',
          dataIndex: 'Password',
          key: '2',
        },
        {
          title: 'Name',
          dataIndex: 'Name',
          key: '3',
        },
        {
          title: 'Age',
          dataIndex: 'Age',
          key: '4',
        },
        {
          title: 'Gender',
          dataIndex: 'Gender',
          key: '5',
        },
        {
          title: 'Email',
          dataIndex: 'Email',
          key: '6',
        },
        {
          title: 'Role',
          dataIndex: 'Role',
          key: '7',
        },
        // {
        //   title: 'Action',
        //   key: 'operation',
        //   fixed: 'right',
        //   width: 100,
        //   // render: () => <a>action</a>,
        // },
      ],
      data : [
        // {
        //         UserName: 'John Brown',
        //         Password: '123456',
        //         Name: 'zhangsan',
        //         Age: 22,
        //         Gender: 'man',
        //         Email: '1804@qq.com',
        //         Role: 0
        //       }
      ]
    };
    let _this = this;
    $.ajax({
      url: 'http://127.0.0.1:8080/music_war_exploded/adminFindAllUsers',
      method: 'GET',
      success: function (tdata) {
        // console.log(tdata);
        let i = 0;
        let tempData = [];
        for (i = 0; i < tdata.length; i++) {
            let temp = {
              UserName: '',
                Password: '',
                Name: '',
                Age: 0,
                Gender: '',
                Email: '',
                Role: 0
            };
            temp.UserName = tdata[i].userName;
            temp.Password = tdata[i].userPwd;
            temp.Name = tdata[i].name;
            temp.Age = tdata[i].age;
            temp.Gender = tdata[i].gender;
            temp.Email = tdata[i].email;
            if (tdata[i].role === 0) {
              temp.Role = 'Admin';
            } else if (tdata[i].role === 1) {
              temp.Role = 'Event Planner';
            } else if (tdata[i].role === 2) {
              temp.Role = 'Customer';
            }
            tempData.push(temp);
            
        }
        _this.setState({
          data: tempData
        });
        // 在这里处理数据
      },
      error: function (error) {
        Modal.error({
          title: 'Server Error',
          content: 'please try again！',
        });
      }
    });


    
    
    
  }
  render() {
    return (
        <div>
            <Table
            columns={this.state.columns}
            dataSource={this.state.data}
            scroll={{
              x: 1300,
            }}
          />
        </div>

    )
  }
  
}

export default UserManagement
