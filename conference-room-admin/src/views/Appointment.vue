<template>
  <div class="appointment-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>预约管理</h2>
    </div>
    
    <!-- 预约列表 -->
    <div class="appointment-list">
      <div 
        v-for="appointment in appointmentList" 
        :key="appointment.id" 
        class="appointment-item"
        :class="{ 'pending': appointment.status === '未审批' }"
      >
        <!-- 预约室名称 -->
        <div class="room-name">{{ appointment.roomName }}</div>
        
        <!-- 预约信息 -->
        <div class="appointment-info">
          <div class="info-row">
            <span class="label">预约人：</span>
            <span class="value">{{ appointment.applicant }}</span>
          </div>
          <div class="info-row">
            <span class="label">预约时间：</span>
            <span class="value">{{ appointment.time }}</span>
          </div>
          <div class="info-row">
            <span class="label">预约状态：</span>
            <span class="value" :class="getStatusClass(appointment.status)">{{ appointment.status }}</span>
          </div>
        </div>
        
        <!-- 审批按钮（仅对未审批的预约显示） -->
        <div v-if="appointment.status === '未审批'" class="approval-button">
          <el-button type="primary" size="small">审批</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

// 预约列表数据
const appointmentList = ref([
  {
    id: 1,
    roomName: '会议室A',
    applicant: '张三',
    time: '2025年10月30号 10:00-13:00',
    status: '未审批'
  },
  {
    id: 2,
    roomName: '会议室B',
    applicant: '李四',
    time: '2025年10月31号 14:00-16:00',
    status: '预约已使用'
  },
  {
    id: 3,
    roomName: '会议室C',
    applicant: '王五',
    time: '2025年11月1号 09:00-11:00',
    status: '有预约未使用'
  },
  {
    id: 4,
    roomName: '会议室D',
    applicant: '赵六',
    time: '2025年11月2号 15:00-17:00',
    status: '已取消'
  },
  {
    id: 5,
    roomName: '会议室E',
    applicant: '孙七',
    time: '2025年11月3号 13:00-15:00',
    status: '未审批'
  }
])

// 根据状态获取对应的CSS类名
const getStatusClass = (status) => {
  switch (status) {
    case '有预约未使用':
      return 'status-unused'
    case '预约已使用':
      return 'status-used'
    case '未审批':
      return 'status-pending'
    case '已取消':
      return 'status-cancelled'
    default:
      return ''
  }
}

// 组件挂载时获取数据
onMounted(() => {
  // 这里应该调用API获取预约数据
  console.log('获取预约数据')
})
</script>

<style scoped lang="scss">
.appointment-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.page-header {
  margin-bottom: 20px;
  
  h2 {
    margin: 0;
    color: #333;
  }
}

.appointment-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.appointment-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #fafafa;
  
  &.pending {
    border-left: 4px solid #409eff;
  }
}

.room-name {
  flex: 0 0 150px;
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.appointment-info {
  flex: 1;
  
  .info-row {
    margin-bottom: 8px;
    
    &:last-child {
      margin-bottom: 0;
    }
    
    .label {
      display: inline-block;
      width: 80px;
      color: #666;
    }
    
    .value {
      color: #333;
      
      &.status-unused {
        color: #e6a23c;
      }
      
      &.status-used {
        color: #67c23a;
      }
      
      &.status-pending {
        color: #409eff;
      }
      
      &.status-cancelled {
        color: #909399;
      }
    }
  }
}

.approval-button {
  flex: 0 0 auto;
}
</style>
