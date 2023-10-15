import React, { Component } from 'react';

import { Button, Form, Input, Modal} from 'antd';
// import { Link } from 'react-router-dom';
import './VenueEdit.css'
import $ from 'jquery'




class VenueEdit extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sections: [{ id: '', sectionname: '', capacity: '' }], // 初始化为一个组合
      venuename: '',
      venueaddress: '',
      venueid:''
    };
  }
  componentDidMount() {
    const rowData = this.props.rowData;
      console.log(rowData);
      let tempSectionList = [];
      for(let j = 0; j < rowData.VenueSectionList.length; j++) {
        let tempScetion = {};
        tempScetion.id = rowData.VenueSectionList[j].id;
        tempScetion.sectionname = rowData.VenueSectionList[j].sectionName;
        tempScetion.capacity = rowData.VenueSectionList[j].capacity;
        tempSectionList.push(tempScetion);
      }
    
      this.setState({
        sections: tempSectionList,
        venuename: rowData.VenueName,
        venueaddress: rowData.VenueAddress,
        venueid: rowData.id,
      }, () => {
        // console.log("my data");
        // console.log(this.state); // 此处打印的是更新后的状态
      });
  }
  componentDidUpdate(prevProps) {
    if (this.props.rowData !== prevProps.rowData) {
      const rowData = this.props.rowData;
      console.log(rowData);
      let tempSectionList = [];
      for(let j = 0; j < rowData.VenueSectionList.length; j++) {
        let tempScetion = {};
        tempScetion.id = rowData.VenueSectionList[j].id;
        tempScetion.sectionname = rowData.VenueSectionList[j].sectionName;
        tempScetion.capacity = rowData.VenueSectionList[j].capacity;
        tempSectionList.push(tempScetion);
      }
    
      this.setState({
        sections: tempSectionList,
        venuename: rowData.VenueName,
        venueaddress: rowData.VenueAddress,
        venueid: rowData.id,
      }, () => {
        // console.log("my data");
        // console.log(this.state); // 此处打印的是更新后的状态
      });
     }    
  }
  
  handleSubmit() {
    // console.log(this.state);
    let _this = this;
    // 进行其他处理或者发起登录请求
    $.ajax({
      url: 'http://127.0.0.1:8080/music_war_exploded/adminVenueSectionEdit',
      method: 'POST',
      data: {
        VenueId: _this.state.venueid,
        VenueName: _this.state.venuename,
        VenueAddress: _this.state.venueaddress,
        SectionList: JSON.stringify(_this.state.sections)
      },
      success: function (tdata) {
        // console.log(tdata);
        _this.props.onDataReceived(tdata);
        if(tdata !== undefined) {
            Modal.success({
              title: 'Prompt.',
              content: 'Edit Success.',
              className: 'custom-error-modal', // 自定义类名
          });
        } else {
          Modal.error({
            title: 'Prompt',
            content: 'Venue Name repeat, try again',
            className: 'custom-error-modal', // 自定义类名
          });
        }
      },
      error: function (error) {
        Modal.error({
            title: 'Edit Fail',
            content: 'Venue Name repeat, try again',
            className: 'custom-error-modal', // 自定义类名
          });
      }
    });
  }
  resetValues() {

  }

  addSection() {
    this.setState((prevState) => ({
      sections: [...prevState.sections, { sectionname: '', capacity: '' }],
    }));
  }
  subSection() {
    let _this = this;
    Modal.confirm({
      title: 'Notice',
      content: 'Sure remove last one section?',
      className: 'custom-error-modal',
      onOk: () => {
        // 在这里执行你的代码
        if(_this.state.sections.length >= 2) {
          _this.setState((prevState) => ({
            sections: prevState.sections.slice(0, -1)
          }));
        } else {
          
          Modal.error({
            title: 'Notice',
            content: 'Last one section',
            className: 'custom-error-modal', // 自定义类名
          });
        }
        // 关闭提示框
        Modal.destroyAll();
      },
      onCancel: () => {
        // 关闭提示框
        Modal.destroyAll();
      },
    });
    
  }

  handleVenueNameChange(e) {
    const { value } = e.target;
    this.setState({
      venuename: value
    });
  }
  handleVenueAddressChange(e) {
    const { value } = e.target;
    this.setState({
      venueaddress: value
    });
  }
  handleSectionNameChange(e, index) {
    const { value } = e.target;
    // console.log(value);
    this.setState((prevState) => {
      const sections = [...prevState.sections];
      sections[index].sectionname = value;
      return { sections };
    });
  }
  
  handleCapacityChange(e, index) {
    const { value } = e.target;
    this.setState((prevState) => {
      const sections = [...prevState.sections];
      sections[index].capacity = value;
      return { sections };
    });
  }
  


  render() {
    
    return (
      <div>
        <Form name="normal_login" className="venue-form">
                <Form.Item
                  
                  rules={[
                    {
                      required: true,
                      message: 'Please input your Venue Name!',
                    },
                  ]}
                >
                  <Input
                    id="venuename"
                    name="venuename"
                    className="venuename"
                    disabled
                    placeholder="venue name"
                    value={this.state.venuename}
                    onChange={(e) => this.handleVenueNameChange(e)}
                  />
                </Form.Item>
                <Form.Item
                  rules={[
                    {
                      required: true,
                      message: 'Please input your Venue Address!',
                    },
                  ]}
                >
                  <Input
                    id="venueaddress"
                    name="venueaddress"
                    className="venueaddress"
                    placeholder="venue address"
                    value={this.state.venueaddress}
                    onChange={(e) => this.handleVenueAddressChange(e)}
                  />
                </Form.Item>
                {this.state.sections.map((section, index) => (
                  <div key={index}>
                    <Form.Item
                      rules={[
                        {
                          required: true,
                          message: 'Please input your Section!',
                        },
                      ]}
                    >
                      <Input
                        id={`sectionname${index}`}
                        name={`sectionname${index}`}
                        className="sectionname"
                        placeholder="section name"
                        value={this.state.sections[index].sectionname}
                        onChange={(e) => this.handleSectionNameChange(e, index)}
                      />
                    </Form.Item>
                    <Form.Item
                      rules={[
                        {
                          required: true,
                          message: 'Please input your Capacity!',
                        },
                      ]}
                    >
                      <Input
                        id={`capacity${index}`}
                        name={`capacity${index}`}
                        className="capacity"
                        placeholder="capacity"
                        value={this.state.sections[index].capacity}
                        onChange={(e) => this.handleCapacityChange(e, index)}
                      />
                    </Form.Item>
                  </div>
                ))}
                <Form.Item>
                  <Button type="primary"  className="venue-edit-form-submit-button" onClick={this.handleSubmit.bind(this)}>
                    Save
                  </Button>
                  {/* <Button type="primary"  className="register-form-reset-button" onClick={this.resetValues}>
                    Reset
                  </Button> */}
                  <Button type="primary"  className="venue-edit-form-add-section-button" onClick={this.addSection.bind(this)}>
                    Add Section
                  </Button>
                  <Button type="primary"  className="venue-edit-form-sub-section-button" onClick={this.subSection.bind(this)}>
                    Remove Section
                  </Button>
                </Form.Item>
              </Form>
      </div>
    )
  }
  
}

export default VenueEdit
