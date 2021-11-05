package cn.moquan.service;

import cn.moquan.bean.*;
import cn.moquan.dao.ClassGradeTeacherDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
import cn.moquan.util.StatusNumber;
import cn.moquan.util.ThrowExceptionUtil;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/21
 */
@Service
public class ClassGradeTeacherService {

    @Autowired
    ClassGradeTeacherDao classGradeTeacherDao;
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClassGradeService classGradeService;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentTeacherService studentTeacherService;
    @Autowired
    TeachCourseInfoService teachCourseInfoService;

    public List<ClassGradeTeacher> getClassGradeTeacher(ClassGradeTeacher info) {
        return classGradeTeacherDao.getClassGradeTeacher(info);
    }

    public boolean insertClassGradeTeacher(ClassGradeTeacher info, List<Integer> idList) {

        if (info.getTeacherId() != 0) {

            for (int classId : idList) {
                // 获取班级信息
                ClassGrade classGrade = new ClassGrade();
                classGrade.setId(classId);
                List<ClassGrade> classGradeList = classGradeService.getClassGrade(classGrade);
                if (classGradeList.size() == 0) {
                    throw new RollBackException("添加教师班级关联时, 班级信息查询失败, 请检查!",
                            new CommonResponseBody(StatusNumber.SUCCESS, "添加教师班级关联时, 班级信息查询失败, 请检查!"));
                }
                classGrade = classGradeList.get(0);

                insertStudentTeacherCascade(info.getTeacherId(), classGrade);

            }
        }

        return classGradeTeacherDao.insertClassGradeTeacher(info, idList);
    }

    public boolean deleteClassGradeTeacherById(List<Integer> idList) {
        return classGradeTeacherDao.deleteClassGradeTeacherById(idList);
    }

    public boolean deleteClassGradeTeacherUseInfo(ClassGradeTeacher info) {

        // 当教师取消与班级的关联时
        if (info.getTeacherId() != 0 && info.getClassId() != 0) {
            // 删除与学生的关联
            StudentTeacher studentTeacher = new StudentTeacher();
            studentTeacher.setTeacherId(info.getTeacherId());
            studentTeacher.setSchoolName(info.getSchoolName());
            studentTeacherService.deleteStudentTeacherUseInfo(studentTeacher);
            // 删除授课信息
            ClassGrade classGrade = new ClassGrade();
            classGrade.setId(info.getClassId());
            List<ClassGrade> classGradesList = classGradeService.getClassGrade(classGrade);
            if (classGradesList.size() == 0) {
                throw new RollBackException("需要解除的班级不存在, 请检查!",
                        new CommonResponseBody(StatusNumber.FAILED, "需要解除的班级不存在, 请检查!"));
            }
            // 获取班级信息
            classGrade = classGradesList.get(0);
            TeachCourseInfo teachCourseInfo = new TeachCourseInfo();
            // 设置目标信息
            teachCourseInfo.setTeacherId(info.getTeacherId());
            teachCourseInfo.setGradeName(classGrade.getGradeName());
            teachCourseInfo.setClassName(classGrade.getClassName());
            teachCourseInfo.setSchoolName(classGrade.getSchoolName());
            // 删除授课信息
            teachCourseInfoService.deleteTeachCourseUseInfo(teachCourseInfo);
        }
        return classGradeTeacherDao.deleteClassGradeTeacherUseInfo(info);
    }

    public boolean updateClassGradeTeacher(ClassGradeTeacher newInfo, ClassGradeTeacher oldInfo) {

        if (getClassGradeTeacher(oldInfo).size() > 0) {
            ThrowExceptionUtil.throwRollBackException(
                    classGradeTeacherDao.updateClassGradeTeacher(newInfo, oldInfo),
                    "更新班级教师关联信息失败, 请检查!"
            );
        }

        return true;
    }

    public boolean deleteClassGradeTeacher(ClassGradeTeacher info) {
        return classGradeTeacherDao.deleteClassGradeTeacher(info);
    }

    /**
     * 添加 教师学生 级联关系
     *
     * @param teacherId  教师id
     * @param classGrade 班级信息
     */
    public void insertStudentTeacherCascade(int teacherId, ClassGrade classGrade) {

        // 根据班级信息查询学生
        String gradeName = classGrade.getGradeName();
        String className = classGrade.getClassName();
        String schoolName = classGrade.getSchoolName();
        // 设置目标学生信息
        Student targetStudent = new Student();
        targetStudent.setGradeName(gradeName);
        targetStudent.setClassName(className);
        targetStudent.setSchoolName(schoolName);
        // 获取学生列表
        List<Student> studentList = studentService.getStudent(targetStudent);
        // 需要更新的列表
        if (studentList.size() > 0) {
            // 获取列表 更新
            List<Integer> studentIdList = new ArrayList<>();
            for (Student s : studentList) {
                studentIdList.add(s.getId());
            }
            // 添加学生教师关联
            studentTeacherService.insertStudentTeacher(
                    new StudentTeacher(0, teacherId, schoolName), studentIdList);
        }

    }
}
