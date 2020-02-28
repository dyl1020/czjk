package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: health_parent
 * @ClassName CheckGroupDao
 * @description:
 * @author: dyl
 * @create: 2020-02-22 22:36
 **/

public interface SetmealDao {

   /* //新增套餐
    void add(Setmeal setmeal);
    //保存中间表数据
    void addSetmealAndCheckGroup(Map map);*/
   void add(Setmeal setmeal);
   void addSetmealAndCheckGroup(Map map);

   //分页查询套餐
    Page<Setmeal> findPage(String queryString);

    //使用套餐id查询套餐数据
    Setmeal findById(Integer id);
    //查询套餐对应的检查项集合
    List<Integer> findCheckGroupIdBySetmealId(Integer id);
    //更新t_setmeal
    void edit(Setmeal setmeal);
    void deleteAssociation(Integer id);
}