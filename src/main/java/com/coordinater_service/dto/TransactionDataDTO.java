package com.coordinater_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionDataDTO {
    @NotNull(message = "orderId should not be null")
    private Integer orderId;
    @NotNull(message = "price should not be null")
    private String item;
    @NotNull(message = "price should not be null")
    private Integer price;
    @NotNull(message = "payment mode should not be null")
    private String paymentMode;
}