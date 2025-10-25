<template>
  <div class="conference-container">
    <!-- 标题 -->
    <div class="header">
      <h1>会议室预约列表</h1>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="预约时间">
          <el-date-picker
            v-model="searchForm.time"
            type="date"
            placeholder="请选择预约日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="会议室房间号">
          <el-input v-model="searchForm.roomNumber" placeholder="请输入预约室房间号" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch">搜索</el-button>
          <el-button @click="onReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 会议室列表 -->
    <div class="room-list">
      <el-row :gutter="20">
        <el-col :span="4" v-for="room in rooms" :key="room.id" class="room-item-col">
          <el-card class="room-item">
            <div class="room-image">
              <img :src="roomImage" alt="会议室图片" />
            </div>
            <div class="room-info">
              <h3>会议室 {{ room.number }}</h3>
            </div>
            <div class="room-actions">
              <el-button size="small" @click="viewDetails(room)">查看详情</el-button>
              <el-button type="primary" size="small" @click="reserveRoom(room)">预约</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 分页器 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[4, 8, 12, 16]"
        :small="false"
        :disabled="false"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalRooms"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 会议室详情弹窗 -->
    <ConferenceInfoPop ref="conferenceInfoPop" />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import roomImage from '@/assets/images/exam.png'
import ConferenceInfoPop from '@/views/pop/ConferenceInfoPop.vue'

export default {
  name: 'Conference',
  components: {
    ConferenceInfoPop
  },
  setup() {
    // 获取子组件引用
    const conferenceInfoPop = ref(null)
    
    // 搜索表单数据
    const searchForm = ref({
      time: '',
      roomNumber: ''
    })

    // 分页数据
    const currentPage = ref(1)
    const pageSize = ref(8)
    const totalRooms = ref(24)

    // 会议室数据
    const rooms = ref([
      { id: 1, number: 'A101', address: '北京市朝阳区某某大厦A座101室' },
      { id: 2, number: 'A102', address: '北京市朝阳区某某大厦A座102室' },
      { id: 3, number: 'A103', address: '北京市朝阳区某某大厦A座103室' },
      { id: 4, number: 'A104', address: '北京市朝阳区某某大厦A座104室' },
      { id: 5, number: 'B201', address: '北京市朝阳区某某大厦B座201室' },
      { id: 6, number: 'B202', address: '北京市朝阳区某某大厦B座202室' },
      { id: 7, number: 'B203', address: '北京市朝阳区某某大厦B座203室' },
      { id: 8, number: 'B204', address: '北京市朝阳区某某大厦B座204室' }
    ])

    // 方法
    const onSearch = () => {
      console.log('搜索:', searchForm.value)
      // 这里可以添加实际的搜索逻辑
    }

    const onReset = () => {
      searchForm.value.time = ''
      searchForm.value.roomNumber = ''
      console.log('重置搜索条件')
    }

    const viewDetails = (room) => {
      console.log('查看会议室详情:', room)
      // 显示弹窗
      // 确保引用存在且有show方法
      if (conferenceInfoPop.value && typeof conferenceInfoPop.value.show === 'function') {
        conferenceInfoPop.value.show(room)
      } else {
        console.error('无法调用show方法:', conferenceInfoPop.value)
      }
    }

    const reserveRoom = (room) => {
      console.log('预约会议室:', room)
      // 这里可以添加预约的逻辑
    }

    const handleSizeChange = (val) => {
      pageSize.value = val
      console.log(`每页 ${val} 条`)
      // 这里可以添加重新加载数据的逻辑
    }

    const handleCurrentChange = (val) => {
      currentPage.value = val
      console.log(`当前页: ${val}`)
      // 这里可以添加重新加载数据的逻辑
    }

    // 检查组件是否正确挂载
    onMounted(() => {
      console.log('ConferenceInfoPop 组件引用:', conferenceInfoPop.value)
    })

    return {
      conferenceInfoPop,
      searchForm,
      currentPage,
      pageSize,
      totalRooms,
      rooms,
      roomImage,
      onSearch,
      onReset,
      viewDetails,
      reserveRoom,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.conference-container {
  padding: 20px;
}

.header h1 {
  margin-top: 40px;
  color: #333;
}

.search-area {
  padding: 20px;
  border-radius: 4px;
}

.room-list {
  margin-bottom: 20px;
}

.room-item-col {
  margin-bottom: 20px;
}

.room-item {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.room-image {
  text-align: center;
  margin-bottom: 15px;
}

.room-image img {
  max-width: 100%;
  height: 150px;
  object-fit: cover;
}

.room-info {
  flex-grow: 1;
  margin-bottom: 15px;
  text-align: center;
}

.room-info h3 {
  margin: 0;
  color: #333;
}

.room-actions {
  display: flex;
  justify-content: space-between;
}

.pagination {
  display: flex;
  justify-content: center;
  padding: 20px 0;
}
</style>
