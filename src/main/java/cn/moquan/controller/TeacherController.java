package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.Teacher;
import cn.moquan.service.TeacherService;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
import cn.moquan.util.StatusNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public CommonResponseBody getTeacherById(@PathVariable int id){

        CommonResponseBody responseBody;

        Teacher teacherById = teacherService.getTeacherById(id);

        if(teacherById != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, teacherById);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getTeacher(@RequestBody BeanUtil<Teacher> teacherBeanUtil){

        CommonResponseBody responseBody;
        List<Teacher> teacherList = teacherService.getTeacher(teacherBeanUtil.getInfo());

        if(teacherList != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, teacherList);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertTeacher(@RequestBody BeanUtil<Teacher> teacherBeanUtil){

        CommonResponseBody responseBody;

        if (teacherService.insertTeacher(teacherBeanUtil.getInfoList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponseBody updateTeacher(@RequestBody BeanUtil<Teacher> teacherUtil){

        CommonResponseBody responseBody;

        try {
            responseBody = teacherService.updateTeacher(
                    teacherUtil.getInfo(), teacherUtil.getIdList());
        } catch (RollBackException e) {
            e.printStackTrace();
            responseBody = e.getErrorInfo();
        }

        return responseBody;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResponseBody deleteTeacherById(@RequestBody BeanUtil<Teacher> teacherBeanUtil){
    
        CommonResponseBody responseBody;

        try {
            responseBody = teacherService.deleteTeacherById(teacherBeanUtil.getIdList());
        } catch (RollBackException e) {
            e.printStackTrace();
            responseBody = e.getErrorInfo();
        }

        return responseBody;
    }
}
