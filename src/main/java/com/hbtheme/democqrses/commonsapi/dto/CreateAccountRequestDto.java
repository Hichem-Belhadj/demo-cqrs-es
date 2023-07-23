package com.hbtheme.democqrses.commonsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateAccountRequestDto {
    private Double initialBalance;
    private String currency;
}
