// 某段时间内的个结点数据
//立即执行函数，减少命名冲突
(function () {
    //1.初始化实例对象 echart.init(dom容器)
    var myChart = echarts.init(document.getElementById('linechart'));
    // 2.指定配置项和数据
    var option = {
        // 设置线条的颜色,数组的写法
        // color: ['skyblue'],
        //设置图标的标题
        title: {},
        //图表的提示框组件
        tooltip: {
            //触发方式：（坐标轴）
            trigger: 'axis'
        },
        // 图例组件，点击显示
        legend: {
            //series里面有name值，data配置可以删掉
        },
        //工具箱组件 另存为图片
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        //网格配置，坐标系大小，grid可以控制柱状图，线性图的大小
        grid: {
            // 控制距离dom容器的距离
            left: '8%',
            right: '8%',
            bottom: '10%',
            // 是否显示刻度标签，如果是true就显示，反之则不显示
            containLable: true
        },
        //设置x轴的相关配置
        xAxis: {
            type: 'category',
            // 是否让我们的线条和坐标轴有缝隙
            boundaryGap: false,
            data: ['2020-01-01', '2020-02-02', '2020-02-08', '2020-02-16', '2020-02-24', '2020-03-01']
        },
        yAxis: {
            type: 'value'
        },
        // 系列图标配置，决定显示哪种类型的图表
        series: [{
                name: '结点1',
                type: 'line',
                data: [2.19, 2.37, 2.45, 3.56, 1.65, 2.43]
            },
            {
                name: '结点2',
                type: 'line',
                data: [2.5, 2.27, 1.17, 1.53, 3.7, 2.32]
            },
            {
                name: '结点3',
                type: 'line',
                data: [2.16, 2.69, 1.54, 1.88, 2.77, 2.54]
            }
        ]

    };
    // 3.配置项给实例对象
    myChart.setOption(option);
    window.addEventListener("resize", function () {
        myChart.resize();
    });
})();

// (function () {
//         var myChart = echarts.init(document.getElementById('realtimechart'));
//         var data = [
//             [2.5, 2.27, 1.17, 1.53, 3.7, 2.32],
//             [2.16, 2.69, 1.54, 1.88, 2.77, 2.54]
//         ]
//         option = {
//             title: {},
//             tooltip: {
//                 trigger: 'axis',
//             },
//             toolbox: {
//                 show: true,
//                 feature: {
//                     dataZoom: {
//                         yAxisIndex: 'none'
//                     },
//                     dataView: {
//                         readOnly: false
//                     },
//                     restore: {},
//                     saveAsImage: {}
//                 }
//             },
//             //网格配置，坐标系大小，grid可以控制柱状图，线性图的大小
//             grid: {
//                 // 控制距离dom容器的距离
//                 left: '5%',
//                 right: '8%',
//                 bottom: '10%',
//                 // 是否显示刻度标签，如果是true就显示，反之则不显示
//                 containLable: true
//             },
//             xAxis: {
//                 type: 'ategory',
//                 // 是否让我们的线条和坐标轴有缝隙
//                 boundaryGap: false,
//             },
//             yAxis: {
//                 type: 'value'
//             },
//             // 系列图标配置，决定显示哪种类型的图表
//             series: [{
//                     name: '结点1',
//                     type: 'line',
//                     data: [2.19, 2.37, 2.45, 3.56, 1.65, 2.43]
//                 }
//             };
//             // 定时器
//             setInterval(getList, 5000);

//             function getList() {
//                 var i = Math.random() * data.length;
//                 option.series[0].data = data[i]
//             }
//             window.addEventListener("resize", function () {
//                 myChart.resize();
//             });
//         })();


var dom = document.getElementById("realtimechart");
var myChart = echarts.init(dom);
var app = {};
option = null;

function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
        name: now.toString(),
        value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
            Math.round(value)
        ]
    };
}

var data = [];
var now = +new Date(1997, 9, 3);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;
for (var i = 0; i < 1000; i++) {
    data.push(randomData());
}

option = {
    title: {
        text: '动态数据 + 时间坐标轴'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};

setInterval(function () {

    for (var i = 0; i < 5; i++) {
        data.shift();
        data.push(randomData());
    }

    myChart.setOption({
        series: [{
            data: data
        }]
    });
}, 1000);;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}