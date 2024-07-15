package org.koreait.controller;

import org.koreait.Article;
import org.koreait.dao.ArticleDao;
import org.koreait.service.ArticleService;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {
    private Connection conn;
    private Scanner sc;
    private String cmd;

    private ArticleService articleService;
    
    public ArticleController(Scanner sc, Connection conn, String cmd) {
        this.conn = conn;
        this.sc = sc;
        this.cmd = cmd;

        this.articleService = new ArticleService();
    }

    public void doWrite() {
        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body`= ?;", body);

        int id = DBUtil.insert(conn, sql);

        // 데이터를 직접 INSERT 하는 것도 service에게 전달, service는 DB의 문지기인
        // Dao에게 전달하여 DB에 INSERT 되도록 하여야한다.

        System.out.println(id + "번 글이 생성되었습니다");
    }

    public void showList() {
        System.out.println("==목록==");
        List<Article> articles = new ArrayList<>();

        SecSql sql = new SecSql();
        sql.append("SELECT * ");
        sql.append("FROM article");
        sql.append("ORDER BY id DESC");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        // Map<String,Object> type인 list
        // DBUil.selectRows에서 해주는 것은?
        //
        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        //순차적으로 articleMap에 articleListMap에 들어있는 정보를 넣어주고
        // list에 저장

        if (articles.size() == 0) {
            System.out.println("게시글 없습니다.");
            return;
        }


        System.out.println("  번호  /   작성 날짜  /  수정 날짜  /  제목  /  내용  ");
        for (Article article : articles) {
            System.out.printf("  %d    /   %s   /   %s   /   %s   /  %s   \n", article.getId(), article.getRegDate(), article.getUpdateDate(), article.getTitle(), article.getBody());
        }


    }

    public void showDetail() {
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력해주세요.");
            return;
        }

        System.out.println("== 상세보기 ==");

        if(articleService.findId(conn, id) == null){
            return;
        }

        Article article = new Article(articleService.findId(conn, id));

        // 이럴때 1행만 가져올수 있는것은 id가 primary이므로 1행만 가져올수 있는 것

        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doDelete() {
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력");
            return;
        }

        if(articleService.findId(conn, id) == null){
            return;
        }

        System.out.println("== 삭제 ==");

        SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);

        DBUtil.delete(conn, sql);

        System.out.println(id + "번 글이 삭제되었습니다.");
    }

    public void doModify() {

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력");
            return;
        }

        if(articleService.findId(conn, id) == null){
            return;
        }

        
        System.out.println("== 수정 ==");
        System.out.print("새 제목 : ");
        String title = sc.nextLine();

        System.out.print("새 내용 : ");
        String body = sc.nextLine();

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

        System.out.println(id + "번 글이 수정되었습니다.");

    }
}
