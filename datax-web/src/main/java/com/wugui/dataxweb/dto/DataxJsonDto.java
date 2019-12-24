package com.wugui.dataxweb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 构建json dto
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName DataxJsonDto
 * @Version 1.0
 * @since 2019/8/1 17:15
 */
@Data
public class DataxJsonDto implements Serializable {

    @ApiModelProperty(value = "读取原数据源id")
    private Long readerDatasourceId;

    @ApiModelProperty(value = "读取原数据源库下表名")
    private List<String> readerTables;

    @ApiModelProperty(value = "读取原数据源库下表下的字段")
    private List<String> readerColumns;

    private Boolean ifStreamWriter = false;

    @ApiModelProperty(value = "往目标数据源写的id")
    private Long writerDatasourceId;

    @ApiModelProperty(value = "往目标数据源写的表名")
    private List<String> writerTables;

    @ApiModelProperty(value = "往目标数据源写的字段名名")
    private List<String> writerColumns;

    @ApiModelProperty(value = "where条件")
    private String whereParams = "";

    @ApiModelProperty(value = "查询的SQL")
    private String querySql ="";

    @ApiModelProperty(value = "执行的SQL")
    private String preSql = "";

    public Long getReaderDatasourceId() {
        return readerDatasourceId;
    }

    public void setReaderDatasourceId(Long readerDatasourceId) {
        this.readerDatasourceId = readerDatasourceId;
    }

    public List<String> getReaderTables() {
        return readerTables;
    }

    public void setReaderTables(List<String> readerTables) {
        this.readerTables = readerTables;
    }

    public List<String> getReaderColumns() {
        return readerColumns;
    }

    public void setReaderColumns(List<String> readerColumns) {
        this.readerColumns = readerColumns;
    }

    public Boolean getIfStreamWriter() {
        return ifStreamWriter;
    }

    public void setIfStreamWriter(Boolean ifStreamWriter) {
        this.ifStreamWriter = ifStreamWriter;
    }

    public Long getWriterDatasourceId() {
        return writerDatasourceId;
    }

    public void setWriterDatasourceId(Long writerDatasourceId) {
        this.writerDatasourceId = writerDatasourceId;
    }

    public List<String> getWriterTables() {
        return writerTables;
    }

    public void setWriterTables(List<String> writerTables) {
        this.writerTables = writerTables;
    }

    public List<String> getWriterColumns() {
        return writerColumns;
    }

    public void setWriterColumns(List<String> writerColumns) {
        this.writerColumns = writerColumns;
    }

    public String getWhereParams() {
        return whereParams;
    }

    public void setWhereParams(String whereParams) {
        this.whereParams = whereParams;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getPreSql() {
        return preSql;
    }

    public void setPreSql(String preSql) {
        this.preSql = preSql;
    }
}
