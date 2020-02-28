package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constast.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.utils.QiniuUtils;
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
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;


   /* @Override //新增套餐
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //保存套餐
        setmealDao.add(setmeal);
        //建立套餐和中间标的关联关系,想中间表保存数据
        if (checkgroupIds!=null && checkgroupIds.length>0){
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
    }

    private void setSetmealAndCheckGroup(Integer setmealid, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            //使用map封装
            Map map = new HashMap();
            map.put("setmealid",setmealid);
            map.put("checkgroupIds",checkgroupIds);
            setmealDao.addSetmealAndCheckGroup(map);
        }
    }*/
   @Override
   public void add(Setmeal setmeal, Integer[] checkgroupIds) {
       // 1：保存套餐
       setmealDao.add(setmeal);
       // 2：建立套餐和检查组的关联关系，向中间表保存数据
       setSetmealAndCheckGruop(setmeal.getId(),checkgroupIds);
   }
    // 建立套餐和检查组的关联关系，向中间表保存数据
    private void setSetmealAndCheckGruop(Integer setmealId, Integer[] checkgroupIds) {
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkGroupId : checkgroupIds) {
                // 使用Map
                Map map = new HashMap();
                map.put("setmealId",setmealId);
                map.put("checkGroupId",checkGroupId);
                setmealDao.addSetmealAndCheckGroup(map);
            }
        }
    }

    //分页查询
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
       //初始化分页
        PageHelper.startPage(currentPage,pageSize);
        //查询
        Page<Setmeal> page =setmealDao.findPage(queryString);
        //封装结果集
        return new PageResult(page.getTotal(),page.getResult());
    }

    //使用套餐id查询套餐数据
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }
    //查询套餐对应的检查项集合
    @Override
    public List<Integer> findCheckGroupIdBySetmealId(Integer id) {
        List <Integer> list = setmealDao.findCheckGroupIdBySetmealId(id);
        return list;
    }
    //编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] chegroupIds) {
       //使用套餐id 查询数据库对应的套餐 获取数据库存放的img
        Setmeal setmeal_img = setmealDao.findById(setmeal.getId());
        String img = setmeal_img.getImg();
        //判断页面传递的图片名称是否和数据库的图片名称一致,名称不一致,就更新图片,删除七牛云之前数据库的图片
        if (setmeal.getImg()!=null && !setmeal.getImg().equals(img)){
            QiniuUtils.deleteFileFromQiniu(img);
        }
        //根据套餐id删除中间表数据
        setmealDao.deleteAssociation(setmeal.getId());
        //向中间表插入数据
        setSetmealAndCheckGruop(setmeal.getId(),chegroupIds);
        //更新套餐基本信息
        setmealDao.edit(setmeal);
    }

    //删除套餐
    @Override
    public void delete(Integer id) {
        // 使用套餐id，查询数据库对应的套餐，获取数据库存放的img
        Setmeal setmeal_img = setmealDao.findById(id);
        // 使用套餐id，查询套餐和检查组中间表
        Long count = setmealDao.findSetmealAndCheckGroupCountBySetmealId(id);
        // 存在数据 抛出运行时异常
        if (count>0){
            throw new RuntimeException(MessageConstant.GET_SETMEALANDCHECKITEMERROR);
        }
        // 删除套餐
        setmealDao.delete(id);
        // 获取存放的图片信息
        String img = setmeal_img.getImg();
        // 需要先删除七牛云之前数据库的图片
        if (img!=null && !"".equals(img)){
            QiniuUtils.deleteFileFromQiniu(img);
        }
    }


}