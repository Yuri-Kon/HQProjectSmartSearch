<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

// 搜索相关
const searchQuery = ref('')
const searched = ref(false)
const showToast = ref(false)
const toastMessage = ref('')
const courseList = ref([])
const searchedOnce = ref(false)


// 课程详情弹窗相关
const selectedCourse = ref(null) // 当前选中的课程对象
const showDetail = ref(false) // 控制课程详情弹窗的显示与隐藏

// 搜索功能
const searchCourses = () => {
  if (!searchQuery.value.trim()) {
    ElMessage.error('请输入搜索内容')
    return
  }

  showToast.value = true
  toastMessage.value = '正在搜索...'
  searchedOnce.value = true

  axios
    .post(
      'http://localhost:8080/student/searchSmartCourse',
      { query: searchQuery.value.trim() }, // ✅ 去除末尾换行
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
    )
    .then((response) => {
      searched.value = true
      if (response.data && response.data.length > 0) {
        courseList.value = response.data
        showToast.value = false
      } else {
        courseList.value = [] // 清空课程列表
        showToast.value = true
        toastMessage.value = '无匹配结果'
        setTimeout(() => {
          showToast.value = false
        }, 3000)
      }
    })
    .catch((error) => {
      console.error('搜索失败:', error)
      ElMessage.error('搜索失败，请稍后再试')
      showToast.value = false
    })
}

// 查看课程详情
const viewCourseDetail = (courseId) => {
  const course = courseList.value.find(c => c.course_id === courseId)
  if (course) {
    selectedCourse.value = course
    showDetail.value = true
  }
}
</script>

<template>
  <div class="search-container">
    <h1>智能课程搜索</h1>
    <div class="search-box">
      <el-input
        v-model="searchQuery"
        type="textarea"
        placeholder="请输入搜索内容"
        style="width: 500px; margin-bottom: 15px"
      ></el-input>
      <el-button type="primary" @click="searchCourses">搜索</el-button>
    </div>

    <div class="result-container">
      <h2>搜索结果</h2>
      <el-table :data="courseList" style="width: 100%">
        <el-table-column prop="course_id" label="课程编号"></el-table-column>
        <el-table-column prop="course_name" label="课程名称"></el-table-column>
        <el-table-column prop="teacher_name" label="教师姓名"></el-table-column> <!-- ✅ 已替换 -->
        <el-table-column prop="course_fee" label="课程费用"></el-table-column>
        <el-table-column prop="course_state" label="课程状态"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              size="small"
              @click="viewCourseDetail(scope.row.course_id)"
            >
              查看详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 状态提示 -->
      <div class="toast" v-if="showToast">{{ toastMessage }}</div>
    </div>
    <el-dialog v-model="showDetail" title="课程详情" width="500px">
      <div v-if="selectedCourse">
        <p><strong>课程编号：</strong>{{ selectedCourse.course_id }}</p>
        <p><strong>课程名称：</strong>{{ selectedCourse.course_name }}</p>
        <p><strong>教师姓名：</strong>{{ selectedCourse.teacher_name }}</p>
        <p><strong>课程费用：</strong>{{ selectedCourse.course_fee }}</p>
        <p><strong>课程状态：</strong>{{ selectedCourse.course_state }}</p>
        <p><strong>上课时间：</strong>{{ selectedCourse.course_start }} ~ {{ selectedCourse.course_end }}</p>
        <p><strong>上课地点：</strong>{{ selectedCourse.course_place }}</p>
        <p><strong>课程简介：</strong>{{ selectedCourse.course_info }}</p>
      </div>
      <template #footer>
        <el-button @click="showDetail = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.search-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.search-box {
  margin-bottom: 30px;
}

.result-container {
  margin-top: 30px;
}

.toast {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px 20px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  border-radius: 4px;
  z-index: 1000;
}
</style>
