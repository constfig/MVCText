package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.Classroom;
import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.service.TeachCourseInfoService;
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
@RequestMapping("/teach")
public class TeachCourseInfoController {

    @Autowired
    TeachCourseInfoService teachCourseInfoService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getTeachCourseInfo(@RequestBody BeanUtil<TeachCourseInfo> teachCourseInfoBeanUtil){

        CommonResponseBody responseBody;
        List<TeachCourseInfo> classroomList = teachCourseInfoService.getTeachCourseInfo(teachCourseInfoBeanUtil.getInfo());

        if(classroomList != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, classroomList);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertTeachCourseInfo(@RequestBody BeanUtil<TeachCourseInfo> teachCourseInfoBeanUtil){

        CommonResponseBody responseBody;

        if (teachCourseInfoService.insertTeachCourseInfo(teachCourseInfoBeanUtil.getInfoList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponseBody updateTeachCourseInfo(@RequestBody BeanUtil<TeachCourseInfo> teachCourseInfoBeanUtil){

        CommonResponseBody responseBody;

        if(teachCourseInfoService.updateTeachCourseInfo(teachCourseInfoBeanUtil.getInfo(), teachCourseInfoBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResponseBody deleteTeachCourseInfo(@RequestBody BeanUtil<Classroom> classroomBeanUtil){
    
        CommonResponseBody responseBody;

        if(teachCourseInfoService.deleteTeachCourseInfo(classroomBeanUtil.getIdList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }
    
        return responseBody;
    }
    
    @ResponseBody
    @RequestMapping(value = "/create")
    public CommonResponseBody createTeachInfo(){
    
        CommonResponseBody responseBody;

        teachCourseInfoService.create();
    
        return responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
    }
}
