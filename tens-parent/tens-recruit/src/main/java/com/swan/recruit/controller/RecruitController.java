package com.swan.recruit.controller;
import java.util.Map;

import entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.swan.recruit.bean.Recruit;
import com.swan.recruit.service.RecruitService;

import entity.PageResult;
import entity.StatusCode;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 推荐职位列表
     */
    @GetMapping("/search/recommend")
    public ResponseEntity searchRecommend() {
        return new ResponseEntity(true,StatusCode.SUCCESS.getCode(), "查询成功", recruitService.searchRecommend());
    }

    @GetMapping("/search/newlist")
    public ResponseEntity searchNewlist() {
        return new ResponseEntity(true,StatusCode.SUCCESS.getCode(), "查询成功", recruitService.searchNewlist());
    }


    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)

    public ResponseEntity findAll() {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", recruitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable String id) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", recruitService.findById(id));
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
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity findSearch(@RequestBody Map searchMap) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", recruitService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param recruit
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Recruit recruit) {
        recruitService.add(recruit);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "增加成功");
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "删除成功");
    }

}
