package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.ClassGrade;
import cn.moquan.service.*;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
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
@RequestMapping("/class")
public class ClassGradeController {

    @Autowired
    ClassGradeService classGradeService;
    @Autowired
    StudentService studentService;
    @Autowired
    ClassGradeTeacherService classGradeTeacherService;
    @Autowired
    ClassroomService classroomService;
    @Autowired
    TeachCourseInfoService teachCourseInfoService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getClassGrade(@RequestBody BeanUtil<ClassGrade> classGradeBeanUtil){

        CommonResponseBody responseBody;

        List<ClassGrade> classGradesList = classGradeService.getClassGrade(classGradeBeanUtil.getInfo());

        if(classGradesList != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, classGradesList);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertClassGrade(@RequestBody BeanUtil<ClassGrade> classGradeBeanUtil){
    
        CommonResponseBody responseBody;

        if(classGradeService.insertClassGrade(classGradeBeanUtil.getInfoList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }
    
        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResponseBody deleteClassGrade(@RequestBody BeanUtil<ClassGrade> classGradeBeanUtil){

        CommonResponseBody responseBody;

        try {
            responseBody = classGradeService.deleteClassGrades(classGradeBeanUtil.getIdList());
        } catch (RollBackException e) {
            responseBody = e.getErrorInfo();
            e.printStackTrace();
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponseBody updateClassGrade(@RequestBody BeanUtil<ClassGrade> classGradeUtil){

        CommonResponseBody responseBody;

        System.out.println(classGradeUtil);

        try {
            responseBody = classGradeService.updateClassGrade(classGradeUtil.getInfo(), classGradeUtil.getIdList());
        } catch (RollBackException e) {
            responseBody = e.getErrorInfo();
            e.printStackTrace();
        }

        return responseBody;
    }

}
