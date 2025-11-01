<template>
  <div class="update-conference-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>{{ isAddMode ? '新增会议室' : '编辑会议室' }}</h2>
    </div>

    <!-- 表单区域 -->
    <div class="form-container">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="120px"
        label-position="right"
      >
        <!-- 会议室名称 -->
        <el-form-item label="会议室名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入会议室名称" />
        </el-form-item>

        <!-- 房号 -->
        <el-form-item label="房号" prop="roomNumber">
          <el-input v-model="formData.roomNumber" placeholder="请输入房号" />
        </el-form-item>

        <!-- 人数 -->
        <el-form-item label="可承受人数" prop="capacity">
          <el-input-number
            v-model="formData.capacity"
            :min="1"
            :max="1000"
            controls-position="right"
          />
        </el-form-item>

        <!-- 面积 -->
        <el-form-item label="面积(㎡)" prop="area">
          <el-input-number
            v-model="formData.area"
            :min="1"
            :max="10000"
            controls-position="right"
          />
        </el-form-item>

        <!-- 用途 -->
        <el-form-item label="用途" prop="purpose">
          <el-input
            v-model="formData.purpose"
            type="textarea"
            placeholder="请输入会议室用途"
            :rows="3"
          />
        </el-form-item>

        <!-- 照片 -->
        <el-form-item label="照片" prop="imageUrl">
          <el-upload
            class="avatar-uploader"
            action=""
            :auto-upload="false"
            :show-file-list="false"
            :on-change="handleImageChange"
          >
            <img v-if="formData.imageUrl" :src="formData.imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
          <div class="image-tip">只能上传一张图片</div>
        </el-form-item>

        <!-- 操作按钮 -->
        <el-form-item class="form-actions">
          <el-button type="primary" @click="handleSubmit">确定</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

// 判断是否为新增模式
const isAddMode = ref(route.query.mode === 'add')

// 表单引用
const formRef = ref()

// 表单数据
const formData = reactive({
  id: null,
  name: '',
  roomNumber: '',
  capacity: 10,
  area: 50,
  purpose: '',
  imageUrl: ''
})

// 表单验证规则
const formRules = {
  name: [
    { required: true, message: '请输入会议室名称', trigger: 'blur' }
  ],
  roomNumber: [
    { required: true, message: '请输入房号', trigger: 'blur' }
  ],
  capacity: [
    { required: true, message: '请输入可承受人数', trigger: 'change' }
  ],
  area: [
    { required: true, message: '请输入面积', trigger: 'change' }
  ],
  purpose: [
    { required: true, message: '请输入会议室用途', trigger: 'blur' }
  ]
}

// 处理图片上传
const handleImageChange = (uploadFile) => {
  const rawFile = uploadFile.raw
  if (rawFile) {
    // 检查文件类型
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif']
    if (!allowedTypes.includes(rawFile.type)) {
      ElMessage.error('只能上传 JPG/PNG/GIF 格式的图片!')
      return
    }
    
    // 检查文件大小 (限制为2MB)
    if (rawFile.size > 2 * 1024 * 1024) {
      ElMessage.error('图片大小不能超过 2MB!')
      return
    }
    
    // 生成预览URL
    formData.imageUrl = URL.createObjectURL(rawFile)
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate((valid) => {
    if (valid) {
      // 这里应该调用API保存数据
      console.log('保存数据:', formData)
      
      // 显示成功消息
      ElMessage.success(isAddMode.value ? '新增会议室成功' : '编辑会议室成功')
      
      // 返回会议室管理页面
      router.push({ name: 'conference' })
    } else {
      console.log('表单验证失败')
      return false
    }
  })
}

// 取消操作
const handleCancel = () => {
  router.push({ name: 'conference' })
}

// 组件挂载时初始化数据
onMounted(() => {
  // 如果是编辑模式，初始化表单数据
  if (!isAddMode.value && route.state?.conferenceData) {
    const data = route.state.conferenceData
    formData.id = data.id
    formData.name = data.name
    formData.roomNumber = data.roomNumber
    formData.capacity = data.capacity
    formData.area = data.area || 50 // 如果没有面积数据，设置默认值
    formData.purpose = data.purpose
    // 注意：图片URL需要从实际数据中获取
    formData.imageUrl = '' // 这里应该设置实际的图片URL
  }
})
</script>

<style scoped lang="scss">
.update-conference-container {
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

.form-container {
  max-width: 600px;
  margin: 0 auto;
}

.form-actions {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  
  .el-button {
    margin: 0 10px;
    min-width: 100px;
  }
}

.avatar-uploader {
  :deep(.el-upload) {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }
  
  :deep(.el-upload:hover) {
    border-color: var(--el-color-primary);
  }
  
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
  }
  
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}

.image-tip {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}
</style>
