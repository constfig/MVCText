package cn.moquan.dao;

import cn.moquan.bean.school.School;
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
public interface SchoolDao {
    List<School> getSchool(School info);

    boolean insertSchool(List<School> infoList);

    boolean updateSchool(@Param("schoolInfo") School info, @Param("idList") List<Integer> idList);

    boolean deleteSchoolById(List<Integer> idList);
}
