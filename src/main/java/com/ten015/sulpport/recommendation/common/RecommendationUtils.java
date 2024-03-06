package com.ten015.sulpport.recommendation.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationUtils {

    public static Map<String, Object> initializeRequestBody(int maxTokens) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1);
        return requestBody;
    }

    public static List<Map<String, String>> createInitialMessages() {
        List<Map<String, String>> messages = new ArrayList<>();
        return messages;
    }

    public static void addUserMessage(List<Map<String, String>> messages, String content) {
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", content);
        messages.add(message);
    }

}
