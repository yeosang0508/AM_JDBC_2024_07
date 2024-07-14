package org.koreait.dao;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class ArticleDao {

    public Map<String, Object> findId(Connection conn, int id){

        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("WHERE id = ?", id);

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없어");
            return null;
        }
        return articleMap;
    }


}
