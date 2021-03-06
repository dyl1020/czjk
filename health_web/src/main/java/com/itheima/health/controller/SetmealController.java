package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constast.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @program: health_parent
 * @ClassName CheckGroupController
 * @description:
 * @author: dyl
 * @create: 2020-02-22 22:50
 **/

@RestController
@RequestMapping(value = "/setmeal")
public class SetmealController {

    @Reference//订阅
    SetmealService setmealService;

    //上传图片
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile imgFile){
        try {
           /* 实现将图片存放到七牛云上
            参数一：字节数组（springmvc的方式）,使用imgFile.getBytes()
            参数二：上传文件名（唯一）*/
           //获取上传的文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 使用UUID的方式生成文件名
            String fileName = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
           //实现将图片存放到七牛云上
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //// 使用UUID的方式生成文件名,并返回
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //新增套餐并保存
    /*@RequestMapping(value = "/add")
    public Result add(@RequestBody Setmeal setmeal,Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }*/

    @RequestMapping(value = "/add")
    public Result add(@RequestBody Setmeal setmeal,Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    //分页查询套餐
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
       return pageResult;
    }

    //使用套餐id查询套餐
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        if (setmeal!=null){
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS,setmeal);
        }else {
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
    //查询套餐对应的检查组集合
    @RequestMapping(value = "/findCheckGroupIdBySetmealId")
    public List<Integer> findCheckGroupIdBySetmealId(Integer id) {
        List<Integer> list = setmealService.findCheckGroupIdBySetmealId(id);
        return list;
    }
    //编辑检查组
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer [] chegroupIds){
        try {
            setmealService.edit(setmeal,chegroupIds);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    //删除套餐
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

}