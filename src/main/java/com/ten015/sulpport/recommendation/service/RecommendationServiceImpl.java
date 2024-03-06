package com.ten015.sulpport.recommendation.service;

import com.ten015.sulpport.recommendation.common.RecommendationUtils;
import com.ten015.sulpport.recommendation.dto.GiftRequest;
import com.ten015.sulpport.recommendation.dto.GiftResponse;
import com.ten015.sulpport.recommendation.dto.GreetingsRequest;
import com.ten015.sulpport.recommendation.dto.MoneyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RecommendationServiceImpl(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    //덕담
    @Override
    public String generateGreetings(GreetingsRequest greetingsReauest) {
        logger.info("RecommendationServiceImpl-generateGreetings() 진입");

        try {
            Map<String, Object> requestBody = RecommendationUtils.initializeRequestBody(500);
            List<Map<String, String>> messages = RecommendationUtils.createInitialMessages();

            // 받는 사람 비어있을 경우
            String name = greetingsReauest.getName() != null ? greetingsReauest.getName() : "이름";

            // prompt
            String userMessage = String.format("%s에게 설날 덕담을 보낼거야. 나이는 %s대이며, 나와의 관계는 %s관계야. 상대방의 현재 상황은 %s이며, 이에 맞는 덕담을 %s로, %s 말투로 추천해줘.",
                    name,
                    greetingsReauest.getAgeGroup(),
                    greetingsReauest.getRelations(),
                    greetingsReauest.getSituation(),
                    greetingsReauest.getFormality(),
                    String.join(", ", greetingsReauest.getTone()));
            RecommendationUtils.addUserMessage(messages, userMessage);

            // body에 prompt 담기
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HttpClientErrorException: {}", e.getStatusCode());
            return "AI 호출 오류";
        } catch (RestClientException e) {
            logger.error("RestClientException: {}", e.getMessage());
            return "AI 호출 오류";
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return "시스템 오류";
        }
    }

    // 용돈
    @Override
    public String generateMoney(MoneyRequest moneyRequest) {
        logger.info("RecommendationServiceImpl-generateMoney() 진입");

        try {
            Map<String, Object> requestBody = RecommendationUtils.initializeRequestBody(500);
            List<Map<String, String>> messages = RecommendationUtils.createInitialMessages();

            String name = moneyRequest.getName() != null ? moneyRequest.getName() : "이름";
            String userMessage = String.format("너는 용돈을 추천해주는 챗봇이야. '%s에게 추천할 금액 n원!' 형식으로 답해줘야 돼. 내가 용돈을 줄 %s의 나이대는 %s대이며, 나와의 관계는 %s관계야. 형식에 맞게 용돈을 추천해줘.",
                    name,name, moneyRequest.getAgeGroup(), moneyRequest.getRelations());

            RecommendationUtils.addUserMessage(messages, userMessage);

            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HttpClientErrorException: {}", e.getStatusCode());
            return "AI 호출 오류";
        } catch (RestClientException e) {
            logger.error("RestClientException: {}", e.getMessage());
            return "AI 호출 오류";
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return "시스템 오류";
        }
    }

    // 선물
    @Override
    public GiftResponse generateGift(GiftRequest giftRequest) throws RestClientException {
        logger.info("RecommendationServiceImpl-generateGift() 진입");

        Map<String, Object> requestBody = RecommendationUtils.initializeRequestBody(500);
        List<Map<String, String>> messages = RecommendationUtils.createInitialMessages();

        String name = giftRequest.getName() != null ? giftRequest.getName() : "이름";
        String userMessage = String.format("%s의 나이대는 %s대이고, 상황은 %s인 상황이야. 나와는 '%s'관계고, 가격대가 %s인 %s 카테고리의 선물을 한개만 추천해주고 이유도 1~2문장으로 설명해서, 선물이름: , 이유: 와 같은 형식으로만 제안해줘.",
                name,
                giftRequest.getAgeGroup(),
                giftRequest.getSituation(),
                giftRequest.getRelations(),
                giftRequest.getPriceRange(),
                String.join(", ", giftRequest.getCategory()));

        RecommendationUtils.addUserMessage(messages, userMessage);
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        return parseResponse(response.getBody());
    }

    // response 위한 파싱메서드
    private GiftResponse parseResponse(String responseString) {
        Pattern pattern = Pattern.compile("선물이름:\\s*(.*?)\\s*이유:\\s*(.*)");
        Matcher matcher = pattern.matcher(responseString);
        GiftResponse giftResponse = new GiftResponse();
        if (matcher.find()) {
            giftResponse.setGiftName(matcher.group(1));
            giftResponse.setReason(matcher.group(2));
        }
        return giftResponse;
    }
}
