<template>
  <a-card :bordered="false" class="card-area">
     <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null : 'fold'">
            <a-col :md="6" :sm="24">
				 <a-form-item
                label="选择日期"
                :labelCol="{ span: 5 }"
                :wrapperCol="{ span: 17, offset: 2 }"
              >
              <a-date-picker
                v-model="dataForm.startDate"
                type="date"
                :editable="false"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                style="width: 100%"
                :clearable="false"
              />
				 </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>

     <div class="operator">
        <a-button @click="batchDelete">删除</a-button>
     </div>
      

      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataList"
               :pagination="pagination"
               :loading="loading"
               :scroll="{ x: 900 }"
               @change="handleTableChange"
               bordered
               :customRow="doubleClick"
      >
        <a-table-column
                type="index"
                header-align="center"
                align="center"
                width="100"
                label="序号"
            >
                <span slot="index" slot-scope="text, record, index">
                <span>{{ index +1 }}</span>
                </span>
            </a-table-column>

      <a-table-column
        prop="infrastructureName"
        header-align="center"
        align="center"
        label="设施名称"
        min-width="370"
        :show-overflow-tooltip="true"
        >
      </a-table-column>

     <a-table-column
        header-align="center"
        align="center"
        label="dateList[0]"
        min-width="170" 
      > 
     </a-table-column>
        <template slot="dataList0" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[0].recordId)" v-if="record.record[0].status != undefined && record.record[0].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[0].recordId)" v-if="record.record[0].status != undefined && record.record[0].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[0].recordId)" v-if="record.record[0].status != undefined && record.record[0].status == 1">{{ text }}</a>
          </div>
        </template>

        <template slot="dataList1" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[1].recordId)" v-if="record.record[1].status != undefined && record.record[1].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[1].recordId)" v-if="record.record[1].status != undefined && record.record[1].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[1].recordId)" v-if="record.record[1].status != undefined && record.record[1].status == 1">{{ text }}</a>
          </div>
        </template>

        <template slot="dataList2" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[2].recordId)" v-if="record.record[2].status != undefined && record.record[2].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[2].recordId)" v-if="record.record[2].status != undefined && record.record[2].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[2].recordId)" v-if="record.record[2].status != undefined && record.record[2].status == 1">{{ text }}</a>
          </div>
        </template>

        <template slot="dataList3" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[3].recordId)" v-if="record.record[3].status != undefined && record.record[3].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[3].recordId)" v-if="record.record[3].status != undefined && record.record[3].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[3].recordId)" v-if="record.record[3].status != undefined && record.record[3].status == 1">{{ text }}</a>
          </div>
        </template>

       <template slot="dataList4" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[4].recordId)" v-if="record.record[4].status != undefined && record.record[4].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[4].recordId)" v-if="record.record[4].status != undefined && record.record[4].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[4].recordId)" v-if="record.record[4].status != undefined && record.record[4].status == 1">{{ text }}</a>
          </div>
        </template>

       <template slot="dataList5" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[5].recordId)" v-if="record.record[5].status != undefined && record.record[5].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[5].recordId)" v-if="record.record[5].status != undefined && record.record[5].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[5].recordId)" v-if="record.record[5].status != undefined && record.record[5].status == 1">{{ text }}</a>
          </div>
        </template>
     
        <template slot="dataList6" slot-scope="text, record">
          <div>
            <a @click="recordEditTest(record.record[6].recordId)" v-if="record.record[6].status != undefined && record.record[6].status == 3" style="color:red">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[6].recordId)" v-if="record.record[6].status != undefined && record.record[6].status == 2" style="color:blue">
              {{ text }}
            </a>
            <a @click="recordEditTest(record.record[6].recordId)" v-if="record.record[6].status != undefined && record.record[6].status == 1">{{ text }}</a>
          </div>
        </template>


        
       
      </a-table>
    </div>
   <infrastructure-add
      v-if="infrastructureAdd.visiable"
      @close="handleInfrastructureAddClose"
      @success="handleInfrastructureAddSuccess"
      :infrastructureAddVisiable="infrastructureAdd.visiable">
    </infrastructure-add>
    
    <record-edit
      ref="recordEdit"
      @close="handleRecordEditClose"
      @success="handleRecordEditSuccess"
      :recordEditVisiable="recordEdit.visiable">
    </record-edit>
  
  </a-card>
</template>

<script>
import RangeDate from "@/components/datetime/RangeDate";
import dayjs from "dayjs";
import { mapState } from "vuex";
import moment from "moment";
import InfrastructureAdd from "../infrastructure/InfrastructureAdd.vue";
import RecordEdit from "../infrastructureRecord/RecordEdit.vue"
moment.locale("zh-cn");

export default {
  name: "InfrastructureRecord",
  components: { RangeDate, InfrastructureAdd,RecordEdit },
  data() {
    return {
      advanced: false,
      infrastructureAdd: {
        visiable: false
      },
      recordEdit: {
        visiable: false
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: [],
      //查询请求的数据
      dataForm: {
        infrastructureName: null,
        community: null,
        //datepicker只支持moment类型，表单获取的string类型会引起报错
        startDate: moment(
          dayjs()
            .subtract(6, "days")
            .format("YYYY-MM-DD")
        ),
        endDate: moment(dayjs().format("YYYY-MM-DD"))
      },
      dataList: [],
      dateList: []
    };
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns() {
      return [
        {
          title: "序号",
          dataIndex: "index",
          width: "30px",
          // scopedSlots: { customRender: 'index' }
          customRender: (text, record, index) => `${index + 1}`
        },
        {
          title: "设施名称",
          dataIndex: "infrastructureName"
        },
        {
          title: this.dateList[0],
          dataIndex: "record[0].mes",
          width: "200px",
          //如何封装起来呢？
          scopedSlots: {customRender: 'dataList1'}
        },
        {
          title: this.dateList[1],
          dataIndex: "record[1].mes",
          width: "200px",
          scopedSlots: {customRender: 'dataList1'}
        },
        {
          title: this.dateList[2],
          dataIndex: "record[2].mes",
          width: "200px",
          scopedSlots: {customRender: 'dataList2'}
        },
        {
          title: this.dateList[3],
          dataIndex: "record[3].mes",
          width: "200px",
          scopedSlots: {customRender: 'dataList3'}
        },
        {
          title: this.dateList[4],
          dataIndex: "record[4].mes",
          width: "200px",
          scopedSlots: {customRender: 'dataList4'}
        },
        {
          title: this.dateList[5],
          dataIndex: "record[5].mes",
          width: "200px",
          scopedSlots: {customRender: 'dataList5'}
        },
        {
          title: this.dateList[6],
          dataIndex: "record[6].mes",
          width: "200px",
          scopedSlots: {customRender: 'dataList6'}
        },
       
      ];
    }
  },
  mounted() {
    this.fetch();
  },
  methods: {
    recordEditTest (row) {
      console.log(row)
      this.$refs.recordEdit.setFormValues(row);
      this.recordEdit.visiable = true;
    },
    view(row) {
      this.buildingView.data = row;
      this.buildingView.visiable = true;
    },
    handleBuildingViewClose() {
      this.buildingView.visiable = false;
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
    },
    add() {
        console.log("?");
      this.infrastructureAdd.visiable = true;
    },
    add2() {
      this.recordAdd.visiable = true;
    },
    handleInfrastructureAddClose() {
      this.infrastructureAdd.visiable = false;
    },
    handleInfrastructureAddSuccess() {
      this.infrastructureAdd.visiable = false;
      this.$message.success("新增设施成功！！");
      this.search();
    },
    handleRecordAddClose() {
      this.recordAdd.visiable = false;
    },
    handleRecordAddSuccess() {
      this.recordAdd.visiable = false;
      this.$message.success("新增记录成功！！");
      this.search();
    },
    edit(record) {
      record.record = record.record.slice(0, 6)
      this.$refs.recordEdit.setFormValues(record);
      this.recordEdit.visiable = true;
    },
    handleRecordEditClose() {
      this.recordEdit.visiable = false;
    },
    handleRecordEditSuccess() {
      this.recordEdit.visiable = false;
      this.$message.success("修改成功");
      this.search();
    },
    handleDeptChange(value) {
      this.queryParams.deptId = value || "";
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要删除的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let ids = that.selectedRowKeys.join(",");
          that.$delete("/cos/building-info/" + ids).then(() => {
            that.$message.success("删除成功");
            that.selectedRowKeys = [];
            that.search();
          });
        },
        onCancel() {
          that.selectedRowKeys = [];
        }
      });
    },
    search() {
      let { sortedInfo, filteredInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      });
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent;
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
        this.paginationInfo.pageSize = this.pagination.defaultPageSize;
      }
      // 重置列过滤器规则
      this.filteredInfo = null;
      // 重置列排序规则
      this.sortedInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.fetch();
    },
    handleTableChange(pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination;
      this.filteredInfo = filters;
      this.sortedInfo = sorter;

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      });
    },
    fetch() {
      // 显示loading
      let that = this;
      let data = {
        // infrastructureName: that.dataForm.infrastructureName,
        //  community: that.dataForm.community,
        startDate: that.dataForm.startDate.format("YYYY-MM-DD"),
        endDate: that.dataForm.endDate.format("YYYY-MM-DD")
      };
      that
        .$get(
          "/cos/infrastructure-manage/searchInfrastructureInRangeByPage",
          data,
          true
        )
        .then(r => {
          console.log(r.data);
          that.dataList = r.data.dataList;
          that.dateList = r.data.dateList;
        });
    },
    cellStatusStyle(text, record) {
      console.log(record.record[1].mes);
      if (record.record[1].mes[0] == "1") {
        return <span style="color:red">{text}</span>;
      } else {
        return text;
      }
    },
    doubleClick(record, index){
      
     },
  },
  watch: {}
};
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
