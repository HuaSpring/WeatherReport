
一： 根据经纬度获取城市名
http://api.map.baidu.com/geocoder?location=24.343221,112.601253&output=json
{
    "status":"OK",
    "result":{
        "location":{
            "lng":112.601253,
            "lat":24.343221
        },
        "formatted_address":"广东省清远市阳山县s114",
        "business":"",
        "addressComponent":{
            "city":"清远市",
            "direction":"",
            "distance":"",
            "district":"阳山县",
            "province":"广东省",
            "street":"s114",
            "street_number":""
        },
        "cityCode":197
    }
}

二：根据城市名获取天气信息
http://www.weather.com.cn/data/sk/101010100.html

http://www.weather.com.cn/data/cityinfo/101010100.html

//http://m.weather.com.cn/data/101010100.html


数据解析格式：
第一个网址提供的json数据为：

{"weatherinfo":{"city":"北京","cityid":"101010100","temp":"-2","WD":"西北风","WS":"3级","SD":"241%","WSE":"3","time":"10:61","isRadar":"1","Radar":"JC_RADAR_AZ9010_JB"}}


第二个网址提供的json数据为：

{"weatherinfo":{"city":"北京","cityid":"101010100","temp1":"3℃","temp2":"-8℃","weather":"晴","img1":"d0.gif","img2":"n0.gif","ptime":"11:00"}}




