package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("== 프로그램 시작 ==");



        int id = 1;
        List<Article> articles = new ArrayList();

        while(true){
            System.out.print("명령어 입력: ");
            String cmd = sc.nextLine().trim();


            if(cmd.equals("article write")){
                System.out.println("게시글 작성");
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, Util.date(), title, body);

                articles.add(article);
                System.out.println(id + "번 글 작성되었습니다.");
                id++;
                System.out.println("== 작성 완료 ==");

            } else if (cmd.equals("article list")){
                System.out.println("== 목록 ==");
                if(articles.size() == 0){
                    System.out.println("작성된 게시글이 없습니다.");
                    continue;
                }
                System.out.println("   번호   /   작성 날짜   /   제목   /   내용   ");
                for(int i = articles.size() - 1; i >= 0; i--){
                    Article article = articles.get(i);
                    System.out.println(article.getId() + "   /   " + article.getRegDate()+"   /   "+article.getTitle()+"   /   "+article.getBody());
                }

            }else if(cmd.equals("exit")){
                System.out.println("프로그램 종료");
                break;
            }
        }
    }
}
