package cn.moquan.bean;

/**
 * @author wangyuanhong
 * @date 2021/10/19
 */
public class School {

    private int id;
    private String name;

    public School() {
    }

    public School(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
