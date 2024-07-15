package org.koreait.controller;

import org.koreait.service.MemberService;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {

    private Connection conn;
    private Scanner sc;

    private MemberService memberService;

    public MemberController(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
        this.memberService = new MemberService();
    }

    public void doJoin(){
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;

        System.out.println("== 회원가입 ==");

        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if(loginId.length() == 0 || loginId.contains(" ")){
                System.out.println("아이디 다시 입력해주세요.");

                continue;
            }


            boolean isLoindIdDup = memberService.isLoginIdDup(conn,loginId);

            if(isLoindIdDup) {
                System.out.println(loginId + "는(은) 이미 사용중입니다.");
                continue;
            }
            break;
        }
        while(true){
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if(loginPw.length() == 0 || loginPw.contains(" ")){
                System.out.println("비밀번호 다시 입력해주세요.");
                continue;
            }

            boolean loginPwCheck = true;

            while (true) {
                System.out.print("비밀번호 확인 : ");
                loginPwConfirm = sc.nextLine().trim();

                if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
                    System.out.println("비번 확인 똑바로 써");
                    continue;
                }
                if (loginPw.equals(loginPwConfirm) == false) {
                    System.out.println("일치하지 않아");
                    loginPwCheck = false;
                }
                break;
            }
            if (loginPwCheck) {
                break;
            }
        }

        while (true) {
            System.out.print("이름 : ");
            name = sc.nextLine();

            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("이름 똑바로 써");
                continue;
            }
            break;
        }


        SecSql sql = new SecSql();

        sql.append("INSERT INTO `member`");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("loginId = ?,", loginId);
        sql.append("loginPw= ?,", loginPw);
        sql.append("name = ?;", name);

        int id = DBUtil.insert(conn, sql);

        System.out.println(id + "번 회원이 생성되었습니다");
    }

    public void dologin() {
        String loginId = null;
        String loginPw = null;

        System.out.println("== 로그인 ==");
        System.out.print("아이디 : ");
        loginId = sc.nextLine().trim();

        if (loginId.length() == 0 || loginId.contains(" ")) {
            System.out.println("아이디 입력해주세요.");

            return;
        }

        System.out.print("비밀번호 : ");
        loginPw = sc.nextLine().trim();

        int trycount = 3;
        int count = 1;

        while (trycount == 1) {

            boolean isMember = memberService.isMember(conn, loginId, loginPw);

            if (isMember == false) {
                trycount--;

                System.out.println(count + "번 실패," + (3 - count) + "번 남았습니다. 비밀번호 다시 입력해주세요.");
                count++;

                continue;
            }

            break;
        }

        memberMap = memberService.getMemberbyloginPw(loginId, loginPw);

        if (memberMap == null) {
            System.out.println("로그인 실패, 횟수를 초과했습니다.");
            return;
        } else {

            memberService.getMemberbyloginPw(loginId, loginPw);
            System.out.println("로그인 성공");

            return;

        }

    }
}
