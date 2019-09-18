package com.swan.base.controller;

import com.swan.base.bean.Label;
import com.swan.base.service.LabelService;
import entity.PageResult;
import entity.ResponseEntity;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lable")
public class LabelController {

    @Autowired
    LabelService lableService;

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"查询成功",lableService.getAll());
    }

    @GetMapping("/{lableId}")
    public ResponseEntity getByLabelId(@PathVariable String lableId) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"查询成功",lableService.getLabelById(lableId));
    }

    @PostMapping()
    public ResponseEntity addLabel(Label label) {
        lableService.addLabel(label);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"添加成功",null);
    }

    @PutMapping("/{lableId}")
    public ResponseEntity modifyLabel(@PathVariable String lableId, Label lable) {
        lable.setId(lableId);
        lableService.modifyLabel(lable);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"修改成功",null);
    }

    @DeleteMapping("/{lableId}")
    public ResponseEntity removeLabel(@PathVariable String lableId) {
        lableService.removeLabel(lableId);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"刪除成功",null);
    }

    @PostMapping("/search")
    public ResponseEntity getBySearch(Label label) {
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"查询成功",lableService.getBySearch(label));
    }

    @PostMapping("/search/{page}/{size}")
    public ResponseEntity pageQuery(@PathVariable int page, @PathVariable int size, Label label) {
        Page<Label> pageData = lableService.pageQuery(page,size,label);
        return new ResponseEntity(true, StatusCode.SUCCESS.getCode(),"查询成功",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }

}
