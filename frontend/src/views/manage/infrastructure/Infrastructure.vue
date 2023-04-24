<template>
  <a-card :bordered="false" class="card-area">
     <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="设施名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}"
             >
                <a-input v-model="queryParams.infrastructureName"/>
              </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24">
              <a-form-item
                label="设施品牌"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}"
             >
                <a-input v-model="queryParams.brand"/>
              </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24">
              <a-form-item
                label="所属苑区"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 17, offset: 2}">
                <a-select v-model="queryParams.community" allowClear>
                  <a-select-option value="金河苑">金河苑</a-select-option>
                  <a-select-option value="金沙苑">金沙苑</a-select-option>
                  <a-select-option value="金景苑">金景苑</a-select-option>
                  <a-select-option value="金浩苑">金浩苑</a-select-option>
                  <a-select-option value="金湾苑">金湾苑</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
     </div> 
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add">新增</a-button>
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="operation" slot-scope="text, record">
          <a-icon type="reconciliation" @click="addRecord(record.id,record.infrastructureName)" title="检 查" style="margin-right: 15px"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>
        </template>
      </a-table>
    </div>
    <infrastructure-add
      v-if="infrastructureAdd.visiable"
      @close="handleInfrastructureAddClose"
      @success="handleInfrastructureAddSuccess"
      :infrastructureAddVisiable="infrastructureAdd.visiable">
    </infrastructure-add>
    <infrastructure-edit
      ref="infrastructureEdit"
      @close="handleInfrastructureEditClose"
      @success="handleInfrastructureEditSuccess"
      :infrastructureEditVisiable="infrastructureEdit.visiable">
    </infrastructure-edit>
     <record-add
      ref="recordAdd"
     
      @close="handleRecordAddClose"
      @success="handleRecordAddSuccess"
      :recordAddVisiable="recordAdd.visiable">
    </record-add>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import InfrastructureAdd from './InfrastructureAdd'
import InfrastructureEdit from './InfrastructureEdit'
import RecordAdd from "./RecordAdd"
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'Infrastructure',
  components: {InfrastructureAdd, InfrastructureEdit, RecordAdd, RangeDate},
  data () {
    return {
      advanced: false,
      infrastructureAdd: {
        visiable: false
      },
      infrastructureEdit: {
        visiable: false
      },
      recordAdd: {
        visiable: false,
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '设施名称',
        dataIndex: 'infrastructureName'
      }, {
        title: '投入使用日期',
        dataIndex: 'useDate',
      }, {
        title: '所属社区',
        dataIndex: 'community'
      }, 
      {
        title: '牌子名称',
        dataIndex: 'brand'
      },  
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    view (row) {
      this.infrastructureView.data = row
      this.infrastructureView.visiable = true
    },
    handleInfrastructureViewClose () {
      this.infrastructureView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.infrastructureAdd.visiable = true
    },
    addRecord(id,infrastructureName) {
      this.$refs.recordAdd.sendData(id,infrastructureName);
      this.recordAdd.visiable = true;
    },
     handleRecordAddClose() {
      this.recordAdd.visiable = false;
    },
    handleRecordAddSuccess() {
      this.recordAdd.visiable = false;
      this.$message.success("新增记录成功！！");
      this.search();
    },
    handleInfrastructureAddClose () {
      this.infrastructureAdd.visiable = false
    },
    handleInfrastructureAddSuccess () {
      this.infrastructureAdd.visiable = false
      this.$message.success('新增设施信息成功')
      this.search()
    },
    edit (record) {
      //console.log(this.$refs.infrastructureEdit);
      this.$refs.infrastructureEdit.setFormValues(record)
      this.infrastructureEdit.visiable = true
    },
    handleInfrastructureEditClose () {
      this.infrastructureEdit.visiable = false
    },
    handleInfrastructureEditSuccess () {
      this.infrastructureEdit.visiable = false
      this.$message.success('修改设施信息成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/infrastructure-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.type === undefined) {
        delete params.type
      }
      this.$get('/cos/infrastructure-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
