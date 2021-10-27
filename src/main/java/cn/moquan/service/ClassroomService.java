package cn.moquan.service;

import cn.moquan.bean.Classroom;
import cn.moquan.bean.ClassGrade;
import cn.moquan.dao.ClassroomDao;
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
public class ClassroomService {

    @Autowired
    ClassroomDao classroomDao;

    public List<Classroom> getClassroom(Classroom info) {
        return classroomDao.getClassroom(info);
    }


    public boolean updateClassroom(Classroom info, List<Integer> idList) {
        return classroomDao.updateClassroom(info, idList);
    }

    public boolean deleteClassroomById(List<Integer> idList) {
        return classroomDao.deleteClassroomById(idList);
    }

    public boolean insertClassroom(List<Classroom> infoList) {
        return classroomDao.insertClassroom(infoList);
    }

    public boolean updateClassGrade(ClassGrade newInfo, ClassGrade oldInfo) {
        return classroomDao.updateClassGrade(newInfo, oldInfo);
    }
}
