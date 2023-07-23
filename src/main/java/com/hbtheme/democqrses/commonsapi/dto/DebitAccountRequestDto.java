package com.hbtheme.democqrses.commonsapi.dto;

import lombok.Data;

@Data
public class DebitAccountRequestDto {
    private String accountId;
    private Double amount;
    private String currency;
}
