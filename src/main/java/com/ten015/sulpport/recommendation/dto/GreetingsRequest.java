package com.ten015.sulpport.recommendation.dto;

import lombok.Data;

import java.util.List;

@Data
public class GreetingsRequest {

    private String name; // 이름
    private String ageGroup; // 나이대
    private String relations; //상대방과의 관계
    private String situation; // 상대방의 현재 상황
    private String formality; // 반말 혹은 존댓말
    private List<String> tone; // 글의 분위기

}
