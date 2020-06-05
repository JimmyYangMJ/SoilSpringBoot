

// 添加节点
function addPoint(map, lng, lat, massage) {
    alert("添加成功");
    var point = new BMap.Point(lng, lat);
    var marker = new BMap.Marker(point);  // 创建标注
    map.addOverlay(marker);               // 将标注添加到地图中
    // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    var label = new BMap.Label(massage, {offset:new BMap.Size(20,-10)});
    marker.setLabel(label);

    // 覆盖物添加*******
    var removeMarker = function(e,ee,marker){
        map.removeOverlay(marker);
    }
    //创建右键菜单
    var markerMenu=new BMap.ContextMenu();
    markerMenu.addItem(new BMap.MenuItem('删除',removeMarker.bind(marker)));
    //
    marker.addContextMenu(markerMenu);
    //***************

}

//右键事件--添加节点
function clickR1(map) {
    //单击获取点击的经纬度
    alert("添加节点:" + rightclickPoint.lng);
    // $('#exampleModal').modal('show');
    if (isMarker){
        alert("此处已有节点");
    }else {
        addPoint(map, rightclickPoint.lng, rightclickPoint.lat, massage);
    }

}
//右键事件--删除节点
function clickR2(map, Marker) {
    alert("删除节点6：" + Marker);
    //单击获取点击的经纬度
    if (Marker){
        removePoint(map, Marker);
    }else {
        alert("此处没有节点不能删除");
    }
}

var map ;
var rightclickPoint;
var len = 0;
setInterval(function () {
    //异步请求
    $.get(  // jQuery post() 方法
        "soilNode/SoilNodeLocation.do", //访问地址
        function (data) {
            if(data.data.length != len){
                map = new BMap.Map("allmap");
                var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
                var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
                var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
                /*缩放控件type有四种类型:
                BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/

                map.addControl(top_left_control);
                map.addControl(top_left_navigation);
                map.addControl(top_right_navigation);

                map.addEventListener("rightclick", function(e){
                    rightclickPoint = {lng:e.point.lng,lat:e.point.lat};
                    isMarker = e.overlay;
                });

                len = data.data.length;
                buildMap(data);
            }
        },
        "json"
    );
}, 3100);

function buildMap(data) {
    locations = data.data;
    var point = new BMap.Point(121.657843,31.268527);

    map.centerAndZoom(point, 20); // 设置中心点 缩放级别为15     //map.setZoom(14);
    map.enableAutoResize(); //设置当地图容器发生改变时地图自动适应
    map.enableScrollWheelZoom(true);

    var pointArray = new Array();
    for(var i=0; i < locations.length;i++){
        if(locations[i].lng == null){ // 不存在位置
            continue;
        }
        var marker = new BMap.Marker(new BMap.Point(locations[i].lng, locations[i].lat)); // 创建点
        map.addOverlay(marker);    //增加点
        pointArray[i] = new BMap.Point(locations[i].lng, locations[i].lat);
        // 添加标签
        var messages = "";
        messages = messages + locations[i].node;
        var label = new BMap.Label(messages,{offset:new BMap.Size(20,-10)});
        marker.setLabel(label);
        label.setStyle({
            "border": 0
        });
        //marker.addEventListener("click",attribute(e));
    }
    //让所有点在视野范围内
    map.setViewport(pointArray);



    var menu = new BMap.ContextMenu();
    var txtMenuItem = [
        {
            text:'添加节点',
            callback:function(){clickR1(map);}
        }
    ];

    for(var i=0; i < txtMenuItem.length; i++){
        menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback, 500));

    }
    map.addContextMenu(menu);
}

