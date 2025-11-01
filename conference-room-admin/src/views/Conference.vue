<template>
  <div class="conference-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>会议室管理列表</h2>
    </div>
    
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="房间号">
          <el-input
            v-model="searchForm.roomNumber"
            placeholder="请输入房间号"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="success" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <!-- 表格区域 -->
    <div class="table-container">
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="roomNumber" label="房号" />
        <el-table-column prop="capacity" label="可承受人数" />
        <el-table-column prop="purpose" label="用途" />
        <el-table-column label="照片">
          <template #default="scope">
            <el-image
              :src="examImage"
              class="room-image"
              :preview-src-list="[examImage]"
              :initial-index="0"
              fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column label="状态">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              active-text="可预约"
              inactive-text="不可预约"
              @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
    <!-- 分页器 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import examImage from '@/assets/images/exam.png'

const router = useRouter()

// 搜索表单数据
const searchForm = ref({
  appointmentTime: '',
  roomNumber: ''
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    name: '会议室A',
    roomNumber: 'A101',
    capacity: 10,
    purpose: '日常会议',
    status: 1 // 1: 可预约, 0: 不可预约
  },
  {
    id: 2,
    name: '会议室B',
    roomNumber: 'B202',
    capacity: 20,
    purpose: '培训会议',
    status: 0
  }
])

// 分页数据
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 搜索功能
const handleSearch = () => {
  console.log('搜索:', searchForm.value)
  // 这里应该调用API获取数据
}

// 重置功能
const handleReset = () => {
  searchForm.value = {
    appointmentTime: '',
    roomNumber: ''
  }
}

// 新增功能
const handleAdd = () => {
  console.log('新增会议室')
  router.push({ name: 'update-conference', query: { mode: 'add' } })
}

// 编辑功能
const handleEdit = (row) => {
  console.log('编辑会议室:', row)
  router.push({ name: 'update-conference', query: { mode: 'edit', id: row.id }, state: { conferenceData: row } })
}

// 删除功能
const handleDelete = (row) => {
  console.log('删除会议室:', row)
  // 这里应该调用API删除数据
}

// 状态切换
const handleStatusChange = (row) => {
  console.log('状态切换:', row)
  // 这里应该调用API更新状态
}

// 分页功能
const handleSizeChange = (val) => {
  pagination.value.pageSize = val
  console.log('每页条数改变:', val)
}

const handleCurrentChange = (val) => {
  pagination.value.currentPage = val
  console.log('当前页改变:', val)
}

// 组件挂载时获取数据
onMounted(() => {
  // 这里应该调用API获取初始数据
})
</script>

<style scoped lang="scss">
.conference-container {
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

.search-container {
  padding: 20px;
  border-radius: 4px;
}

.search-form {
  .el-form-item {
    margin-right: 20px;
  }
}

.table-container {
  margin-bottom: 20px;
}

.room-image {
  width: 50px;
  height: 50px;
  border-radius: 4px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}
</style>
