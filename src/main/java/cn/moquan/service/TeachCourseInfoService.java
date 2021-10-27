package cn.moquan.service;

import cn.moquan.bean.TeachCourseInfo;
import cn.moquan.bean.ClassGrade;
import cn.moquan.dao.TeachCourseInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
@Service
public class TeachCourseInfoService {

    @Autowired
    TeachCourseInfoDao teachCourseInfoDao;

    public List<TeachCourseInfo> getTeachCourseInfo(TeachCourseInfo info) {
        return teachCourseInfoDao.getTeachCourseInfo(info);
    }

    public boolean insertTeachCourseInfo(List<TeachCourseInfo> infoList) {
        return teachCourseInfoDao.insertTeachCourseInfo(infoList);
    }

    public boolean updateTeachCourseInfo(TeachCourseInfo info, List<Integer> idList) {
        return teachCourseInfoDao.updateTeachCourseInfo(info, idList);
    }

    public boolean deleteTeachCourseInfo(List<Integer> idList) {
        return teachCourseInfoDao.deleteTeachCourseInfo(idList);
    }

    public boolean updateClassGrade(ClassGrade newInfo, ClassGrade oldInfo) {
        return teachCourseInfoDao.updateClassGrade(newInfo, oldInfo);
    }
    public boolean updateClassGrade(TeachCourseInfo newInfo, TeachCourseInfo oldInfo) {
        return teachCourseInfoDao.updateClassGrade(newInfo, oldInfo);
    }

    public boolean deleteTeachCourseUseInfo(TeachCourseInfo info) {
        return teachCourseInfoDao.deleteTeachCourseUseInfo(info);
    }
}
