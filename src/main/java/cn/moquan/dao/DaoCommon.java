package cn.moquan.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * describe 普通Dao类接口
 *
 * @author wangyuanhong
 * @date 2021/10/30
 */
public interface DaoCommon<E> {

    // GET
    E getInfoById(int id);

    List<E> getInfoList(E info);

    List<E> getInfoListById(List<Integer> idList);

    // INSERT
    boolean insertInfo(E info);

    boolean insertInfos(List<E> infoList);

    // DELETE
    boolean deleteInfo(E info);

    boolean deleteInfoById(int id);

    boolean deleteInfosById(List<Integer> idList);

    // UPDATE
    boolean updateInfo(@Param("newInfo") E newInfo, @Param("targetInfo") E targetInfo);

    boolean updateInfos(@Param("newInfo") E newInfo, @Param("idList") List<E> idList);


}
