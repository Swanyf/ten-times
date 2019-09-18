package com.swan.article.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.article.bean.Article;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleMapper extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    // 文章审核通过
    @Query(value = "update tb_article set state = 1 where id = ?", nativeQuery = true)
    public void examine(String id);

    // 文章点赞
    @Query(value = "update tb_article set thumbup = thumbup+1 where id = ?", nativeQuery = true)
    public void dianzan(String id);
	
}
