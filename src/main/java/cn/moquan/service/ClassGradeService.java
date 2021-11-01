package cn.moquan.service;

import cn.moquan.bean.ClassGradeTeacher;
import cn.moquan.bean.Classroom;
import cn.moquan.bean.Student;
import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.bean.ClassGrade;
import cn.moquan.dao.ClassGradeDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
import cn.moquan.util.StatusNumber;
import cn.moquan.util.ThrowExceptionUtil;
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


    public List<ClassGrade> getClassGrade(ClassGrade classGradeInfo) {

        return classGradeDao.getClassGrade(classGradeInfo);
    }

    public boolean insertClassGrade(List<ClassGrade> classGradeInfoList) {

        for(ClassGrade nowClassGrade : classGradeInfoList){

            String nowSchoolName = nowClassGrade.getGradeName();
            String nowClassroomRealId = nowClassGrade.getClassroomRealId();
            String nowClassName = nowClassGrade.getClassName();
            String nowGradeName = nowClassGrade.getGradeName();

            Classroom oldClassroom = new Classroom();
            oldClassroom.setSchoolName(nowSchoolName);
            oldClassroom.setRealId(nowClassroomRealId);

            Classroom newClassroom = new Classroom();
            newClassroom.setClassName(nowClassName);
            newClassroom.setGradeName(nowGradeName);

            ThrowExceptionUtil.throwRollBackException(
                    classroomService.updateClassroomCommon(newClassroom, oldClassroom),
                    "添加班级时,更新教室信息失败, 请检查!"
            );
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

        for(ClassGrade c :classGrade ){
            updateOtherInfo(c.getId(), newInfo);
        }


        return classGradeDao.updateClassGradeCommon(newInfo, targetInfo);
    }

    public boolean deleteClassGrade(ClassGrade info) {

        List<ClassGrade> classGrade = getClassGrade(info);

        for(ClassGrade c :classGrade ){
            clearClassGradeInfo(c.getId());
        }

        ThrowExceptionUtil.throwRollBackException(
                classGradeDao.deleteClassGrade(info),
                "删除班级信息失败, 请检查!"
        );
        return true;
    }

    public void clearClassGradeInfo(int id){
        ClassGrade classGradeInfo = new ClassGrade();
        classGradeInfo.setId(id);
        // 根据系统id查找 唯一
        classGradeInfo = getClassGrade(classGradeInfo).get(0);

        // 处理学生关联
        // 查询所有学生
        Student studentInfo = new Student();
        studentInfo.setSchoolName(classGradeInfo.getSchoolName());
        studentInfo.setGradeName(classGradeInfo.getGradeName());
        studentInfo.setClassName(classGradeInfo.getClassName());
        // 获取学生列表
        List<Student> studentList = studentService.getStudent(studentInfo);
        ArrayList<Integer> studentIdList = new ArrayList<Integer>();
        for (Student s : studentList) {
            studentIdList.add(s.getId());
        }
        // 清除班级信息
        studentInfo.setGradeName("");
        studentInfo.setClassName("");
        // 开始更新
        if (studentService.updateStudent(studentInfo, studentIdList).getStatus()
                == StatusNumber.FAILED) {
            throw new RollBackException("学生班级信息重置失败",
                    new CommonResponseBody(StatusNumber.FAILED,
                            "学生班级信息重置失败, 请检查后重试"));
        }

        // 处理教室信息关联
        // 查询教室信息
        Classroom classroomInfo = new Classroom();
        classroomInfo.setSchoolName(classGradeInfo.getSchoolName());
        classroomInfo.setGradeName(classGradeInfo.getGradeName());
        classroomInfo.setClassName(classGradeInfo.getClassName());
        List<Classroom> classroomList = classroomService.getClassroom(classroomInfo);
        ArrayList<Integer> classroomIdList = new ArrayList<Integer>();
        for (Classroom info : classroomList) {
            classroomIdList.add(info.getId());
        }
        classroomInfo.setGradeName("");
        classroomInfo.setClassName("");
        if (classroomService.updateClassroom(classroomInfo, classroomIdList).getStatus() != StatusNumber.SUCCESS) {
            throw new RollBackException("教室信息重置失败",
                    new CommonResponseBody(StatusNumber.FAILED,
                            "教室信息重置失败, 请检查后重试"));
        }

        // 授课信息删除
        TeachCourseInfo teachCourseInfo = new TeachCourseInfo();
        teachCourseInfo.setSchoolName(classGradeInfo.getSchoolName());
        teachCourseInfo.setGradeName(classGradeInfo.getGradeName());
        teachCourseInfo.setClassName(classGradeInfo.getClassName());
        List<TeachCourseInfo> teachCourseInfoList =
                teachCourseInfoService.getTeachCourseInfo(teachCourseInfo);
        ArrayList<Integer> teachCourseInfoIdList = new ArrayList<>();
        for (TeachCourseInfo info : teachCourseInfoList) {
            teachCourseInfoIdList.add(info.getId());
        }
        if (!teachCourseInfoService.deleteTeachCourseInfo(teachCourseInfoIdList)) {
            throw new RollBackException("删除授课信息失败",
                    new CommonResponseBody(StatusNumber.FAILED,
                            "删除授课信息失败, 请检查后重试"));
        }

        ClassGradeTeacher classGradeTeacherInfo = new ClassGradeTeacher();
        classGradeTeacherInfo.setSchoolName(classGradeInfo.getSchoolName());
        classGradeTeacherInfo.setClassId(id);

        // 删除班级_教师关联关系
        if (!classGradeTeacherService.deleteClassGradeTeacherUseInfo(classGradeTeacherInfo)) {
            throw new RollBackException("删除班级教师关联关系失败",
                    new CommonResponseBody(StatusNumber.FAILED,
                            "删除班级教师关联关系失败, 请检查后重试"));
        }
    }

    public void updateOtherInfo(int id, ClassGrade classGradeInfo){
        ClassGrade nowInfo = new ClassGrade();
        nowInfo.setId(id);
        // 查询班级的 旧 班号 级号
        ClassGrade classGradeOld = getClassGrade(nowInfo).get(0);

        // 更新学生班级
        Student studentInfo = new Student(classGradeInfo.getGradeName(), classGradeInfo.getClassName(), classGradeInfo.getSchoolName());
        Student targetStudent = new Student(classGradeOld.getGradeName(), classGradeOld.getClassName(), classGradeOld.getSchoolName());
        ThrowExceptionUtil.throwRollBackException(
                studentService.updateStudentCommon(studentInfo, targetStudent),
                "更新班级信息时, 更新学生班级信息失败, 请检查!");

        // 更新授课信息
        ThrowExceptionUtil.throwRollBackException(
                teachCourseInfoService.updateClassGrade(classGradeInfo, classGradeOld),
                "更新班级信息时, 更新授课信息失败, 请检查!");

        // 更新教室信息
        if (classGradeInfo.getClassroomRealId() == null) {

            Classroom newClassroom = new Classroom();
            newClassroom.setSchoolName(classGradeInfo.getSchoolName());
            newClassroom.setGradeName(classGradeInfo.getGradeName());
            newClassroom.setClassName(classGradeInfo.getClassName());
            newClassroom.setRealId(classGradeInfo.getClassroomRealId());
            Classroom newTargetClassroom = new Classroom();
            newTargetClassroom.setSchoolName(classGradeInfo.getSchoolName());
            newTargetClassroom.setRealId(classGradeInfo.getClassroomRealId());
            Classroom nullClassroom = new Classroom();
            newClassroom.setGradeName("");
            newClassroom.setClassName("");
            nullClassroom.setSchoolName(classGradeOld.getSchoolName());
            nullClassroom.setRealId(classGradeOld.getClassroomRealId());
            Classroom nullTargetClassroom = new Classroom();
            nullTargetClassroom.setSchoolName(classGradeOld.getSchoolName());
            nullTargetClassroom.setRealId(classGradeOld.getClassroomRealId());

            // 更新新的
            ThrowExceptionUtil.throwRollBackException(
                    classroomService.updateClassroomCommon(newClassroom, newTargetClassroom),
                    "更新班级信息时, 更新教室信息失败, 请检查!");
            // 置空原来的
            ThrowExceptionUtil.throwRollBackException(
                    classroomService.updateClassroomCommon(nullClassroom, nullTargetClassroom),
                    "更新班级信息时, 更新教室信息失败, 请检查!");
        } else {
            // 将班号级号值为空
            Classroom nullGradeInfo = new Classroom();
            nullGradeInfo.setGradeName("");
            nullGradeInfo.setClassName("");
            nullGradeInfo.setSchoolName(classGradeOld.getSchoolName());
            Classroom oldClassroom = new Classroom();
            oldClassroom.setSchoolName(classGradeOld.getSchoolName());
            oldClassroom.setGradeName(classGradeOld.getGradeName());
            oldClassroom.setClassName(classGradeOld.getClassName());
            ThrowExceptionUtil.throwRollBackException(
                    classroomService.updateClassroomCommon(nullGradeInfo, oldClassroom),
                    "更新班级信息时, 更新教室信息失败, 请检查!");
            // 更新新教室信息
            Classroom classroomInfo = new Classroom();
            classroomInfo.setSchoolName(classGradeInfo.getSchoolName());
            classroomInfo.setRealId(classGradeInfo.getClassroomRealId());
            Classroom classroom = classroomService.getClassroom(classroomInfo).get(0);
            // 置空原教室信息
            classroom.setClassName(classGradeInfo.getClassName());
            classroom.setGradeName(classGradeInfo.getGradeName());
            ArrayList<Integer> classroomIdList = new ArrayList<Integer>();
            classroomIdList.add(classroom.getId());
            // 开始更新
            ThrowExceptionUtil.throwRollBackException(
                    classroomService.updateClassroom(classroom, classroomIdList).getStatus() != StatusNumber.SUCCESS,
                    "更新班级信息时教室信息更新失败, 请检查!");
        }
    }
}
