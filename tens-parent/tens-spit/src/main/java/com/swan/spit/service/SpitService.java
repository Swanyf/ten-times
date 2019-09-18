package com.swan.spit.service;

import com.swan.spit.bean.Spit;
import com.swan.spit.mapper.SpitMapper;
import entity.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.security.provider.ConfigFile;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Spliterator;

@Service
public class SpitService {

    @Autowired
    private SpitMapper spitMapper;
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @return 查询全部
     */
    public List<Spit> findAll() {
        return spitMapper.findAll();
    }

    /**
     * @return 查询单个
     */
    public Spit findById(String id) {
        return spitMapper.findById(id).get();
    }

    /**
     * 添加
     */
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setState("1");

        spit.setShare(0);
        spit.setThumbup(0);
        // 上级id存在说明是追加发布评论回复数量
        if (StringUtils.isNotBlank(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spitdb");
        }
        spitMapper.save(spit);
    }

    /**
     * 修改
     */
    public void update(Spit spit) {
        spitMapper.save(spit);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        spitMapper.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentid, int page, int size) {
        PageRequest result = PageRequest.of(page - 1, size);
        return spitMapper.findByParentid(parentid, result);
    }

    /**
     * 点赞数+1
     * @param spitId
     */
    public ResponseEntity spitDianzan(String spitId) {
        /* 防止重复点赞 */
        Object result = redisTemplate.opsForValue().get("Spit:" + spitId + "thumbup");
        if (result == null) {
            redisTemplate.opsForValue().set("Spit:" + spitId + "thumbup","true");

            /* 使用mongo方式 */
            // 原生mongoDb实现自增1命令 db.spitdb.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
            List<Spit> id = mongoTemplate.find(new Query().addCriteria(Criteria.where("_id").is(spitId)), Spit.class);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spitId));
            Update update = new Update();
            update.inc("thumbup", 1);
            mongoTemplate.updateFirst(query, update, "spit");
            return ResponseEntity.SUCCESS();
        } else {
            return ResponseEntity.ERROR("已点赞");
        }
    }
}
