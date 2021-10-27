package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.school.School;
import cn.moquan.bean.teacher.Teacher;
import cn.moquan.bean.teacher.TeacherUtil;
import cn.moquan.service.SchoolService;
import cn.moquan.service.TeacherService;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.StateNumber;
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
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    SchoolService schoolService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getSchool(@RequestBody BeanUtil<School> schoolBeanUtil){

        CommonResponseBody responseBody;
        List<School> schoolList = schoolService.getSchool(schoolBeanUtil.getInfo());

        if(schoolList != null){
            responseBody = new CommonResponseBody(StateNumber.SUCCESS, schoolList);
        }else{
            responseBody = new CommonResponseBody(StateNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertSchool(@RequestBody BeanUtil<School> schoolBeanUtil){

        CommonResponseBody responseBody;

        if (schoolService.insertSchool(schoolBeanUtil.getInfoList())){
            responseBody = new CommonResponseBody(StateNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StateNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponseBody updateSchool(@RequestBody BeanUtil<School> schoolBeanUtil){

        CommonResponseBody responseBody;

        if(schoolService.updateSchool(schoolBeanUtil.getInfo(), schoolBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StateNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StateNumber.FAILED);
        }

        return responseBody;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResponseBody deleteSchoolById(@RequestBody BeanUtil<School> schoolBeanUtil){
    
        CommonResponseBody responseBody;

        if(schoolService.deleteSchoolById(schoolBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StateNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StateNumber.FAILED);
        }
    
        return responseBody;
    }
}
