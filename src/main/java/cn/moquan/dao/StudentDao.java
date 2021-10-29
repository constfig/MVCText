package cn.moquan.dao;

import cn.moquan.bean.Student;
import cn.moquan.bean.ClassGrade;
import cn.moquan.util.CommonResponseBody;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    List<Student> getStudent(Student studentInfo);

    boolean insertStudent(List<Student> studentInfo);

    boolean deleteStudentById(int id);

    boolean deleteStudents(List<Integer> studentIdList);

    boolean updateStudent(@Param("info") Student info, @Param("idList") List<Integer> idList);

    Student getStudentById(int id);

    boolean updateStudentCommon(@Param("newInfo")Student newInfo, @Param("oldInfo") Student oldInfo);

    boolean deleteStudent(Student info);
}
