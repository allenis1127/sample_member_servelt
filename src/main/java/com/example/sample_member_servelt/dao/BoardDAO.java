package com.example.sample_member_servelt.dao;

import com.example.sample_member_servelt.dto.BoardDTO;
import com.example.sample_member_servelt.dto.MemberDTO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Log4j2
public class BoardDAO {
    private static BoardDAO instance;

    private BoardDAO() {

    }

    public static BoardDAO getInstance() {
        if (instance == null)
            instance = new BoardDAO();
        return instance;
    }

    public boolean insertPost(BoardDTO boardDTO) throws SQLException, ClassNotFoundException {
        log.info("insertPost()...");
        String query = "insert into board values(null, ?, ?, ?, ?, ?, ?, ?)";
        int result;

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, boardDTO.getId());
        preparedStatement.setString(2, boardDTO.getName());
        preparedStatement.setString(3, boardDTO.getSubject());
        preparedStatement.setString(4, boardDTO.getContent());
        preparedStatement.setString(5, boardDTO.getRegistDay());
        preparedStatement.setInt(6, boardDTO.getHit());
        preparedStatement.setString(7, boardDTO.getIp());
        result = preparedStatement.executeUpdate();

        return result == 1;
    }

    public int getListCount(String items, String text) throws SQLException, ClassNotFoundException {
        String query;
        if (items == null && text == null) {
            query = "SELECT count(*) FROM board";
        }
        else {
            query = "SELECT count(*) FROM board WHERE " + items + " LIKE '%" + text + "%'";
        }

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public ArrayList<BoardDTO> getList(int page, int limit, String items, String text) throws SQLException, ClassNotFoundException {
        log.info("getList()..." );
        int start = (page - 1) * limit;  // 예) 1페이지면 0, 2페이지면 5, 3페이지면 10

        String query;
        if (items == null && text == null)  // 검색이 없는 경우
            query = "select * from board ORDER BY num DESC LIMIT " + start + ", " + limit;
        else
            query = "SELECT  * FROM board where " + items + " like '%" + text + "%' ORDER BY num DESC LIMIT " + start + ", " + limit;

        ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            BoardDTO board = new BoardDTO();
            board.setNum(resultSet.getInt("num"));
            board.setId(resultSet.getString("id"));
            board.setName(resultSet.getString("name"));
            board.setSubject(resultSet.getString("subject"));
            board.setContent(resultSet.getString("content"));
            board.setRegistDay(resultSet.getString("registDay"));
            board.setHit(resultSet.getInt("hit"));
            board.setIp(resultSet.getString("ip"));
            log.info(board);  // ToString() 필요
            list.add(board); // 리스트에 추가
        }
        return list;
    }

    public BoardDTO selectOneByNum(int num) throws SQLException, ClassNotFoundException {
        BoardDTO boardDTO = null;
        String query = "SELECT * FROM board WHERE num = ? ";

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, num);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            updateHit(num);
            boardDTO = new BoardDTO();
            boardDTO.setNum(resultSet.getInt("num"));
            boardDTO.setId(resultSet.getString("id"));
            boardDTO.setName(resultSet.getString("name"));
            boardDTO.setSubject(resultSet.getString("subject"));
            boardDTO.setContent(resultSet.getString("content"));
            boardDTO.setRegistDay(resultSet.getString("registDay"));
            boardDTO.setHit(resultSet.getInt("hit"));
            boardDTO.setIp(resultSet.getString("ip"));
            log.info(boardDTO);
        }
        return boardDTO;
    }
    //선택된 글의 조회수 증가하기
    private void updateHit(int num) throws SQLException, ClassNotFoundException {
        String query = "UPDATE board SET hit = hit + 1 WHERE num = ?";

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, num);
        preparedStatement.executeUpdate();
    }

    public boolean deleteOne(int num) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM board WHERE num = ?";
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, num);
        return preparedStatement.executeUpdate() == 1;
    }

    public boolean updateOne(BoardDTO boardDTO) throws SQLException, ClassNotFoundException {
        String query = "UPDATE `board` SET `name` = ?, `subject` = ?, `content` = ? WHERE `num` = ?";
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, boardDTO.getName());
        preparedStatement.setString(2, boardDTO.getSubject());
        preparedStatement.setString(3, boardDTO.getContent());
        preparedStatement.setInt(4, boardDTO.getNum());
        return preparedStatement.executeUpdate() == 1;
    }
}
