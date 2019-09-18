package com.swan.qa.controller;

import com.swan.qa.bean.Problem;
import com.swan.qa.feignclient.BaseFeignLableClient;
import com.swan.qa.service.ProblemService;
import entity.PageResult;
import entity.ResponseEntity;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	@Autowired
	private BaseFeignLableClient baseFeignLableClient;

	@GetMapping("/newlist/{labelId}/{page}/{size}")
    public ResponseEntity newProblems(@PathVariable String labelId,@PathVariable int page,@PathVariable int size) {
        Page<Problem> result = problemService.newProblems(labelId, page, size);
        return ResponseEntity.SUCCESS(new PageResult<Problem>(result.getTotalElements(),result.getContent()));
    }

    @GetMapping("/hotlist/{labelId}/{page}/{size}")
    public ResponseEntity hotProblems(String labelId, int page, int size) {
        Page<Problem> result = problemService.newProblems(labelId, page, size);
        return new ResponseEntity(StatusCode.SUCCESS.getCode(), result);
    }
    @GetMapping("/waitlist/{labelId}/{page}/{size}")
    public ResponseEntity waitProblems(String labelId, int page, int size) {
        Page<Problem> result = problemService.newProblems(labelId, page, size);
        return new ResponseEntity(StatusCode.SUCCESS.getCode(), result);
    }
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity findAll(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param lableId lableId
	 * @return
	 */
	@GetMapping("/lable/{lableId}")
	public ResponseEntity findById(@PathVariable String lableId){
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",baseFeignLableClient.getByLabelId(lableId));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public ResponseEntity findSearch( @RequestBody Map searchMap){
        return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity add(@RequestBody Problem problem  ){
		problemService.add(problem);
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public ResponseEntity update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new ResponseEntity(true,StatusCode.SUCCESS.getCode(),"删除成功");
	}
	
}
