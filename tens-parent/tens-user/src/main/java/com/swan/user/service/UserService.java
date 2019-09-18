package com.swan.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.swan.user.mapper.UserMapper;
import com.swan.user.bean.User;
import util.ToolUtil;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	BCryptPasswordEncoder encoder;

	public User login(User user) {
		User userDB = userMapper.findByNickname(user.getNickname());
		if (userDB != null && encoder.matches(user.getPassword(), userDB.getPassword())) {
			return userDB;
		}
		return userDB;
	}

	public void userRegister(String code, User user) {
		String encodePwd = encoder.encode(user.getPassword());
		user.setPassword(encodePwd);

		// 根据前台传递的验证码和redis中的判断是否正确
		String authcode = (String) redisTemplate.opsForValue().get("authcode");
		if (StringUtils.isBlank(authcode)) {
			throw new RuntimeException("请先获取验证码");
		}
		if (!Objects.equals(authcode,code)) {
			throw new RuntimeException("验证码错误!");
		}

		user.setId(idWorker.nextId()+"");
		user.setFollowcount(0);//关注数
		user.setFanscount(0);//粉丝数
		user.setOnline(0L);//在线时长
		user.setRegdate(new Date());//注册日期
		user.setUpdatedate(new Date());//更新日期
		user.setLastdate(new Date());//最后登陆日期

		userMapper.save(user);
	}

    /**
     * 发送短信验证码
     */
    public void sendAuthCode(String mobile) {
        String authcode = ToolUtil.getRandomNum(6);
        // 将验证码存放到redis中，设置3分钟过期时间
        redisTemplate.opsForValue().set("user:" + mobile + "authcode", authcode, 5, TimeUnit.MINUTES);
        // 将验证码发送给rabbitmq
        HashMap<Object, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("authcode", authcode);
        rabbitTemplate.convertAndSend("sms_authcode",map);
    }

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userMapper.findAll();
	}


	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userMapper.findAll(specification, pageRequest);
	}


	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userMapper.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userMapper.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		userMapper.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userMapper.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userMapper.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
