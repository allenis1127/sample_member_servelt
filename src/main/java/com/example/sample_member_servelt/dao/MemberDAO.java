package com.example.sample_member_servelt.dao;

import com.example.sample_member_servelt.dto.MemberDTO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class MemberDAO {
    private static MemberDAO instance;

    private MemberDAO() {

    }

    public static MemberDAO getInstance() {
        if (instance == null)
            instance = new MemberDAO();
        return instance;
    }
    public boolean insertMember(MemberDTO memberDTO) throws Exception {
        String query = "INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())";

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, memberDTO.getId());
        preparedStatement.setString(2, memberDTO.getPassword());
        preparedStatement.setString(3, memberDTO.getName());
        preparedStatement.setString(4, memberDTO.getGender());
        preparedStatement.setString(5, memberDTO.getBirth());
        preparedStatement.setString(6, memberDTO.getMail());
        preparedStatement.setString(7, memberDTO.getPhone());
        preparedStatement.setString(8, memberDTO.getAddress());
        int result = preparedStatement.executeUpdate();

        return result == 1;
    }

    public boolean updateMember(MemberDTO memberDTO) throws Exception {
        log.info("updateMember()...");
        String query = "UPDATE member SET password=?, name=?, gender=?, birth=?, mail=?, phone=?, address=? WHERE id=?";
        log.info(query);

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, memberDTO.getPassword());
        preparedStatement.setString(2, memberDTO.getName());
        preparedStatement.setString(3, memberDTO.getGender());
        preparedStatement.setString(4, memberDTO.getBirth());
        preparedStatement.setString(5, memberDTO.getMail());
        preparedStatement.setString(6, memberDTO.getPhone());
        preparedStatement.setString(7, memberDTO.getAddress());
        preparedStatement.setString(8, memberDTO.getId());
        int result = preparedStatement.executeUpdate();
        return result == 1;
    }

    public boolean isExistId(String id) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) AS cnt FROM member WHERE id=?";
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            if (resultSet.getInt(1) == 1) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public boolean login(String id, String pass) throws SQLException, ClassNotFoundException {
        log.info("login()...");
        String query = "SELECT * FROM member WHERE id = ? AND password = ?";
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, pass);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getName(String id) throws SQLException, ClassNotFoundException {
        log.info("getName()...");
        String query = "SELECT name FROM member WHERE id = ?";
        String name = null;
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            name = resultSet.getString(1);
        }
        return name;
    }

    public MemberDTO getMemberOne(String id) throws SQLException, ClassNotFoundException {
        log.info("getMemberOne()...");
        log.info("id: " + id);
        MemberDTO memberDTO = null;
        String query = "SELECT * FROM member WHERE ID=?";
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            memberDTO = new MemberDTO();
            memberDTO.setId(id);
            memberDTO.setName(resultSet.getString("name"));
            memberDTO.setPassword(resultSet.getString("password"));
            memberDTO.setGender(resultSet.getString("gender"));
            memberDTO.setBirth(resultSet.getString("birth"));
            memberDTO.setMail(resultSet.getString("mail"));
            memberDTO.setPhone(resultSet.getString("phone"));
            memberDTO.setAddress(resultSet.getString("address"));
        }
        return memberDTO;
    }
}
