package org.koreait.dao;

import org.koreait.Article;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    private Connection conn;

    public ArticleDao(Connection conn) {
        this.conn = conn;

    }

    public Map<String, Object> findId(Connection conn, int id) {

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


    public int doWrite(String title, String body) {

        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body`= ?;", body);

        return DBUtil.insert(conn, sql);

    }

    public List<Article> showList() {

        List<Article> articles = new ArrayList<>();

        SecSql sql = new SecSql();
        sql.append("SELECT * ");
        sql.append("FROM article");
        sql.append("ORDER BY id DESC");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        return articles;
    }

    public void doDelete(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);

        DBUtil.delete(conn, sql);
    }

    public void doModify(int id, String title, String body) {

        SecSql sql = new SecSql();

        sql.append("UPDATE article");
        sql.append("SET UpdateDate = NOW()");
        if (title.length() > 0) {
            sql.append(", title = ?", title);
        }
        if (body.length() > 0) {
            sql.append(", `body` = ?", body);
        }
        sql.append("WHERE id = ?", id);


        DBUtil.update(conn, sql);
    }
}
