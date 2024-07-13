package org.koreait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("==프로그램 시작==");
        Scanner sc = new Scanner(System.in);

        List<Article> articles = new ArrayList<>();

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("article write")) {
                System.out.println("==글쓰기==");
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();


                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

                    conn = DriverManager.getConnection(url, "root", "");

                    System.out.println("연결 성공!");

                    String sql = "INSERT INTO article ";
                    sql += "SET regDate = NOW(),";
                    sql += "updateDate = NOW(),";
                    sql += "title = '" + title + "',";
                    sql += "`body` = '" + body + "';";

                    pstmt = conn.prepareStatement(sql);

                    int affectedRows = pstmt.executeUpdate();

                    System.out.println("affected rows = " + affectedRows);

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (cmd.equals("article list")) {
                System.out.println("==목록==");
                if (articles.size() == 0) {
                    System.out.println("게시글이 없습니다");
                    continue;
                }
                System.out.println("   번호    /    제목     ");
                for (Article article : articles) {
                    System.out.printf("     %d      /    %s    \n", article.getId(), article.getTitle());
                }

            }


        }

        System.out.println("==프로그램 종료==");
    }
}