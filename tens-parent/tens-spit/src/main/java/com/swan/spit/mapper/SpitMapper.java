package com.swan.spit.mapper;

import com.swan.spit.bean.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitMapper extends MongoRepository<Spit,String> {

    Page<Spit> findByParentid(String parentid, Pageable result);

}
