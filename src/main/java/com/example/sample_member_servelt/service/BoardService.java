package com.example.sample_member_servelt.service;

import com.example.sample_member_servelt.dao.BoardDAO;
import com.example.sample_member_servelt.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class BoardService {
    static final int LISTCOUNT = 5;
    private static BoardService instance;

    private BoardService() {

    }

    public static BoardService getInstance() {
        if (instance == null)
            instance = new BoardService();
        return instance;
    }
    public boolean addPost(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("addPost...");
        BoardDAO dao = BoardDAO.getInstance();

        BoardDTO board = new BoardDTO();

        HttpSession session = request.getSession();
        board.setId((String) session.getAttribute("sessionMemberId")); // 세션에 저장된 아이디 사용.
        board.setName(request.getParameter("name"));
        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));

        log.info(board.getName());  // 디버깅을 위해서 출력
        log.info(board.getSubject());
        log.info(board.getContent());
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
        String registerDay = formatter.format(new java.util.Date());

        board.setHit(0); // 조회수
        board.setRegistDay(registerDay);  // 등록일
        board.setIp(request.getRemoteAddr()); // 접근 IP

        return dao.insertPost(board);
    }

    public void getList(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        BoardDAO dao = BoardDAO.getInstance();
        List<BoardDTO> boardlist;  // 게시물을 담는 리스트

        int page = 1;  // 페이지 번호
        int limit = LISTCOUNT;  // 페이지당 노출될 게시물 수

        if(request.getParameter("page") != null) { // 전달되는 페이지 번호가 있으면
            page = Integer.parseInt(request.getParameter("page"));
        }

        String items = request.getParameter("items"); // 검색 필드.
        String text = request.getParameter("text"); // 검색어.

        int totalRecord = dao.getListCount(items, text); // 전체 게시물 수
        boardlist = dao.getList(page, limit, items, text); // 현재 페이지에 해당하는 게시물 목록

        int totalPage;  // 전체 페이지 수

        // 전체 페이지 계산
        if (totalRecord % limit == 0){ // 전체 게시물의 숫자가 페이지당 게시물의 배수이면
            totalPage = totalRecord / limit;
            Math.floor(totalPage); // Math.floor() : 주어진 숫자와 같거나 작은 정수 중에서 가장 큰 수를 반환
        }
        else {
            totalPage = totalRecord / limit;
            Math.floor(totalPage);
            totalPage =  totalPage + 1;  // 배수가 아니면 + 1 예) 전체게시물이 51개이고 페이지당 노출되는 게시물의 수가 10개이면 전체페이지는?
        }

        // 뷰에 전달할 값들
        request.setAttribute("limit", limit); // 페이지당 노출될 게시물 수
        request.setAttribute("page", page); // 현재 페이지
        request.setAttribute("totalPage",totalPage); // 전체 페이지 수
        request.setAttribute("totalRecord",totalRecord); // 전체 게시물 수
        request.setAttribute("boardlist", boardlist); // 현재 페이지에 해당하는 게시물 목록

        request.setAttribute("items", items); // 검색 필드.
        request.setAttribute("text", text); // 검색어.
    }

    public void getOne(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("getOne()...");
        BoardDAO boardDAO = BoardDAO.getInstance();

        int num = Integer.parseInt(request.getParameter("num"));
        int page = Integer.parseInt(request.getParameter("page"));

        BoardDTO board = boardDAO.selectOneByNum(num);

        request.setAttribute("num", num);
        request.setAttribute("page", page);
        request.setAttribute("board", board);
    }

    public boolean removeOne(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("removeOne()...");
        BoardDAO boardDAO = BoardDAO.getInstance();

        int num = Integer.parseInt(request.getParameter("num"));
        return boardDAO.deleteOne(num);
    }

    public boolean modifyOne(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("modifyOne()...");
        BoardDAO boardDAO = BoardDAO.getInstance();
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setNum(Integer.parseInt(request.getParameter("num")));
        boardDTO.setName(request.getParameter("name"));
        boardDTO.setSubject(request.getParameter("subject"));
        boardDTO.setContent(request.getParameter("content"));

        return boardDAO.updateOne(boardDTO);
    }
}
