package com.swan.recruit.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.recruit.bean.Enterprise;

import java.util.List;

public interface EnterpriseMapper extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    // 查询企业表ishot字段为1的记录
    public List<Enterprise> findByIshot(String ishot);

}
