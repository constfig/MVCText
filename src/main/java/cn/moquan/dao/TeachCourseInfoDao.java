package cn.moquan.dao;

import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.bean.ClassGrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
public interface TeachCourseInfoDao {
    List<TeachCourseInfo> getTeachCourseInfo(TeachCourseInfo info);

    boolean insertTeachCourseInfo(List<TeachCourseInfo> infoList);

    boolean updateTeachCourseInfo(@Param("teachInfo") TeachCourseInfo info,@Param("idList") List<Integer> idList);

    boolean deleteTeachCourseInfo(List<Integer> idList);

    boolean updateClassGrade(@Param("newInfo") TeachCourseInfo newInfo,@Param("oldInfo") TeachCourseInfo oldInfo);

    boolean deleteTeachCourseUseInfo(TeachCourseInfo info);

    boolean create();
}
