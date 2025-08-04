<template>
    <el-form inline="true">
        <el-form-item>
            <selector v-model="params.difficulty" placeholder="请选择题⽬难度" style="width: 200px;"></selector>
        </el-form-item>
        <el-form-item>
            <el-input v-model="params.title" placeholder="请您输⼊要搜索的题⽬标题" />
        </el-form-item>
        <el-form-item>
            <el-button plain @Click="onSearch">搜索</el-button>
            <el-button plain type="info" @Click="onReset">重置</el-button>
            <el-button plain type="primary" :icon="Plus" @Click="onAddQuestion">添加题⽬</el-button>
        </el-form-item>
    </el-form>
    <el-table height="526px" :data="questionList">
        <el-table-column prop="questionId" width="180px" label="题⽬id" />
        <el-table-column prop="title" label="题⽬标题" />
        <el-table-column prop="difficulty" label="题⽬难度" width="90px">
            <template #default="{ row }">
                <div v-if="row.difficulty === 1" style="color:#3EC8FF;">简单</div>
                <div v-if="row.difficulty === 2" style="color:#FE7909;">中等</div>
                <div v-if="row.difficulty === 3" style="color:#FD4C40;">困难</div>
            </template>
        </el-table-column>
        <el-table-column prop="createName" label="创建⼈" width="140px" />
        <el-table-column prop="createTime" label="创建时间" width="180px" />
        <el-table-column label="操作" width="100px" fixed="right">
            <template #default="{ row }">
                <el-button type="text" @Click="onEdit(row.questionId)">编辑
                </el-button>
                <el-button type="text" class="red" @Click="onDelete(row.questionId)">删除
                </el-button>
            </template>
        </el-table-column>
    </el-table> 
    <el-pagination background size="small" layout="total, sizes, prev, pager, next, jumper" :total="total"  
    v-model:current-page="params.pageNum"  v-model:page-size="params.pageSize"
    :page-sizes="[5, 10, 20, 30]" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    <question-drawer ref="questionDrawerRef" @success="onSuccess"></question-drawer>
</template>
<script setup>
import { Plus } from "@element-plus/icons-vue"
import Selector from "@/components/QuestionSelector.vue"
import { getQuestionListService, delQuestionService } from "@/apis/question";
import { reactive, ref } from "vue";
import QuestionDrawer from "@/components/QuestionDrawer.vue";

const params = reactive({
    pageNum: 1,
    pageSize: 10,
    difficulty: '',
    title: ''
})

const questionList = ref([])
const total = ref(0)


async function getQuestionList() {
    const result = await getQuestionListService(params)
    console.log(result)
    questionList.value = result.rows
    total.value = result.total
}
getQuestionList()

function handleSizeChange(newSize) {
    //params.pageSize = newSize
    params.pageNum = 1
    getQuestionList()
}

function handleCurrentChange(newPage) {
    // params.pageNum = newPage
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

const questionDrawerRef = ref()

function onAddQuestion() {
    questionDrawerRef.value.open()
}

function onSuccess(service) {
  if (service === 'add') {
    params.pageNum = 1
  }
  getQuestionList()
}

async function onEdit(questionId) {
    questionDrawerRef.value.open(questionId)
}

async function onDelete(questionId) {
    await delQuestionService(questionId)
    params.pageNum = 1
    getQuestionList()  
}
</script>