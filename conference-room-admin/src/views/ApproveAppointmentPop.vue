<template>
  <el-dialog
    v-model="dialogVisible"
    title="审批预约"
    width="500px"
    :before-close="handleClose"
  >
    <el-form :model="form" label-width="80px">
      <el-form-item label="审批结果">
        <el-select v-model="form.approvalResult" placeholder="请选择审批结果" @change="onApprovalChange">
          <el-option label="同意" value="agree"></el-option>
          <el-option label="驳回" value="reject"></el-option>
        </el-select>
      </el-form-item>
      
      <el-form-item label="驳回理由" v-if="showRejectReason">
        <el-input
          v-model="form.rejectReason"
          type="textarea"
          placeholder="请输入驳回理由"
          :rows="3"
        ></el-input>
      </el-form-item>
    </el-form>
    
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'

const dialogVisible = defineModel('visible', { type: Boolean, default: false })
const emit = defineEmits(['confirm', 'update:visible'])

const form = reactive({
  approvalResult: '',
  rejectReason: ''
})

// 是否显示驳回理由输入框
const showRejectReason = computed(() => {
  return form.approvalResult === 'reject'
})

// 审批选项改变时的处理
const onApprovalChange = (value) => {
  if (value !== 'reject') {
    form.rejectReason = ''
  }
}

// 关闭弹窗
const handleClose = () => {
  dialogVisible.value = false
  emit('update:visible', false)
  // 重置表单
  form.approvalResult = ''
  form.rejectReason = ''
}

// 确定按钮点击事件
const handleConfirm = () => {
  if (!form.approvalResult) {
    ElMessage.warning('请选择审批结果')
    return
  }
  
  if (form.approvalResult === 'reject' && !form.rejectReason.trim()) {
    ElMessage.warning('请输入驳回理由')
    return
  }
  
  // 发送审批结果
  emit('confirm', {
    approvalResult: form.approvalResult,
    rejectReason: form.rejectReason
  })
  
  handleClose()
}
</script>

<style scoped lang="scss">
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
