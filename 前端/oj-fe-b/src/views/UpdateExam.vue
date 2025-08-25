<template>
  <div class="add-exam-component-box">
    <div class="add-exam-component">
      <!-- 竞赛信息模块 -->
      <div class="exam-base-info-box">
        <!-- 标题 -->
        <div class="exam-base-title">
          <span class="base-title">{{ type === 'edit' ? '编辑竞赛' : '添加竞赛' }}</span>
          <span class="go-back" @click="goBack">返回</span>
        </div>
        <!-- 基本信息 -->
        <div class="exam-base-info">
          <div class="group-box">
            <div class="group-item">
              <div class="item-label required">竞赛名称</div>
              <div>
                <el-input v-model="formExam.title" style="width:420px" placeholder="请填写竞赛名称"></el-input>
              </div>
            </div>
          </div>
          <div class="group-box">
            <div class="group-item">
              <div class="item-label required">竞赛周期</div>
              <div>
                <el-date-picker v-model="formExam.examDate" :disabledDate="disabledDate" type="datetimerange"
                  start-placeholder="竞赛开始时间" end-placeholder="竞赛结束时间" value-format="YYYY-MM-DD HH:mm:ss" />
              </div>
            </div>
          </div>
          <div class="group-box">
            <div class="group-item">
              <el-button class="exam-base-info-button" type="primary" plain @click="saveBaseInfo">保存</el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 添加竞赛题目 -->
      <div class="exam-select-question-box">
        <el-button class="exam-add-question" :icon="Plus" type="text" @click="addQuestion()">
          添加题目
        </el-button>
        <el-table height="336px" :data="formExam.examQuestionList" class="question-select-list">
          <el-table-column prop="questionId" width="180px" label="题目id" />
          <el-table-column prop="title" :show-overflow-tooltip="true" label="题目标题" />
          <el-table-column prop="difficulty" width="80px" label="题目难度">
            <template #default="{ row }">
              <div v-if="row.difficulty === 1" style="color:#3EC8FF;">简单</div>
              <div v-if="row.difficulty === 2" style="color:#FE7909;">中等</div>
              <div v-if="row.difficulty === 3" style="color:#FD4C40;">困难</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80px">
            <template #default="{ row }">
              <el-button circle type="text" @click="deleteExamQuestion(formExam.examId, row.questionId)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 题目配置模块 题目列表勾选加序号 -->
      <div>
        <el-dialog v-model="dialogVisible">
          <div class="exam-list-box">
            <div class="exam-list-title required">选择竞赛题目</div>
            <el-form inline="true">
              <el-form-item label="题目难度">
                <selector v-model="params.difficulty" style="width: 120px;"></selector>
              </el-form-item>
              <el-form-item label="题目名称">
                <el-input v-model="params.title" placeholder="请您输入要搜索的题目标题" />
              </el-form-item>
              <el-form-item>
                <el-button @click="onSearch" plain>搜索</el-button>
                <el-button @click="onReset" plain type="info">重置</el-button>
              </el-form-item>

            </el-form>
            <!-- 题目列表 -->
            <el-table :data="questionList" @select="handleRowSelect">
              <el-table-column type="selection"></el-table-column>
              <el-table-column prop="questionId" label="题目id" />
              <el-table-column prop="title" label="题目标题" />
              <el-table-column prop="difficulty" label="题目难度">
                <template #default="{ row }">
                  <div v-if="row.difficulty === 1" style="color:#3EC8FF;">简单</div>
                  <div v-if="row.difficulty === 2" style="color:#FE7909;">中等</div>
                  <div v-if="row.difficulty === 3" style="color:#FD4C40;">困难</div>
                </template>
              </el-table-column>
            </el-table>
            <!-- 分页区域 -->
            <div class="exam-question-list-button">
              <el-pagination background size="small" layout="total, sizes, prev, pager, next, jumper" :total="total"
                v-model:current-page="params.pageNum" v-model:page-size="params.pageSize"
                :page-sizes="[1, 5, 10, 15, 20]" @size-change="handleSizeChange"
                @current-change="handleCurrentChange" />
              <el-button class="question-select-submit" type="primary" plain
                @click="submitSelectQuestion">提交</el-button>
            </div>
          </div>
        </el-dialog>
      </div>

      <!-- 提交任务区域 -->
      <div class="submit-box absolute">
        <el-button type="info" plain @click="goBack">取消</el-button>
        <el-button type="primary" plain @click="publishExam">发布竞赛</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { examAddService, addExamQuestionService, getExamDetailService, editExamService, delExamQuestionService, publishExamService} from "@/apis/exam"
import { getQuestionListService } from "@/apis/question"
import Selector from "@/components/QuestionSelector.vue"
import router from '@/router'
import { reactive, ref } from "vue"
import { Plus } from '@element-plus/icons-vue'
import { useRoute } from 'vue-router';

const type = useRoute().query.type
const formExam = reactive({
  examId: '',
  title: '',
  examDate: ''
})

const params = reactive({
  pageNum: 1,
  pageSize: 10,
  difficulty: '',
  title: ''
})


// 返回
function goBack() {
  router.go(-1)
}

async function saveBaseInfo() {
  const fd = new FormData()
  for (let key in formExam) {
    if (key === 'examDate') {
      fd.append('startTime', formExam.examDate[0]);
      fd.append('endTime', formExam.examDate[1]);
    } else if (key !== 'startTime' && key !== 'endTime') {
      fd.append(key, formExam[key])
    }
  }
  if (formExam.examId) {
    //编辑
    await editExamService(fd)
  } else {
    const addRes = await examAddService(fd)
    formExam.examId = addRes.data   
  }
  // const addRes = await examAddService(fd)
  // formExam.examId = addRes.data   
  ElMessage.success('基本信息保存成功')
}

const questionList = ref([])
const total = ref(0)
async function getQuestionList() {
  const result = await getQuestionListService(params)
  console.log(result)
  questionList.value = result.rows
  total.value = result.total
}

const dialogVisible = ref(false)
function addQuestion() {
  if (formExam.examId === null || formExam.examId === '') {
    ElMessage.error('请先保存竞赛基本信息')
  } else {
    getQuestionList()
    dialogVisible.value = true
  }
}

function handleSizeChange() {
  params.pageNum = 1
  getQuestionList()
}

function handleCurrentChange() {
  getQuestionList()
}


function onSearch() {
  params.pageNum = 1
  getQuestionList()
}

function onReset() {
  params.pageNum = 1
  params.pageSize = 10
  params.title = ''
  params.difficulty = ''
  getQuestionList()
}

async function publishExam() {
  await publishExamService(formExam.examId)
  router.push("/oj/layout/exam")
}

const questionIdSet = ref([])

function handleRowSelect(selection) {
  questionIdSet.value = []
  selection.forEach(element => {
    questionIdSet.value.push(element.questionId)
  });
}

async function submitSelectQuestion() {
  if (questionIdSet.value && questionIdSet.value.length < 1) {
    ElMessage.error('请先选择要提交的题目')
    return false
  }
  const examQ = reactive({
    examId: formExam.examId,
    questionIdSet: questionIdSet.value
  })
  console.log(examQ)
  await addExamQuestionService(examQ);
  getExamDetailById(formExam.examId)
  dialogVisible.value = false
  ElMessage.success('竞赛题目添加成功')
}

async function getExamDetail() {
  const examId = useRoute().query.examId
  if (examId) {
    formExam.examId = examId
    getExamDetailById(examId)
  }
}
getExamDetail()

async function deleteExamQuestion(examId, questionId) {
  await delExamQuestionService(examId, questionId)
  getExamDetailById(examId)
  ElMessage.success('竞赛题目删除成功')
}

async function getExamDetailById(examId) {
  const examDetail = await getExamDetailService(examId)
  formExam.examQuestionList = []
  Object.assign(formExam, examDetail.data)
  formExam.examDate = [examDetail.data.startTime, examDetail.data.endTime]
}

</script>

<style lang="scss" scoped>
.add-exam-component-box {
  height: 100%;
  overflow: hidden;
  position: relative;
}

.exam-list-box {
  background: #fff;
  padding: 20px 24px;

  .question-select-submit {
    margin-left: 0;
    margin-top: 20px;
    width: 100%;
  }

  .exam-list-title {
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    position: relative;
    padding: 15px 20px;
    padding-top: 0;

    &.required::before {
      position: absolute;
      content: '*';
      font-size: 20px;
      color: red;
      left: 10px;
    }
  }
}

.add-exam-component {
  width: 100%;
  background: #fff;
  padding-bottom: 120px;
  overflow-y: auto;
  box-sizing: border-box;
  height: calc(100vh - 50px);
  margin-top: -10px;

  .exam-select-question-box {

    background: #fff;
    border-bottom: 1px solid #fff;
    border-radius: 2px;
    width: 100%;

    .exam-add-question {
      font-size: 14px;
      float: right;
      margin: 10px 20px 5px 0;
    }

    .question-select-list {
      margin: 0 0 20px 0;
      height: 200px;
    }
  }

  .exam-base-info-box {
    background: #fff;
    border-bottom: 1px solid #fff;
    border-radius: 2px;
    margin-bottom: 10px;
    width: 100%;
    box-sizing: border-box;

    .exam-base-title {
      width: 100%;
      box-sizing: border-box;
      height: 52px;
      border-bottom: 1px solid #e9e9e9;
      display: flex;
      justify-content: space-between;
      align-items: center;

      .base-title {
        font-size: 16px;
        font-weight: 500;
        color: #333333;
      }

      .go-back {
        color: #999;
        cursor: pointer;
      }
    }

    .exam-base-info {
      box-sizing: border-box;
      border-bottom: 1px solid #e9e9e9;
    }

    .mesage-list-content {
      box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.1);
      background-color: rgba(255, 255, 255, 1);
      border-radius: 10px;
      width: 1200px;
      margin-top: 20px;
    }
  }

  .group-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: calc(100% - 64px);
    margin: 24px 0;

    .group-item {
      display: flex;
      align-items: center;
      width: 100%;

      .exam-base-info-button {
        margin-left: 104px;
        width: 420px;
      }

      .item-label {
        font-size: 14px;
        font-weight: 400;
        width: 94px;
        text-align: left;
        color: rgba(0, 0, 0, 0.85);
        position: relative;
        padding-left: 10px;

        &.required::before {
          position: absolute;
          content: '*';
          font-size: 20px;
          color: red;
          left: 0px;
          top: -2px;
        }
      }
    }
  }

  .submit-box {
    display: flex;
    align-items: center;
    justify-content: center;
    background: transparent;

    &.absolute {
      position: absolute;
      width: calc(100% - 48px);
      bottom: 0;
      background: #fff;
      z-index: 999;
    }
  }
}
</style>

<style>
.w-e-text-container {
  min-height: 142px;
}
</style>