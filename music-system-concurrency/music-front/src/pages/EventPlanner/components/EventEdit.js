import React, { Component } from 'react';

import { Button, Form, Input, Modal, Select, DatePicker } from 'antd';
import moment from 'moment';
// import { Link } from 'react-router-dom';
import './EventEdit.css'
import $ from 'jquery'




class EventEdit extends Component {
  constructor(props) {
    super(props);
    let _this = this;
    this.state = {
      selectedTimes:[moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss'),moment(_this.props.rowData.endTime, 'YYYY-MM-DD HH:mm:ss')],
      selectedVenue: '',
      eventId:'',
      eventName: '',
      startTime:'',
      endTime: '',
      venue: [{ //记录活动添加时候的场地列表数据
        id: '',
        venueName: '',
        venueAddress: '',
        venueSectionList: [{
          id: '',
          price: '',
          sectionName:'',
          venueId: '',
          capacity:'',
          delete: ''
        }],
      }]
    };
  }
  componentDidMount() {
    let rowData = this.props.rowData;
    // console.log(rowData);
    // console.log(rowData.venue);
    // console.log(typeof(rowData));
    let _this = this;
    // console.log(_this.props.rowData.startTime);
    //     console.log(_this.props.rowData.endTime);
    this.setState({
      eventId: rowData.id,
      eventName: rowData.eventName,
      startTime: rowData.startTime,
      endTime: rowData.endTime,
      venue: [rowData.venue],
      selectedVenue: rowData.venue.id,
      selectedTimes:[moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss'),moment(_this.props.rowData.endTime, 'YYYY-MM-DD HH:mm:ss')],
    },()=>{
      // console.log(_this.state);
    });
  }

  componentDidUpdate(prevProps) {
    if (this.props.rowData !== prevProps.rowData) {
          let rowData = this.props.rowData;
        // console.log(rowData);
        // console.log(rowData.venue);
        // console.log(typeof(rowData));
        
        let _this = this;
        // console.log(_this.props.rowData.startTime);
        // console.log(_this.props.rowData.endTime);
        this.setState({
          eventId: rowData.id,
          eventName: rowData.eventName,
          startTime: rowData.startTime,
          endTime: rowData.endTime,
          venue: [rowData.venue],
          selectedVenue: rowData.venue.id,
          selectedTimes:[moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss'),moment(_this.props.rowData.endTime, 'YYYY-MM-DD HH:mm:ss')],
        },()=>{
          // console.log(_this.state);
        });
     }    
  }


  handleSubmit() {

    // console.log(this.state);
    let userinfo = JSON.parse(sessionStorage.getItem("user"));
    // console.log(userinfo);
    let _this = this;
    // 进行其他处理或者发起登录请求
      $.ajax({
        url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerUpdateDateAndPrice',
        method: 'POST',
        data: {
          eventId: _this.state.eventId,
          eventName: _this.state.eventName,
          startTime: _this.state.startTime,
          endTime: _this.state.endTime,
          venue: JSON.stringify(_this.state.venue),
          user: JSON.stringify(userinfo)
        },
        success: function (tdata) {
          // console.log("edit");
          // console.log(tdata);
          
          _this.props.onDataReceived(tdata);
          if(tdata !== undefined) {
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
  }
  resetValues() {

  }


  handleEventNameChange(e) {
    const { value } = e.target;
    this.setState({
      eventName: value
    });
  }
  // 处理RangePicker的onChange事件
  timeOnChange = (dates) => {
    let _this = this;
    if (dates && dates.length === 2) {
      const [start, end] = dates;
      this.setState({
        startTime: moment(start).format('YYYY-MM-DD HH:mm:ss'),
        endTime: moment(end).format('YYYY-MM-DD HH:mm:ss'),
        selectedTimes:[moment(start, 'YYYY-MM-DD HH:mm:ss'),moment(end, 'YYYY-MM-DD HH:mm:ss')],
      });
    } else {
      this.setState({
        startTime: null,
        endTime: null,
        // selectedTimes:[moment(start, 'YYYY-MM-DD HH:mm:ss'),moment(end, 'YYYY-MM-DD HH:mm:ss')],
      });
    }
  };

  handleVenueChange = (value) => {
    const selectedValue = value;
    this.setState({
      selectedVenue:　selectedValue
    });
    // console.log(selectedValue);
    // this.setState({ selectedValue: value }); // 更新所选值到状态
  };

  handleInputChange(e, section) {
    const { name, value } = e.target;
  
    this.setState((prevState) => {
      // 在这里更新相应的section对象的值
      const updatedVenue = prevState.venue.map((venueItem) => {
        if (venueItem.id === this.state.selectedVenue) {
          const updatedSection = venueItem.venueSectionList.find((sec) => sec.id === section.id);
          if (name.startsWith("sectionName_")) {
            updatedSection.sectionName = value;
          } else if (name.startsWith("price_")) {
            updatedSection.price = value;
          }
        }
        return venueItem;
      });
  
      return { venue: updatedVenue };
    });
  }
  


  render() {
    const { Option } = Select;
    const { RangePicker } = DatePicker;
    
    return (
      <div>
        <Form name="normal_login" className="event-form">
                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your Event Name!',
                    },
                  ]}
                >
                  <Input
                    id="eventname"
                    name="eventname"
                    className="eventname"
                    placeholder="event name"
                    value={this.state.eventName}
                    onChange={(e) => this.handleEventNameChange(e)}
                  />
                </Form.Item>
                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your Time Range!',
                    },
                  ]}
                >
                  <RangePicker
                    className="setime"
                    showTime={{ format: 'HH:mm' }}
                    format="YYYY-MM-DD HH:mm"
                    placeholder={['Start Time', 'End Time']}
                    onChange={this.timeOnChange}
                    defaultValue={this.state.selectedTimes}
                    value={this.state.selectedTimes}
                  />
                </Form.Item>
                <Select
                  // style={{ width: 200 }}
                  className="selectavenue"
                  placeholder="Venue"
                  disabled
                  onChange={this.handleVenueChange}
                  value={this.state.selectedVenue ? this.state.selectedVenue : 'Select Venue'}
                >
                  {this.state.venue.map((venue) => (
                    <Option key={venue.id} value={venue.id}>
                      {venue.venueName}
                    </Option>
                  ))}
                </Select>
                {this.state.selectedVenue && (
                    <div>
                      {this.state.venue.find((venue) => venue.id === this.state.selectedVenue).venueSectionList.map((section) => (
                          <div key={section.id}>
                            Seciton Name:
                            <Input
                              name={`sectionName_${section.id}`}
                              placeholder="Section Name"
                              disabled
                              value={section.sectionName}
                              onChange={(e) => this.handleInputChange(e, section)}
                            />
                            Price:
                            <Input
                              name={`price_${section.id}`}
                              placeholder="Price"
                              value={section.price}
                              onChange={(e) => this.handleInputChange(e, section)}
                            />
                          </div>
                        ))}
                    </div>
                  )}

                <Form.Item>
                  <Button type="primary"  className="eventedit-save-form-submit-button" onClick={this.handleSubmit.bind(this)}>
                    Save
                  </Button>
                </Form.Item>
              </Form>
      </div>
    )
  }
  
}

export default EventEdit
