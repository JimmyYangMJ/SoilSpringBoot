//加载显示地图
function initialize() {
    var mp = new BMapGL.Map('nodemap');
    mp.centerAndZoom(new BMapGL.Point(121.658149, 31.270235), 18);
}

function loadScript() {
    var script = document.createElement("script");
    script.src =
        "https://api.map.baidu.com/api?v=1.0&type=webgl&ak=glhaW5DqSZ1kC232KkelBwPoOYbkIjDZ&callback=initialize";
    document.body.appendChild(script);
}

window.onload = loadScript;