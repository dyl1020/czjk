package com.itheima.health.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constast.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: health_parent
 * @ClassName CheckItemController
 * @description:
 * @author: dyl
 * @create: 2020-02-16 17:20
 **/

@RestController
@RequestMapping(value = "/checkitem")
public class CheckItemController {

    @Reference//订阅
    CheckItemService checkItemService;

    //查询所有
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        List<CheckItem> list=checkItemService.findAll();
        if (list!=null && list.size()>0){
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }else {
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //新增检查项
    @RequestMapping(value = "/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
            return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //分页条件查询
    @RequestMapping(value="/findPage")
    public PageResult findPage(@RequestBody  QueryPageBean queryPageBean){
       PageResult pageResult = checkItemService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
       return pageResult;
    }

    //删除检查项
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            checkItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException re) {
            re.printStackTrace();
            return new Result(false,re.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

   /* //id查询检查项
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        CheckItem checkItem = checkItemService.findById(id);
        if (checkItem!= null){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }else {
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //编辑保存检查项
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }*/

    // 跳转到检查项编辑页面
    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem = checkItemService.findById(id);
        if(checkItem!=null){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }
        else{
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //编辑保存
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }
}