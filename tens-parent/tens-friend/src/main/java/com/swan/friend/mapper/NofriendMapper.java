package com.swan.friend.mapper;

import com.swan.friend.bean.Nofriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NofriendMapper extends JpaRepository<Nofriend,String>,JpaSpecificationExecutor<Nofriend>{
	
}
