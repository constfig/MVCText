package cn.moquan.dao;

import cn.moquan.bean.ClassGrade;
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
public interface ClassGradeDao {
    List<ClassGrade> getClassGrade(ClassGrade classGradeInfo);

    boolean insertClassGrade(List<ClassGrade> classGradeInfoList);

    boolean deleteClassGrade(List<Integer> idList);

    boolean updateClassGrade(@Param("classGradeInfo") ClassGrade classGradeInfo, @Param("idList") List<Integer> idList);
}
