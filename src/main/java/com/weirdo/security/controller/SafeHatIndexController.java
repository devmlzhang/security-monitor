package com.weirdo.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.weirdo.security.utils.NumToStringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  页面跳转
 * </p>
 *
 * @author ML.Zhang
 * @since 2018/11/21
 */
@Controller
public class SafeHatIndexController {

    @Value("${websocket_hostname}")
    private String hostName;
    @Value("${websocket_port}")
    private String port;
    @Value("${mqtt_clientId}")
    private String mqttClientId;

    @RequestMapping("/baidu")
    public String  alarm(Model modelMap, String projId,String radius){
        String projId2= "7730596820333821952";
        System.out.println(projId);
        //动态创建topic
        String longitude="106.62577";
        String latitude="26.648352";
        if(radius.isEmpty()){
            radius="300";
        }

        modelMap.addAttribute("longitude",longitude);
        modelMap.addAttribute("latitude",latitude);
        modelMap.addAttribute("projId",projId2);
        modelMap.addAttribute("safeRadius",radius);
        modelMap.addAttribute("siteMap","PEK");//离线地图 标志
        String projTopic = NumToStringUtil.numToString(Long.valueOf("7730596820333821952"));
        modelMap.addAttribute("projTopic",projTopic);
        modelMap.addAttribute("hostName",hostName);
        modelMap.addAttribute("mqttClientId",mqttClientId);
        modelMap.addAttribute("mqttPort",port);

        return "baidu";
    }



}
