<template>
  <el-form inline="true">
    <el-form-item label="创建日期">
      <el-date-picker v-model="datetimeRange" style="width: 240px" type="datetimerange" range-separator="至"
        start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
    </el-form-item>
    <el-form-item label="竞赛名称">
      <el-input v-model="params.title" placeholder="请您输入要搜索的竞赛名称" />
    </el-form-item>
    <el-form-item>
      <el-button @click="onSearch" plain>搜索</el-button>
      <el-button @click="onReset" plain type="info">重置</el-button>
      <el-button type="primary" :icon="Plus" plain @click="onAddExam">添加竞赛</el-button>
    </el-form-item>
  </el-form>
  <!-- 表格 -->
  <el-table height="526px" :data="examList">
    <el-table-column prop="title" label="竞赛标题"/>
    <el-table-column prop="startTime" width="180px" label="竞赛开始时间" />
    <el-table-column prop="endTime" width="180px" label="竞赛结束时间" />
    <el-table-column label="是否开赛" width="100px">
      <template #default="{ row }">
        <div v-if="!isNotStartExam(row)">
          <el-tag type="warning">已开赛</el-tag>
        </div>
        <div v-else>
          <el-tag type="info">未开赛</el-tag>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="status" width="100px" label="是否发布">
      <template #default="{ row }">
        <div v-if="row.status == 0">
          <el-tag type="danger">未发布</el-tag>
        </div>
        <div v-if="row.status == 1">
          <el-tag type="success">已发布</el-tag>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="createName" width="140px" label="创建用户" />
    <el-table-column prop="createTime" width="180px" label="创建时间" />
    <el-table-column label="操作" width="180px">
      <template #default="{ row }">
        <el-button v-if="isNotStartExam(row) && row.status == 0" type="text" @click="onEdit(row.examId)">编辑
        </el-button>
        <el-button v-if="isNotStartExam(row) && row.status == 0" type="text" @click="onDelete(row.examId)" class="red">删除
        </el-button>
        <el-button v-if="row.status == 1 && isNotStartExam(row)" type="text"
          @click="cancelPublishExam(row.examId)">撤销发布</el-button>
        <el-button v-if="row.status == 0 && isNotStartExam(row)" type="text"
          @click="publishExam(row.examId)">发布</el-button>
        <el-button type="text" v-if="!isNotStartExam(row)">已开赛，不允许操作</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!-- 分页区域 -->
  <el-pagination background size="small" layout="total, sizes, prev, pager, next, jumper" :total="total"
    v-model:current-page="params.pageNum" v-model:page-size="params.pageSize" :page-sizes="[5, 10, 15, 20]"
    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue'
import { getExamListService, delExamService, publishExamService, cancelPublishExamService } from '@/apis/exam'
import { reactive,ref } from 'vue'
import router from '@/router'

function isNotStartExam(exam) {
  const now = new Date(); //当前时间
  return new Date(exam.startTime) > now
}

const params = reactive({
  pageNum: 1,
  pageSize: 10,
  startTime:'',
  endTime:'',
  title: ''
})

const examList = ref([])
const total = ref(0)
const datetimeRange = ref([])

async function getExamList() {
  if (datetimeRange.value[0] instanceof Date) {
    params.startTime = formatDateTime(datetimeRange.value[0])
  }
  if (datetimeRange.value[1] instanceof Date) {
    params.endTime = formatDateTime(datetimeRange.value[1])
  }
  const result = await getExamListService(params)
  examList.value = result.rows
  total.value = result.total
}

function formatDateTime(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

getExamList()

function handleSizeChange(newSize) {
  params.pageNum = 1
  getExamList()
}

function handleCurrentChange(newPage) {
  getExamList()
}

function onSearch() {
  params.pageNum = 1
  getExamList()
}

function onReset() {
  params.pageNum = 1
  params.pageSize = 10
  params.title = ''
  params.startTime = ''
  params.endTime = ''
  datetimeRange.value.length = 0
  getExamList()
}

function onAddExam() {
  //跳转到新的页面上   
  router.push("/oj/layout/updateExam?type=add")
}

async function onEdit(examId) {
  // await getExamDetailService(examId)
  router.push(`/oj/layout/updateExam?type=edit&examId=${examId}`)
  ///oj/layout/updateExam?type=edit&examId=${examId}
  ///oj/layout/updateExam?type=edit&examId=123
}

async function onDelete(examId) {
  await delExamService(examId)
  params.pageNum = 1
  getExamList()
}

async function publishExam(examId) {
  await publishExamService(examId)
  getExamList()
}

async function cancelPublishExam(examId) {
  await cancelPublishExamService(examId)
  getExamList()
}

</script>