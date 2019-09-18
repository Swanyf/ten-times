package com.swan.recruit.controller;
import java.util.Map;

import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.swan.recruit.bean.Enterprise;
import com.swan.recruit.service.EnterpriseService;

import entity.ResponseEntity;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseService enterpriseService;

	/**
	 * 热门企业列表
	 */
	@GetMapping("/search/hotlist")
	public ResponseEntity hotlist() {
		return new ResponseEntity(StatusCode.SUCCESS.getCode(), enterpriseService.hotlist());
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity findAll(){
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",enterpriseService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public ResponseEntity findById(@PathVariable String id){
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",enterpriseService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public ResponseEntity findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
		return  new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",  new PageResult<>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public ResponseEntity findSearch( @RequestBody Map searchMap){
        return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",enterpriseService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param enterprise
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity add(@RequestBody Enterprise enterprise  ){
		enterpriseService.add(enterprise);
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"增加成功");
	}
	
	/**
	 * 修改
	 * @param enterprise
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public ResponseEntity update(@RequestBody Enterprise enterprise, @PathVariable String id ){
		enterprise.setId(id);
		enterpriseService.update(enterprise);		
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable String id ){
		enterpriseService.deleteById(id);
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"删除成功");
	}
	
}
