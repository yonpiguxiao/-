<template>
  <el-dialog
    title="预约会议室"
    v-model="visible"
    width="500px"
    :before-close="handleClose"
  >
    <el-form
      :model="form"
      :rules="rules"
      ref="formRef"
      label-width="100px"
    >
      <el-form-item label="预约时间" prop="reservationDate">
        <el-date-picker
          v-model="form.reservationDate"
          type="date"
          placeholder="请选择预约日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      
      <el-form-item label="开始时间" prop="startTime">
        <el-select v-model="form.startTime" placeholder="请选择开始时间">
          <el-option
            v-for="hour in 24"
            :key="hour - 1"
            :label="`${hour - 1}:00`"
            :value="`${hour - 1}:00`"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item label="结束时间" prop="endTime">
        <el-select v-model="form.endTime" placeholder="请选择结束时间" :disabled="!form.startTime">
          <el-option
            v-for="hour in 24"
            :key="hour"
            :label="`${hour}:00`"
            :value="`${hour}:00`"
            :disabled="hour <= parseInt(form.startTime)"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item label="预约人" prop="reserverName">
        <el-input v-model="form.reserverName" placeholder="请输入预约人名字" />
      </el-form-item>
      
      <el-form-item label="联系电话" prop="contactPhone">
        <el-input v-model="form.contactPhone" placeholder="请输入联系号码" />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { ref, reactive, defineExpose } from 'vue'

export default {
  name: 'AddAppointmentPop',
  setup() {
    // 控制弹窗显示隐藏
    const visible = ref(false)
    
    // 表单数据
    const form = reactive({
      reservationDate: '',
      startTime: '9:00',
      endTime: '10:00',
      reserverName: '',
      contactPhone: ''
    })
    
    // 表单验证规则
    const rules = {
      reservationDate: [
        { required: true, message: '请选择预约日期', trigger: 'change' }
      ],
      startTime: [
        { required: true, message: '请选择开始时间', trigger: 'change' }
      ],
      endTime: [
        { required: true, message: '请选择结束时间', trigger: 'change' }
      ],
      reserverName: [
        { required: true, message: '请输入预约人名字', trigger: 'blur' }
      ],
      contactPhone: [
        { required: true, message: '请输入联系号码', trigger: 'blur' }
      ]
    }
    
    // 表单引用
    const formRef = ref(null)
    
    // 显示弹窗
    const show = () => {
      visible.value = true
    }
    
    // 关闭弹窗前的处理
    const handleClose = (done) => {
      formRef.value.resetFields()
      done()
    }
    
    // 取消按钮
    const cancel = () => {
      formRef.value.resetFields()
      visible.value = false
    }
    
    // 确定按钮
    const confirm = () => {
      formRef.value.validate((valid) => {
        if (valid) {
          // 将时间段合并为一个字符串
          const timePeriod = `${form.startTime}-${form.endTime}`;
          const appointmentInfo = {
            ...form,
            timePeriod
          };
          console.log('预约信息:', appointmentInfo)
          // 这里可以添加提交预约的逻辑
          // 提交成功后关闭弹窗
          visible.value = false
        } else {
          console.log('表单验证失败')
          return false
        }
      })
    }
    
    // 暴露给父组件的方法
    defineExpose({ show })
    
    return {
      visible,
      form,
      rules,
      formRef,
      show,
      handleClose,
      cancel,
      confirm
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
