package cn.moquan.bean;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class Student {

    /*
        "realId" : "",
        "name" : "",
        "gradeName" : "",
        "className" : "",
        "classroomRealId" : "",
        "schoolName" : ""
     */

    private int id;
    private String realId;
    private String name;
    private String gradeName;
    private String className;
    private String classroomRealId;
    private String schoolName;

    public Student() {
    }

    public Student(int id, String realId, String name, String gradeName, String className, String classroomRealId, String schoolName) {
        this.id = id;
        this.realId = realId;
        this.name = name;
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

    public String getRealId() {
        return realId;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Student{" +
                "id=" + id +
                ", realId=" + realId +
                ", name='" + name + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", className='" + className + '\'' +
                ", classroomRealId='" + classroomRealId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
