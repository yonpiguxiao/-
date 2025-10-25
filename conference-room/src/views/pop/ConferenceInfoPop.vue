<template>
  <el-dialog
    v-model="visible"
    title="会议室详情"
    width="500px"
    :before-close="handleClose"
  >
    <!-- 会议室基本信息 -->
    <div class="room-details">
      <!-- <h3>会议室详情字段</h3> -->
      <el-descriptions :column="1" border>
        <el-descriptions-item label="会议室房间号">{{ roomData.number }}</el-descriptions-item>
        <el-descriptions-item label="地址">{{ roomData.address || '北京市朝阳区某某大厦' }}</el-descriptions-item>
        <el-descriptions-item label="会议室照片">
          <el-image
            style="width: 100px; height: 100px"
            :src="roomImage"
            fit="cover"
            :preview-src-list="[roomImage]"
          />
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <!-- 时间选择和预约状态查询 -->
    <div class="reservation-check">
      <h4>查看预约状态</h4>
      <el-form :model="timeForm" label-width="80px">
        <el-form-item label="选择日期">
          <el-date-picker
            v-model="timeForm.date"
            type="date"
            placeholder="请选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="选择时段">
          <el-radio-group v-model="timeForm.timeSlot">
            <el-radio label="上午">上午</el-radio>
            <el-radio label="下午">下午</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="checkReservationStatus">查询预约状态</el-button>
        </el-form-item>
      </el-form>

      <!-- 预约状态显示 -->
      <div v-if="reservationStatus !== null" class="status-display">
        <el-alert
          :type="reservationStatus ? 'warning' : 'success'"
          :title="reservationStatus ? '该时间段已被预约' : '该时间段未被预约'"
          show-icon
        />
      </div>
    </div>

    <!-- 弹窗底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { ref } from 'vue'
import roomImage from '@/assets/images/exam.png'
import { ElMessage } from 'element-plus'

export default {
  name: 'ConferenceInfoPop',
  setup() {
    // 弹窗可见性
    const visible = ref(false)
    
    // 会议室数据
    const roomData = ref({
      id: 1,
      number: 'A101',
      address: '北京市朝阳区某某大厦'
    })
    
    // 时间选择表单
    const timeForm = ref({
      date: '',
      timeSlot: '上午'
    })
    
    // 预约状态（null表示未查询，true表示已预约，false表示未预约）
    const reservationStatus = ref(null)
    
    // 显示弹窗
    const show = (room) => {
      roomData.value = room
      visible.value = true
      // 重置查询状态
      reservationStatus.value = null
      // 设置默认日期为今天
      const today = new Date()
      const formattedDate = today.toISOString().split('T')[0]
      timeForm.value.date = formattedDate
    }
    
    // 关闭弹窗
    const handleClose = () => {
      visible.value = false
    }
    
    // 查询预约状态
    const checkReservationStatus = () => {
      if (!timeForm.value.date) {
        ElMessage.warning('请选择日期')
        return
      }
      
      // 模拟查询预约状态的逻辑
      // 在实际应用中，这里应该调用后端API查询预约状态
      console.log('查询预约状态:', {
        roomId: roomData.value.id,
        date: timeForm.value.date,
        timeSlot: timeForm.value.timeSlot
      })
      
      // 模拟查询结果（随机返回已预约或未预约）
      // 在实际应用中，应该根据后端返回的数据来设置状态
      setTimeout(() => {
        // 为了演示，我们随机返回预约状态
        // 在实际应用中，应该根据后端返回的数据来设置状态
        reservationStatus.value = Math.random() > 0.5
      }, 500)
    }
    
    return {
      visible,
      roomData,
      timeForm,
      reservationStatus,
      roomImage,
      show,
      handleClose,
      checkReservationStatus
    }
  }
}
</script>

<style scoped>
.room-details {
  margin-bottom: 20px;
}

.room-details h3 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #333;
}

.reservation-check {
  margin-bottom: 20px;
}

.reservation-check h4 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #333;
}

.status-display {
  margin-top: 15px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
