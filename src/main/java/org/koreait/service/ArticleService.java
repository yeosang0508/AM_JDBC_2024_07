package org.koreait.service;

import org.koreait.Article;
import org.koreait.dao.ArticleDao;

import java.sql.Connection;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao;

    public ArticleService() {
        this.articleDao = new ArticleDao();
    }

    public Map<String, Object> findId(Connection conn, int id) {
        return articleDao.findId(conn, id);

    }
}
