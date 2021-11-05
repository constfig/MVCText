package cn.moquan.service;

import cn.moquan.bean.*;
import cn.moquan.dao.ClassGradeDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
import cn.moquan.util.StatusNumber;
import cn.moquan.util.ThrowExceptionUtil;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Service
public class ClassGradeService {

    @Autowired
    ClassGradeDao classGradeDao;
    @Autowired
    StudentService studentService;
    @Autowired
    TeachCourseInfoService teachCourseInfoService;
    @Autowired
    ClassroomService classroomService;
    @Autowired
    ClassGradeTeacherService classGradeTeacherService;
    @Autowired
    StudentTeacherService studentTeacherService;


    public List<ClassGrade> getClassGrade(ClassGrade classGradeInfo) {

        return classGradeDao.getClassGrade(classGradeInfo);
    }

    public boolean insertClassGrade(List<ClassGrade> classGradeInfoList) {

        for (ClassGrade nowClassGrade : classGradeInfoList) {

            String nowSchoolName = nowClassGrade.getSchoolName();
            String nowClassroomRealId = nowClassGrade.getClassroomRealId();
            String nowClassName = nowClassGrade.getClassName();
            String nowGradeName = nowClassGrade.getGradeName();

            Classroom oldClassroom = new Classroom();
            oldClassroom.setSchoolName(nowSchoolName);
            oldClassroom.setRealId(nowClassroomRealId);

            Classroom newClassroom = new Classroom();

            newClassroom.setClassName(nowClassName);
            newClassroom.setGradeName(nowGradeName);
            newClassroom.setSchoolName(nowSchoolName);
            newClassroom.setRealId(nowClassroomRealId);

            ThrowExceptionUtil.throwRollBackException(
                    classroomService.updateClassroomCommon(newClassroom, oldClassroom),
                    "添加班级时,更新教室信息失败, 请检查!"
            );

            /*
             * 级联更新判断
             * 如果 学生表 和 授课信息表 存在相关信息 就进行级联更新
             * 后续取消了 授课信息表的级联更新, 授课信息表会因为其它数据的变动而删除
             */

            Student student = new Student();
            student.setSchoolName(nowSchoolName);
            student.setClassroomRealId(nowClassroomRealId);
            TeachCourseInfo teachCourseInfo = new TeachCourseInfo();
            teachCourseInfo.setSchoolName(nowSchoolName);
            teachCourseInfo.setClassroomRealId(nowClassroomRealId);

            List<Student> studentList = studentService.getStudent(student);
            List<TeachCourseInfo> teachCourseInfoList = teachCourseInfoService.getTeachCourseInfo(teachCourseInfo);
            if (studentList.size() > 0 || teachCourseInfoList.size() > 0) {
                student.setClassName(nowClassName);
                student.setGradeName(nowGradeName);
//                teachCourseInfo.setClassName(nowClassName);
//                teachCourseInfo.setGradeName(nowGradeName);
                for (Student s : studentList) {
//                    studentService.updateStudentCommon(student, s);
                    // 添加学生的班级信息
                    studentService.updateStudentCommonNotCascade(student, s);
                }
//                for(TeachCourseInfo t : teachCourseInfoList){
//                    teachCourseInfoService.updateClassGrade(teachCourseInfo, t);
//                }
            }
        }


        return classGradeDao.insertClassGrade(classGradeInfoList);
    }

    public CommonResponseBody deleteClassGrades(List<Integer> idList) {

        for (int id : idList) {
            clearClassGradeInfo(id);
        }

        if (!classGradeDao.deleteClassGrades(idList)) {
            throw new RollBackException("删除班级信息失败",
                    new CommonResponseBody(StatusNumber.FAILED,
                            "删除班级信息失败, 请检查后重试"));
        }

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    /**
     * 更新班级信息
     *
     * @param classGradeInfo (requisite)
     *                       className
     *                       gradeName
     *                       schoolName
     *                       (choice)
     *                       ClassroomRealId
     * @param idList         批量 id列表
     * @return
     */
    public CommonResponseBody updateClassGrade(ClassGrade classGradeInfo, List<Integer> idList) {

        System.out.println(classGradeInfo);
        System.out.println(idList);

        for (int id : idList) {
            updateOtherInfo(id, classGradeInfo);
        }

        ThrowExceptionUtil.throwRollBackException(
                classGradeDao.updateClassGrade(classGradeInfo, idList),
                "更新班级信息失败, 请检查!"
        );

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public boolean updateClassGradeCommon(ClassGrade newInfo, ClassGrade targetInfo) {

        List<ClassGrade> classGrade = getClassGrade(targetInfo);

        for (ClassGrade c : classGrade) {
            updateOtherInfo(c.getId(), newInfo);
        }


        return classGradeDao.updateClassGradeCommon(newInfo, targetInfo);
    }

    public boolean deleteClassGrade(ClassGrade info) {

        List<ClassGrade> classGrade = getClassGrade(info);

        if (classGrade.size() > 0) {
            for (ClassGrade c : classGrade) {
                clearClassGradeInfo(c.getId());
            }

            ThrowExceptionUtil.throwRollBackException(
                    classGradeDao.deleteClassGrade(info),
                    "删除班级信息失败, 请检查!"
            );
        }

        return true;
    }

    public void clearClassGradeInfo(int id) {

        ClassGrade classGrade = new ClassGrade();
        classGrade.setId(id);
        List<ClassGrade> classGradeList = getClassGrade(classGrade);
        // 检查该班级是否存在
        if (classGradeList.size() == 0) {
            throw new RollBackException("该班级不存在, 请检查!",
                    new CommonResponseBody(StatusNumber.FAILED, "该班级不存在, 请检查!"));
        }
        // 获取班级信息
        classGrade = classGradeList.get(0);

        String schoolName = classGrade.getSchoolName();
        String gradeName = classGrade.getGradeName();
        String className = classGrade.getClassName();
        String classroomRealId = classGrade.getClassroomRealId();

        // 置空教室信息
        Classroom nullClassroomInfo = new Classroom();
        nullClassroomInfo.setClassName("");
        nullClassroomInfo.setGradeName("");
        Classroom targetClassroomInfo = new Classroom();
        targetClassroomInfo.setSchoolName(schoolName);
        targetClassroomInfo.setRealId(classroomRealId);
        // 更新教室的班级信息
        classroomService.updateClassroomNotCascading(nullClassroomInfo, targetClassroomInfo);

        // 更新学生的班级信息
        Student newStudentInfo = new Student();
        newStudentInfo.setGradeName("");
        newStudentInfo.setClassName("");
        Student targetStudentInfo = new Student();
        targetStudentInfo.setSchoolName(schoolName);
        targetStudentInfo.setGradeName(gradeName);
        targetStudentInfo.setClassName(className);
        targetStudentInfo.setClassroomRealId(classroomRealId);
        // 更新学生的班级信息  删除教师时 学生和老师就没有关联了
//        studentService.updateStudentCommonNotCascade(newStudentInfo, targetStudentInfo);
        studentService.updateStudentCommon(newStudentInfo, targetStudentInfo);
/*
        // 更新授课信息
        TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
        newTeachCourseInfo.setGradeName("");
        newTeachCourseInfo.setClassName("");
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(schoolName);
        targetTeachCourseInfo.setGradeName(gradeName);
        targetTeachCourseInfo.setClassName(className);
        targetTeachCourseInfo.setClassroomRealId(classroomRealId);
        // 更新
        teachCourseInfoService.updateClassGrade(newTeachCourseInfo, targetTeachCourseInfo);*/

        // 删除授课信息
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(schoolName);
        targetTeachCourseInfo.setGradeName(gradeName);
        targetTeachCourseInfo.setClassName(className);
        targetTeachCourseInfo.setClassroomRealId(classroomRealId);
        // 更新
        teachCourseInfoService.deleteTeachCourseUseInfo(targetTeachCourseInfo);

        // 删除班级教师关联
        classGradeTeacherService.deleteClassGradeTeacherUseInfo(
                new ClassGradeTeacher(0, id, 0, schoolName));
    }

    /**
     * 班级级联更新接口
     *
     * @param id             更新目标的 id
     * @param classGradeInfo 新的信息
     */
    public void updateOtherInfo(int id, ClassGrade classGradeInfo) {
        ClassGrade nowInfo = new ClassGrade();
        nowInfo.setId(id);
        // 查询班级的 旧 班号 级号
        ClassGrade classGradeOld = getClassGrade(nowInfo).get(0);
        String oldSchoolName = classGradeOld.getSchoolName();
        String oldGradeName = classGradeOld.getGradeName();
        String oldClassName = classGradeOld.getClassName();
        String oldClassroomRealId = classGradeOld.getClassroomRealId();

        String newClassroomRealId = classGradeInfo.getClassroomRealId();
        String newClassName = classGradeInfo.getClassName();
        String newGradeName = classGradeInfo.getGradeName();
        String newSchoolName = classGradeInfo.getSchoolName();

        // 是否更新教室信息
        if (newClassroomRealId != null && !"".equals(newClassroomRealId) &&
                !newClassroomRealId.equals(oldClassroomRealId)) {
            // 更新教室
            // 将原来的教室置为空教室
            // 获取原来的教室信息
            Classroom oldClassroom = new Classroom();
            oldClassroom.setSchoolName(oldSchoolName);
            oldClassroom.setRealId(oldClassroomRealId);
            oldClassroom.setGradeName(oldGradeName);
            oldClassroom.setClassName(oldClassName);

            // 获取到信息
            List<Classroom> classroomList = classroomService.getClassroom(oldClassroom);

            // 原班级为空 不需要置空
            if (!"".equals(oldClassroomRealId)) {
                if (classroomList.size() > 0) {
                    // 设置班级的教室信息用于置空更新
                    Classroom nullClassroomInfo = new Classroom();
                    nullClassroomInfo.setClassName("");
                    nullClassroomInfo.setGradeName("");
                    // 开始更新教室信息, 这个更新不需要教室进行级联更新
                    classroomService.updateClassroomNotCascading(nullClassroomInfo, oldClassroom);
                } else {
                    // 原教室不存在 报错 信息错误
                    throw new RollBackException("更新班级教室时, 原教室查询失败, 请检查!",
                            new CommonResponseBody(StatusNumber.FAILED, "更新班级教室时, 原教室查询失败, 请检查!"));
                }
            }

            // 将空教室更新为当前使用
            // 判断教室是否有班级进行使用
            Classroom newClassroom = new Classroom();
            newClassroom.setRealId(newClassroomRealId);
            newClassroom.setSchoolName(newSchoolName);
            classroomList = classroomService.getClassroom(newClassroom);
            if (classroomList.size() > 0) {
                // 设置新教室的更新参数
                Classroom newClassroomInfo = new Classroom();
                newClassroomInfo.setClassName(newClassName);
                newClassroomInfo.setGradeName(newGradeName);
                // 开始更新新的教室号, 不需要级联更新
                classroomService.updateClassroomNotCascading(newClassroomInfo, newClassroom);
            } else {
                // 新教室不存在 报错 信息错误
                throw new RollBackException("更新班级教师时, 新教室查询失败, 请检查!",
                        new CommonResponseBody(StatusNumber.FAILED, "更新班级教师时, 新教室查询失败, 请检查!"));
            }
        } else {// 更新教室号完成

            Classroom newClassroom = new Classroom();
            newClassroom.setGradeName(newGradeName);
            newClassroom.setClassName(newClassName);
            newClassroom.setSchoolName(newSchoolName);
            Classroom targetClassroom = new Classroom();
            targetClassroom.setSchoolName(oldSchoolName);
            targetClassroom.setGradeName(oldGradeName);
            targetClassroom.setClassName(oldClassName);
            targetClassroom.setRealId(oldClassroomRealId);
            // 更新信息
            classroomService.updateClassroomNotCascading(newClassroom, targetClassroom);

        }// end if 教室信息更新完成 开始更新级联信息


        // 更新学生信息表中的数据
        // 准备需要更新的数据
        Student newStudentInfo = new Student();
        newStudentInfo.setSchoolName(newSchoolName);
        newStudentInfo.setGradeName(newGradeName);
        newStudentInfo.setClassName(newClassName);
        newStudentInfo.setClassroomRealId(newClassroomRealId);
        // 查询需要更新的目标学生
        Student targetStudentInfo = new Student();
        targetStudentInfo.setSchoolName(oldSchoolName);
        targetStudentInfo.setGradeName(oldGradeName);
        targetStudentInfo.setClassName(oldClassName);
        targetStudentInfo.setClassroomRealId(oldClassroomRealId);
        // 级联更新学生信息
//        studentService.updateStudentCommon(newStudentInfo, targetStudentInfo);
        studentService.updateStudentCommonNotCascade(newStudentInfo, targetStudentInfo);

        // 更新授课信息表中的数据
        // 准备需要更新的数据
        TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
        newTeachCourseInfo.setSchoolName(newSchoolName);
        newTeachCourseInfo.setGradeName(newGradeName);
        newTeachCourseInfo.setClassName(newClassName);
        newTeachCourseInfo.setClassroomRealId(newClassroomRealId);
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(oldSchoolName);
        targetTeachCourseInfo.setGradeName(oldGradeName);
        targetTeachCourseInfo.setClassName(oldClassName);
        targetTeachCourseInfo.setClassroomRealId(oldClassroomRealId);
        // 级联更新授课信息
        teachCourseInfoService.updateClassGrade(newTeachCourseInfo, targetTeachCourseInfo);
    }

    public boolean updateClassGradeNotCascading(ClassGrade newInfo, ClassGrade oldInfo) {

        return classGradeDao.updateClassGradeCommon(newInfo, oldInfo);
    }
}
