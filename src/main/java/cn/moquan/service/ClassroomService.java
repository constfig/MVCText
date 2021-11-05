package cn.moquan.service;

import cn.moquan.bean.Classroom;
import cn.moquan.bean.ClassGrade;
import cn.moquan.bean.Student;
import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.dao.ClassroomDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
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

        if (info.getRealId() != null && !"".equals(info.getRealId())) {
            for (int id : idList) {
                // 级联更新其他信息
                updateOtherInfo(id, info);

            }// for end
        }

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

        if (classroomList.size() > 0) {
            ThrowExceptionUtil.throwRollBackException(
                    classroomDao.deleteClassroom(info),
                    "删除教室信息失败, 请检查!"
            );
        }

        return true;
    }

    public boolean updateClassroomCommon(Classroom newInfo, Classroom oldInfo) {

        // 当更新教室号时进行级联更新
        if (newInfo.getRealId() != null && !"".equals(newInfo.getRealId()) &&
                !newInfo.getRealId().equals(oldInfo.getRealId())) {
            List<Classroom> classroomList = getClassroom(oldInfo);
            for (Classroom c : classroomList) {
                updateOtherInfo(c.getId(), newInfo);
            }
        }

        return classroomDao.updateClassroomCommon(newInfo, oldInfo);
    }

    /**
     * 级联更新
     * 只能更新教室号
     *
     * @param targetId
     * @param newClassroomInfo
     */

    public void updateOtherInfo(int targetId, Classroom newClassroomInfo) {

        Classroom targetClassroom = new Classroom();
        targetClassroom.setId(targetId);
        List<Classroom> classroomList = getClassroom(targetClassroom);
        if (classroomList.size() == 0) {
            throw new RollBackException("没有找需更新教室, 请检查!",
                    new CommonResponseBody(StatusNumber.FAILED, "没有找需更新教室, 请检查!"));
        }
        targetClassroom = classroomList.get(0);

        String targetRealId = targetClassroom.getRealId();
        String targetSchoolName = targetClassroom.getSchoolName();

        String newRealId = newClassroomInfo.getRealId();

        // 更新学生的教室号
        Student newStudentInfo = new Student();
        newStudentInfo.setClassroomRealId(newRealId);
        Student targetStudent = new Student();
        targetStudent.setSchoolName(targetSchoolName);
        targetStudent.setClassroomRealId(targetRealId);
        // 更新
        studentService.updateStudentCommonNotCascade(newStudentInfo, targetStudent);

        // 更新授课信息的教室号
        TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
        newTeachCourseInfo.setClassroomRealId(newRealId);
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(targetSchoolName);
        targetTeachCourseInfo.setClassroomRealId(targetRealId);
        // 更新
        teachCourseInfoService.updateClassGrade(newTeachCourseInfo, targetTeachCourseInfo);

        // 更新班级中的教室号
        ClassGrade newClassGradeInfo = new ClassGrade();
        newClassGradeInfo.setClassroomRealId(newRealId);
        ClassGrade targetClassGradeInfo = new ClassGrade();
        targetClassGradeInfo.setClassroomRealId(targetRealId);
        targetClassGradeInfo.setSchoolName(targetSchoolName);
        // 更新
        classGradeService.updateClassGradeNotCascading(newClassGradeInfo, targetClassGradeInfo);
    }

    public void clearClassroomInfo(int id) {
        Classroom targetClassroom = new Classroom();
        targetClassroom.setId(id);
        List<Classroom> classroomList = getClassroom(targetClassroom);
        if (classroomList.size() == 0) {
            throw new RollBackException("没有找需删除的教室, 请检查!",
                    new CommonResponseBody(StatusNumber.FAILED, "没有找需删除的教室, 请检查!"));
        }
        targetClassroom = classroomList.get(0);

        String targetRealId = targetClassroom.getRealId();
        String targetSchoolName = targetClassroom.getSchoolName();

        // 更新学生的教室号
        Student newStudentInfo = new Student();
        newStudentInfo.setClassroomRealId("");
        Student targetStudent = new Student();
        targetStudent.setSchoolName(targetSchoolName);
        targetStudent.setClassroomRealId(targetRealId);
        // 更新
        studentService.updateStudentCommonNotCascade(newStudentInfo, targetStudent);

/*        // 更新授课信息的教室号
        TeachCourseInfo newTeachCourseInfo = new TeachCourseInfo();
        newTeachCourseInfo.setClassroomRealId("");
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(targetSchoolName);
        targetTeachCourseInfo.setClassroomRealId(targetRealId);
        // 更新
        teachCourseInfoService.updateClassGrade(newTeachCourseInfo, targetTeachCourseInfo);*/

        // 删除授课信息
        TeachCourseInfo targetTeachCourseInfo = new TeachCourseInfo();
        targetTeachCourseInfo.setSchoolName(targetSchoolName);
        targetTeachCourseInfo.setClassroomRealId(targetRealId);
        // 更新
        teachCourseInfoService.deleteTeachCourseUseInfo(targetTeachCourseInfo);

        // 更新班级中的教室号
        ClassGrade newClassGradeInfo = new ClassGrade();
        newClassGradeInfo.setClassroomRealId("");
        ClassGrade targetClassGradeInfo = new ClassGrade();
        targetClassGradeInfo.setClassroomRealId(targetRealId);
        targetClassGradeInfo.setSchoolName(targetSchoolName);
        // 更新
        classGradeService.updateClassGradeNotCascading(newClassGradeInfo, targetClassGradeInfo);
    }

    /**
     * 进行更新时不会进行级联更新
     *
     * @param newInfo 需要更新的参数
     * @param oldInfo 寻找唯一目标的参数
     * @return 是否更新成功
     */
    public boolean updateClassroomNotCascading(Classroom newInfo, Classroom oldInfo) {
        return classroomDao.updateClassroomCommon(newInfo, oldInfo);
    }
}
