package com.swan.search;

import com.swan.search.bean.Article;
import com.swan.search.service.ArticleService;
import entity.PageResult;
import entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity save(@RequestBody Article article) {
        articleService.add(article);
        return ResponseEntity.SUCCESS("添加成功");
    }

    @GetMapping("/search/{keywords}/{page}/{size}")
    public ResponseEntity findByTitlePage(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {
        Page<Article> result = articleService.findByTitlePage(keywords, page, size);
        return ResponseEntity.SUCCESS(new PageResult<Article>(result.getTotalElements(),result.getContent()));
    }

}
