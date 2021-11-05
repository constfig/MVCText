package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.ClassGradeTeacher;
import cn.moquan.service.ClassGradeTeacherService;
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
@RequestMapping("/classTeacher")
public class ClassGradeTeacherController {

    @Autowired
    ClassGradeTeacherService classGradeTeacherService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getClassGradeTeacher(@RequestBody BeanUtil<ClassGradeTeacher> classGradeTeacherBeanUtil){

        CommonResponseBody responseBody;

        List<ClassGradeTeacher> classGradeTeacherList = classGradeTeacherService.getClassGradeTeacher(classGradeTeacherBeanUtil.getInfo());

        if(classGradeTeacherList != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, classGradeTeacherList);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertClassGradeTeacher(@RequestBody BeanUtil<ClassGradeTeacher> classGradeTeacherBeanUtil){

        CommonResponseBody responseBody;

        if(classGradeTeacherService.insertClassGradeTeacher(classGradeTeacherBeanUtil.getInfo(), classGradeTeacherBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }
    
        return responseBody;
    }

//    @ResponseBody
//    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE)
//    public CommonResponseBody deleteClassGradeTeacherById(@RequestBody BeanUtil<ClassGradeTeacher> classGradeTeacherBeanUtil){
//
//        CommonResponseBody responseBody;
//
//        if(classGradeTeacherService.deleteClassGradeTeacherById(classGradeTeacherBeanUtil.getIdList())){
//            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
//        }else{
//            responseBody = new CommonResponseBody(StatusNumber.FAILED);
//        }
//
//        return responseBody;
//    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResponseBody deleteClassGradeTeacher(@RequestBody BeanUtil<ClassGradeTeacher> classGradeTeacherBeanUtil){

        CommonResponseBody responseBody;

        System.out.println(classGradeTeacherBeanUtil.getInfo());

        if(classGradeTeacherService.deleteClassGradeTeacherUseInfo(classGradeTeacherBeanUtil.getInfo())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

}
