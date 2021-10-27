package cn.moquan.dao;

import cn.moquan.bean.Student;
import cn.moquan.bean.classgrade.ClassGrade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    List<Student> getStudent(Student studentInfo);

    boolean insertStudent(List<Student> studentInfo);

    boolean deleteStudent(int id);

    boolean deleteStudents(List<Integer> studentIdList);

    boolean updateStudent(@Param("info") Student info, @Param("idList") List<Integer> idList);

    Student getStudentById(int id);

    boolean updateClassGrade(@Param("newInfo")ClassGrade newInfo, @Param("oldInfo") ClassGrade oldInfo);
}
