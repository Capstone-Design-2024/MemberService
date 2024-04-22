package com.memberservice.memberservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberInfoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.SIGNUP-EVENT}")
    private String SIGNUP_EVENT;

    public void memberInfoProducer(Map<String, Object> data) {
        log.info("signup-event message: {}", data);
        this.kafkaTemplate.send(SIGNUP_EVENT, makeMessage(data));
    }

    private String makeMessage(Map<String, Object> data) {
        JSONObject obj = new JSONObject();
        obj.put("uuid", "MemberInfoProducer/" + Instant.now().toEpochMilli());
        obj.put("data", data);
        return obj.toJSONString();
    }
}

