package com.swan.qa.service;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.swan.qa.mapper.ProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.swan.qa.bean.Problem;

@Service
public class ProblemService {

	@Autowired
	private ProblemMapper problemMapper;
	
	@Autowired
	private IdWorker idWorker;

    public Page<Problem> newProblems(String labelId, int page, int size) {
        PageRequest result = PageRequest.of(page-1, size);
        return problemMapper.newProblems(labelId, result);
    }

    public Page<Problem> hotProblems(String labelId, int page, int size) {
        PageRequest result = PageRequest.of(page-1, size);
        return problemMapper.hotProblems(labelId, result);
    }

    public Page<Problem> waitProblems(String labelId, int page, int size) {
        PageRequest result = PageRequest.of(page-1, size);
        return problemMapper.waitProblems(labelId, result);
    }



    /**
	 * 查询全部列表
	 * @return
	 */
	public List<Problem> findAll() {
		return problemMapper.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Problem> findSearch(Map whereMap, int page, int size) {
		Specification<Problem> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return problemMapper.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Problem> findSearch(Map whereMap) {
		Specification<Problem> specification = createSpecification(whereMap);
		return problemMapper.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Problem findById(String id) {
		return problemMapper.findById(id).get();
	}

	/**
	 * 增加
	 * @param problem
	 */
	public void add(Problem problem) {
		problem.setId( idWorker.nextId()+"" );
		problemMapper.save(problem);
	}

	/**
	 * 修改
	 * @param problem
	 */
	public void update(Problem problem) {
		problemMapper.save(problem);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		problemMapper.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Problem> createSpecification(Map searchMap) {

		return new Specification<Problem>() {

			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // 内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 是否解决
                if (searchMap.get("solve")!=null && !"".equals(searchMap.get("solve"))) {
                	predicateList.add(cb.like(root.get("solve").as(String.class), "%"+(String)searchMap.get("solve")+"%"));
                }
                // 回复人昵称
                if (searchMap.get("replyname")!=null && !"".equals(searchMap.get("replyname"))) {
                	predicateList.add(cb.like(root.get("replyname").as(String.class), "%"+(String)searchMap.get("replyname")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
