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
      
      <el-form-item label="时间段" prop="timePeriod">
        <el-select v-model="form.timePeriod" placeholder="请选择预约时间段">
          <el-option label="上午" value="上午"></el-option>
          <el-option label="下午" value="下午"></el-option>
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
      timePeriod: '',
      reserverName: '',
      contactPhone: ''
    })
    
    // 表单验证规则
    const rules = {
      reservationDate: [
        { required: true, message: '请选择预约日期', trigger: 'change' }
      ],
      timePeriod: [
        { required: true, message: '请选择预约时间段', trigger: 'change' }
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
          console.log('预约信息:', form)
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
