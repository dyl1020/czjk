package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constast.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: health_parent
 * @ClassName CheckGroupController
 * @description:
 * @author: dyl
 * @create: 2020-02-22 22:50
 **/

@RestController
@RequestMapping(value = "/checkgroup")
public class CheckGroupController {

    @Reference//订阅
    CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping(value = "/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //分页查询检查组
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult = checkGroupService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
       return pageResult;
    }

    //使用检查组id查询检查组
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup = checkGroupService.findById(id);
            if (checkGroup!=null){
                return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS,checkGroup);
            }else {
                return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
            }
    }
    //查询检查组对应检查项的集合
    @RequestMapping(value = "/findCheckItemIdsByCheckGroupId")
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id){
        List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return list;
    }
    //修改检查组
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    //删除检查组
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            checkGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException ex){
            ex.printStackTrace();
            return new Result(false,ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    //查询所有检查组
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        List<CheckGroup> list = checkGroupService.findAll();
        if (list!=null && list.size()>0){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}