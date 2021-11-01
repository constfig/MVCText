package cn.moquan.service;

import cn.moquan.bean.ClassGradeTeacher;
import cn.moquan.bean.StudentTeacher;
import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.bean.Teacher;
import cn.moquan.dao.TeacherDao;
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
public class TeacherService {

    @Autowired
    TeacherDao teacherDao;
    @Autowired
    TeachCourseInfoService teachCourseInfoService;
    @Autowired
    StudentTeacherService studentTeacherService;
    @Autowired
    ClassGradeTeacherService classGradeTeacherService;

    public Teacher getTeacherById(int id) {
        return teacherDao.getTeacherById(id);
    }

    public List<Teacher> getTeacher(Teacher teacherInfo) {

        return teacherDao.getTeacher(teacherInfo);
    }

    public boolean insertTeacher(List<Teacher> teacherInfoList) {

        return teacherDao.insertTeacher(teacherInfoList);
    }

    public CommonResponseBody updateTeachers(Teacher teacherInfo, List<Integer> idList) {

        boolean updateTeachCourseInfoFlag = true;

        for (int id : idList) {

            updateOtherInfo(id, teacherInfo);

        }
        ThrowExceptionUtil.throwRollBackException(
                teacherDao.updateTeachers(teacherInfo, idList),
                "更新教师信息失败, 请检查!");
        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public CommonResponseBody deleteTeacherById(List<Integer> idList) {

        for (int id : idList) {
            clearTeacherInfo(id);
        }

        ThrowExceptionUtil.throwRollBackException(
                teacherDao.deleteTeacherById(idList),
                "删除教师信息失败, 请检查!"
        );

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public boolean deleteTeacher(Teacher info) {

        List<Teacher> teacherList = getTeacher(info);

        for(Teacher teacher : teacherList){
            clearTeacherInfo(teacher.getId());
        }

        ThrowExceptionUtil.throwRollBackException(
                teacherDao.deleteTeacher(info),
                "删除教师信息失败, 请检查!"
        );

        return true;
    }

    public boolean updateTeacher(Teacher newInfo, Teacher oldInfo) {

        List<Teacher> teacherList = getTeacher(oldInfo);

        for(Teacher teacher : teacherList){
            updateOtherInfo(teacher.getId(), newInfo);
        }

        ThrowExceptionUtil.throwRollBackException(
                teacherDao.updateTeacher(newInfo, oldInfo),
                "更新教室信息失败, 请检查!"
        );
        return true;
    }

    public void clearTeacherInfo(int id){

        Teacher teacherById = getTeacherById(id);

        // 删除学生教师关系
        ThrowExceptionUtil.throwRollBackException(
                studentTeacherService.deleteStudentTeacherUseInfo(
                        new StudentTeacher(0, id, teacherById.getSchoolName())),
                "删除教师时, 删除学生教师关联失败, 请检查!");
        // 删除班级教师关联关系
        ThrowExceptionUtil.throwRollBackException(
                classGradeTeacherService.deleteClassGradeTeacherUseInfo(
                        new ClassGradeTeacher(0, 0, id, teacherById.getSchoolName())),
                "删除教师信息时, 删除班级教师关联关系失败, 请检查!"
        );

        // 删除授课信息表记录
        ThrowExceptionUtil.throwRollBackException(
                teachCourseInfoService.deleteTeachCourseUseInfo(
                        new TeachCourseInfo(0, id)),
                "删除教师信息时, 删除班级教师关系失败, 请检查!"
        );
    }
    
    public void updateOtherInfo(int id , Teacher teacherInfo){

        String newSchoolName = teacherInfo.getSchoolName();
        String newTeacherName = teacherInfo.getName();
        String newTeachCourseName = teacherInfo.getTeachCourseName();

        // 获取教师原信息
        Teacher oldTeacherInfo = getTeacherById(id);

        TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
        newTeachCourseInfo.setSchoolName(newSchoolName);
        newTeachCourseInfo.setTeacherName(newTeacherName);
        newTeachCourseInfo.setCourseName(newTeachCourseName);
        TeachCourseInfo oldTeachCourseInfo = new TeachCourseInfo();
        oldTeachCourseInfo.setTeacherId(id);

        ThrowExceptionUtil.throwRollBackException(
                teachCourseInfoService.updateClassGrade(newTeachCourseInfo, oldTeachCourseInfo),
                "更新教师信息时, 更新授课信息失败, 请检查!");
    }
}
