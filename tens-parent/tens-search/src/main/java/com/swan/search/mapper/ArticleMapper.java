package com.swan.search.mapper;

import com.swan.search.bean.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleMapper extends ElasticsearchRepository<Article, String> {

    Page<Article> findByTitleOrContentLike(String keywords, Pageable result);

}
