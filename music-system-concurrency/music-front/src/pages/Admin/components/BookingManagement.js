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
      isOpenAdd: false,
      isOpenEdit: false,
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
          title: 'TicketCount',
          render: (text, record) => record.userBookingEvent ? record.userBookingEvent.number : '', // 自定义渲染函数
          dataIndex: 'userBookingEvent.number',
          key: '5',
        },
        {
          title: 'See more',
          key: 'See more',
          fixed: 'right',
          width: 100,
          render: () => <a onClick={this.setEditTrue.bind(this)}>See more</a>,
        },
        // {
        //   title: 'See more',
        //   key: 'See more',
        //   fixed: 'right',
        //   width: 100,
        //   render: (text, record) => <a onClick={() => this.deleteRow(record)}>See more</a>,
        // }
      ],
      data : []
    };

    let _this = this;
    let user_id =  JSON.parse(sessionStorage.getItem("user")).id;
    // // 进行其他处理或者发起登录请求
    $.ajax({
      // url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerFindAllEventsAndVenueInfo',
      url: 'http://127.0.0.1:8080/music_war_exploded/findAllCustomerBookingEvents',
      data:{
        user_id,
      },  
      method: 'GET',
      success: function (tdata) {
        console.log(tdata)
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
    // let _this = this;
    // Modal.confirm({
    //   title: 'Notice',
    //   content: 'Sure delete this row?',
    //   className: 'custom-error-modal',
    //   onOk: () => {
    //     let _this = this;
    // 进行其他处理或者发起登录请求
      // $.ajax({
      //   url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerCancelEvent',
      //   method: 'POST',
      //   data: {
      //     eventId: record.id,
      //     venue: JSON.stringify(record.venue)
      //   },
      //   success: function (tdata) {
      //     // console.log(tdata);
      //     // if(tdata !== undefined) {
      //     //   for(let i = 0; i < tdata.length; i++) {
      //     //     // 创建一个新的Date对象，传入时间戳作为参数
      //     //       let date = new Date(tdata[i].startTime);
      //     //       // 从Date对象中获取年、月、日、小时、分钟和秒
      //     //       let year = date.getFullYear();
      //     //       let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
      //     //       let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
      //     //       let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
      //     //       let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
      //     //       let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
      //     //       tdata[i].startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    
    
      //     //       date = new Date(tdata[i].endTime);
      //     //       // 从Date对象中获取年、月、日、小时、分钟和秒
      //     //        year = date.getFullYear();
      //     //        month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
      //     //        day = date.getDate().toString().padStart(2, '0'); // 保证两位数
      //     //        hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
      //     //        minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
      //     //        seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
      //     //       tdata[i].endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      //     //   }
      //     //   _this.setState({
      //     //     data: tdata
      //     //   });
      //     //     Modal.success({
      //     //       title: 'Prompt.',
      //     //       content: 'Edit Success.',
      //     //       className: 'custom-error-modal', // 自定义类名
      //     //   });
      //     // }
      //   },
      //   error: function (error) {
      //     Modal.error({
      //         title: 'Add Fail',
      //         content: 'Time Conflict or Event Name Repeat, try again',
      //         className: 'custom-error-modal', // 自定义类名
      //       });
      //   }
      // });
      //   // 关闭提示框
      //   Modal.destroyAll();
      // },
      // onCancel: () => {
      //   // 关闭提示框
      //   Modal.destroyAll();
      // },
    // });


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
  searchByEventNameAndTime() {
    // console.log(this.state);
    let inputEventName = this.state.inputEventName;
    let inputStartTime = this.state.inputStartTime;
    let inputEndTime = this.state.inputEndTime;
    //search interface
    let _this = this;

    // $.ajax({
    //   url: 'http://127.0.0.1:8080/music_war_exploded/customerSearch',
    //   method: 'GET',
    //   data: {
    //     inputEventName,
    //     inputStartTime,
    //     inputEndTime
    //   },
    //   success: function (tdata) {
    //     // console.log(tdata);
    //     for(let i = 0; i < tdata.length; i++) {
    //       // 创建一个新的Date对象，传入时间戳作为参数
    //         let date = new Date(tdata[i].startTime);
    //         // 从Date对象中获取年、月、日、小时、分钟和秒
    //         let year = date.getFullYear();
    //         let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
    //         let day = date.getDate().toString().padStart(2, '0'); // 保证两位数
    //         let hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
    //         let minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
    //         let seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
    //         tdata[i].startTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;


    //         date = new Date(tdata[i].endTime);
    //         // 从Date对象中获取年、月、日、小时、分钟和秒
    //          year = date.getFullYear();
    //          month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
    //          day = date.getDate().toString().padStart(2, '0'); // 保证两位数
    //          hours = date.getHours().toString().padStart(2, '0'); // 保证两位数
    //          minutes = date.getMinutes().toString().padStart(2, '0'); // 保证两位数
    //          seconds = date.getSeconds().toString().padStart(2, '0'); // 保证两位数
    //         tdata[i].endTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    //     }
    //     _this.setState({
    //       data: tdata
    //     });
    //   },
    //   error: function (error) {
    //     Modal.error({
    //         title: 'Find Fail',
    //         content: 'try again',
    //         className: 'custom-error-modal', // 自定义类名
    //       });
    //   }
    // });
    
  }


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

  render() {
    const { Search } = Input;
    const { RangePicker } = DatePicker;
    return (
      <div>
        
        {/* <Input 
          className="eventName" 
          placeholder="eventName" 
          onChange={this.changeEventName.bind(this)}
          value={this.state.inputEventName}
          style={{ width: '300px' }}
          />
        <RangePicker
                    className="events-setime"
                    showTime={{ format: 'HH:mm' }}
                    format="YYYY-MM-DD HH:mm"
                    placeholder={['Start Time', 'End Time']}
                    onChange={this.timeOnChange}
                    // defaultValue={this.state.selectedTimes}
                    value={this.state.selectedTimes}
                  /> */}
        {/* <Button type="primary" onClick={this.searchByEventNameAndTime.bind(this)}>
            Search
        </Button>
        <Button type="primary" className="reset-btn" onClick={this.setEventNameAndTime.bind(this)}>
            Reset
        </Button> */}
          {/* <Modal
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
        </Modal> */}
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

export default BookingManagement
