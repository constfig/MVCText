package cn.moquan.controller;

import cn.moquan.bean.BeanUtil;
import cn.moquan.bean.Student;
import cn.moquan.service.StudentService;
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
 * @date 2021/10/19
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public CommonResponseBody getStudentById(@PathVariable("id") int id){

        CommonResponseBody responseBody;

        Student studentById = studentService.getStudentById(id);

        if(studentById != null){
            responseBody = new CommonResponseBody(StatusNumber.SUCCESS, studentById);
        }else{
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResponseBody getStudent(@RequestBody BeanUtil<Student> studentBeanUtil) {

        CommonResponseBody responseBody;

        List<Student> studentList = studentService.getStudent(studentBeanUtil.getInfo());

        if (studentList != null) {
            responseBody =  new CommonResponseBody(StatusNumber.SUCCESS, studentList);
        } else {
            responseBody = new CommonResponseBody(StatusNumber.FAILED);
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResponseBody insertStudent(@RequestBody BeanUtil<Student> studentBeanUtil){

        CommonResponseBody commonResponseBody;

        try {
            commonResponseBody = studentService.insertStudent(studentBeanUtil.getInfoList());
        } catch (RollBackException e) {
            commonResponseBody =  e.getErrorInfo();
        }

        return commonResponseBody;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CommonResponseBody deleteStudent(@PathVariable("id") int id){

        CommonResponseBody responseBody;

        try {
           responseBody = studentService.deleteStudentById(id);
        } catch (RollBackException e) {
            e.printStackTrace();
            responseBody = e.getErrorInfo();
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResponseBody deleteStudents(@RequestBody BeanUtil<Student> studentBeanUtil){

        CommonResponseBody responseBody;

        try {
            responseBody = studentService.deleteStudents(studentBeanUtil.getIdList());
        } catch (RollBackException e) {
            e.printStackTrace();
            responseBody = e.getErrorInfo();
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponseBody updateStudent(@RequestBody BeanUtil<Student> studentBeanUtil){

        CommonResponseBody responseBody;

        try {
            responseBody = studentService.updateStudent(studentBeanUtil.getInfo(), studentBeanUtil.getIdList());
        } catch (RollBackException e) {
            e.printStackTrace();
            responseBody = e.getErrorInfo();
        }

        return responseBody;
    }
}
