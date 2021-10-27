package cn.moquan.service;

import cn.moquan.bean.ClassGradeTeacher;
import cn.moquan.dao.ClassGradeTeacherDao;
import jdk.nashorn.internal.ir.IfNode;
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

    public boolean deleteClassGradeTeacher(ClassGradeTeacher info, List<Integer> idList) {
        return classGradeTeacherDao.deleteClassGradeTeacher(info, idList);
    }
    public boolean deleteClassGradeTeacherUseInfo(ClassGradeTeacher info) {
        return classGradeTeacherDao.deleteClassGradeTeacherUseInfo(info);
    }
}
