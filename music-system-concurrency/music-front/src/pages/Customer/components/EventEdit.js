import React, { Component } from 'react';

import { Button, Form, Input, Modal, Select, DatePicker,InputNumber } from 'antd';

import moment from 'moment';
// import { Link } from 'react-router-dom';
import './EventEdit.css'
import $ from 'jquery'




class EventEdit extends Component {
  constructor(props) {
    super(props);
    let _this = this;
    this.state = {
      bookNumber: 0,
      selectedTimes:[moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss'),moment(_this.props.rowData.endTime, 'YYYY-MM-DD HH:mm:ss')],
      selectedVenue: '',
      selectedSection: '',
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
      selectedSection: '',
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
          selectedSection: '',
          selectedTimes:[moment(_this.props.rowData.startTime, 'YYYY-MM-DD HH:mm:ss'),moment(_this.props.rowData.endTime, 'YYYY-MM-DD HH:mm:ss')],
        },()=>{
          // console.log(_this.state);
        });
     }    
  }


  handleSubmit() {
    let user_id =  JSON.parse(sessionStorage.getItem("user")).id;
    let event_id = this.state.eventId;
    let venue_id = this.state.selectedVenue;
    let venue_section_id = this.state.selectedSection;
    let bookNumber = this.state.bookNumber;
    if (venue_section_id === undefined || venue_section_id == null || venue_section_id == "") {
      Modal.error({
        title: 'Prompt.',
        content: 'Please choose section.',
        className: 'custom-error-modal', // 自定义类名
      });
  return ;
}
    let capacity = this.state.venue.find((venue) => venue.id === this.state.selectedVenue).venueSectionList.find((section) => section.id === this.state.selectedSection).capacity;
    console.log("user_id:" + user_id);
    console.log("event_id:" + event_id);
    console.log("venue_id:" + venue_id);
    console.log("venue_section_id:" + venue_section_id);
    console.log("bookNumber:"+bookNumber);
    
    if (this.state.bookNumber > capacity) {
          Modal.error({
            title: 'Prompt.',
            content: 'Number of tickets exceeds availability.',
            className: 'custom-error-modal', // 自定义类名
          });
      return ;
    }
    if (this.state.bookNumber === 0) {
          Modal.error({
            title: 'Prompt.',
            content: 'Number should not be empty.',
            className: 'custom-error-modal', // 自定义类名
          });
      return ;
    }
    


   
      let _this = this;
    // 进行其他处理或者发起登录请求
      $.ajax({
        url: 'http://127.0.0.1:8080/music_war_exploded/customerBookEvent',
        method: 'POST',
        data: {
          user_id: user_id,
          event_id: event_id,
          venue_id: venue_id,
          venue_section_id: venue_section_id,
          number: bookNumber
        },
        success: function (tdata) {
          // console.log("edit");
          // console.log(tdata);
          // if(tdata === "book success") {
              Modal.success({
                title: 'Prompt.',
                content: 'Book Success.',
                className: 'custom-error-modal', // 自定义类名
            });
          // }

        },
        error: function (e) {
          
          Modal.error({
              title: 'Book Fail',
              content: 'Book Fail, try again',
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
  handleSectionChange = (value) => {
    const selectedValue = value;
    // console.log(selectedValue);
    this.setState({
      selectedSection:　selectedValue
    });
  }

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
          } else if (name.startsWith("capacity_")){
            updatedSection.capacity = value;
          }
        }
        return venueItem;
      });
  
      return { venue: updatedVenue };
    });
  }
  
  onChangeInputNumber(value) {
    // console.log('changed', value);
    this.setState({
      bookNumber: value
    })
  };

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
                    disabled
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
                    disabled
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
                    {/* Select Section: */}
                    <Select
                      className="section-select"
                      placeholder="Select Section"
                      onChange={this.handleSectionChange} // Update the selected section
                      value={this.state.selectedSection ? this.state.selectedSection : 'Select Section'}
                    >
                      {this.state.venue
                        .find((venue) => venue.id === this.state.selectedVenue)
                        .venueSectionList.map((section) => (
                          <Select.Option key={section.id} value={section.id}>
                            {section.sectionName}
                          </Select.Option>
                        ))}
                    </Select>

                    {this.state.selectedSection && (
                      <div>
                        Price:
                        <Input
                          name={`price_${this.state.selectedSection}`}
                          placeholder="Price"
                          disabled
                          value={
                            this.state.venue
                              .find((venue) => venue.id === this.state.selectedVenue)
                              .venueSectionList.find(
                                (section) => section.id === this.state.selectedSection
                              ).price
                          }
                          // onChange={(value) => this.handlePriceChange(value)}
                        />
                        Tickets Left:
                        <Input
                          name={`capacity_${this.state.selectedSection}`}
                          disabled
                          placeholder="Capacity"
                          value={
                            this.state.venue
                              .find((venue) => venue.id === this.state.selectedVenue)
                              .venueSectionList.find(
                                (section) => section.id === this.state.selectedSection
                              ).capacity
                          }
                          // onChange={(value) => this.handleCapacityChange(value)}
                        />
                      </div>
                    )}
                  </div>
                )}


                <Form.Item>
                  <Button type="primary"  className="register-form-submit-button" onClick={this.handleSubmit.bind(this)}>
                    Book
                  </Button>
                  {/* <span class="form-InputNumber-button-spane">Number:</span> */}
                  <InputNumber className="form-InputNumber-button" placeholder="Number"
                  min={1} max={999999999999}  onChange={this.onChangeInputNumber.bind(this)} />

                </Form.Item>
              </Form>
      </div>
    )
  }
  
}

export default EventEdit
