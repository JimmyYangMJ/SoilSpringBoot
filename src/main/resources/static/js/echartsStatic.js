
    function generateChart(data) {

        var dom = document.getElementById("echart");
        var myChart = echarts.init(dom);
        // 加载数据
        course = data;
        var status = course.status;

        if(status == 1){  //  没有相应记录
            alert("没有相应记录");
        }
        var node = course.data;
        var nodeId = node[0].node;
        nodeId = "结点 " + nodeId;
        var nodeTime = new Array();
        var nodeHumidity = new Array();
        for (var i = 0; i < node.length; i++) {
            nodeTime[i] = node[i].times;
            nodeHumidity[i] = node[i].humidity;
        }
        //
        option = {
            title : {
                text: '土壤水势',
                subtext: course.msg
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:[nodeId]
                // , '节点二'
            },
            dataZoom:[
                {
                    show: true,
                    start: 0,
                    end: 100,
                    type: 'inside'
                },
                {
                    type: 'slider',//图表下方的伸缩条
                    show : true, //是否显示
                    realtime : true, //拖动时，是否实时更新系列的视图
                    start : 0, //伸缩条开始位置（1-100），可以随时更改
                    end : 100, //伸缩条结束位置（1-100），可以随时更改
                }
            ],
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : true,
                    data: nodeTime

                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} pa'
                    }
                }
            ],
            series : [
                {
                    name: nodeId,
                    type:'line',
                    data: nodeHumidity,
                    // data:[11, 50, 15, 13, 12, 13, 10,9],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                }
                // ,
                // {
                //     name:'节点二',
                //     type:'line',
                //     data:[1, -2, 2, 5, 3, 2, 0,4],
                //     markPoint : {
                //         data : [
                //             {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                //         ]
                //     },
                //     markLine : {
                //         data : [
                //             {type : 'average', name : '平均值'}
                //         ]
                //     }
                // }
            ]
        };

        // 为echarts对象加载数据
        myChart.setOption(option, window.onresize = myChart.resize);
    }

//buttion调用的方法
function getData() {
    var node = $('#node').val();
    var day = $('#day').val();
    alert("结点："+node + " 时间：" + day);
    //异步请求
    $.get(  // jQuery post() 方法
        "soil/selectSoilSet.do", //访问地址
        {node: node, day: day}, //携带的参数
        function (data) {
            generateChart(data);
        },
        "json"
    );
}
