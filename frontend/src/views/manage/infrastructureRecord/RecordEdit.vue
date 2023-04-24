<template>
  <a-modal
    v-model="show"
    :title="recordData.infrastructureName"
    @cancel="onClose"
    style="font-size: 15px; font-weight: 650; color: #000c17"
    :width="800"
  >
    <template slot="footer">
      <a-button key="back" @click="onClose"> 取消 </a-button>
      <a-button
        key="submit"
        type="primary"
        :loading="loading"
        @click="handleSubmit"
      >
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label="检查日期" v-bind="formItemLayout">
            <a-date-picker
              type="date"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              :clearable="false"
              style="width:100%"
              v-decorator="[
                'date',
                { rules: [{ required: true, message: '何年何月何日？' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="状态" v-bind="formItemLayout">
            <a-select
              v-decorator="[
                'status',
                { rules: [{ required: true, message: '请选择状态!' }] },
              ]"
            >
              <a-select-option value="1">设施正常-合格</a-select-option>
              <a-select-option value="2">设施过期-替换</a-select-option>
              <a-select-option value="3">设施损坏-紧急</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="员工" v-bind="formItemLayout">
            <a-select
              v-decorator="[
                'worker',
                { rules: [{ required: true, message: '请选择员工!' }] },
              ]"
            >
              <a-select-option value="1">张铁柱</a-select-option>
              <a-select-option value="2">张狗蛋</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>

        <a-col :span="12">
          <a-form-item label="备注信息" v-bind="formItemLayout">
            <a-input
              v-decorator="[
                'message',
                { rules: [{ required: true, message: '请输入备注!' }] },
              ]"
            />
          </a-form-item>
        </a-col>
        <br />
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { mapState } from "vuex";
import dayjs from "dayjs";
import moment from "moment";
moment.locale("zh-cn");
function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 },
};
export default {
  name: "RecordEdit",
  props: {
    recordEditVisiable: {
      default: false,
    },
  },
  computed: {
    ...mapState({
      currentUser: (state) => state.account.user,
    }),
    show: {
      get: function () {
        return this.recordEditVisiable;
      },
      set: function () {},
    },
  },
  data() {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: "",
      recordData: {},
    };
  },
  methods: {
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj);
      }
      this.previewImage = file.url || file.preview;
      this.previewVisible = true;
    },
    //给表单赋初始值
    setFormValues(id) {
      this.getRecord(id)
     /* let record = await this.getRecord(id);
      this.recordData = record;
      this.rowId = record.recordId;
      let fields = [
        "infrastructureName",
        "message",
        "worker",
        "status",
        "date",
      ];
      let obj = {};
      Object.keys(record).forEach((key) => {
        if (key === "status") {
          record[key] = record[key].toString();
        }
        if (key == "date") {
          record[key] = moment(record[key]);
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          obj[key] = record[key];
        }
      });
      this.form.setFieldsValue(obj);*/
    },
    reset() {
      this.loading = false;
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    //提交修改
    handleSubmit() {
      this.form.validateFields((err, values) => {
        values.id = this.rowId;
        values.date = moment(values.date).format('YYYY-MM-DD');
        if (!err) {
          this.loading = true;
          this.$put("/cos/infrastructure-manage/updateInfrastructureRecord", {
            ...values,
          })
            .then((r) => {
              this.reset();
              this.$emit("success");
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    //根據id獲取這一條维检记录
    getRecord(id) {
      let record;
      this.$get(
        "/cos/infrastructure-manage/searchInfrastructureRecord",
        { id: id },
        true
      ).then((r) => {
        console.log(r.data.data.data);
        record = r.data.data.data;
        this.recordData = record;
        this.rowId = record.recordId
        let fields = [
          "infrastructureName",
          "message",
          "worker",
          "status",
          "date"
        ];
        let obj = {};
        Object.keys(record).forEach(key => {
          if (key === "status") {
            record[key] = record[key].toString();
          }
          if (key == "date") {
            record[key] = moment(record[key]);
          }
          if (fields.indexOf(key) !== -1) {
            this.form.getFieldDecorator(key);
            obj[key] = record[key];
          }
        });
        this.form.setFieldsValue(obj); 
      });
    },
  },
};
</script>

<style scoped>
</style>
