package com.swan.article.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swan.article.bean.Column;

public interface ColumnMapper extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{
	
}
