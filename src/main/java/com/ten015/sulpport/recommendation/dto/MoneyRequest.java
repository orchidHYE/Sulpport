package com.ten015.sulpport.recommendation.dto;

import lombok.Data;

@Data
public class MoneyRequest {

    private String name; // 이름
    private String ageGroup; // 나이대
    private String relations; //상대방과의 관계

}
