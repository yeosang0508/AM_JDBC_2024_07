package org.koreait.controller;

import org.koreait.Article;
import org.koreait.service.ArticleService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ArticleController {
    private Connection conn;
    private Scanner sc;

    private ArticleService articleService;

    public ArticleController(Scanner sc, Connection conn) {
        this.conn = conn;
        this.sc = sc;


        this.articleService = new ArticleService(conn);
    }

    public void doWrite() {
        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int id = articleService.doWrite(title, body);

        System.out.println(id + "번 글이 생성되었습니다");
    }

    public void showList() {
        System.out.println("==목록==");
        List<Article> articles = articleService.showList();

        if (articles.size() == 0) {
            System.out.println("게시글 없습니다.");
            return;
        }


        System.out.println("  번호  /   작성 날짜  /  수정 날짜  /  제목  /  내용  ");
        for (Article article : articles) {
            System.out.printf("  %d    /   %s   /   %s   /   %s   /  %s   \n", article.getId(), article.getRegDate(), article.getUpdateDate(), article.getTitle(), article.getBody());
        }


    }

    public void showDetail(String cmd) {
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


        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doDelete(String cmd) {
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

        articleService.doDelete(id);

        System.out.println(id + "번 글이 삭제되었습니다.");
    }

    public void doModify(String cmd) {

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

        articleService.doModify(id, title, body);

        System.out.println(id + "번 글이 수정되었습니다.");

    }
}
