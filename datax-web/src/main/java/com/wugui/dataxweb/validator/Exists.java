package com.wugui.dataxweb.validator;

import com.wugui.dataxweb.dto.ExistsForm;

public interface Exists {
    boolean checkExists(String field, String value, String selfId, String... otherParams);
    default boolean checkExists(String field, String value, String selfId, ExistsForm form, String... otherParams){
        return checkExists(field, value, selfId, otherParams);
    }
    default void setGroups(Class<?>[] groups){}
}
