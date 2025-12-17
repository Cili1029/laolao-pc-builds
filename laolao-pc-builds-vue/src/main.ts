import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import VueCookies from 'vue3-cookies'
import { createPinia } from 'pinia'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(VueCookies, {
  expireTimes: "7d", // 默认过期时间
  path: "/",
  domain: "",
  secure: false, // 开发环境用 false，生产环境用 true
  sameSite: "Lax"
})
app.mount('#app')
