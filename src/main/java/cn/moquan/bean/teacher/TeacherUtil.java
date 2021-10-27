package cn.moquan.bean.teacher;

import java.util.List;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class TeacherUtil {

    private Teacher teacherInfo;
    private List<Integer> idList;
    private List<Teacher> teacherInfoList;

    public Teacher getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(Teacher teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public List<Teacher> getTeacherInfoList() {
        return teacherInfoList;
    }

    public void setTeacherInfoList(List<Teacher> teacherInfoList) {
        this.teacherInfoList = teacherInfoList;
    }

    @Override
    public String toString() {
        return "TeacherUtil{" +
                "teacherInfo=" + teacherInfo +
                ", idList=" + idList +
                ", teacherInfoList=" + teacherInfoList +
                '}';
    }
}
