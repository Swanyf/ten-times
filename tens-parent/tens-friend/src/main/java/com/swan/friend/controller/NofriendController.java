package com.swan.friend.controller;

import com.swan.friend.bean.Nofriend;
import com.swan.friend.service.NofriendService;
import entity.PageResult;
import entity.ResponseEntity;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/nofriend")
public class NofriendController {

	@Autowired
	private NofriendService nofriendService;

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity findAll(){
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",nofriendService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public ResponseEntity findById(@PathVariable String id){
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",nofriendService.findById(id));
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
		Page<Nofriend> pageList = nofriendService.findSearch(searchMap, page, size);
		return  new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",  new PageResult<Nofriend>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public ResponseEntity findSearch( @RequestBody Map searchMap){
        return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",nofriendService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param nofriend
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity add(@RequestBody Nofriend nofriend  ){
		nofriendService.add(nofriend);
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"增加成功");
	}
	
	/**
	 * 修改
	 * @param nofriend
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public ResponseEntity update(@RequestBody Nofriend nofriend, @PathVariable String id ){
		nofriend.setUserid(id);
		nofriendService.update(nofriend);		
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable String id ){
		nofriendService.deleteById(id);
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"删除成功");
	}
	
}
