package com.swan.base.service;

import com.swan.base.bean.Label;
import com.swan.base.mapper.LabelMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private IdWorker idWorker;

    public List<Label> getAll() {
        return labelMapper.findAll();
    }

    public Label getLabelById(String id) {
        return labelMapper.findById(id).get();
    }

    public void addLabel(Label label) {
        label.setId(idWorker.nextId() + "");
        labelMapper.save(label);
    }

    public void removeLabel(String id) {
        labelMapper.deleteById(id);
    }

    public void modifyLabel(Label label) {
        labelMapper.save(label);
    }

    public List<Label> getBySearch(Label label) {
        /**
         * root 根对象，将条件封装到哪个bean对象中，where xxx = xxx
         * query 查询关键字，如group by、order by、distinct
         * cb 封装查询条件的对象，若直接返回null，表示不需要任何条件
         */
        return labelMapper.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(label.getLabelname())) {
                Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                list.add(labelname);
            }
            if (StringUtils.isNotBlank(label.getState())) {
                Predicate state = cb.like(root.get("state").as(String.class), label.getState());
                list.add(state);
            }
            Predicate[] predicate = new Predicate[list.size()];
            predicate = list.toArray(predicate);
            return cb.and(predicate);
        });
    }

    public Page<Label> pageQuery(int page, int size, Label label) {
        Pageable pageable = PageRequest.of(page - 1, size);   // 底层page页码从0开始，所以总页数要减1
        return labelMapper.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(label.getLabelname())) {
                Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                list.add(labelname);
            }
            if (StringUtils.isNotBlank(label.getState())) {
                Predicate state = cb.like(root.get("state").as(String.class), label.getState());
                list.add(state);
            }
            Predicate[] predicate = new Predicate[list.size()];
            predicate = list.toArray(predicate);
            return cb.and(predicate);
        }, pageable);
    }
}
