<script setup>
import {useRouter} from 'vue-router';
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import {ElMessageBox} from "element-plus";
import axios from 'axios';
import qs from 'querystring';
import {ElMessage} from 'element-plus';
import {onMounted} from 'vue';

let router = useRouter();
const handleOpen = (value) => {
  if (value === 2.1) {
    router.replace("/student/bindid")
  }
  if (value === 2.3) {
    router.replace("/student/change-password")
  }
  if (value === 1.5) {
    router.replace("/student/check-in")
  }
  if (value === 1.4) {
    router.replace("/student/pay")
  }
  if (value === 1.1) {
    router.replace("/student/search-course")
  }
  if (value === 1.2) {
    router.replace("/student/search-grade")
  }
  console.log(value);
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}

function logOut() {
  ElMessageBox.confirm('确定要退出登录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    router.replace("/login")
  }).catch(() => {
    // 用户点击了取消，不做任何操作
  });
}

//用于验证该用户是否是学生
function verify(){
  let username = sessionStorage.getItem("username");
  let data = {
    username: username,
  }

  axios.post("http://localhost:8080/getUserType", qs.stringify(data))
      .then((res) => {
        if (res.data.userType === "student") {
            //如果成功则不设任何限制
        } else {
          //如果失败则直接传递回login界面
          router.replace("/login");
        }
      })
}

//需要将上面的方法一开始就挂载，但是为了开发的方便暂时注释掉
onMounted(() => {
  // verify();
})
</script>

<template>
  <h1>同学您好！欢迎使用本系统！</h1>
  <el-row class="tac">
    <el-col :span="4">
      <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <document/>
            </el-icon>
            <span>培训相关</span>
          </template>
          <el-menu-item-group title="课程">
            <el-menu-item index="1-1">
              <router-link to="/student/searchCourse">查询课程信息</router-link>
            </el-menu-item>
            <el-menu-item index="1-2">
              <router-link to="/student/searchGrade">查询课程成绩</router-link>
            </el-menu-item>
            <el-menu-item index="1-3">
              <router-link to="/student/courseEvaluate">填写课程评价</router-link>
            </el-menu-item>
            <el-menu-item index="1-4">
              <router-link to="/student/SmartCourseSearch">智能课程搜索</router-link>
            </el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="费用与签到">
            <el-menu-item index="1-4">
              <router-link to="/student/pay">培训费用缴纳</router-link>
            </el-menu-item>
            <el-menu-item index="1-5">
              <router-link to="/student/check">现场签到</router-link>
            </el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <icon-menu/>
            </el-icon>
            <span>账户操作</span>
          </template>
          <el-menu-item index="2-1">
            <router-link to="/student/bindid">绑定身份信息</router-link>
          </el-menu-item>
          <el-menu-item index="2-2" @click="logOut">退出登陆</el-menu-item>
          <el-menu-item index="2-3">
            <router-link to="/student/change-password">修改密码</router-link>
          </el-menu-item>

        </el-sub-menu>
      </el-menu>
    </el-col>
    <el-col :span="3">
    </el-col>
    <el-col :span="15">
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<style scoped></style>