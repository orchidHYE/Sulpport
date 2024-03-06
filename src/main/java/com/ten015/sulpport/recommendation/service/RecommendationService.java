package com.ten015.sulpport.recommendation.service;

import com.ten015.sulpport.recommendation.dto.GiftRequest;
import com.ten015.sulpport.recommendation.dto.GiftResponse;
import com.ten015.sulpport.recommendation.dto.GreetingsRequest;
import com.ten015.sulpport.recommendation.dto.MoneyRequest;

public interface RecommendationService {
    String generateGreetings (GreetingsRequest greetingsRequest);

    String generateMoney (MoneyRequest moneyRequest);

    GiftResponse generateGift(GiftRequest giftRequest);
}
