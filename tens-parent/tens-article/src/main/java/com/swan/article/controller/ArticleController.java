package com.swan.article.controller;

import java.util.Map;

import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.swan.article.bean.Article;
import com.swan.article.service.ArticleService;

import entity.ResponseEntity;
import entity.StatusCode;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/examine/{articleId}")
    public ResponseEntity examine(@PathVariable String articleId) {
        articleService.examine(articleId);
        return ResponseEntity.SUCCESS("审核成功");
    }

    @GetMapping("/thumbup/{articleId}")
    public ResponseEntity dianzan(@PathVariable String articleId) {
        articleService.dianzan(articleId);
        return ResponseEntity.SUCCESS("已点赞");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable String id) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", articleService.findById(id));
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
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity findSearch(@RequestBody Map searchMap) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Article article) {
        articleService.add(article);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(), "删除成功");
    }

}
