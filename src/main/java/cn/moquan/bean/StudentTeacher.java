package cn.moquan.bean;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/21
 */
public class StudentTeacher {

    private int id;
    private int studentId;
    private int teacherId;
    private String schoolName;

    public StudentTeacher() {
    }

    public StudentTeacher(int studentId, int teacherId, String schoolName) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.schoolName = schoolName;
    }

    public StudentTeacher(int id, int studentId, int teacherId, String schoolName) {
        this.id = id;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.schoolName = schoolName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "StudentTeacher{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", teacherId=" + teacherId +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
