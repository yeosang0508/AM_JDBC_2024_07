package org.koreait;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("== 프로그램 시작 ==");





        while(true){
            System.out.print("명령어 입력: ");
            String cmd = sc.nextLine().trim();


            if(cmd.equals("article write")){


                Connection conn = null;
                PreparedStatement pstmt = null;

                try{
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

                    conn = DriverManager.getConnection(url, "root", "");

                    System.out.println("DB 연결 성공");

                    System.out.println("게시글 작성");
                    System.out.print("제목 : ");
                    String title = sc.nextLine();
                    System.out.print("내용 : ");
                    String body = sc.nextLine();

                    String sql = "INSERT INTO article ";
                    sql += "SET regDate = NOW(),";
                    sql += "title = '" + title + "',";
                    sql += "`body` = '" + body + "';";


                    pstmt = conn.prepareStatement(sql);

                    int affectedRows = pstmt.executeUpdate();
                    System.out.println(affectedRows + "번 글 작성되었습니다.");

                    System.out.println("== 작성 완료 ==");


                }catch(ClassNotFoundException e){
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e){
                    System.out.println("에러 : " + e);
                } finally {
                    try{
                        if(conn != null && !conn.isClosed()){
                            conn.close();
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }try{
                        if(pstmt != null && !pstmt.isClosed()){
                            pstmt.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }



            } else if (cmd.equals("article list")){
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                List<Article> articles = new ArrayList();

                try{
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

                    conn = DriverManager.getConnection(url, "root","");

                    System.out.println("DB 연결 성공");

                    System.out.println("== 목록 ==");

                    String sql = "select * from article ";
                    sql += "ORDER BY id DESC";

                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery(sql);

                    while(rs.next()){
                        int id = rs.getInt("id");
                        String regDate = rs.getString("regDate");
                        String title = rs.getString("title");
                        String body = rs.getString("body");

                        Article article = new Article(id, regDate, title, body);
                        articles.add(article);
                    }

                    if(articles.size() == 0){
                        System.out.println("작성된 게시글이 없습니다.");
                        continue;
                    }

                    System.out.println("   번호   /   작성 날짜   /   제목   /   내용   ");
                    for(int i = articles.size() - 1; i >= 0; i--){
                        Article article = articles.get(i);
                        System.out.println(article.getId() + "   /   " + article.getRegDate()+"   /   "+article.getTitle()+"   /   "+article.getBody());
                    }


                }catch (ClassNotFoundException e){
                    System.out.println("드라이버 로딩 실패" + e);
                }catch (SQLException e){
                    System.out.println("에러 : " + e);
                } finally {
                    try{
                        if(rs != null && !rs.isClosed()){
                            rs.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }try{
                        if(pstmt != null && !pstmt.isClosed()){
                            pstmt.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }try{
                        if(conn != null && !conn.isClosed()){
                            conn.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }


            }else if(cmd.equals("exit")){
                System.out.println("프로그램 종료");
                break;
            }
        }
    }
}
