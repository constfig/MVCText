package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.StudentTeacher;
import cn.moquan.service.StudentTeacherService;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.StatusNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Controller
@RequestMapping("/studentTeacher")
public class StudentTeacherController {

    @Autowired
    StudentTeacherService studentTeacherService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getStudentTeacher(@RequestBody BeanUtil<StudentTeacher> studentTeacherBeanUtil){

        CommonResponseBody responseBody;

        List<StudentTeacher> studentTeacherList = studentTeacherService.getStudentTeacher(studentTeacherBeanUtil.getInfo());

        if(studentTeacherList != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, studentTeacherList);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertStudentTeacher(@RequestBody BeanUtil<StudentTeacher> studentTeacherBeanUtil){

        CommonResponseBody responseBody;

        if(studentTeacherService.insertStudentTeacher(studentTeacherBeanUtil.getInfo(), studentTeacherBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }
    
        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public CommonResponseBody deleteStudentTeacherById(@RequestBody BeanUtil<StudentTeacher> studentTeacherBeanUtil){

        CommonResponseBody responseBody;

        if(studentTeacherService.deleteStudentTeacherById(studentTeacherBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResponseBody deleteStudentTeacher(@RequestBody BeanUtil<StudentTeacher> studentTeacherBeanUtil){

        CommonResponseBody responseBody;

        if(studentTeacherService.deleteStudentTeacher(studentTeacherBeanUtil.getInfo(), studentTeacherBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

}
