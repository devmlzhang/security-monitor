package com.weirdo.security.service.impl;

import com.weirdo.security.service.MqttClientService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * mqtt消息实现类
 * </p>
 *
 * @author ML.Zhang
 * @since 2019-04-12
 */
@Service
public class MqttClientServiceImpl implements MqttClientService {

    private static String TOPIC_PRIX = "topic/";// 斜杠变点
    @Value("${mqtt_expiration}")
    private long mqtt_expiration;

    @Autowired
    private MqttPahoMessageHandler handler;

    @Override
    public void sendMessage(String topicName, String data) {
        if (StringUtils.isEmpty(topicName) || StringUtils.isEmpty(data)) {
            return;
        }
        try {
            Message<String> message = MessageBuilder.withPayload(data).setHeader(MqttHeaders.TOPIC, topicName).setExpirationDate(mqtt_expiration*1000).build();
            handler.handleMessage(message);
        } catch (Exception e) {
            System.out.println(getExceptionMessage(e));
        }
    }

    @Override
    public String getTopicName(String type, String name) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(type)) {
            return null;
        }
        String topicName = TOPIC_PRIX + type + "/" + name;
        return topicName;
    }

    public String getExceptionMessage(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
