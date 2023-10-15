import React, { Component } from 'react';

import { Button, Form, Input, Modal, Table, DatePicker} from 'antd';
// import { Link } from 'react-router-dom';
import './BookingManagement.css'
import EventAdd from './EventAdd';
import BookingEventEdit from './BookingEventEdit';
import $ from 'jquery'
import moment from 'moment';

class BookingManagement extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user_id:'',
      userBookingEvent: '',
      isOpenAdd: false,
      isOpenEdit: false,
      inputUserName: '',
      inputEventName: '',
      inputStartTime: '',
      inputEndTime: '',
      selectedTimes:'',
      columns : [
        {
          title: 'id',
          dataIndex: 'rowNumber',
          key: 'rowNumber',
          render: (text, record, index) => index + 1, // Generate row number
        },
        {
          title: 'Event Name',
          render: (text, record) => record.event ? record.event.eventName : '', // 自定义渲染函数
          key: '1',
        },
        {
          title: 'Event Planner Name',
          render: (text, record) => record.event ? record.event.eventPlannerName : '', // 自定义渲染函数
          key: '2',
        },
        {
          title: 'StartTime',
          render: (text, record) => record.event ? record.event.startTime : '', // 自定义渲染函数
          // dataIndex: 'event.startTime',
          key: '3',
        },
        {
          title: 'EndTime',
          render: (text, record) => record.event ? record.event.endTime : '', // 自定义渲染函数
          // dataIndex: 'event.endTime',
          key: '4',
        },
        {
          title: 'User Name',
          render: (text, record) => record.user ? record.user.userName : '', // 自定义渲染函数
          // dataIndex: 'user.userName',
          key: '5',
        },
        {
          title: 'User Email',
          render: (text, record) => record.user ? record.user.email : '', // 自定义渲染函数
          // dataIndex: 'user.email',
          key: '6',
        },
        {
          title: 'Venue Name',
          render: (text, record) => record.venue ? record.venue.venueName : '', // 自定义渲染函数
          // dataIndex: 'venue.venueName',
          key: '7',
        },
        {
          title: 'Venue Address',
          render: (text, record) => record.venue ? record.venue.venueAddress : '', // 自定义渲染函数
          // dataIndex: 'venue.venueAddress',
          key: '8',
        },
        {
          title: 'Section Name',
          render: (text, record) => record.venueSectionForEventplannerCreate ? record.venueSectionForEventplannerCreate.sectionName : '', // 自定义渲染函数
          // dataIndex: 'venueSectionForEventplannerCreate.sectionName',
          key: '9',
        },
        {
          title: 'Tickets Left',
          render: (text, record) => record.venueSectionForEventplannerCreate ? record.venueSectionForEventplannerCreate.capacity : '', // 自定义渲染函数
          // dataIndex: 'venueSectionForEventplannerCreate.capacity',
          key: '10',
        },
        {
          title: 'Price',
          render: (text, record) => record.venueSectionForEventplannerCreate ? record.venueSectionForEventplannerCreate.price : '', // 自定义渲染函数
          dataIndex: 'venueSectionForEventplannerCreate.price',
          key: '11',
        },
        {
          title: 'TicketCount',
          render: (text, record) => record.userBookingEvent ? record.userBookingEvent.number : '', // 自定义渲染函数
          dataIndex: 'userBookingEvent.number',
          key: '12',
        },
        {
          title: 'Capacity',
          render: (text, record) => record.venueSectionForEventplannerCreate ? record.venueSectionForEventplannerCreate.capacity + record.venueSectionForEventplannerCreate.addCapacity : '', // 自定义渲染函数
          dataIndex: 'venueSectionForEventplannerCreate.capacity',
          key: '12',
        },
        // {
        //   title: 'Cancel',
        //   key: 'Cancel',
        //   fixed: 'right',
        //   width: 100,
        //   render: () => <a onClick={this.setEditTrue.bind(this)}>Cancel</a>,
        // },
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
    let user_id =  JSON.parse(sessionStorage.getItem("user")).id;
    // // 进行其他处理或者发起登录请求
    $.ajax({
      // url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerFindAllEventsAndVenueInfo',
      url: 'http://127.0.0.1:8080/music_war_exploded/findEventPlannerCreateBookingEvents',
      data:{
        user_id,
      },  
      method: 'GET',
      success: function (tdata) {
        console.log(tdata)
        // moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss')
        // tdata[0].event.eventName;
        // console.log(tdata)
        for(let i = 0; i < tdata.length; i++) {
          // 创建一个新的Date对象，传入时间戳作为参数
            let date = new Date(tdata[i].event.startTime);
            // 从Date对象中获取年、月、日、小时、分钟和秒
            let year = date.getFullYear();
            let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
            let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
            let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
            let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
            let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
            tdata[i].event.startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


            date = new Date(tdata[i].event.endTime);
            // 从Date对象中获取年、月、日、小时、分钟和秒
             year = date.getFullYear();
             month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
             day = date.getDate().toString().padStart(2, '0'); // 保证两位数
             hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
             minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
             seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
            tdata[i].event.endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        }
        _this.setState({
          data: tdata
        },()=>{
          // console.log(_this.state.data);
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
        tdata[i].event.startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


        date = new Date(tdata[i].endTime);
        // 从Date对象中获取年、月、日、小时、分钟和秒
         year = date.getFullYear();
         month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
         day = date.getDate().toString().padStart(2, '0'); // 保证两位数
         hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
         minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
         seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
        tdata[i].event.endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
    this.setState({
      data: tdata
    });
   
  }
  deleteRow(record) {
    
    let _this = this;
    _this.setState({
      user_id: record.event.userId,
      userBookingEvent: record.userBookingEvent
    },()=>{
      // console.log(_this.state.user_id);
      // console.log(_this.state.userBookingEvent);
    });
    Modal.confirm({
      title: 'Notice',
      content: 'Sure Cancel this booking?',
      className: 'custom-error-modal',
      onOk: () => {
      $.ajax({
        url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerCancelOneBooking',
        method: 'POST',
        data: {
          user_id: _this.state.user_id,
          userBookingEvent: JSON.stringify(_this.state.userBookingEvent),

        },
        success: function (tdata) {
          // console.log(tdata);
          if(tdata !== undefined) {
            for(let i = 0; i < tdata.length; i++) {
              // 创建一个新的Date对象，传入时间戳作为参数
                let date = new Date(tdata[i].event.startTime);
                // 从Date对象中获取年、月、日、小时、分钟和秒
                let year = date.getFullYear();
                let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
                let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
                let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
                let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
                let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
                tdata[i].event.startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    
    
                date = new Date(tdata[i].event.endTime);
                // 从Date对象中获取年、月、日、小时、分钟和秒
                 year = date.getFullYear();
                 month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
                 day = date.getDate().toString().padStart(2, '0'); // 保证两位数
                 hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
                 minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
                 seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
                tdata[i].event.endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
            }
            _this.setState({
              data: tdata
            });
              Modal.success({
                title: 'Prompt.',
                content: 'Cancel Success.',
                className: 'custom-error-modal', // 自定义类名
            });
          }
        },
        error: function (error) {
          Modal.error({
              title: 'Cancel Fail',
              content: 'Cancel Fail, have customer booking, can not cancel!, try again',
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
  
  setEventNameAndTime() {
    this.setState({
      inputStartTime: '',
      inputEndTime: '',
      selectedTimes:[],
      inputEventName: ''
    });
  }
  // 处理RangePicker的onChange事件
  timeOnChange = (dates) => {
    let _this = this;
    if (dates && dates.length === 2) {
      const [start, end] = dates;
      this.setState({
        inputStartTime: moment(start).format('YYYY-MM-DD HH:mm:ss'),
        inputEndTime: moment(end).format('YYYY-MM-DD HH:mm:ss'),
        selectedTimes:[moment(start, 'YYYY-MM-DD HH:mm:ss'),moment(end, 'YYYY-MM-DD HH:mm:ss')],
      });
    } else {
      this.setState({
        inputStartTime: null,
        inputEndTime: null,
        // selectedTimes:[moment(start, 'YYYY-MM-DD HH:mm:ss'),moment(end, 'YYYY-MM-DD HH:mm:ss')],
      });
    }
  };

  changeEventName(event) {
    // 使用event.target.value获取输入框的当前值
    const inputEventName = event.target.value;
    // 更新eventName的状态
    this.setState({ inputEventName });
  }
  searchByEventNameAndTime() {
    // console.log(this.state);
    let inputUserName = this.state.inputUserName
    // console.log(inputUserName);
    if (inputUserName==='' || inputUserName===undefined) {
      let _this = this;
    let user_id =  JSON.parse(sessionStorage.getItem("user")).id;
    // // 进行其他处理或者发起登录请求
    $.ajax({
      // url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerFindAllEventsAndVenueInfo',
      url: 'http://127.0.0.1:8080/music_war_exploded/findEventPlannerCreateBookingEvents',
      data:{
        user_id,
      },  
      method: 'GET',
      success: function (tdata) {
        // console.log(tdata)
        // moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss')
        // tdata[0].event.eventName;
        // console.log(tdata)
        for(let i = 0; i < tdata.length; i++) {
          // 创建一个新的Date对象，传入时间戳作为参数
            let date = new Date(tdata[i].event.startTime);
            // 从Date对象中获取年、月、日、小时、分钟和秒
            let year = date.getFullYear();
            let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
            let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
            let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
            let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
            let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
            tdata[i].event.startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


            date = new Date(tdata[i].event.endTime);
            // 从Date对象中获取年、月、日、小时、分钟和秒
             year = date.getFullYear();
             month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
             day = date.getDate().toString().padStart(2, '0'); // 保证两位数
             hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
             minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
             seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
            tdata[i].event.endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        }
        _this.setState({
          data: tdata
        },()=>{
          // console.log(_this.state.data);
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
    } else {
         //search interface
      let _this = this;

      $.ajax({
        url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerSearchByUserName',
        method: 'GET',
        data: {
          inputUserName
        },
        success: function (tdata) {
          // console.log(tdata);
          for(let i = 0; i < tdata.length; i++) {
            // 创建一个新的Date对象，传入时间戳作为参数
              let date = new Date(tdata[i].event.startTime);
              // 从Date对象中获取年、月、日、小时、分钟和秒
              let year = date.getFullYear();
              let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
              let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
              let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
              let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
              let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
              tdata[i].event.startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


              date = new Date(tdata[i].event.endTime);
              // 从Date对象中获取年、月、日、小时、分钟和秒
              year = date.getFullYear();
              month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
              day = date.getDate().toString().padStart(2, '0'); // 保证两位数
              hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
              minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
              seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
              tdata[i].event.endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
          }
          _this.setState({
            data: tdata
          },()=>{
            // console.log(_this.state.data);
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
   
    
  }



  changeUserName(event) {
    // 使用event.target.value获取输入框的当前值
    const inputUserName = event.target.value;
    // 更新eventName的状态
    this.setState({ inputUserName });
  }
  render() {
    const { Search } = Input;
    const { RangePicker } = DatePicker;
    return (
      <div>
        <Input 
          className="userName" 
          placeholder="userName" 
          onChange={this.changeUserName.bind(this)}
          value={this.state.inputUserName}
          style={{ width: '300px' }}
          />
        <Button type="primary" onClick={this.searchByEventNameAndTime.bind(this)}>
            Search
        </Button>

        <Modal
          id="EventBook"
          title="EventBook"
          centered
          visible={this.state.isOpenEdit}
          closable={false} // 禁用关闭按钮
          onOk={this.setEditFalse.bind(this)}
          // onCancel={this.setEditFalse.bind(this)}
          cancelButtonProps={{ hidden: true }} // Hide the cancel button
          width={1000}
          bodyStyle={{ maxHeight: '85%', overflowY: 'auto' }}
        >
          <BookingEventEdit rowData={this.state.selectedRowData} onDataReceived={this.handleDataFromEventAdd}></BookingEventEdit>
        </Modal>
        <Table
          columns={this.state.columns}
          dataSource={this.state.data}
          scroll={{
            x: 1300,
          }}
          // onRow={(record) => {
          //   return {
          //     onClick: () => this.handleRowClick(record), // 点击行时调用handleRowClick函数
          //   };
          // }}
        />
      </div>
    )
  }
  
}

export default BookingManagement
