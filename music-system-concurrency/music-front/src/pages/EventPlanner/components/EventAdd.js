import React, { Component } from 'react';

import { Button, Form, Input, Modal, Select, DatePicker } from 'antd';
import moment from 'moment';
// import { Link } from 'react-router-dom';
import './EventAdd.css'
import $ from 'jquery'




class EventAdd extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedVenue: '',
      eventname: '',
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
    let _this = this;
    // 进行其他处理或者发起登录请求
    $.ajax({
      url: 'http://127.0.0.1:8080/music_war_exploded/getVenueAndVenueSection',
      method: 'GET',
      success: function (tdata) {
        // console.log(tdata);
        let all = [];
        for(let i = 0; i < tdata.length; i++) {
          let temp = {
            id: '',
            venueName: '',
            venueAddress: '',
            venueSectionList: [],
          }
          temp.id = tdata[i].id;
          temp.venueName = tdata[i].venueName;
          temp.venueAddress = tdata[i].venueAddress;
          for(let j = 0; j < tdata[i].venueSectionList.length; j++) {
            let tempList = {
              id: '',
              price: '',
              sectionName:'',
              venueId: '',
              capacity:'',
              delete: ''
            }
            tempList.id = tdata[i].venueSectionList[j].id;
            tempList.price = tdata[i].venueSectionList[j].price;
            tempList.sectionName = tdata[i].venueSectionList[j].sectionName;
            tempList.venueId = tdata[i].venueSectionList[j].venueId;
            tempList.capacity = tdata[i].venueSectionList[j].capacity;
            tempList.delete = tdata[i].venueSectionList[j].delete;
            temp.venueSectionList.push(tempList);
          }
          all.push(temp);
        }
        _this.setState({
          venue: all
        },() => {
          // console.log(_this.state.venue);
        });
        
      },
      error: function (error) {
        
      }
    });
  }
  handleSubmit() {


    let _this = this;
    let eventPlannerName = JSON.parse(sessionStorage.getItem("user")).name;
    let eventName = this.state.eventname;
    let st = this.state.startTime;
    let et = this.state.endTime;
    let userId = JSON.parse(sessionStorage.getItem("user")).id;
    let venueAndSectionList = this.state.venue.find((sec) => sec.id === _this.state.selectedVenue);
    
    if(eventPlannerName === undefined || eventName === undefined || st === undefined || et===undefined || userId === undefined || venueAndSectionList === undefined) {
          Modal.error({
              title: 'Prompt.',
              content: 'Please input completely.',
              className: 'custom-error-modal', // 自定义类名
          });
    } else {
      // console.log(venueAndSectionList);
      let _this = this;
    // 进行其他处理或者发起登录请求
      $.ajax({
        url: 'http://127.0.0.1:8080/music_war_exploded/eventPlannerAddEvent',
        method: 'POST',
        data: {
          event_planner_name: eventPlannerName, //活动组织者
          event_name: eventName,//活动名称
          start_time: st,//开始时间
          end_time: et,//结束时间
          user_id: userId,//活动组织者id
          venueAndSectionList: JSON.stringify(venueAndSectionList),//场地和区域
        },
        success: function (tdata) {
          // console.log(tdata);
          
          _this.props.onDataReceived(tdata);
          if(tdata !== undefined) {
              Modal.success({
                title: 'Prompt.',
                content: 'Add Success.',
                className: 'custom-error-modal', // 自定义类名
            });
          } else if(tdata==="event name repeat."){
            Modal.error({
              title: 'Prompt',
              content: 'Time Conflict or Event Name Repeat, try again',
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
    
    
  }
  resetValues() {

  }


  handleEventNameChange(e) {
    const { value } = e.target;
    this.setState({
      eventname: value
    });
  }
  // 处理RangePicker的onChange事件
  timeOnChange = (dates) => {
    if (dates && dates.length === 2) {
      const [start, end] = dates;
      this.setState({
        startTime: moment(start).format('YYYY-MM-DD HH:mm:ss'),
        endTime: moment(end).format('YYYY-MM-DD HH:mm:ss'),
      });
    } else {
      this.setState({
        startTime: null,
        endTime: null,
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
                    value={this.state.eventname}
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
                  />
                </Form.Item>
                <Select
                  // style={{ width: 200 }}
                  className="selectavenue"
                  placeholder="Venue"
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
                  <Button type="primary"  className="eventadd-form-submit-button" onClick={this.handleSubmit.bind(this)}>
                    Add
                  </Button>
                </Form.Item>
              </Form>
      </div>
    )
  }
  
}

export default EventAdd
