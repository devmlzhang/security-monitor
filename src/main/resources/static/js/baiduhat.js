
$(function() {

    //样式
    adjustLayout();
    //加载左侧菜单
    LeftInit();
    //初始化界面
   // initMap();
    //mqtt连接
   // mqtt_connect();
    var webMercator = latLng2WebMercator(106.6762,26.6144);
    console.log("转换后的墨卡托坐标："+webMercator);

    var lngLat = webMercator2LngLat(11873782.165,3073958.800);
    console.log("转换后的经纬度："+lngLat);
});

/**
 * 左侧菜单加载
 */
function LeftInit() {
    layui.use(['tree', 'layer'], function(){
        var layer = layui.layer
            ,$ = layui.jquery;

        var companyList=[];
        var teamList=[];

        getLaborPersonAndTeam();

        // getTreeData();

        function getLaborPersonAndTeam() {
            $.ajax({
                type: "GET",
                url: "/laborPerson/getLaborPersonAndTeam",
                dataType: "json",
                data: {'projId':projId},
                //请求成功后要执行的函数，拼接html
                success: function (data) {
                    console.log(data);
                    companyList=data.result;
                    $("#companyList").empty();
                    if(companyList.length>0){
                        companyList.forEach(function (value) {
                            $('#companyList').append(
                                '<div class="labor-company">'+ '<span class="labor-company-span">'+value.laborCompanyName+'</span>'+'<hr class="t"/>'+'<ul id="tree'+value.laborCompanyName+'"></ul>'+'</div>'
                            );
                            teamList=value.laborcompanyTeam;
                            /* teamList.forEach(function (v) {
                                 v.spread=true;//展开树
                             });*/
                            getTreeData(value.laborCompanyName,teamList)
                        });
                    }
                }
            });
        }

        function getTreeData(treeId,data) {
            layui.tree({
                elem: '#tree'+treeId //指定元素
                , target: '_blank' //是否新选项卡打开（比如节点返回href才有效）
                , click: function (item) { //点击节点回调
                    layer.msg('当前节名称：' + item.name + '<br>全部参数：' + JSON.stringify(item));
                    console.log(item);
                }
                , nodes: data
            });
        }

    });
}

/**
 * 消息处理
 * @param data
 */
function handldMessage(data) {
    console.log(data);
    $("#longitude").text(data.longitude);
    $("#latitude").text(data.latitude);
    personLocation(data);
}

/**
 * 人员定位
 * @param data
 */
function personLocation(data){
    // var pt = new BMap.Point(data.longitude,data.latitude);
    var warning =[];
    warning.wid=data.latitude;
    warning.wlon=data.longitude;
    warning.wlat=data.latitude;
    drawWaringMaker(ssMap,warning);
    updateMarkerPosition(ssMap,warning);
}

/**
 * 初始化地图
 */
function initMap() {
    // 初始化地图
    if (!ssMap) {
        ssMap = new SSMap("mapBox", apc3);
    }
    ssMap.showPostionDiv = "showxy";
    ssMap.show();
    ssMap.addToolBox(LayerConfig.draw.name, LayerConfig.draw.title, LayerConfig.draw.zoom, 'mapBox', 'toolBox');
    SafetyHatShow.init(ssMap);

}

/**
 * 经纬度转墨卡托
 * @param lng
 * @param lat
 * @returns {number[]}
 */
function latLng2WebMercator(lng, lat) {//[114.32894, 30.585748]
    var earthRad = 6378137.0;
    var x = lng * Math.PI / 180 * earthRad;
    var a = lat * Math.PI / 180;
    var y = earthRad / 2 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
    return [x, y]; //[12727039.383734727, 3579066.6894065146]
}

/**
 * 墨卡托转经纬度
 * @param x
 * @param y
 * @returns {number[]}
 */
function webMercator2LngLat(x, y) {//[12727039.383734727, 3579066.6894065146]
    var lng = x / 20037508.34 * 180;
    var lat = y / 20037508.34 * 180;
    lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180)) - Math.PI / 2);
    return [lng, lat]; //[114.32894001591471, 30.58574800385281]
}


/**
 * 样式布局
 */
function adjustLayout() {
    var domHeight = $(window).height();
    var content = $("#mapBox");
    var mapboxW = $("#rightMap").width();
    content.css({ width: mapboxW +'px',height: domHeight-18+'px' })
}


/**
 * 安全帽对象
 * @type {{ssMap: null, init: SafetyHatShow.init}}
 */
var SafetyHatShow={
    ssMap:null,
    init:function(ssMap){
        this.ssMap=ssMap;
        this.ssMap.addLayer('areaLayer', '区域', 14);
        this.ssMap.addLayer('defenceareaLayer', '防区', 14);
        this.ssMap.addLayer('cameraLayer', '球机', 14);
        this.ssMap.addLayer('radarLayer', '雷达', 14);
        this.ssMap.addLayer('warningLayer', '入侵物体', 14);
        this.ssMap.addLayer('warningTraceLayer', '入侵轨迹', 14);
    }

};

/**
 * 更新位置
 * @param ssMap
 * @param warning
 */
function updateMarkerPosition(ssMap, warning) {
    ssMap.updateMarkerPosition('W_'+warning.wid, [warning.wlon, warning.wlat]);
}

/**
 * 画人员
 * @param ssMap
 * @param warning
 */
function drawWaringMaker(ssMap, warning) {
    var point=[warning.wlon, warning.wlat];
    var iconUrl = '../static/images/marker/poi/person.png';

    var prop = {
        id :'W_'+warning.wid,
        //id :'W_11',
        label : '',
        showpopup : true,
        showfunction: function() {
            var html = "姓名：张三"+ "<br/>";
            html += "所在经度：" + warning.wlon + "°<br/>";
            html += "所在纬度：" + warning.wlat + "°<br/>";
            html += "所在位置：火星"  + "<br/>";
            ssMap.showPopup('W_'+warning.wid, html);
        }
    };

    var options = {
        coordinate : point,
        trans : true,
        prop : prop,
        angle : 0,
        iconType : 'image',
        iconUrl : iconUrl,
        layer : 'warningLayer',
        resourceType : 'W'
    };
    ssMap.drawMarker(options);
}



