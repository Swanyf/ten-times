package com.swan.search.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "tens_article", type = "article")
public class Article {

    @Id
    private String id;//ID

    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;//标题，表示该标题区域能够被搜索

    @Field(index= true ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;//文章正文，表示搜索的时候是整体匹配还是部分单词匹配

    private String state;//审核状态
}
