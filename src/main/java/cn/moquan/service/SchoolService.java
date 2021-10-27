package cn.moquan.service;

import cn.moquan.bean.school.School;
import cn.moquan.dao.SchoolDao;
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
public class SchoolService {

    @Autowired
    SchoolDao schoolDao;

    public List<School> getSchool(School info) {
        return schoolDao.getSchool(info);
    }

    public boolean insertSchool(List<School> infoList) {
        return schoolDao.insertSchool(infoList);
    }

    public boolean updateSchool(School info, List<Integer> idList) {
        return schoolDao.updateSchool(info, idList);
    }

    public boolean deleteSchoolById(List<Integer> idList) {
        return schoolDao.deleteSchoolById(idList);
    }
}
