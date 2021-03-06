package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {


   // void add(Setmeal setmeal, Integer[] checkgroupIds);
   void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Setmeal findById(Integer id);
    List<Integer> findCheckGroupIdBySetmealId(Integer id);
    void edit(Setmeal setmeal, Integer[] chegroupIds);

    void delete(Integer id);
}
