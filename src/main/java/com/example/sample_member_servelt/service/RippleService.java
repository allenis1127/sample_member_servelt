package com.example.sample_member_servelt.service;

import com.example.sample_member_servelt.dao.BoardDAO;
import com.example.sample_member_servelt.dao.RippleDAO;
import com.example.sample_member_servelt.dto.RippleDTO;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RippleService {
    private static RippleService instance;

    private RippleService() {

    }

    public static RippleService getInstance() {
        if (instance == null)
            instance = new RippleService();
        return instance;
    }

    public boolean addRipple(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("addRipple()...");
        RippleDAO rippleDAO = RippleDAO.getInstance();

        RippleDTO rippleDTO = new RippleDTO();
        rippleDTO.setBoardNum(Integer.valueOf(request.getParameter("num")));
        rippleDTO.setMemberId((String) request.getSession().getAttribute("sessionMemberId")); // 세션에 저장된 아이디 사용.
        rippleDTO.setName(request.getParameter("name"));
        rippleDTO.setContent(request.getParameter("content"));
        rippleDTO.setIp(request.getRemoteAddr());

        return rippleDAO.insertRipple(rippleDTO);
    }

    public ArrayList<RippleDTO> getRipples(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("getRipples()...");

        RippleDAO rippleDAO = RippleDAO.getInstance();
        int num = Integer.parseInt(request.getParameter("num"));
        ArrayList<RippleDTO> ripples = rippleDAO.selectRipples(num);
        for (RippleDTO ripple: ripples) {
            log.info(ripple.getMemberId());
            log.info((String) request.getSession().getAttribute("sessionMemberId"));
            if (ripple.getMemberId().equals((String) request.getSession().getAttribute("sessionMemberId"))) {
                ripple.setLogin(true);
            }
            else {
                ripple.setLogin(false);
            }
        }
        return ripples;
    }

    public boolean removeRipple(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("removeRipple()");

        RippleDAO rippleDAO = RippleDAO.getInstance();
        int rippleId = Integer.parseInt(request.getParameter("rippleId"));
        return rippleDAO.deleteRipple(rippleId);
    }
}
