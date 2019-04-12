var ssMap = null;
$(function() {

    //样式
    adjustLayout();
    //初始化界面
    initMap();
    //mqtt连接
    mqtt_connect();
    var webMercator = latLng2WebMercator(106.6762,26.6144);
    console.log("转换后的墨卡托坐标："+webMercator);

    var lngLat = webMercator2LngLat(11873782.165,3073958.800);
    console.log("转换后的经纬度："+lngLat);
});



/**
 * 消息处理
 * @param data
 */
function handldMessage(data) {
    //console.log(data);
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
    warning.wid=data.userId;
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
    //ssMap.addToolBox(LayerConfig.draw.name, LayerConfig.draw.title, LayerConfig.draw.zoom, 'mapBox', 'toolBox');
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
        this.ssMap.addLayer('warningLayer', '入侵物体', 14);
        this.ssMap.addLayer('warningTraceLayer', '运动轨迹', 14);
    }
};


//绘画当前人运动轨迹
function drawUserLine(thisUserId){
    var this_=this;
    var ssMap=null;
    this_.feature = new ol.Feature();
    this_.ssMap.removeMarkers('areaLayer');
    this_.ssMap.removeMarkers('warningLayer');
    this_.ssMap.removeMarkers('warningTraceLayer');
    var params={"projId":projId,"userId":thisUserId};
    var geometry = null;
    $.get("/device/collection/getSafetyHatCollectionList",params,function (res) {
        if(res.errCode==0){
            //var points=[];
            var points=[[106.6172,26.6450],[106.6232,26.6451],[106.6232,26.6415],[106.6185,26.6414],[106.6172,26.6450]];
            /*res.data.forEach(function (v) {
                points.push([v.longitude,v.latitude]);
            });*/
            console.log("points=======:"+points);

            var prop = {
                id : 'TR_1'
            };

            var options = {
                points : points,
                geometry : 'polyline',
                trans : true,
                prop : prop,
                angle : 0,
                layer : 'warningTraceLayer',
                resourceType : 'TR'
            };
           this_.ssMap.drawLineMarker(options);
        }
    })
}


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




