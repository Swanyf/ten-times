package com.swan.recruit.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.recruit.bean.Recruit;

import java.util.List;

public interface RecruitMapper extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    // 查询状态为2并以创建日期降序排序，查询前4条记录
    public List<Recruit> findTop4ByStateNotOrderByCreatetimeDesc(String state);

    //查询状态不为0并以创建日期降序排序，查询前12条记录
    public List<Recruit> findTop12ByStateOrderByCreatetimeDesc(String state);

}
