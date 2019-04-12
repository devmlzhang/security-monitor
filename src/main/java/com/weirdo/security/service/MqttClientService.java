package com.weirdo.security.service;

/**
 * <p>
 * mqtt消息
 * </p>
 *
 * @author ML.Zhang
 * @since 2019-04-12
 */
public interface MqttClientService {
    void sendMessage(String topicName, String data);

    String getTopicName(String type, String name);

}
