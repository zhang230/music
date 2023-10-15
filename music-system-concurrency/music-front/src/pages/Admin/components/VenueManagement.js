import React, { Component } from 'react';

import { Button, Form, Input, Modal, Table} from 'antd';
// import { Link } from 'react-router-dom';
import './VenueManagement.css'
import VenueAdd from './VenueAdd';
import VenueEdit from './VenueEdit';
import $ from 'jquery'


class VenueManagement extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpenAdd: false,
      isOpenEdit: false,
      columns : [
        {
          title: 'Venue Name',
          dataIndex: 'VenueName',
          key: '1',
        },
        {
          title: 'Venue Address',
          dataIndex: 'VenueAddress',
          key: '2',
        },
       
        {
          title: 'Edit',
          key: 'Edit',
          fixed: 'right',
          width: 100,
          render: () => <a onClick={this.setEditTrue.bind(this)}>Edit</a>,
        },
        {
          title: 'Delete',
          key: 'Delete',
          fixed: 'right',
          width: 100,
          render: (text, record) => <a onClick={() => this.deleteRow(record)}>Delete</a>,
        }
      ],
      data : []
    };

    let _this = this;
    // 进行其他处理或者发起登录请求
    $.ajax({
      url: 'http://127.0.0.1:8080/music_war_exploded/adminFindAllVenueAndVenueSection',
      method: 'GET',
      success: function (tdata) {
        let tt = [];
        for(let i = 0; i < tdata.length; i++) {
          let temp = {};
          temp.id = tdata[i].id;
          temp.VenueName = tdata[i].venueName;
          temp.VenueAddress = tdata[i].venueAddress;
          temp.VenueSectionList = tdata[i].venueSectionList;
          tt.push(temp);
        }
        _this.setState({
          data: tt
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
  handleDataFromVenueAdd = (tdata) => {
    // console.log(tdata);
    // 在这里处理从 VenueAdd 组件传递过来的数据
    let tt = [];
    for(let i = 0; i < tdata.length; i++) {
      let temp = {};
      temp.id = tdata[i].id;
      temp.VenueName = tdata[i].venueName;
      temp.VenueAddress = tdata[i].venueAddress;
      temp.VenueSectionList = tdata[i].venueSectionList;
      tt.push(temp);
    }
    this.setState({
      data: tt
    });
    // console.log(this.state.data);
  }
  deleteRow(record) {
    // console.log(record);
    let _this = this;
    Modal.confirm({
      title: 'Notice',
      content: 'Sure delete this row?',
      className: 'custom-error-modal',
      onOk: () => {
        
        $.ajax({
          url: 'http://127.0.0.1:8080/music_war_exploded/adminDeleteVenueAndVenueSections',
          method: 'POST',
          data: {
            VenueId: record.id,
            VenueName: record.VenueName,
            VenueAddress: record.VenueAddress,
            SectionList: JSON.stringify(record.VenueSectionList)
          },
          success: function (tdata) {
            // console.log(tdata);
            // _this.props.onDataReceived(tdata);
            if(tdata !== undefined) {
              let tt = [];
              for(let i = 0; i < tdata.length; i++) {
                let temp = {};
                temp.id = tdata[i].id;
                temp.VenueName = tdata[i].venueName;
                temp.VenueAddress = tdata[i].venueAddress;
                temp.VenueSectionList = tdata[i].venueSectionList;
                tt.push(temp);
              }
              _this.setState({
                data: tt
              });
                Modal.success({
                  title: 'Prompt.',
                  content: 'Delete Success.',
                  className: 'custom-error-modal', // 自定义类名
              });
            } else {
              Modal.error({
                title: 'Prompt',
                content: 'Delete fail, try again',
                className: 'custom-error-modal', // 自定义类名
              });
            }
          },
          error: function (error) {
            Modal.error({
                title: 'Delete Fail',
                content: 'Delete fail, try again',
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
      // isOpenEdit: true, // 打开VenueEdit模块
    });
  };


  render() {
    
    return (
      <div>
        
        <Button type="primary" onClick={this.setTrue.bind(this)}>
            Add Venue
        </Button>
          <Modal
          id="Venue"
          title="Venue"
          centered
          visible={this.state.isOpenAdd}
          closable={false} // 禁用关闭按钮
          onOk={this.setFalse.bind(this)}
          // onCancel={this.setFalse.bind(this)}
          cancelButtonProps={{ hidden: true }} // Hide the cancel button
          width={1000}
          bodyStyle={{ maxHeight: '85%', overflowY: 'auto' }}
        >
          <VenueAdd rowData={this.state.selectedRowData} onDataReceived={this.handleDataFromVenueAdd}></VenueAdd>
        </Modal>
        <Modal
          id="VenueEdit"
          title="VenueEdit"
          centered
          visible={this.state.isOpenEdit}
          closable={false} // 禁用关闭按钮
          onOk={this.setEditFalse.bind(this)}
          // onCancel={this.setEditFalse.bind(this)}
          cancelButtonProps={{ hidden: true }} // Hide the cancel button
          width={1000}
          bodyStyle={{ maxHeight: '85%', overflowY: 'auto' }}
        >
          <VenueEdit rowData={this.state.selectedRowData} onDataReceived={this.handleDataFromVenueAdd}></VenueEdit>
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

export default VenueManagement
