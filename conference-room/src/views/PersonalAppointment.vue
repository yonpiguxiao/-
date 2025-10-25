<template>
  <div class="personal-appointment">
    <div class="header">
      <h1>个人预约列表</h1>
    </div>
    <div class="appointment-list">
      <div 
        v-for="appointment in appointments" 
        :key="appointment.id" 
        class="appointment-item"
      >
        <div class="room-info">
          {{ appointment.roomName }}
          <span class="status-tag" :class="getStatusClass(appointment.status)">{{ appointment.status }}</span>
        </div>
        <div class="time-info">
          <span>{{ formatDateTime(appointment.date, appointment.timeSlot) }}</span>
          <button 
            v-if="isFutureAppointment(appointment.date, appointment.timeSlot)" 
            class="cancel-btn"
            @click="cancelAppointment(appointment.id)"
          >
            取消预约
          </button>
        </div>
      </div>
      
      <!-- 当没有预约时显示 -->
      <div v-if="appointments.length === 0" class="no-appointments">
        暂无预约记录
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PersonalAppointment',
  data() {
    return {
      // 模拟预约数据
      appointments: [
        {
          id: 1,
          roomName: '会议室A',
          date: '2025-10-26',
          timeSlot: '09:00-10:00',
          status: '已通过'
        },
        {
          id: 2,
          roomName: '会议室B',
          date: '2025-10-25',
          timeSlot: '14:00-15:00',
          status: '审批中'
        },
        {
          id: 3,
          roomName: '会议室C',
          date: '2025-10-27',
          timeSlot: '11:00-12:00',
          status: '未通过'
        },
        {
          id: 4,
          roomName: '会议室D',
          date: '2025-10-24',
          timeSlot: '09:00-11:00',
          status: '已过期'
        }
      ]
    }
  },
  methods: {
    /**
     * 判断是否为未来的预约
     * @param {string} date - 预约日期 'YYYY-MM-DD'
     * @param {string} timeSlot - 时间段 'HH:MM-HH:MM'
     * @returns {boolean}
     */
    isFutureAppointment(date, timeSlot) {
      // 解析时间段获取结束时间
      const endTime = timeSlot.split('-')[1];
      const [endHour, endMinute] = endTime.split(':').map(Number);
      
      // 创建预约结束时间对象
      const [year, month, day] = date.split('-').map(Number);
      const appointmentEndDate = new Date(year, month - 1, day, endHour, endMinute);
      
      // 获取当前时间
      const now = new Date();
      
      // 比较时间
      return appointmentEndDate > now;
    },
    
    /**
     * 格式化日期时间显示
     * @param {string} date - 'YYYY-MM-DD'
     * @param {string} timeSlot - 'HH:MM-HH:MM'
     * @returns {string} - 'YYYY年MM月DD日 HH:MM-HH:MM'
     */
    formatDateTime(date, timeSlot) {
      // 解析日期
      const [year, month, day] = date.split('-');
      
      return `${year}年${month}月${day}日 ${timeSlot}`;
    },
    
    /**
     * 获取状态标签的样式类
     * @param {string} status - 预约状态
     * @returns {string} - 样式类名
     */
    getStatusClass(status) {
      switch (status) {
        case '已通过':
          return 'status-approved';
        case '审批中':
          return 'status-pending';
        case '未通过':
          return 'status-rejected';
        case '已过期':
          return 'status-expired';
        default:
          return '';
      }
    },

    /**
     * 取消预约
     * @param {number} id - 预约ID
     */
    cancelAppointment(id) {
      // 这里应该调用API取消预约
      // 现在我们只是从前端移除
      this.appointments = this.appointments.filter(app => app.id !== id);
      alert(`预约已取消`);
    }
  }
}
</script>

<style scoped>


.personal-appointment {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  .header h1 {
    margin-top: 40px;
    color: #333;
  }
}



.appointment-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.appointment-item {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.room-info {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.time-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  color: #666;
}

.cancel-btn {
  background-color: #ff4d4f;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 6px 12px;
  cursor: pointer;
  font-size: 14px;
}

.cancel-btn:hover {
  background-color: #ff7875;
}

.no-appointments {
  text-align: center;
  color: #999;
  font-size: 16px;
  padding: 30px 0;
}

.status-tag {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: normal;
  margin-left: 10px;
  vertical-align: middle;
}

.status-approved {
  background-color: #f6ffed;
  color: #52c41a;
  border: 1px solid #b7eb8f;
}

.status-pending {
  background-color: #e6f7ff;
  color: #1890ff;
  border: 1px solid #91d5ff;
}

.status-rejected {
  background-color: #fff2f0;
  color: #ff4d4f;
  border: 1px solid #ffccc7;
}

.status-expired {
  background-color: #f9f9f9;
  color: #bfbfbf;
  border: 1px solid #d9d9d9;
}
</style>
