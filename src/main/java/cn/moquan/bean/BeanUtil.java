package cn.moquan.bean;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/20
 */
public class BeanUtil<E> {

    private E info;
    private List<E> infoList;
    private List<Integer> idList;

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public List<E> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<E> infoList) {
        this.infoList = infoList;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    @Override
    public String toString() {
        return "BeanUtil{" +
                "info=" + info +
                ", infoList=" + infoList +
                ", idList=" + idList +
                '}';
    }
}
