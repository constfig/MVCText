package cn.moquan.dao;

import cn.moquan.bean.StudentTeacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/21
 */
@Repository
public interface StudentTeacherDao {

    List<StudentTeacher> getStudentTeacher(StudentTeacher info);

    boolean insertOnceStudentTeacher(StudentTeacher info);

    boolean insertStudentTeacher(@Param("info") StudentTeacher info,@Param("idList") List<Integer> idList);

    boolean deleteStudentTeacherById(List<Integer> idList);

    boolean deleteStudentTeacher(@Param("info") StudentTeacher info,@Param("idList") List<Integer> idList);

    boolean deleteStudentTeacherUseInfo(StudentTeacher info);
}
