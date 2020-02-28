package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constast.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import org.aspectj.lang.annotation.Around;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: health_parent
 * @ClassName CheckGroupServiceImpl
 * @description:
 * @author: dyl
 * @create: 2020-02-22 22:49
 **/

@Service
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Override//新增检查组
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //第一步 接收到检查组的对象checkgroup,执行保存检查组,保存检查组的同时,返回检查组的id
         checkGroupDao.add(checkGroup);
        //第二步 接收到integer类型的数组,存放检查项的id,和保存返回的检查组id,项中间表插入数据
         this.CheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }
    //向中间表插入数据
    private void CheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        //遍历
        if (checkitemIds != null && checkitemIds.length>0){
            for (Integer checkItemId : checkitemIds) {
                //保存(dao方法传递多个参数 )
                checkGroupDao.addCheckGroupAndCheckItem(checkGroupId,checkItemId);
                //map传递多个参数
//                Map map = new HashMap();
//                map.put("checkgroup_id",checkGroupId);
//                map.put("checkitem_id",checkItemId);
//                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }
    }


    @Override  //分页查询检查组
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //初始化分页
        PageHelper.startPage(currentPage,pageSize);
        //查询
       Page<CheckGroup> page = checkGroupDao.findPage(queryString);
       //封装结果集
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override //使用检查组id 查询检查组
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override  //根据检查组id查询对应的检查项的集合
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> list = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return list;
    }

    @Override  //修改检查组
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
       // 1：修改编辑检查组的相关信息字段，更新t_checkgroup
        checkGroupDao.edit(checkGroup);
       // 2：修改检查组和检查项的中间表，更新t_checkgroup_checkitem（中间表）
             //（1）使用检查组的id，先删除之前的数据
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckGroupId(checkGroup.getId());
             //（2）重新建立检查组和检查项的关联关系，新增中间表的数据
        this.CheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }

    @Override  //删除检查组
    public void delete(Integer id) {
        //删除检查组之前,先使用检查组id,判断中间表中是否存有数据
        //查询检查组和检查项中间表
        Long count1 = checkGroupDao.findCheckGroupIdByCheckItemId(id);
        //存在数据
        if (count1>0){
            throw new RuntimeException(MessageConstant.GET_CHECKGROUPANDCHECKITEMERROR);
        }
        //查询检查组和套餐中间表
        Long count2 = checkGroupDao.findSetmealAndCheckGroupCountByCheckGroupId(id);
        //存在数据
        if (count2>0){
            throw new RuntimeException(MessageConstant.GET_CHECKITEMANDSETMEALERROR);
        }
        checkGroupDao.delete(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> list = checkGroupDao.findAll();
        return list;
    }
}