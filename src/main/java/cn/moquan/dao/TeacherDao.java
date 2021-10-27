package cn.moquan.dao;

import cn.moquan.bean.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Repository
public interface TeacherDao {
    Teacher getTeacherById(int id);

    List<Teacher> getTeacher(Teacher teacherInfo);

    boolean insertTeacher(List<Teacher> teacherInfoList);

    boolean updateTeacher(@Param("teacherInfo") Teacher teacherInfo, @Param("idList") List<Integer> idList);

    boolean deleteTeacherById(List<Integer> idList);
}
