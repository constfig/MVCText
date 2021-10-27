package cn.moquan.bean;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/21
 */
public class ClassGradeTeacher {

    private int id;
    private int classId;
    private int teacherId;
    private String schoolName;

    public ClassGradeTeacher() {
    }

    public ClassGradeTeacher(int id, int classId, int teacherId, String schoolName) {
        this.id = id;
        this.classId = classId;
        this.teacherId = teacherId;
        this.schoolName = schoolName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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
        return "ClassGradeTeacher{" +
                "id=" + id +
                ", classId=" + classId +
                ", teacherId=" + teacherId +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
