package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.Classroom;
import cn.moquan.service.ClassroomService;
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
 * 21/10/27 开始更新第二版
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getClassroom(@RequestBody BeanUtil<Classroom> classroomBeanUtil){

        CommonResponseBody responseBody;
        List<Classroom> classroomList = classroomService.getClassroom(classroomBeanUtil.getInfo());

        if(classroomList != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, classroomList);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertClassroom(@RequestBody BeanUtil<Classroom> classroomBeanUtil){

        CommonResponseBody responseBody;

        if (classroomService.insertClassroom(classroomBeanUtil.getInfoList())){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponseBody updateClassroom(@RequestBody BeanUtil<Classroom> classroomBeanUtil){

        CommonResponseBody responseBody;

        try {
            responseBody = classroomService.updateClassroom(classroomBeanUtil.getInfo(), classroomBeanUtil.getIdList());
        } catch (RollBackException e) {
            e.printStackTrace();
            responseBody = e.getErrorInfo();
        }

        return responseBody;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResponseBody deleteClassroomById(@RequestBody BeanUtil<Classroom> classroomBeanUtil){
    
        CommonResponseBody responseBody;

        try {
            responseBody = classroomService.deleteClassroomById(classroomBeanUtil.getIdList());
        } catch (RollBackException e) {
            e.printStackTrace();
             responseBody = e.getErrorInfo();
        }

        return responseBody;
    }
}
