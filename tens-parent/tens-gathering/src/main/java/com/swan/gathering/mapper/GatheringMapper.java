package com.swan.gathering.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.gathering.bean.Gathering;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface GatheringMapper extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{
	
}
