package com.hbtheme.democqrses.commonsapi.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class CreditAccountRequestDto {
    private String accountId;
    private Double amount;
    private String currency;
}
