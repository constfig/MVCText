package cn.moquan.dao;

import cn.moquan.bean.ClassGradeTeacher;
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
public interface ClassGradeTeacherDao {


    List<ClassGradeTeacher> getClassGradeTeacher(ClassGradeTeacher info);

    boolean insertClassGradeTeacher(@Param("info") ClassGradeTeacher info, @Param("idList") List<Integer> idList);

    boolean deleteClassGradeTeacherById(List<Integer> idList);

    boolean deleteClassGradeTeacher(@Param("info") ClassGradeTeacher info, @Param("idList") List<Integer> idList);

    boolean deleteClassGradeTeacherUseInfo(ClassGradeTeacher info);
}
