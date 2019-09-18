package com.swan.friend.service;

import com.swan.friend.bean.Nofriend;
import com.swan.friend.mapper.NofriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class NofriendService {

	@Autowired
	private NofriendMapper nofriendDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Nofriend> findAll() {
		return nofriendDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Nofriend> findSearch(Map whereMap, int page, int size) {
		Specification<Nofriend> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return nofriendDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Nofriend> findSearch(Map whereMap) {
		Specification<Nofriend> specification = createSpecification(whereMap);
		return nofriendDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Nofriend findById(String id) {
		return nofriendDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param nofriend
	 */
	public void add(Nofriend nofriend) {
		nofriend.setUserid( idWorker.nextId()+"" );
		nofriendDao.save(nofriend);
	}

	/**
	 * 修改
	 * @param nofriend
	 */
	public void update(Nofriend nofriend) {
		nofriendDao.save(nofriend);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		nofriendDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Nofriend> createSpecification(Map searchMap) {

		return new Specification<Nofriend>() {

			@Override
			public Predicate toPredicate(Root<Nofriend> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 
                if (searchMap.get("friendid")!=null && !"".equals(searchMap.get("friendid"))) {
                	predicateList.add(cb.like(root.get("friendid").as(String.class), "%"+(String)searchMap.get("friendid")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
