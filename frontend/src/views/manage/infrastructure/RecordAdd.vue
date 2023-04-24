<template>
  <a-modal v-model="show" title="新增设施检查记录" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="10">
          <a-form-item label='设施名称' v-bind="formItemLayout">
            <span style="font-size: 15px;font-weight: 650;color: #000c17">{{infrastructureName}}</span>
          </a-form-item>
        </a-col>
         <a-col :span="10">
          <a-form-item label='检查日期' v-bind="formItemLayout">
            <a-date-picker  v-decorator="[
            'date',
            { rules: [{ required: true, message: '啥时候啊？' }] }
            ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="10">
          <a-form-item label='员工' v-bind="formItemLayout">
            <a-select v-decorator="[
            'worker',
            { rules: [{ required: true, message: '谁来承担这个责任？' }] }
            ]">
              <a-select-option value="1">赵铁柱</a-select-option> 
              <a-select-option value="2">张狗蛋</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
         <a-col :span="10">
          <a-form-item label='状态' v-bind="formItemLayout">
            <a-select v-decorator="[
            'status',
            { rules: [{ required: true, message: '精神状态是否良好？' }] }
            ]">
              <a-select-option value="1">正常~</a-select-option> 
              <a-select-option value="2">不太好。</a-select-option>
              <a-select-option value="3">坏了！</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
       
         <a-col :span="20">
          <a-form-item label='备注信息' v-bind="formItemLayout">
            <a-input v-decorator="[
            'message',
            { rules: [{ required: true, message: '你采访这个设施时，它的心情是什么？' }] }
            ]"/>
          </a-form-item>
        </a-col> 
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
import dayjs from "dayjs";
// 以下4行引入解决日期控件英文的问题
import moment from 'moment'
import 'moment/locale/zh-cn'
moment.locale('zh-cn')


const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'RecordAdd',
  props: {
    recordAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.recordAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      infrastructureName:'',
      infrastructure_id:0,
    }
  },
  methods: {
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    sendData(id,infrastructureName) {
      this.infrastructure_id = id;
      this.infrastructureName = infrastructureName;
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          values.infrastructure_id = this.infrastructure_id
          values.date = moment(values.date).format('YYYY-MM-DD');
          console.log(values);
          this.loading = true
          this.$post('/cos/infrastructure-manage/addRecord', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
