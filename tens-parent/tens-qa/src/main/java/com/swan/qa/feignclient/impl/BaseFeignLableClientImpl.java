package com.swan.qa.feignclient.impl;

import com.swan.qa.feignclient.BaseFeignLableClient;
import entity.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseFeignLableClientImpl implements BaseFeignLableClient {

    @Override
    public ResponseEntity getByLabelId(String lableId) {
        return ResponseEntity.SUCCESS("触发熔断器");
    }

}
