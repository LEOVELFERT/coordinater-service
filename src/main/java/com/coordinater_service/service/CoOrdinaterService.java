package com.coordinater_service.service;

import com.coordinater_service.dto.TransactionDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.coordinater_service.constants.URLConstants.*;

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
         callExternalApis(ROLLBACK80_URL, transactionDataDTO);
         callExternalApis(ROLLBACK81_URL, transactionDataDTO);
        return "Transaction Declined";
    }

    private boolean commit(TransactionDataDTO transactionDataDTO) {
        boolean isOrderCommitted = callExternalApis(COMMIT80_URL, transactionDataDTO);
        boolean isPaymentCommitted = callExternalApis(COMMIT81_URL, transactionDataDTO);
        return isOrderCommitted&&isPaymentCommitted;
    }

    public boolean prepare(TransactionDataDTO transactionDataDTO) {
        boolean isOrderPrepared = callExternalApis(PREPARE80_URL, transactionDataDTO);
        boolean isPaymentPrepared = callExternalApis(PREPARE81_URL, transactionDataDTO);
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
