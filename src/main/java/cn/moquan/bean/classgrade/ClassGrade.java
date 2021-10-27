package cn.moquan.bean.classgrade;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class ClassGrade {

    private int id;
    private String gradeName;
    private String className;
    private String classroomRealId;
    private String schoolName;

    public ClassGrade() {
    }

    public ClassGrade(int id, String gradeName, String className, String classroomRealId, String schoolName) {
        this.id = id;
        this.gradeName = gradeName;
        this.className = className;
        this.classroomRealId = classroomRealId;
        this.schoolName = schoolName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassroomRealId() {
        return classroomRealId;
    }

    public void setClassroomRealId(String classroomRealId) {
        this.classroomRealId = classroomRealId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "ClassGrade{" +
                "id=" + id +
                ", gradeName='" + gradeName + '\'' +
                ", className='" + className + '\'' +
                ", classroomRealId='" + classroomRealId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
