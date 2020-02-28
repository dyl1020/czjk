package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
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

public interface CheckGroupDao {

    //新增检查组
    void add(CheckGroup checkGroup);
    void addCheckGroupAndCheckItem(@Param(value = "checkGroupId") Integer checkGroupId, @Param(value = "checkItemId") Integer checkItemId);
    //    void addCheckGroupAndCheckItem(Map map);


    //分页查询
    Page<CheckGroup> findPage(String queryString);


    CheckGroup findById(Integer id);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);
    void deleteCheckGroupAndCheckItemByCheckGroupId(Integer id);

    void delete(Integer id);
    Long findCheckGroupIdByCheckItemId(Integer id);
    Long findSetmealAndCheckGroupCountByCheckGroupId(Integer id);

    List<CheckGroup> findAll();

}