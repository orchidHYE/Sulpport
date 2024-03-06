package com.ten015.sulpport.recommendation.controller;

import com.ten015.sulpport.recommendation.dto.GiftRequest;
import com.ten015.sulpport.recommendation.dto.GiftResponse;
import com.ten015.sulpport.recommendation.dto.GreetingsRequest;
import com.ten015.sulpport.recommendation.dto.MoneyRequest;
import com.ten015.sulpport.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    // 덕담
    @PostMapping ("/greetings")
    public ResponseEntity<String> generateGreetings (@RequestBody GreetingsRequest greetingsRequest) {
        String greetingsResponse = recommendationService.generateGreetings(greetingsRequest);
        return ResponseEntity.ok(greetingsResponse);
    }

    // 용돈
    @PostMapping ("/money")
    public ResponseEntity<String> generateMoney (@RequestBody MoneyRequest moneyRequest) {
        String moneyResponse = recommendationService.generateMoney(moneyRequest);
        return ResponseEntity.ok(moneyResponse);
    }

    // 선물
    @PostMapping("/gift")
    public ResponseEntity<GiftResponse> generateGift(@RequestBody GiftRequest request) {
        GiftResponse giftResponse = recommendationService.generateGift(request);
        return ResponseEntity.ok(giftResponse);
    }
}
