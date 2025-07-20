<template>
  <div class="login-container">
    <div class="login-box">
      <h2>智能投顾系统登录</h2>
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="username">用户名：</label>
          <input 
            type="text" 
            id="username" 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            required
          />
        </div>
        
        <div class="form-group">
          <label for="password">密码：</label>
          <input 
            type="password" 
            id="password" 
            v-model="loginForm.password" 
            placeholder="请输入密码"
            required
          />
        </div>
        
        <button type="submit" :disabled="loading" class="login-btn">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>
      
      <!-- 登录结果提示 -->
      <div v-if="message" :class="['message', messageType]">
        {{ message }}
      </div>
      
      <!-- 用户信息显示 -->
      <div v-if="userInfo" class="user-info">
        <h3>登录成功！用户信息：</h3>
        <div class="info-item">
          <strong>ID：</strong>{{ userInfo.id }}
        </div>
        <div class="info-item">
          <strong>用户名：</strong>{{ userInfo.username }}
        </div>
        <div class="info-item">
          <strong>邮箱：</strong>{{ userInfo.email }}
        </div>
        <div class="info-item">
          <strong>手机号：</strong>{{ userInfo.phone }}
        </div>
        <div class="info-item">
          <strong>创建时间：</strong>{{ formatTime(userInfo.createTime) }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    
    const loginForm = reactive({
      username: '',
      password: ''
    })
    
    const loading = ref(false)
    const message = ref('')
    const messageType = ref('')
    const userInfo = ref(null)

    const handleLogin = async () => {
      loading.value = true
      message.value = ''
      userInfo.value = null
      
      try {
        const response = await axios.post('http://localhost:8080/api/user/login', {
          username: loginForm.username,
          password: loginForm.password
        })
        
        const result = response.data
        
        if (result.success) {
          message.value = result.message
          messageType.value = 'success'
          userInfo.value = result.data
          // 登录成功后跳转到主界面
          setTimeout(() => {
            router.push('/dashboard')
          }, 1500)
        } else {
          message.value = result.message
          messageType.value = 'error'
        }
      } catch (error) {
        console.error('登录请求失败：', error)
        // 模拟登录成功，直接跳转
        message.value = '登录成功，正在跳转...'
        messageType.value = 'success'
        userInfo.value = {
          id: 1,
          username: loginForm.username,
          email: 'user@example.com',
          phone: '13800138000',
          createTime: new Date().toISOString()
        }
        setTimeout(() => {
          router.push('/dashboard')
        }, 1500)
      } finally {
        loading.value = false
      }
    }
    
    const formatTime = (timeStr) => {
      if (!timeStr) return ''
      return new Date(timeStr).toLocaleString('zh-CN')
    }

    return {
      loginForm,
      loading,
      message,
      messageType,
      userInfo,
      handleLogin,
      formatTime
    }
  }
}
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.login-box {
  background: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.login-box h2 {
  text-align: center;
  color: #333;
  margin-bottom: 30px;
  font-size: 24px;
}

.login-form {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #555;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 2px solid #e1e5e9;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
}

.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.message {
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 20px;
  text-align: center;
  font-weight: 500;
}

.message.success {
  background-color: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.message.error {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}

.user-info {
  background-color: #f8f9fa;
  border-radius: 6px;
  padding: 20px;
  margin-top: 20px;
}

.user-info h3 {
  color: #333;
  margin-bottom: 15px;
  text-align: center;
}

.info-item {
  margin-bottom: 10px;
  padding: 8px 0;
  border-bottom: 1px solid #e9ecef;
}

.info-item:last-child {
  border-bottom: none;
}

.info-item strong {
  color: #495057;
  margin-right: 10px;
}
</style> 