package com.example.sample_member_servelt.controller;

import com.example.sample_member_servelt.dto.MemberDTO;
import com.example.sample_member_servelt.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
@WebServlet("/member/*")
public class MemberController extends HttpServlet {
    MemberService memberService = new MemberService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String RequestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = RequestURI.substring(contextPath.length());

        log.info("command: " + command);
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        switch (command) {
            case "/member/addMember":  // 회원 가입 페이지 노출
                log.info("addMember...");
                req.getRequestDispatcher("/WEB-INF/template/member/addMember.jsp").forward(req, resp);
                break;
            case "/member/addMemberAction":  // 회원 가입 처리
                log.info("addMemberAction...");
                try {
                    if (memberService.addMember(req)) {
                        resp.sendRedirect("resultMember?msg=1");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/member/modifyMember":  // 회원 정보 수정 페이지 노출
                log.info("modifyMember...");
                try {
                    MemberDTO memberDTO = memberService.getMemberOne(req);
                    log.info("memberDTO : " + memberDTO);
                    req.setAttribute("member", memberDTO);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                req.getRequestDispatcher("/WEB-INF/template/member/modifyMember.jsp").forward(req, resp);
                break;
            case "/member/modifyMemberAction": // 회원 정보 수정 처리
                log.info("modifyMemberAction...");
                try {
                    if (memberService.modifyMember(req)) {
                        resp.sendRedirect("resultMember?msg=0");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/member/login":  // 로그인 페이지 노출
                log.info("login...");
                req.getRequestDispatcher("/WEB-INF/template/member/login.jsp").forward(req, resp);
                break;
            case "/member/loginAction":  // 로그인 처리
                log.info("loginAction...");
                try {
                    if (memberService.login(req)) {
                        resp.sendRedirect("resultMember?msg=2");
                    } else {
                        resp.sendRedirect("login?error=1");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/member/logoutAction":  // 로그아웃 처리
                log.info("logoutAction...");
                memberService.logout(req);
                resp.sendRedirect("/member/login");
                break;
            case "/member/ajaxIdCheck":  // 아이디 중복 체크
                log.info("ajaxIdCheck...");
                try {
                    memberService.isExistId(req, resp);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/member/resultMember":  // 결과 페이지
                log.info("resultMember...");
                req.getRequestDispatcher("/WEB-INF/template/member/resultMember.jsp").forward(req, resp);
                break;
        }
    }
}
