package com.swan.user.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.user.bean.Admin;

public interface AdminMapper extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{

    Admin findByLoginname(String loginname);

}
