package com.swan.qa.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.qa.bean.Problem;
import org.springframework.data.jpa.repository.Query;

public interface ProblemMapper extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    // 最新回复的问题显示在上方， 按回复时间降序排序。
    @Query(value = "select * from tb_problem p,tb_pl pl where p.id = pl.problemid and pl.labelid = ? order by createtime desc",nativeQuery = true)     // nativeQuerys 表示支持sql
    public Page<Problem> newProblems(String id, Pageable pageable);

    // 热门问答列表
    @Query(value = "select * from tb_problem p,tb_pl pl where p.id = pl.problemid and pl.labelid = ? order by reply desc",nativeQuery = true)     // nativeQuerys 表示支持sql
    public Page<Problem> hotProblems(String id, Pageable pageable);

    @Query(value = "select * from tb_problem p,tb_pl pl where p.id = pl.problemid and pl.labelid = ? and reply = 0 order by createtime desc",nativeQuery = true)     // nativeQuerys 表示支持sql
    public Page<Problem> waitProblems(String id, Pageable pageable);

}
