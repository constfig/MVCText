package cn.moquan.service;

import cn.moquan.bean.Classroom;
import cn.moquan.bean.ClassGrade;
import cn.moquan.bean.Student;
import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.dao.ClassroomDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.StatusNumber;
import cn.moquan.util.ThrowExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Service
public class ClassroomService {

    @Autowired
    ClassroomDao classroomDao;
    @Autowired
    ClassGradeService classGradeService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeachCourseInfoService teachCourseInfoService;


    public List<Classroom> getClassroom(Classroom info) {
        return classroomDao.getClassroom(info);
    }


    public CommonResponseBody updateClassroom(Classroom info, List<Integer> idList) {

        for (int id : idList) {
            // 级联更新其他信息
            updateOtherInfo(id, info);

        }// for end

        ThrowExceptionUtil.throwRollBackException(
                classroomDao.updateClassroom(info, idList),
                "更新教室信息失败, 请检查!"
        );

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public CommonResponseBody deleteClassroomById(List<Integer> idList) {

        for (int id : idList) {

            // 清除其它表中的教室信息
            clearClassroomInfo(id);

        }

        ThrowExceptionUtil.throwRollBackException(
                classroomDao.deleteClassroomById(idList),
                "删除教室信息失败, 请检查!"
        );

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public boolean insertClassroom(List<Classroom> infoList) {
        return classroomDao.insertClassroom(infoList);
    }

    public boolean deleteClassroom(Classroom info) {

        List<Classroom> classroomList = getClassroom(info);

        for (Classroom c : classroomList) {
            clearClassroomInfo(info.getId());
        }

        ThrowExceptionUtil.throwRollBackException(
                classroomDao.deleteClassroom(info),
                "删除教室信息失败, 请检查!"
        );

        return true;
    }

    public boolean updateClassroomCommon(Classroom newInfo, Classroom oldInfo) {

        if (newInfo.getId() != -1) {
            List<Classroom> classroomList = getClassroom(oldInfo);
            for (Classroom c : classroomList) {
                updateOtherInfo(c.getId(), newInfo);
            }
        } else {
            newInfo.setId(0);
        }

        return classroomDao.updateClassroomCommon(newInfo, oldInfo);
    }


    public void updateOtherInfo(int targetId, Classroom newClassroomInfo) {
        // 获取教室信息
        Classroom oldInfo = getClassroom(new Classroom(targetId)).get(0);
        String newRealId = newClassroomInfo.getRealId();
        String newSchoolName = newClassroomInfo.getSchoolName();

        // 只更新教室号
        // 更新学生信息中的教室号
        Student newStudentInfo = new Student();
        newStudentInfo.setClassroomRealId(newRealId);
        newStudentInfo.setSchoolName(newSchoolName);
        Student targetStudentInfo = new Student();
        targetStudentInfo.setSchoolName(oldInfo.getSchoolName());
        targetStudentInfo.setClassroomRealId(oldInfo.getRealId());
        // 更新
        cascadingUpdateStudent(newStudentInfo, targetStudentInfo);

        // 更新班级信息中的教室号
        ClassGrade newClassGrade = new ClassGrade();
        newClassGrade.setClassroomRealId(newRealId);
        newClassGrade.setSchoolName(newSchoolName);
        ClassGrade targetClassGrade = new ClassGrade();
        targetClassGrade.setClassroomRealId(oldInfo.getRealId());
        targetClassGrade.setSchoolName(oldInfo.getSchoolName());
        // update
        cascadingUpdateClassGrade(newClassGrade, targetClassGrade);

        // 更新授课信息中的教室号
        TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
        newTeachCourseInfo.setSchoolName(newSchoolName);
        newTeachCourseInfo.setClassroomRealId(newRealId);
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(oldInfo.getSchoolName());
        targetTeachCourseInfo.setClassroomRealId(oldInfo.getRealId());
        // update
        cascadingUpdateTeachCourseInfo(newTeachCourseInfo, targetTeachCourseInfo);

    }

    public void clearClassroomInfo(int id) {
        // 获取教室信息
        Classroom oldInfo = getClassroom(new Classroom(id)).get(0);

        if ("".equals(oldInfo.getGradeName()) || "".equals(oldInfo.getClassName())) {
            return;
        }

        // 更新学生信息
        Student newStudentInfo = new Student("");
        Student targetStudent = new Student(oldInfo.getGradeName(),
                oldInfo.getClassName(), oldInfo.getSchoolName());

        cascadingUpdateStudent(newStudentInfo, targetStudent);

        // 更新班级信息
        ClassGrade newClassGradeInfo = new ClassGrade();
        newClassGradeInfo.setClassroomRealId("");
        ClassGrade targetClassGrade = new ClassGrade(oldInfo.getGradeName(),
                oldInfo.getClassName(), oldInfo.getSchoolName());
        cascadingUpdateClassGrade(newClassGradeInfo, targetClassGrade);

        // 删除授课信息
        TeachCourseInfo teachCourseInfo = new TeachCourseInfo();
        teachCourseInfo.setClassroomRealId(oldInfo.getRealId());
        teachCourseInfo.setSchoolName(oldInfo.getSchoolName());
        cascadingDeleteClassGradeTeacher(teachCourseInfo);
    }


    public void cascadingUpdateStudent(Student newStudentInfo, Student targetStudent){
        if(studentService.getStudent(targetStudent).size() > 0){
            ThrowExceptionUtil.throwRollBackException(
                    studentService.updateStudentCommon(newStudentInfo, targetStudent),
                    "更新教室信息时, 更新学生信息失败, 请检查!"
            );
        }
    }

    public void cascadingUpdateTeachCourseInfo(TeachCourseInfo newTeachCourseInfo, TeachCourseInfo targetTeachCourseInfo){
        if(teachCourseInfoService.getTeachCourseInfo(targetTeachCourseInfo).size() > 0){
            ThrowExceptionUtil.throwRollBackException(
                    teachCourseInfoService.updateClassGrade(newTeachCourseInfo, targetTeachCourseInfo),
                    "更新教室信息(教室号)时, 授课信息更新失败(教室号), 请检查!"
            );
        }
    }

    public void cascadingUpdateClassGrade(ClassGrade newClassGradeInfo,ClassGrade targetClassGrade){
        if(classGradeService.getClassGrade(targetClassGrade).size() > 0){
            ThrowExceptionUtil.throwRollBackException(
                    classGradeService.updateClassGradeCommon(newClassGradeInfo, targetClassGrade),
                    "更新教室信息时, 更新班级信息失败, 请检查!"
            );
        }
    }

    public void cascadingDeleteClassGradeTeacher(TeachCourseInfo teachCourseInfo){
        if(teachCourseInfoService.getTeachCourseInfo(teachCourseInfo).size() > 0){
            ThrowExceptionUtil.throwRollBackException(
                    teachCourseInfoService.deleteTeachCourseUseInfo(teachCourseInfo),
                    "更新教室信息时, 更新班级信息失败, 请检查!"
            );
        }
    }

    /**
     * 进行更新时不会进行级联更新
     * @param newInfo 需要更新的参数
     * @param oldInfo 寻找唯一目标的参数
     * @return 是否更新成功
     */
    public boolean updateClassroomNotCascading(Classroom newInfo, Classroom oldInfo) {

        return classroomDao.updateClassroomCommon(newInfo, oldInfo);
    }
}
