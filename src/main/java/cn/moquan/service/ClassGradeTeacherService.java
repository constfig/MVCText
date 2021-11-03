package cn.moquan.service;

import cn.moquan.bean.ClassGradeTeacher;
import cn.moquan.dao.ClassGradeTeacherDao;
import cn.moquan.util.ThrowExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ClassGradeTeacher> getClassGradeTeacher(ClassGradeTeacher info) {
        return classGradeTeacherDao.getClassGradeTeacher(info);
    }

    public boolean insertClassGradeTeacher(ClassGradeTeacher info, List<Integer> idList) {
        return classGradeTeacherDao.insertClassGradeTeacher(info, idList);
    }

    public boolean deleteClassGradeTeacherById(List<Integer> idList) {
        return classGradeTeacherDao.deleteClassGradeTeacherById(idList);
    }

    public boolean deleteClassGradeTeachers(ClassGradeTeacher info, List<Integer> idList) {
        return classGradeTeacherDao.deleteClassGradeTeachers(info, idList);
    }
    public boolean deleteClassGradeTeacherUseInfo(ClassGradeTeacher info) {
        return classGradeTeacherDao.deleteClassGradeTeacherUseInfo(info);
    }

    public boolean updateClassGradeTeacher(ClassGradeTeacher newInfo, ClassGradeTeacher oldInfo) {

        ThrowExceptionUtil.throwRollBackException(
                classGradeTeacherDao.updateClassGradeTeacher(newInfo, oldInfo),
                "更新班级教师关联信息失败, 请检查!"
        );

        return true;
    }

    public boolean deleteClassGradeTeacher(ClassGradeTeacher info) {
        return classGradeTeacherDao.deleteClassGradeTeacher(info);
    }
}
