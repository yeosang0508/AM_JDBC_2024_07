package org.koreait.service;

import org.koreait.Article;
import org.koreait.dao.ArticleDao;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao;
    private Connection conn;


    public ArticleService(Connection conn) {
        this.articleDao = new ArticleDao(conn);
        this.conn = conn;
    }

    public Map<String, Object> findId(Connection conn, int id) {
        return articleDao.findId(conn, id);

    }

    public int doWrite(String title, String body) {
        return articleDao.doWrite(title, body);

    }

    public List<Article> showList() {
        return articleDao.showList();
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }

    public void doModify(int id, String title, String body) {
        articleDao.doModify(id,title,body);
    }
}
