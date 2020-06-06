
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var nodeTime = new Array();
var nodeHumidity = new Array();
var nodeId = "结点 " + 1;
option = null;
option = {
    title : {
        text: '土壤水势',
        subtext: 'sspu'
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

    ]
};
//////////////////////////////////////////////
var socket;
if(!window.WebSocket){
    window.WebSocket = window.MozWebSocket;
}

if(window.WebSocket){
    socket = new WebSocket("ws://localhost:8081/webSocket");
    socket.onmessage = function(event){
        message = event.data;
        // var temp = "<6,1,1,1.2,1.5,6.4,2020-06-06 12:12:13,>";
        var soilData = message.split(",");
        // alert(soilData[6]+ "," + soilData[5]);
        nodeTime.push(soilData[6]);
        nodeHumidity.push(soilData[5]);

        // alert(nodeTime.length + "," + nodeTime.length);
        myChart.setOption(option, window.onresize = myChart.resize);
        var ta = document.getElementById('responseContent');
        ta.value += event.data + "\r\n";
    };

    socket.onopen = function(event){

        var ta = document.getElementById('responseContent');
        ta.value = "你当前的浏览器支持WebSocket,请进行后续操作\r\n";
    };

    socket.onclose = function(event){
        var ta = document.getElementById('responseContent');
        ta.value = "";
        ta.value = "WebSocket连接已经关闭\r\n";
    };
}else{
    alert("您的浏览器不支持WebSocket");
}

// 向 服务器 发送消息
function send(message){
    if(!window.WebSocket){
        return;
    }
    if(socket.readyState == WebSocket.OPEN){
        socket.send(message);
    }else{
        alert("WebSocket连接没有建立成功！！");
    }
}

///////////////////////////////////////////////
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
