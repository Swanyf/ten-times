package com.swan.friend.controller;

import entity.ResponseEntity;
import entity.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.PassThroughExceptionTranslationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RestController
public class FriendController {

    @PostMapping("/friend/like/{friendid}/{type}")
    public ResponseEntity addFriend(@PathVariable String friendid, @PathVariable String type, HttpServletRequest request) {
        String userClaims = request.getParameter("user_claims");
        if (StringUtils.isBlank(userClaims)) {
            throw new RuntimeException("访问错误");
        }
        if (Objects.equals("1", type)) {
            throw new RuntimeException("已经设置过喜欢");
        }
        else {
            return ResponseEntity.SUCCESS("操作成功");
        }

    }

}
