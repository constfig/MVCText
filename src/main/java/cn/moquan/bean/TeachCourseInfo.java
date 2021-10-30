package cn.moquan.bean;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class TeachCourseInfo {

    // 无法标识一个唯一的教师

    /*
    * 当出现 同一个学校中同名教师教授同样课程时
    * 获取唯一的教师和授课信息关联时会非常困难
    * */

    private int id;
    private int teacherId;
    private String teacherName;
    private String classroomRealId;
    private String gradeName;
    private String className;
    private String courseName;
    private String schoolName;

    public TeachCourseInfo() {
    }

    public TeachCourseInfo(String schoolName, String teacherName, String courseName) {
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.schoolName = schoolName;
    }

    public TeachCourseInfo(int id, String teacherName, String classroomRealId, String gradeName, String className, String courseName, String schoolName) {
        this.id = id;
        this.teacherName = teacherName;
        this.classroomRealId = classroomRealId;
        this.gradeName = gradeName;
        this.className = className;
        this.courseName = courseName;
        this.schoolName = schoolName;
    }

    public TeachCourseInfo(int id, int teacherId, String teacherName, String classroomRealId, String gradeName, String className, String courseName, String schoolName) {
        this.id = id;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.classroomRealId = classroomRealId;
        this.gradeName = gradeName;
        this.className = className;
        this.courseName = courseName;
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "TeachCourseInfo{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", classroomRealId='" + classroomRealId + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", className='" + className + '\'' +
                ", courseName='" + courseName + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassroomRealId() {
        return classroomRealId;
    }

    public void setClassroomRealId(String classroomRealId) {
        this.classroomRealId = classroomRealId;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

}
