import { createApp } from "vue";
import { createRouter, createWebHashHistory } from 'vue-router'

import App from "./App.vue";

// import "~/styles/element/index.scss";

// import ElementPlus from "element-plus";
// import all element css, uncommented next line
// import "element-plus/dist/index.css";

// or use cdn, uncomment cdn link in `index.html`

import "~/styles/index.scss";
import "uno.css";

// If you want to use ElMessage, import it.
import "element-plus/theme-chalk/src/message.scss";





// 定义路由，没几个页面直接写在这里，也可以独立一个路由文件
const hello = () => import('./pages/HelloWorld.vue')
const LocalExcel = () => import('./pages/LocalExcel.vue')
const ShareExcel = () => import('./pages/ShareExcel.vue')
const EchartDemo = () => import('./pages/EchartDemo.vue')

const routes = [
    { path: '/', component: hello, meta: { keepAlive: true } },
    { path: '/local', component: LocalExcel, meta: { keepAlive: true } },
    { path: '/share', component: ShareExcel, meta: { keepAlive: true } },
    { path: '/chart', component: EchartDemo, meta: { keepAlive: true } },
]
// 创建路由实例并传递 `routes` 配置

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})


const app = createApp(App);
// app.use(ElementPlus);

//整个应用支持路由。
app.use(router)


app.mount("#app");
