<template>
  <a-modal v-model="show" title="新增设施" @cancel="onClose" :width="800">
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
            <a-input v-decorator="[
            'infrastructureName',
            { rules: [{ required: true, message: '给他取什么名字呢？' }] }
            ]"/>
          </a-form-item>
        </a-col> 
         <a-col :span="10">
          <a-form-item label='牌子名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'brand',
            { rules: [{ required: true, message: '哪个厂出生的？' }] }
            ]"/>
          </a-form-item>
        </a-col> 
         <a-col :span="10">
          <a-form-item label='位置' v-bind="formItemLayout">
            <a-select v-decorator="[
            'community',
            { rules: [{ required: true, message: '我要去哪里找它呢？' }] }
            ]">
              <a-select-option value="金河苑">金河苑</a-select-option> 
              <a-select-option value="金沙苑">金沙苑</a-select-option>
              <a-select-option value="大马路">大马路</a-select-option>
            </a-select>
          </a-form-item>
        </a-col> 
        
        <a-col :span="10">
          <a-form-item label='投入使用日期' v-bind="formItemLayout">
             <a-date-picker
                type="date"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                :clearable="false" 
                style="width:100%"
                v-decorator="[
                'useDate',
                   { rules: [{ required: true, message: '噢！是何年何月何日？开启了你的新征程' }] }
                ]"
              />
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
moment.locale('zh-cn')


const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'InfrastructureAdd',
  props: {
    infrastructureAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.infrastructureAddVisiable
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
      previewImage: ''
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
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log(values);
          values.publisher = this.currentUser.userId
          this.loading = true
          this.$post('/cos/infrastructure-info', {
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
