package com.swan.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.swan.user.mapper.AdminMapper;
import com.swan.user.bean.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Admin login(Admin admin) {
        Admin adminDB = adminMapper.findByLoginname(admin.getLoginname());
        if (admin != null && bCryptPasswordEncoder.matches(admin.getPassword(),adminDB.getPassword())) {
            return adminDB;
        }
        return adminDB;
    }

    /**
     * 查询全部列表
     */
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     */
    public Page<Admin> findSearch(Map whereMap, int page, int size) {
        Specification<Admin> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminMapper.findAll(specification, pageRequest);
    }

    /**
     * 条件查询
     *
     * @param whereMap
     */
    public List<Admin> findSearch(Map whereMap) {
        Specification<Admin> specification = createSpecification(whereMap);
        return adminMapper.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     */
    public Admin findById(String id) {
        return adminMapper.findById(id).get();
    }

    /**
     * 增加
     *
     * @param admin
     */
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        adminMapper.save(admin);
    }

    /**
     * 修改
     *
     * @param admin
     */
    public void update(Admin admin) {
        adminMapper.save(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        adminMapper.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     */
    private Specification<Admin> createSpecification(Map searchMap) {

        return new Specification<Admin>() {

            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 登陆名称
                if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                    predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }

}
