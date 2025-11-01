<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">会议室预约管理系统登录</h2>
      <div class="input-group">
        <label for="username">账号：</label>
        <input 
          type="text" 
          id="username" 
          v-model="loginForm.username" 
          placeholder="请输入账号"
          class="input-field"
        />
      </div>
      <div class="input-group">
        <label for="password">密码：</label>
        <input 
          type="password" 
          id="password" 
          v-model="loginForm.password" 
          placeholder="请输入密码"
          class="input-field"
        />
      </div>
      <button @click="handleLogin" class="login-button">登录</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    handleLogin() {
      // 登录逻辑可以在这里实现
      console.log('登录信息:', this.loginForm);
      // 这里可以添加实际的登录验证逻辑
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  width: 400px;
  padding: 30px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-title {
  margin-bottom: 30px;
  color: #333;
  font-size: 24px;
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  justify-content: space-between;
}

.input-group label {
  width: 60px;
  text-align: left;
  margin-right: 10px;
  color: #666;
}

.input-field {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.input-field:focus {
  outline: none;
  border-color: #409eff;
}

.login-button {
  width: 100%;
  padding: 12px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
}

.login-button:hover {
  background-color: #337ecc;
}
</style>
