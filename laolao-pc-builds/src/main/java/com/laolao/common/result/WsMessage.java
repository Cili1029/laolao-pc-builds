package com.laolao.common.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsMessage<T> {
    private String type; // 消息类型
    private T data;      // 数据内容

    // 全局复用ObjectMapper
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 创建WsMessage并序列化为JSON字符串
     * @param type 消息类型
     * @param data 数据内容
     * @return JSON字符串
     */
    public static <T> String of(String type, T data) {
        try {
            WsMessage<T> message = new WsMessage<>(type, data);
            return OBJECT_MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("WsMessage 序列化 JSON 失败", e);
        }
    }
}
