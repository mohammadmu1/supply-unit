package com.globitel.SupplyUnit.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDto {
    private boolean status;
    private String message;
}