package com.swan.qa.feignclient;

import com.swan.qa.feignclient.impl.BaseFeignLableClientImpl;
import entity.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "tens-base", fallback = BaseFeignLableClientImpl.class)
public interface BaseFeignLableClient {

    @GetMapping("/lable/{lableId}")
    ResponseEntity getByLabelId(@PathVariable(value = "lableId") String lableId);

}
