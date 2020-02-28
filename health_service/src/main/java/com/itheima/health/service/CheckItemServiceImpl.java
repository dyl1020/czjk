package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constast.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: health_parent
 * @ClassName CheckItemServiceImpl
 * @description:
 * @author: dyl
 * @create: 2020-02-16 17:18
 **/
@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;

    @Override//查询所有
    public List<CheckItem> findAll() {

        List<CheckItem> list = checkItemDao.findAll();
        return list;
    }

    @Override//新增检查项
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override //分页查询
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //使用mybatis分页插件,在applicationContext-dao.xml中定义插件
        //初始化参数
        PageHelper.startPage(currentPage,pageSize);
        //查询 返回page
        Page <CheckItem> page = checkItemDao.findPage(queryString);
        //封装pageResult
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override//删除检查项
    public void delete(Integer id) {
        //在删除检查项之前,先给予判断,判断当前检查项是否被检查组关联,如果没有关联,就可以删除 如果有关联,则不能删除,给予提示
        long count = checkItemDao.findCheckGroupAndCheckItemCountByCheckItemId(id);
        //判断数据count是否存在,存在就不能删除
        if (count>0){
            //给予提示(异常),将异常抛给controller 只有运行时异常,spring的事务处理才捕获
            throw new RuntimeException(MessageConstant.GET_CHECKITEMANDCHECKGROUPERROR);
        }
        //删除
        checkItemDao.delete(id);
    }

    /*@Override//根据id检查检查项
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }
    @Override//编辑保存检查项
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }*/

    // 主键查询
    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemDao.findCheckItemById(id);
        return checkItem;
    }

    // 编辑保存
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

}
