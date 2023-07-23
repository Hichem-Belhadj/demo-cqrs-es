package com.hbtheme.democqrses.query.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hbtheme.democqrses.commonsapi.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    // JsonProperty is used here to avoid circular reference errors, as we haven't created a DTO.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
