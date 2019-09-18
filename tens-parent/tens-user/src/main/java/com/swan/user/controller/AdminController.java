package com.swan.user.controller;

import com.swan.user.bean.Admin;
import com.swan.user.service.AdminService;
import entity.PageResult;
import entity.ResponseEntity;
import entity.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    JwtUtil jwtUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Admin admin) {
        admin = adminService.login(admin);
        if (admin == null) {
            throw new RuntimeException("登录失败");
        }
        else {
            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
            HashMap<String, String> map = new HashMap<>();
            map.put("role", "admin");
            map.put("token", token);
            return ResponseEntity.SUCCESS("登录成功",map);
        }
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Admin admin) {
        String pwd = bCryptPasswordEncoder.encode(admin.getPassword());
        admin.setPassword(pwd);
        adminService.add(admin);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "增加成功");
    }

    /**
     * 查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable String id) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", adminService.findById(id));
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
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity findSearch(@RequestBody Map searchMap) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id, HttpServletRequest request) {
        String claims = request.getParameter("admin_claims");
        if (StringUtils.isNotBlank(claims)) {
            adminService.deleteById(id);
            return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "删除成功");
        } else {
            throw new RuntimeException("没有权限操作");
        }
    }

}
