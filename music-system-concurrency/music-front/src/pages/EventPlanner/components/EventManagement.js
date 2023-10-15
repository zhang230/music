import React, { Component } from 'react';

import { Button, Form, Input, Modal, Table} from 'antd';
// import { Link } from 'react-router-dom';
import './EventManagement.css'
import EventAdd from './EventAdd';
import EventEdit from './EventEdit';
import $ from 'jquery'
import moment from 'moment';

class EventManagement extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpenAdd: false,
      isOpenEdit: false,
      columns : [
        {
          title: 'Event Name',
          dataIndex: 'eventName',
          key: '1',
        },
        {
          title: 'Event Planner Name',
          dataIndex: 'eventPlannerName',
          key: '2',
        },
        {
          title: 'StartTime',
          dataIndex: 'startTime',
          key: '3',
        },
        {
          title: 'EndTime',
          dataIndex: 'endTime',
          key: '4',
        },
        {
          title: 'Last Modified',
          dataIndex: 'lastModifiedName',
          key: '5',
        },
        {
          title: 'Edit',
          key: 'Edit',
          fixed: 'right',
          width: 100,
          render: () => <a onClick={this.setEditTrue.bind(this)}>Edit</a>,
        },
        {
          title: 'Cancel',
          key: 'Cancel',
          fixed: 'right',
          width: 100,
          render: (text, record) => <a onClick={() => this.deleteRow(record)}>Cancel</a>,
        }
      ],
      data : []
    };

    let _this = this;
    // // 进行其他处理或者发起登录请求
    $.ajax({
      url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerFindAllEventsAndVenueInfo',
      method: 'GET',
      success: function (tdata) {
        // console.log(tdata)
        for(let i = 0; i < tdata.length; i++) {
          // 创建一个新的Date对象，传入时间戳作为参数
            let date = new Date(tdata[i].startTime);
            // 从Date对象中获取年、月、日、小时、分钟和秒
            let year = date.getFullYear();
            let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
            let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
            let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
            let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
            let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
            tdata[i].startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


            date = new Date(tdata[i].endTime);
            // 从Date对象中获取年、月、日、小时、分钟和秒
             year = date.getFullYear();
             month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
             day = date.getDate().toString().padStart(2, '0'); // 保证两位数
             hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
             minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
             seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
            tdata[i].endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        }
        _this.setState({
          data: tdata
        });
      },
      error: function (error) {
        Modal.error({
            title: 'Find Fail',
            content: 'try again',
            className: 'custom-error-modal', // 自定义类名
          });
      }
    });




  }
  handleDataFromEventAdd = (tdata) => {
    // console.log("this is main page");
    // console.log(tdata);
    
    for(let i = 0; i < tdata.length; i++) {
      // 创建一个新的Date对象，传入时间戳作为参数
        let date = new Date(tdata[i].startTime);
        // 从Date对象中获取年、月、日、小时、分钟和秒
        let year = date.getFullYear();
        let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
        let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
        let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
        let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
        let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
        tdata[i].startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


        date = new Date(tdata[i].endTime);
        // 从Date对象中获取年、月、日、小时、分钟和秒
         year = date.getFullYear();
         month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
         day = date.getDate().toString().padStart(2, '0'); // 保证两位数
         hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
         minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
         seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
        tdata[i].endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
    this.setState({
      data: tdata
    });
   
  }
  deleteRow(record) {
    // console.log(record);
    let _this = this;
    Modal.confirm({
      title: 'Notice',
      content: 'Sure delete this row?',
      className: 'custom-error-modal',
      onOk: () => {
        let _this = this;
    // 进行其他处理或者发起登录请求
      $.ajax({
        url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerCancelEvent',
        method: 'POST',
        data: {
          eventId: record.id,
          venue: JSON.stringify(record.venue)
        },
        success: function (tdata) {
          // console.log(tdata);
          if(tdata !== undefined) {
            for(let i = 0; i < tdata.length; i++) {
              // 创建一个新的Date对象，传入时间戳作为参数
                let date = new Date(tdata[i].startTime);
                // 从Date对象中获取年、月、日、小时、分钟和秒
                let year = date.getFullYear();
                let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
                let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
                let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
                let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
                let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
                tdata[i].startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    
    
                date = new Date(tdata[i].endTime);
                // 从Date对象中获取年、月、日、小时、分钟和秒
                 year = date.getFullYear();
                 month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
                 day = date.getDate().toString().padStart(2, '0'); // 保证两位数
                 hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
                 minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
                 seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
                tdata[i].endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
            }
            _this.setState({
              data: tdata
            });
              Modal.success({
                title: 'Prompt.',
                content: 'Edit Success.',
                className: 'custom-error-modal', // 自定义类名
            });
          }
        },
        error: function (error) {
          Modal.error({
              title: 'Add Fail',
              content: 'Time Conflict or Event Name Repeat, try again',
              className: 'custom-error-modal', // 自定义类名
            });
        }
      });
        // 关闭提示框
        Modal.destroyAll();
      },
      onCancel: () => {
        // 关闭提示框
        Modal.destroyAll();
      },
    });


  }
  setEditTrue() {
    this.setState({
      isOpenEdit: true
    });

  }

  setEditFalse() {
    this.setState({
      isOpenEdit: false
    });
    
  }
  setTrue() {
    this.setState({
      isOpenAdd: true
    });

  }

  setFalse() {
    this.setState({
      isOpenAdd: false
    });
    
  }
  
  handleRowClick = (record) => {
    // console.log(record);
    this.setState({
      selectedRowData: record, // 将所选行的数据保存在状态中
      // isOpenEdit: true, // 打开EventEdit模块
    });
  };


  render() {
    
    return (
      <div>
        
        <Button type="primary" onClick={this.setTrue.bind(this)}>
            Add Event
        </Button>
          <Modal
          id="Event"
          title="Event"
          centered
          visible={this.state.isOpenAdd}
          closable={false} // 禁用关闭按钮
          onOk={this.setFalse.bind(this)}
          // onCancel={this.setFalse.bind(this)}
          cancelButtonProps={{ hidden: true }} // Hide the cancel button
          width={1000}
          bodyStyle={{ maxHeight: '85%', overflowY: 'auto' }}
        >
          <EventAdd rowData={this.state.selectedRowData} onDataReceived={this.handleDataFromEventAdd}></EventAdd>
        </Modal>
        <Modal
          id="EventEdit"
          title="EventEdit"
          centered
          visible={this.state.isOpenEdit}
          closable={false} // 禁用关闭按钮
          onOk={this.setEditFalse.bind(this)}
          // onCancel={this.setEditFalse.bind(this)}
          cancelButtonProps={{ hidden: true }} // Hide the cancel button
          width={1000}
          bodyStyle={{ maxHeight: '85%', overflowY: 'auto' }}
        >
          <EventEdit rowData={this.state.selectedRowData} onDataReceived={this.handleDataFromEventAdd}></EventEdit>
        </Modal>
        <Table
          columns={this.state.columns}
          dataSource={this.state.data}
          scroll={{
            x: 1300,
          }}
          onRow={(record) => {
            return {
              onClick: () => this.handleRowClick(record), // 点击行时调用handleRowClick函数
            };
          }}
        />
      </div>
    )
  }
  
}

export default EventManagement
