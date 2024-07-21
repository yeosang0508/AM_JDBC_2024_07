package org.koreait;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public void run() {

        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            Connection conn = null;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

            try {
                conn = DriverManager.getConnection(url, "root", "");

                int actionResult = doAction(conn, sc, cmd);

                if (actionResult == -1) {
                    System.out.println("==프로그램 종료==");
                    sc.close();
                    break;
                }

            } catch (SQLException e) {
                System.out.println("에러 1 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int doAction(Connection conn, Scanner sc, String cmd) {
        if (cmd.equals("exit")) {
            System.out.println("프로그램 종료");
            return -1;
        }

        if (cmd.equals("article write")) {
            PreparedStatement pstmt = null;

            System.out.println("게시글 작성");
            System.out.print("제목 : ");
            String title = sc.nextLine();
            System.out.print("내용 : ");
            String body = sc.nextLine();

            SecSql sql = new SecSql();

            sql.append("INSERT INTO article");
            sql.append("SET regDate = NOW(),");
            sql.append("updateDate = NOW(),");
            sql.append("title = ?,", title);
            sql.append("`body` = ?;", body);

            int id = DBUtil.insert(conn, sql);
            System.out.println(id + "번 글 작성되었습니다.");

        } else if (cmd.equals("article list")) {
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            List<Article> articles = new ArrayList();

            System.out.println("== 목록 ==");

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("ORDER BY id DESC");

            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

            for (Map<String, Object> articleMap : articleListMap) {
                articles.add(new Article(articleMap));
            }
            if (articles.size() == 0) {
                System.out.println("작성된 게시글이 없습니다.");
                return 0;
            }

            System.out.println("   번호   /   작성 날짜   /   제목   /   내용   ");

            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                System.out.println(article.getId() + "   /   " + article.getRegDate() + "   /   " + article.getTitle() + "   /   " + article.getBody());
            }


        } else if (cmd.startsWith("article modify")) {

            int id;

            try {
                id = Integer.parseInt(cmd.split(" ")[2].trim());
            } catch (Exception e) {
                System.out.println("정수로 입력해주세요.");
                return 0;
            }

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("WHERE id = ?", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

            if(articleMap.isEmpty()){
                System.out.println(id + "번 글은 없습니다.");
                return 0;
            }
            System.out.println("== 게시글 수정 ==");

            System.out.print("수정할 제목 : ");
            String title = sc.nextLine();

            System.out.print("수정할 내용 : ");
            String body = sc.nextLine();


             sql = new SecSql();

            sql.append("UPDATE article");
            sql.append("SET regDate = NOW(),");
            sql.append("updateDate = NOW()");
            if (title.length() > 0) {
                sql.append(", title = ?", title);
            }
            if (body.length() > 0) {
                sql.append(", `body` = ?", body);
            }
            sql.append("WHERE id = ?", id);

            DBUtil.update(conn, sql);

            System.out.println(id + "번 글이 수정되었습니다.");

        } else if (cmd.startsWith("article delete")) {
            int id;

            try {
                id = Integer.parseInt(cmd.split(" ")[2].trim());
            } catch (Exception e) {
                System.out.println("정수로 입력해주세요.");
                return 0;
            }

            SecSql sql = new SecSql();

            sql.append("DELETE FROM article");
            sql.append("WHERE id = ?", id);

            DBUtil.delete(conn, sql);

            System.out.println(id + "번 글이 삭제되었습니다.");
        } else if (cmd.startsWith("article detail")) {
            int id;

            try {
                id = Integer.parseInt(cmd.split(" ")[2].trim());
            } catch (Exception e) {
                System.out.println("정수로 입력해주세요.");
                return 0;
            }

            SecSql sql = new SecSql();

            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("WHERE id = ?", id);

            Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

            if(articleMap.isEmpty()){
                System.out.println(id + "번 글은 없습니다.");
                return 0;
            }

            Article article = new Article(articleMap);


            System.out.println("번호 : " + article.getId());
            System.out.println("작성날짜 : " + article.getRegDate());
            System.out.println("수정날짜 : " + article.getUpdateDate());
            System.out.println("제목 : " + article.getTitle());
            System.out.println("내용 : " + article.getBody());

        }

        return 0;
    }
}




