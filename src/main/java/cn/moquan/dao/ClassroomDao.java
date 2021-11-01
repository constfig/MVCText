package cn.moquan.dao;

import cn.moquan.bean.Classroom;
import cn.moquan.bean.ClassGrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
public interface ClassroomDao {
    List<Classroom> getClassroom(Classroom info);

    boolean updateClassroom(@Param("classroomInfo") Classroom info,@Param("idList") List<Integer> idList);

    boolean deleteClassroomById(List<Integer> idList);

    boolean insertClassroom(List<Classroom> infoList);

    boolean updateClassGrade(@Param("newInfo")ClassGrade newInfo, @Param("oldInfo") ClassGrade oldInfo);

    boolean deleteClassroom(Classroom info);

    boolean updateClassroomCommon(@Param("newInfo") Classroom newInfo, @Param("oldInfo") Classroom oldInfo);
}