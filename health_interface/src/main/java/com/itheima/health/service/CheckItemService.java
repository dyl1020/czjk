package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);


   /* CheckItem findById(Integer id);
    void edit(CheckItem checkItem);*/

    // 跳转到检查项编辑页面
    CheckItem findById(Integer id);
    // 编辑保存
    void edit(CheckItem checkItem);
}
