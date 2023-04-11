package com.example.sample_member_servelt.controller;

import com.example.sample_member_servelt.service.BoardService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
@WebServlet("*.do")
public class BoardController extends HttpServlet {
    BoardService boardService = BoardService.getInstance();
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

        String viewPath = "/WEB-INF/template/board/"; // 템플릿 파일 경로

        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        if (command.contains("addForm.do")) { // 글입력 폼
            log.info("addForm.do...");
            req.getRequestDispatcher(viewPath + "addForm.jsp").forward(req, resp);
        }
        else if (command.contains("addAction.do")) { // 글 입력
            log.info("addAction.do");
            try {
                if (boardService.addPost(req)) {
                    resp.sendRedirect("list.do");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if (command.contains("list.do")) { // 글 목록
            log.info("list.do");
            try {
                boardService.getList(req);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            req.getRequestDispatcher(viewPath + "list.jsp").forward(req, resp);
        }
        else if (command.contains("view.do")) { // 글 보기
            log.info("view.do");
            try {
                boardService.getOne(req);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            req.getRequestDispatcher(viewPath + "view.jsp").forward(req, resp);
        }
        else if (command.contains("removeAction.do")) { // 글 삭제
            log.info("removeAction.do");
            try {
                if(boardService.removeOne(req)) {
                    resp.sendRedirect("list.do");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if (command.contains("modifyForm.do")) { // 글 수정 폼
            log.info("modifyForm.do");
            try {
                boardService.getOne(req);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            req.getRequestDispatcher(viewPath + "modifyForm.jsp").forward(req, resp);
        }
        else if (command.contains("modifyAction.do")) { // 글 수정 처리
            log.info("modifyAction.do");
            try {
                if (boardService.modifyOne(req)) {
                    resp.sendRedirect("list.do");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            req.getRequestDispatcher(viewPath + "error.jsp").forward(req, resp);
        }
    }
}
