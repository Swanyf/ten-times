package com.swan.spit.controller;

import com.swan.spit.bean.Spit;
import com.swan.spit.service.SpitService;
import entity.PageResult;
import entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    SpitService spitService;

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.SUCCESS(spitService.findAll());
    }

    @GetMapping("/comment/{parentid}/{page}/{size}")
    public ResponseEntity findByParentId(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> result = spitService.findByParentId(parentid, page, size);
        return ResponseEntity.SUCCESS(new PageResult<Spit>(result.getTotalElements(), result.getContent()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Spit spit) {
        spitService.save(spit);
        return ResponseEntity.SUCCESS();
    }

    @GetMapping("/{spitId}")
    public ResponseEntity findById(@PathVariable String spitId) {
        return ResponseEntity.SUCCESS(spitService.findById(spitId));
    }

    @PutMapping("/{spitId}")
    public ResponseEntity update(@PathVariable String spitId, Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return ResponseEntity.SUCCESS();
    }

    @DeleteMapping("/{spitId}")
    public ResponseEntity delete(@PathVariable String spitId) {
        spitService.delete(spitId);
        return ResponseEntity.SUCCESS();
    }

    @PutMapping("/thumbup/{spitId}")
    public ResponseEntity dianzan(@PathVariable String spitId) {
        return spitService.spitDianzan(spitId);
    }
}
