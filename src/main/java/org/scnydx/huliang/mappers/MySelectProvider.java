package org.scnydx.huliang.mappers;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.lang.reflect.Field;

/**
 * @Author: CSG
 * @Description: 自定义扩展的select
 * @Date: Create in 9:47 2018/2/12
 * @Modify by:
 */
public class MySelectProvider extends MapperTemplate {
    public MySelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectAllPage(MappedStatement ms) {
        Class<?> entityClass =this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

    public String selectByFilter(MappedStatement ms) {
        Class<?> entityClass =this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(getSelectColumns(entityClass));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }

    public String selectByFilterPage(MappedStatement ms) {
        return selectByFilter(ms);
    }

    private String getSelectColumns(Class<?> entityClass){
        StringBuilder sql = new StringBuilder();
        try {
            Class clazz = Class.forName(entityClass.getName());
            Field[] fields = clazz.getDeclaredFields();
            sql.append("<where>");
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                sql.append("<if test=\"");
                sql.append(fieldName);
                sql.append(" != null\"> ");
                if (field.isAnnotationPresent(Like.class)) {
                    sql.append("and ");
                    sql.append(getColumnName(fieldName));
                    sql.append(" like CONCAT('%',#{");
                    sql.append(fieldName);
                    sql.append(",jdbcType=VARCHAR},'%') </if>");
                }else{
                    sql.append("and ");
                    sql.append(getColumnName(fieldName));
                    sql.append(" = #{");
                    sql.append(fieldName);
                    sql.append("} </if>");
                }
            }
            sql.append("</where>");

            return sql.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取数据库的字段名
     * @return
     */
    private String getColumnName(String fieldName) {
        StringBuilder str = new StringBuilder(fieldName);
        StringBuilder newStr = new StringBuilder();
        for(int i=0;i<str.length();i++) {
            char ch = str.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                newStr.append("_");
                newStr.append((char) (ch +32));
            }else{
                newStr.append(ch);
            }
        }
        return newStr.toString();
    }



}
