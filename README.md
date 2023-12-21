# 使用Java + Vue3实现 LuckySheet表格Demo 💓 💞 💘 💖

[在线演示GitHub](https://liyang-it.github.io/LuckySheet-Java/#/)


[在线演示内网穿透](http://120.78.134.13/sheet)

## 实现功能😆
1. 单表格数据编辑
2. 多人协同表格编辑
3. 前端方式导出Excel
4. 后端方式导出Excel


## Java开发环境😁
* JDK >= ~1.7~ 改为 JDK21,主要尝鲜使用 虚拟线程 [免费可商用JDK21下载](https://bell-sw.com/pages/downloads/#jdk-21-lts)
* SpringBoot ~2.5.2~ 同JDK一致改为使用最新的 `3.2.0` 版本
* MySQL 5.6.40
* MyBatis ~2.2.2~ 更改为与 SpringBoot`3.2.0` 支持的版本`3.0.3`

使用 `JDK21` 主要是体验`虚拟线程`, 其他代码都可以与 `JDK8`兼容

请注意 JDK从11开始 Java基础包 `javax.` 改为了 `jakarta.`

例如 JDK8中的 `javax.websocket.*`

在JDK11中变成了 `jakarta.websocket.*`

## 如果要使用 `JDK8`启动 

* 请批量把项目所有 `jakarta.` 替换为 `javax.`, 同时SpringBoot版本切换小于 `2.6.X`
* 切换项目结构的JDK语言级别，和开发工具的JDK编译版本


## Vue开发环境😁
* Node.js >= 20
* Vue3
* element-plus
* Vite

安装依赖
```cmd
npm install
```
启动
```cmd
npm run dev
```

打包
```cmd
npm run build
```
如果运行出现异常，请检查 当前Node版本是否大于等于20, 推荐使用[`NVM` node版本管理工具](https://blog.csdn.net/qq_40739917/article/details/134822473)


## 文件目录说明😁
`server` Java服务

`web` Vue页面

