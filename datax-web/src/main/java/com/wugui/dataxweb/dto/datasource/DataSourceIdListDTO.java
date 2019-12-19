package com.wugui.dataxweb.dto.datasource;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataSourceIdListDTO {
    private List<Long> idList = new ArrayList<>();
}
