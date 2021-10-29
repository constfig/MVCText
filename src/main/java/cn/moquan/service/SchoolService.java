package cn.moquan.service;

import cn.moquan.bean.*;
import cn.moquan.dao.SchoolDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.StatusNumber;
import cn.moquan.util.ThrowExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.LanguageCallback;
import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Service
public class SchoolService {

    @Autowired
    SchoolDao schoolDao;
    @Autowired
    StudentService studentService;
    @Autowired
    ClassGradeService classGradeService;
    @Autowired
    ClassGradeTeacherService classGradeTeacherService;
    @Autowired
    TeachCourseInfoService teachCourseInfoService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentTeacherService studentTeacherService;
    @Autowired
    ClassroomService classroomService;


    public List<School> getSchool(School info) {
        return schoolDao.getSchool(info);
    }

    public boolean insertSchool(List<School> infoList) {
        return schoolDao.insertSchool(infoList);
    }

    public CommonResponseBody updateSchool(School info, List<Integer> idList) {

        for (int id : idList){
            String newSchoolName = info.getName();
            School oldSchool = getSchool(new School(id, null)).get(0);
            String oldSchoolName = oldSchool.getName();

            // 更新授课信息
            TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
            newTeachCourseInfo.setSchoolName(newSchoolName);
            TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
            targetTeachCourseInfo.setSchoolName(oldSchoolName);
            teachCourseInfoService.updateClassGrade(newTeachCourseInfo, targetTeachCourseInfo);
            // 更新学生信息
            Student newStudentInfo = new Student();
            newStudentInfo.setSchoolName(newSchoolName);
            Student targetStudent = new Student();
            targetStudent.setSchoolName(oldSchoolName);
            studentService.updateStudentCommon(newStudentInfo, targetStudent);
            // 更新学生教师信息
            StudentTeacher newStudentTeacher = new StudentTeacher();
            newStudentTeacher.setSchoolName(newSchoolName);
            StudentTeacher targetStudentTeacher = new StudentTeacher();
            targetStudentTeacher.setSchoolName(oldSchoolName);
            studentTeacherService.updateStudentTeacher(newStudentTeacher, targetStudentTeacher);
            // 更新教师信息
            Teacher newTeacher = new Teacher();
            newTeacher.setSchoolName(newSchoolName);
            Teacher targetTeacher = new Teacher();
            targetTeacher.setSchoolName(oldSchoolName);
            teacherService.updateTeacher(newTeacher, targetTeacher);
            // 更新班级信息
            ClassGrade newClassGrade = new ClassGrade();
            newClassGrade.setSchoolName(newSchoolName);
            ClassGrade targetClassGrade = new ClassGrade();
            targetClassGrade.setSchoolName(oldSchoolName);
            classGradeService.updateClassGradeCommon(newClassGrade, targetClassGrade);
            // 更新班级教师信息
            ClassGradeTeacher newClassGradeTeacher = new ClassGradeTeacher();
            newClassGradeTeacher.setSchoolName(newSchoolName);
            ClassGradeTeacher targetClassGradeTeacher = new ClassGradeTeacher();
            targetClassGradeTeacher.setSchoolName(oldSchoolName);
            classGradeTeacherService.updateClassGradeTeacher(newClassGradeTeacher, targetClassGradeTeacher);
            // 更新教室信息
            Classroom newClassroom = new Classroom();
            newClassroom.setSchoolName(newSchoolName);
            Classroom targetClassroom = new Classroom();
            targetClassroom.setSchoolName(oldSchoolName);
            classroomService.updateClassGrade(newClassGrade, targetClassGrade);
        }

        // 更新学校信息
        schoolDao.updateSchool(info, idList);

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public CommonResponseBody deleteSchoolById(List<Integer> idList) {

        for(int id : idList){
            School oldSchoolInfo = getSchool(new School(id, null)).get(0);
            String schoolName = oldSchoolInfo.getName();
            // 删除学生
            Student studentInfo = new Student();
            studentInfo.setSchoolName(oldSchoolInfo.getName());
            studentService.deleteStudent(studentInfo);
            // 删除班级
            ClassGrade classGradeInfo = new ClassGrade();
            classGradeInfo.setSchoolName(oldSchoolInfo.getName());
            classGradeService.deleteClassGrade(classGradeInfo);
            // 删除教室
            Classroom classroomInfo = new Classroom();
            classroomInfo.setSchoolName(schoolName);
            classroomService.deleteClassroom(classroomInfo);
            // 删除教师
            Teacher teacherInfo = new Teacher();
            teacherInfo.setSchoolName(schoolName);
            teacherService.deleteTeacher(teacherInfo);
            // 删除授课信息
            TeachCourseInfo teachCourseInfo = new TeachCourseInfo();
            teachCourseInfo.setSchoolName(schoolName);
            teachCourseInfoService.deleteTeachCourseUseInfo(teachCourseInfo);
            // 删除学生教师关联
            StudentTeacher studentTeacherInfo = new StudentTeacher();
            studentTeacherInfo.setSchoolName(schoolName);
            studentTeacherService.deleteStudentTeacherUseInfo(studentTeacherInfo);
            // 删除班级教师关联
            ClassGradeTeacher classGradeTeacher = new ClassGradeTeacher();
            classGradeTeacher.setSchoolName(schoolName);
            classGradeTeacherService.deleteClassGradeTeacher(classGradeTeacher);
        }

        ThrowExceptionUtil.throwRollBackException(
                schoolDao.deleteSchoolById(idList),
                "删除学校信息失败, 请检查!"
        );

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }
}
