package com.coordinater_service.service;

import com.coordinater_service.dto.TransactionDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CoOrdinaterService {
   private final RestTemplate restTemplate;

    public String placeOrder(TransactionDataDTO transactionDataDTO) {
        if(prepare(transactionDataDTO)){
            if(commit(transactionDataDTO)){
                return "Order placed successFully!..";
            }else{
                return rollback(transactionDataDTO);
            }
        }
        return rollback(transactionDataDTO);
    }

    public String rollback(TransactionDataDTO transactionDataDTO) {
         callExternalApis("http://localhost:8080/api/order/rollback", transactionDataDTO);
         callExternalApis("http://localhost:8081/api/payment/rollback", transactionDataDTO);
        return "Transaction Declined";
    }

    private boolean commit(TransactionDataDTO transactionDataDTO) {
        boolean isOrderCommitted = callExternalApis("http://localhost:8080/api/order/commit", transactionDataDTO);
        boolean isPaymentCommitted = callExternalApis("http://localhost:8081/api/payment/commit", transactionDataDTO);
        return isOrderCommitted&&isPaymentCommitted;
    }

    public boolean prepare(TransactionDataDTO transactionDataDTO) {
        boolean isOrderPrepared = callExternalApis("http://localhost:8080/api/order/prepare", transactionDataDTO);
        boolean isPaymentPrepared = callExternalApis("http://localhost:8081/api/payment/prepare", transactionDataDTO);
        return isOrderPrepared&&isPaymentPrepared;
    }

    public boolean callExternalApis(String uri,TransactionDataDTO transactionDataDTO){
        try{
            return restTemplate.postForEntity(uri, transactionDataDTO, String.class)
                    .getStatusCode().is2xxSuccessful();
        }catch (Exception e){
            return false;
        }
    }
    
    
}
