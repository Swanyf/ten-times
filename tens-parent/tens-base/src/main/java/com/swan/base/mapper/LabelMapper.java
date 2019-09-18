package com.swan.base.mapper;

import com.swan.base.bean.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelMapper extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
}
