package cn.moquan.bean;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class Classroom {

    private int id;
    private String realId;
    private String gradeName;
    private String className;
    private String schoolName;

    public Classroom() {
    }

    public Classroom(int id) {
        this.id = id;
    }

    public Classroom(int id, String realId, String gradeName, String className, String schoolName) {
        this.id = id;
        this.realId = realId;
        this.gradeName = gradeName;
        this.className = className;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", realId='" + realId + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", className='" + className + '\'' +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
