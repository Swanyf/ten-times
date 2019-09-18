package com.swan.user.controller;

import java.util.Map;

import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.swan.user.bean.User;
import com.swan.user.service.UserService;

import entity.ResponseEntity;
import entity.StatusCode;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register/{code}")
    public ResponseEntity userRegister(@PathVariable String code, @RequestBody User user) {
        userService.userRegister(code, user);
        return ResponseEntity.SUCCESS("注册成功!");
    }

    /**
     * 发送短信验证码
     */
    @PostMapping("/sendsms/{mobile}")
    public ResponseEntity sendAuthCode(@PathVariable String mobile) {
        userService.sendAuthCode(mobile);
        return ResponseEntity.SUCCESS("发送成功");
    }

    /**
     * 查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable String id) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public ResponseEntity findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity findSearch(@RequestBody Map searchMap) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "删除成功");
    }

}
