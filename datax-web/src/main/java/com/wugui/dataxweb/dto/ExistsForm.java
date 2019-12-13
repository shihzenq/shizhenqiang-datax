package com.wugui.dataxweb.dto;

public interface ExistsForm {

    /**
     * 获取当前实体类的主键值
     *
     * @return 返回主键值
     */
    String getPrimaryKey();

    /**
     * 获取指定字段的值
     *
     * @param field 字段名称
     * @return 返回字段值
     */
    String getUniqueFieldValue(String field);

    String[] getOtherParams();

    default void setData(Object object){}
}
