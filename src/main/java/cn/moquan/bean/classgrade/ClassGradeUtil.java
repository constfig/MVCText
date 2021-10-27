package cn.moquan.bean.classgrade;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
public class ClassGradeUtil {

    private ClassGrade classGradeInfo;
    private List<ClassGrade> classGradeInfoList;
    private List<Integer> idList;

    public ClassGrade getClassGradeInfo() {
        return classGradeInfo;
    }

    public void setClassGradeInfo(ClassGrade classGradeInfo) {
        this.classGradeInfo = classGradeInfo;
    }

    public List<ClassGrade> getClassGradeInfoList() {
        return classGradeInfoList;
    }

    public void setClassGradeInfoList(List<ClassGrade> classGradeInfoList) {
        this.classGradeInfoList = classGradeInfoList;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    @Override
    public String toString() {
        return "ClassGradeUtil{" +
                "classGradeInfo=" + classGradeInfo +
                ", classGradeInfoList=" + classGradeInfoList +
                ", idList=" + idList +
                '}';
    }
}
