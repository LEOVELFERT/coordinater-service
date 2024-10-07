package com.coordinater_service.controller;

import com.coordinater_service.dto.TransactionDataDTO;
import com.coordinater_service.service.CoOrdinaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/co-ordinate")
@RequiredArgsConstructor
public class CoOrdinaterController {

    private final CoOrdinaterService coOrdinaterService;

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody TransactionDataDTO transactionDataDTO){
        try{
            return new ResponseEntity<>(coOrdinaterService.placeOrder(transactionDataDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error happened while placing order",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
