package com.cluo.mybatis.binding;

/**
 *
 * @param <T>
 */
public class MapperMethod<T> {
    private String sql;
    private Class<T> type;

    public MapperMethod() {
    }

    public MapperMethod(String sql, Class<T> type) {
        this.sql = sql;
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}
