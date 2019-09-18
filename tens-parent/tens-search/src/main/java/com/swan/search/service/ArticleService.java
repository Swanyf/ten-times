package com.swan.search.service;

import com.swan.search.bean.Article;
import com.swan.search.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    public void add(Article article) {
        articleMapper.save(article);
    }

    public Page<Article> findByTitlePage(String keywords, int page, int size) {
        PageRequest result = PageRequest.of(page - 1, size);
        return articleMapper.findByTitleOrContentLike(keywords, result);
    }
}
