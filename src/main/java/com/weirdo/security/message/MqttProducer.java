package com.weirdo.security.message;


import com.alibaba.fastjson.JSONObject;
import com.weirdo.security.model.MsgModel;
import com.weirdo.security.service.MqttClientService;
import com.weirdo.security.utils.NumToStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class MqttProducer {
    /**
     * 定时发送主题消息
     */
    private String DA_TOPIC = "Topic/Weirdo/DaChange";

    @Autowired
    MqttClientService mqttClientService;

    @Scheduled(fixedDelay = 8000)
    public void send() {
        String msg = "xx";
        String TOPIC = NumToStringUtil.numToString(Long.valueOf("7730596820333821952"));
        if (msg != null) {
            try {

                for(int i=0;i<4;i++){
                    JSONObject mqParams= new JSONObject();
                    mqParams.put("projId","7744371220954677248");
                    mqParams.put("userId","359972069698238");
                    mqParams.put("longitude","3232");
                    mqParams.put("latitude","22");
                    MsgModel msgModel = new MsgModel();
                    msgModel.setData(mqParams);
                    msgModel.setMsgType("A");
                    mqttClientService.sendMessage(TOPIC, JSONObject.toJSONString(msgModel));
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }


}
