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

    public CommonResponseBody updateTeacher(Teacher teacherInfo, List<Integer> idList) {

        boolean updateTeachCourseInfoFlag = true;

        for (int id : idList) {

            String newSchoolName = teacherInfo.getSchoolName();
            String newTeacherName = teacherInfo.getName();
            String newTeachCourseName = teacherInfo.getTeachCourseName();

            // 获取教师原信息
            Teacher oldTeacherInfo = getTeacherById(id);

            // 两个都不为null 都更新
            if (newTeacherName != null && newTeachCourseName != null) {

                /*
                 * 1. 根据教师信息获取班级信息
                 * 2. 通过班级教师关系获取唯一授课信息
                 * 3. 进行更新操作
                 * */

                /*
                 * 1. 更新数据库, 建立唯一联系
                 * */

                // 根据教师信息更新授课信息
                updateTeachCourseInfoFlag = teachCourseInfoService.updateClassGrade(
                        new TeachCourseInfo(newSchoolName, newTeacherName, newTeachCourseName),
                        new TeachCourseInfo(oldTeacherInfo.getSchoolName(),
                                oldTeacherInfo.getName(), oldTeacherInfo.getTeachCourseName()));

            } else {
                // 谁不为空更新谁
                if (newTeacherName != null) {
                    updateTeachCourseInfoFlag = teachCourseInfoService.updateClassGrade(
                            new TeachCourseInfo(newSchoolName, newTeacherName, null),
                            new TeachCourseInfo(oldTeacherInfo.getSchoolName(),
                                    oldTeacherInfo.getName(), oldTeacherInfo.getTeachCourseName()));
                }

                if (newTeachCourseName != null) {
                    updateTeachCourseInfoFlag = teachCourseInfoService.updateClassGrade(
                            new TeachCourseInfo(newSchoolName, null, newTeachCourseName),
                            new TeachCourseInfo(oldTeacherInfo.getSchoolName(),
                                    oldTeacherInfo.getName(), oldTeacherInfo.getTeachCourseName()));
                }
            }

            ThrowExceptionUtil.throwRollBackException(updateTeachCourseInfoFlag,
                    "更新教师信息时, 更新授课信息失败, 请检查!");

        }
        ThrowExceptionUtil.throwRollBackException(
                teacherDao.updateTeacher(teacherInfo, idList),
                "更新教师信息失败, 请检查!");
        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public CommonResponseBody deleteTeacherById(List<Integer> idList) {

        for (int id : idList) {

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
                        new TeachCourseInfo(teacherById.getSchoolName(),
                                teacherById.getName(), teacherById.getTeachCourseName())),
                    "删除教师信息时, 删除班级教师关系失败, 请检查!"
            );
        }

        ThrowExceptionUtil.throwRollBackException(
                teacherDao.deleteTeacherById(idList),
                "删除教师信息失败, 请检查!"
        );

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

}
