<template>
    <div class="main-div bg" id="main">
        <div class="echart-button">
            <button @click="loadAsyncData1()">重新赋值数据</button>
            <button @click="loadAsyncData2()">还原默认数据</button>
        </div>
        <div class="echart-content">

            <div id="myChart1" class="echart-c" :style="{ width: computeWidth }" />
            <div id="myChart2" class="echart-c" :style="{ width: computeWidth }" />
            <div id="myChart3" class="echart-c" :style="{ width: computeWidth }" />
        </div>
    </div>
</template>

<script setup lang="ts">

// 引入 Echart
import * as echarts from "echarts";

import { ref, shallowRef, onActivated, onMounted } from 'vue'


// 定义三个 echart实例, 不能使用 ref存储echart实例，需要使用 shallowRef(https://cn.vuejs.org/api/reactivity-advanced.html#shallowref)，具体原因：https://echarts.apache.org/zh/faq.html#others
const myChart1 = shallowRef({})
const myChart2 = shallowRef({})
const myChart3 = shallowRef({})


// 定义 echart图表标题文字样式
const titleStyle = {
    color: 'white', // 标题颜色
    fontSize: 18, // 标题字体大小
    fontWeight: 'bold', // 标题字体粗细
    fontFamily: 'Inter, system-ui, Avenir, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB","Microsoft YaHei", "微软雅黑", Arial, sans-serif', // 标题字体
}

// 定义 echart图表 legend标题文字样式
const legendStyle = {
    color: 'white', // 标题颜色
    fontSize: 10, // 标题字体大小
    fontFamily: 'Inter, system-ui, Avenir, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB","Microsoft YaHei", "微软雅黑", Arial, sans-serif', // 标题字体
}

// 定义三个echart实例对应的option

// 折线图
const option1 = ref({
    title: {
        text: '折线图示例',
        textStyle: titleStyle,
        left: 'right'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine'],
        textStyle: legendStyle
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name: 'Email',
            type: 'line',
            stack: 'Total',
            data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
            name: 'Union Ads',
            type: 'line',
            stack: 'Total',
            data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
            name: 'Video Ads',
            type: 'line',
            stack: 'Total',
            data: [150, 232, 201, 154, 190, 330, 410]
        },
        {
            name: 'Direct',
            type: 'line',
            stack: 'Total',
            data: [320, 332, 301, 334, 390, 330, 320]
        },
        {
            name: 'Search Engine',
            type: 'line',
            stack: 'Total',
            data: [820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
})
// 柱形图
const option2 = ref({
    title: {
        text: '柱形图',
        subtext: 'Fake Data',
        left: 'right',
        textStyle: titleStyle
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220]
        }
    ]
})
// 饼状图
const option3 = ref({
    title: {
        text: '饼状图',
        subtext: 'Fake Data',
        left: 'right',
        textStyle: titleStyle
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        textStyle: legendStyle
    },
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
                { value: 1048, name: 'Search Engine' },
                { value: 735, name: 'Direct' },
                { value: 580, name: 'Email' },
                { value: 484, name: 'Union Ads' },
                { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

// 初始化所有echart图表
async function initAll() {

    // 等待宽度计算完成
    await computeWidthFun()

    // 初始化 dom图表
    myChart1.value = echarts.init(document.getElementById("myChart1"), null, { renderer: 'canvas' });
    myChart2.value = echarts.init(document.getElementById("myChart2"), null, { renderer: 'canvas' });
    myChart3.value = echarts.init(document.getElementById("myChart3"), null, { renderer: 'canvas' });

    console.info('图表DOM初始化完成，开始绘制图表')

    // 绘制图表数据
    myChart1.value.setOption(option1.value);
    myChart2.value.setOption(option2.value);
    myChart3.value.setOption(option3.value);
}
// 销毁所有echart图表
async function destroyAll() {
    myChart1.value.dispose()
    myChart2.value.dispose()
    myChart3.value.dispose()
}

// 动态计算 echart图表容器宽度
const computeWidth = ref('0px')

// 计算宽方法, 必须要等待宽度计算完成之后在渲染图表，不然会导致渲染图表的时候，框度还没计算出来，无法正常显示, 使用 async + Promise函数实现
async function computeWidthFun() {
    return new Promise<void>((resolve) => {
        // 当前屏幕可视化宽
        const clientWidth = document.getElementById('main').clientWidth
        // 计算三个容器平均宽度，需要去掉 padding、margin-left的宽
        computeWidth.value = (clientWidth / 3) - 24 + 'px'
        console.info('单个容器宽：', computeWidth.value)
        resolve()
    })

}

// 模拟异步加载数据
async function loadAsyncData1() {
    // 异步加载数据官网实例：https://echarts.apache.org/handbook/zh/how-to/data/dynamic-data

    // 重新绘制图表数据, 直接修改 option的数据
    const copyOption1 = option1.value
    const copyOption2 = option2.value
    const copyOption3 = option3.value

    copyOption1.series = [
        {
            name: 'Email',
            type: 'line',
            stack: 'Total',
            data: [820, 932, 901, 934, 1290, 1330, 1320]
        },
        {
            name: 'Union Ads',
            type: 'line',
            stack: 'Total',
            data: [150, 232, 22, 154, 190, 330, 410]
        },
        {
            name: 'Video Ads',
            type: 'line',
            stack: 'Total',
            data: [320, 332, 301, 334, 390, 330, 320]
        },
        {
            name: 'Direct',
            type: 'line',
            stack: 'Total',
            data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
            name: 'Search Engine',
            type: 'line',
            stack: 'Total',
            data: [13213, 132, 101, 134, 90, 230, 210]
        }
    ]

    copyOption2.series = [
        {
            name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [300, 123, 22, 66, 22, 111, 800]
        }
    ]

    copyOption3.series = [
        {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
                { value: 10, name: 'Search Engine' },
                { value: 666, name: 'Direct' },
                { value: 580, name: 'Email' },
                { value: 122, name: 'Union Ads' },
                { value: 88, name: 'Video Ads' }
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]

    // 赋值
    myChart1.value.setOption(copyOption1);
    myChart2.value.setOption(copyOption2);
    myChart3.value.setOption(copyOption3);

}

// 还原默认数据
async function loadAsyncData2() {
    // 重新绘制图表数据, 重新赋值原option
    const copyOption1 = option1.value
    const copyOption2 = option2.value
    const copyOption3 = option3.value

    copyOption1.series = [
        {
            name: 'Email',
            type: 'line',
            stack: 'Total',
            data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
            name: 'Union Ads',
            type: 'line',
            stack: 'Total',
            data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
            name: 'Video Ads',
            type: 'line',
            stack: 'Total',
            data: [150, 232, 201, 154, 190, 330, 410]
        },
        {
            name: 'Direct',
            type: 'line',
            stack: 'Total',
            data: [320, 332, 301, 334, 390, 330, 320]
        },
        {
            name: 'Search Engine',
            type: 'line',
            stack: 'Total',
            data: [820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]

    copyOption2.series = [
        {
            name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220]
        }
    ]

    copyOption3.series = [
        {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
                { value: 1048, name: 'Search Engine' },
                { value: 735, name: 'Direct' },
                { value: 580, name: 'Email' },
                { value: 484, name: 'Union Ads' },
                { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]

    // 赋值
    myChart1.value.setOption(copyOption1);
    myChart2.value.setOption(copyOption2);
    myChart3.value.setOption(copyOption3);
}

// 缓存组件重新打开
onActivated(() => {
    window.document.title = 'Echart示例'
})

// 页面初始化完成生命周期
onMounted(async () => {
    // 初始化图表
    await initAll()

    // 监听浏览器宽大小变化，重新初始化图表
    window.addEventListener('resize', async function () {
        // 销毁容器
        await destroyAll()

        // 重新初始化
        await initAll()
    });
})

</script>

<style scoped>
.main-div {
    height: calc(100vh - 60px);
    position: absolute;
    margin-top: 60px;
    width: 100%;
}

.bg {
    background-color: #522546;
}

.echart-c {
    float: left;
    width: 600px;
    height: 500px;
    background-color: #311d3f;
    margin-left: 10px;
    border-radius: 5px;
    padding: 10px 5px;
}

.echart-button {
    margin-top: 40px;
}

.echart-button button {
    background-color: #88304e;
    padding: 10px 60px;
    color: white;
    border-radius: 10px;
    border: none;
    margin: 0px 5px;
    cursor: pointer;
}

.echart-content {
    margin-top: 40px;
}
</style>