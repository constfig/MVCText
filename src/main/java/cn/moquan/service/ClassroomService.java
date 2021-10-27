package cn.moquan.service;

import cn.moquan.bean.Classroom;
import cn.moquan.bean.ClassGrade;
import cn.moquan.bean.Student;
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


    public List<Classroom> getClassroom(Classroom info) {
        return classroomDao.getClassroom(info);
    }


    public boolean updateClassroom(Classroom info, List<Integer> idList) {

        for (int id : idList) {
            // 获取教室信息
            Classroom oldInfo = getClassroom(new Classroom(id)).get(0);
            String newClassName = info.getClassName();
            String newGradeName = info.getClassName();

            // 更新班号级号就要进行级联更新
            if (newClassName != null || newGradeName != null) {

//                classGradeService.updateClassGrade();

            }


        }

        return classroomDao.updateClassroom(info, idList);
    }

    public CommonResponseBody deleteClassroomById(List<Integer> idList) {

        for (int id : idList) {

            // 获取教室信息
            Classroom oldInfo = getClassroom(new Classroom(id)).get(0);

            // 更新学生信息
            Student newStudentInfo = new Student("");
            Student targetStudent = new Student(oldInfo.getGradeName(),
                    oldInfo.getClassName(), oldInfo.getSchoolName());
            ThrowExceptionUtil.throwRollBackException(
                    studentService.updateStudentCommon(newStudentInfo, targetStudent),
                    "删除教室信息时, 更新学生信息失败, 请检查!"
            );

            // 更新班级信息
            ClassGrade newClassGradeInfo = new ClassGrade();
            newClassGradeInfo.setClassroomRealId("");
            ClassGrade targetClassGrade = new ClassGrade(oldInfo.getGradeName(),
                    oldInfo.getClassName(), oldInfo.getSchoolName());
            ThrowExceptionUtil.throwRollBackException(
                    classGradeService.updateClassGradeCommon(newClassGradeInfo, targetClassGrade),
                    "删除教室信息时, 更新班级信息失败, 请检查!"
            );

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

    public boolean updateClassGrade(ClassGrade newInfo, ClassGrade oldInfo) {
        return classroomDao.updateClassGrade(newInfo, oldInfo);
    }
}
