<template>
  <div class="navbar">
    <div class="nav-links">
      <router-link to="/home/conference" class="nav-link" active-class="active">
        会议室预约
      </router-link>
      <router-link to="/home/personal-appointment" class="nav-link" active-class="active">
        个人预约
      </router-link>
    </div>
    <div class="user-info" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
      <img :src="headImage" alt="用户头像" class="user-avatar" />
      <span class="user-name">管理员</span>
      <div v-if="showDropdown" class="dropdown-menu">
        <router-link to="/login" class="dropdown-item">登录</router-link>
        <a href="#" class="dropdown-item" @click.prevent="logout">退出登录</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import headImage from '@/assets/images/head_image.png'

const showDropdown = ref(false)
const router = useRouter()

const logout = () => {
  // 在实际应用中，这里会清除用户的认证状态
  // 例如：localStorage.removeItem('token')
  
  // 跳转到登录页面
  router.push('/login')
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  background-color: #409eff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: #5b8cbc;
}

.nav-links {
  display: flex;
  gap: 30px;
}

.nav-link {
  text-decoration: none;
  color: #333;
  font-size: 16px;
  padding: 8px 16px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.nav-link:hover {
  background-color: #f5f5f5;
}

.nav-link.active {
  background-color: #409eff;
  color: #fff;
}

.user-info {
  position: relative;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-size: 14px;
  color: #333;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: #fff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 10px 0;
  z-index: 1000;
  min-width: 120px;
}

.dropdown-item {
  display: block;
  padding: 8px 16px;
  text-decoration: none;
  color: #333;
  font-size: 14px;
  transition: background-color 0.3s;
}

.dropdown-item:hover {
  background-color: #f5f5f5;
}
</style>
