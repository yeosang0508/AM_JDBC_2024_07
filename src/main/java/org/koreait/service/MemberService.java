package org.koreait.service;

import org.koreait.dao.MemberDao;

import java.lang.reflect.Member;
import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = new MemberDao();
    }

    public boolean isLoginIdDup(Connection conn, String loginId){
        return memberDao.isLoginIdDup(conn, loginId);
    }

    public boolean isMember(Connection conn, String loginId, String loginPw) {
        return memberDao.isMember(conn, loginId,loginPw);
    }

    public Object getMemberbyloginPw(String loginId, String loginPw) {
    }
}
