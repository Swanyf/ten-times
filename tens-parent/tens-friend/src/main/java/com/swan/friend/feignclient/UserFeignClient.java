package com.swan.friend.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("tens-user")
public class UserFeignClient {



}
