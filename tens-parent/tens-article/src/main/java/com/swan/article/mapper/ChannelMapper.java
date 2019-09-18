package com.swan.article.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.article.bean.Channel;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ChannelMapper extends JpaRepository<Channel,String>,JpaSpecificationExecutor<Channel>{
	
}
