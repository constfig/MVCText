package cn.moquan.service;

import cn.moquan.bean.Student;
import cn.moquan.bean.StudentTeacher;
import cn.moquan.dao.StudentTeacherDao;
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
public class StudentTeacherService {

    @Autowired
    StudentTeacherDao studentTeacherDao;

    public List<StudentTeacher> getStudentTeacher(StudentTeacher info) {
        return studentTeacherDao.getStudentTeacher(info);
    }

    public boolean insertOnceStudentTeacher(StudentTeacher info) {
        return studentTeacherDao.insertOnceStudentTeacher(info);
    }

    public boolean insertStudentTeacher(StudentTeacher info, List<Integer> idList) {
        return studentTeacherDao.insertStudentTeacher(info, idList);
    }

    public boolean deleteStudentTeacherById(List<Integer> idList) {
        return studentTeacherDao.deleteStudentTeacherById(idList);
    }

    public boolean deleteStudentTeacher(StudentTeacher info, List<Integer> idList) {
        return studentTeacherDao.deleteStudentTeacher(info, idList);
    }

    public boolean deleteStudentTeacherUseInfo(StudentTeacher info) {
        return studentTeacherDao.deleteStudentTeacherUseInfo(info);
    }
}
