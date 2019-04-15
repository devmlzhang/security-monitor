package com.weirdo.security.message;


import com.alibaba.fastjson.JSONObject;
import com.weirdo.security.model.MsgModel;
import com.weirdo.security.service.MqttClientService;
import com.weirdo.security.utils.NumToStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@Slf4j
public class MqttProducer {
    /**
     * 定时发送主题消息
     */
    private String DA_TOPIC = "Topic/Weirdo/DaChange";

    @Autowired
    MqttClientService mqttClientService;

    @Scheduled(fixedDelay = 1000)
    public void send() {
        String msg = "xx";
        String TOPIC = NumToStringUtil.numToString(Long.valueOf("7730596820333821952"));
        if (msg != null) {
            try {

                JSONObject mqParams= new JSONObject();
                mqParams.put("projId","7730596820333821952");
                mqParams.put("userId","359972069698238");
                String lng = String.valueOf(106.62577+Math.random()*0.001);
                String lat = String.valueOf(26.648352+Math.random()*0.001);
                mqParams.put("longitude",lng);
                mqParams.put("latitude",lat);
                MsgModel msgModel = new MsgModel();
                msgModel.setData(mqParams);
                msgModel.setMsgType("A");
                log.info("amq消息：{}",mqParams.toString());
                mqttClientService.sendMessage(TOPIC, JSONObject.toJSONString(msgModel));
            } catch (Exception e) {
                e.printStackTrace();


            }
        }

    }


}
