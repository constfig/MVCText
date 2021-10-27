package cn.moquan.bean;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class Teacher {

    private int id;
    private String realId;
    private String name;
    private String teachCourseName;
    private String schoolName;

    public Teacher() {
    }

    public Teacher(int id, String realId, String name, String teachCourseName, String schoolName) {
        this.id = id;
        this.realId = realId;
        this.name = name;
        this.teachCourseName = teachCourseName;
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

    public String getTeachCourseName() {
        return teachCourseName;
    }

    public void setTeachCourseName(String teachCourseName) {
        this.teachCourseName = teachCourseName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", realId=" + realId +
                ", name='" + name + '\'' +
                ", teachCourseName='" + teachCourseName + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
