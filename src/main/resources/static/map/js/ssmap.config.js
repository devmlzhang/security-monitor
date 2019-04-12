window.mapResUrl = '/static/map/';
window.onImageLoadErrorImage = window.mapResUrl + "tiles/blank.png";
var MapConfig = {
	airports : {
		PEK : {
			angle : 0* Math.PI / 180,//顺时针方向旋转
			center : [106.62617,26.648599],
			compass : "/static/map/img/compass/pek.png",
			extent:[106.6118,26.6289, 106.7408,26.6332],
			layers : [ {
				name : "公司",
                tileUrl: "http://www.bigemap.com:9001/bigemap.googlemap-satellite/tms/{z}/{x}/{y}.jpg"
               // tileUrl: mapResUrl + "tiles/pek/{z}/{y}/{x}.jpg"
			} ],
			maxResolution : 9.554628535647031,
			numZoomLevels : 6,
            minZoomLevel: 12,
            maxZoomlevel: 23,
            defaultZoom: 14,
			offset : [ 0, 0 ],
			title : "xx有限公司",
            resetScale:true    //地图注记是否随着地图的比例尺变化而改变大小
		},
		GOS : {
			angle : 0 * Math.PI / 180,
			//center : [13292252.22,4854497.777],
			center : [119.405550,39.920506],
			compass : "/static/map/img/compass/gos.png",
			extent : [13291243.883, 4853601.121,13293260.566,4855394.433],
			layers : [ {
				name : "公司",
				tileUrl : mapResUrl + "tiles/gos/{z}/{y}/{x}.png"
			} ],
            maxResolution : 9.554628535647031,
            numZoomLevels : 6,
            minZoomLevel: 14,
            maxZoomlevel: 23,
            defaultZoom: 17,
            offset : [ 0, 0 ],
			title : "贵州绿箭科技公司",
            resetScale:true
        },
		HET : {
			angle : 17.3 * Math.PI / 180,
			center : [106.676,26.614],
			compass : mapResUrl + "img/compass/het.png",
			extent : [106.664,26.603, 106.694,26.626],
			layers : [ {
				name : "公司",
				tileUrl : mapResUrl + "tiles/lm/{z}/{y}/{x}.png"
			} ],
            maxResolution : 9.554628535647031,
            numZoomLevels : 4,
            minZoomLevel: 14,
            maxZoomlevel: 23,
            defaultZoom: 16,
			offset : [ 0, 0 ],
			title : "龙马技术有限公司",
            resetScale:true
        }
	},
	mapStyles : {
		symbols : {
			Point : {
				pointRadius : 5,
				graphicName : "circle",
				fillColor : "white",
				fillOpacity : 0.0,
				strokeWidth : 1,
				strokeOpacity : 1,
				strokeColor : "#333333"
			},
			Line : {
				strokeWidth : 2,
				strokeOpacity : 1,
				strokeColor : "#666666",
				strokeDashstyle : "dash"
			},
			Polygon : {
				strokeWidth : 2,
				strokeOpacity : 1,
				strokeColor : "#666666",
				fillColor : "white",
				fillOpacity : 0.3
			}
		},
		// 唯一值
		uniqueValues : {
			colors : [ "#004444", "#660066", "#666666", "#66cc66", "#990066",
					"#996666", "#000066", "#99cc66", "#cc0066", "#cc6666",
					"#cccc66", "#ff0066", "#ff6666", "#ffcc66", "#006666",
					"#00cc66", "#330066", "#336666" ]
		}
	}
};

var LayerConfig= {
    device: {name: 'device', title: '设备', zoom: 1},
    psn: {name: 'psn', title: '机位', zoom: 16,textSize:{16:"14px",17:"17px",18:"19px",19:"21px"}},
    flight: {name: 'flt', title: '飞机', zoom: 14},
    alert: {name: 'alert', title: '报警', zoom: 14},
    route: {name: 'route', title: '轨迹', zoom: 14},
    draw: {name: 'draw', title: '绘制', zoom: 14}
};
