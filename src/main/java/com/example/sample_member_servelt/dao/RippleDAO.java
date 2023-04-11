package com.example.sample_member_servelt.dao;

import com.example.sample_member_servelt.dto.RippleDTO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RippleDAO {
    private static RippleDAO instance;

    private RippleDAO() {

    }

    public static RippleDAO getInstance() {
        if (instance == null)
            instance = new RippleDAO();
        return instance;
    }

    public boolean insertRipple(RippleDTO rippleDTO) throws SQLException, ClassNotFoundException {
        log.info("insertRipple()...");
        int result;
        String query = "INSERT INTO `ripple` VALUES (null, ?, ?, ?, ?, now(), ?)";

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, rippleDTO.getBoardNum());
        preparedStatement.setString(2, rippleDTO.getMemberId());
        preparedStatement.setString(3, rippleDTO.getName());
        preparedStatement.setString(4, rippleDTO.getContent());
        preparedStatement.setString(5, rippleDTO.getIp());
        result = preparedStatement.executeUpdate();

        return result == 1;
    }

    public ArrayList<RippleDTO> selectRipples(int boradNum) throws SQLException, ClassNotFoundException {
        log.info("selectRipples()...");

        ArrayList<RippleDTO> ripples = new ArrayList<>();
        String query = "SELECT * FROM `ripple` WHERE boardNum = ?";

        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, boradNum);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            RippleDTO rippleDTO = new RippleDTO();
            rippleDTO.setRippleId(resultSet.getInt("rippleId"));
            rippleDTO.setMemberId(resultSet.getString("memberId"));
            rippleDTO.setName(resultSet.getString("name"));
            rippleDTO.setContent(resultSet.getString("content"));
            rippleDTO.setInsertDate(resultSet.getString("insertDate"));
            rippleDTO.setIp(resultSet.getString("ip"));
            log.info(rippleDTO);
            ripples.add(rippleDTO);
        }
        return ripples;
    }

    public boolean deleteRipple(int rippleId) throws SQLException, ClassNotFoundException {
        log.info("deleteRipple()...");

        String query = "DELETE FROM `ripple` WHERE rippleId = ?";
        @Cleanup Connection connection = DBConnection.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, rippleId);

        return preparedStatement.executeUpdate() == 1;
    }
}
