package com.example.sample_member_servelt.service;

import com.example.sample_member_servelt.dao.MemberDAO;
import com.example.sample_member_servelt.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
public class MemberService {
    MemberDAO memberDAO = MemberDAO.getInstance();

    public boolean addMember(HttpServletRequest request) throws Exception {

        MemberDTO memberDTO = new MemberDTO();

        String year = request.getParameter("birthyy");
        String month = request.getParameterValues("birthmm")[0];
        String day = request.getParameter("birthdd");
        String mail1 = request.getParameter("mail1");
        String mail2 = request.getParameterValues("mail2")[0];

        memberDTO.setId(request.getParameter("id"));
        memberDTO.setPassword(request.getParameter("password"));
        memberDTO.setName(request.getParameter("name"));
        memberDTO.setGender(request.getParameter("gender"));
        memberDTO.setBirth(year + "/" + month + "/" + day);
        memberDTO.setMail(mail1 + "@" + mail2);
        memberDTO.setPhone(request.getParameter("phone"));
        memberDTO.setAddress(request.getParameter("address"));

        return memberDAO.insertMember(memberDTO);
    }

    public boolean modifyMember(HttpServletRequest request) throws Exception {
        MemberDTO memberDTO = new MemberDTO();

        String year = request.getParameter("birthyy");
        String month = request.getParameterValues("birthmm")[0];
        String day = request.getParameter("birthdd");
        String mail1 = request.getParameter("mail1");
        String mail2 = request.getParameterValues("mail2")[0];

        String id = (String) request.getSession().getAttribute("sessionMemberId");
        memberDTO.setId(id);
        memberDTO.setPassword(request.getParameter("password"));
        memberDTO.setName(request.getParameter("name"));
        memberDTO.setGender(request.getParameter("gender"));
        memberDTO.setBirth(year + "/" + month + "/" + day);
        memberDTO.setMail(mail1 + "@" + mail2);
        memberDTO.setPhone(request.getParameter("phone"));
        memberDTO.setAddress(request.getParameter("address"));
        return memberDAO.updateMember(memberDTO);
    }
    public void isExistId(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        log.info("isExistId()...");
        String id = request.getParameter("id");

        response.getWriter().print("{\"result\":\"");
        if (memberDAO.isExistId(id)) {
            response.getWriter().print("true");
        }
        else {
            response.getWriter().print("false");
        }
        response.getWriter().print("\"}");
    }

    public boolean login(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("login()...");
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String name;
        if (memberDAO.login(id, password)) {
            name = memberDAO.getName(id);
            request.getSession().setAttribute("sessionMemberId", id);
            request.getSession().setAttribute("sessionMemberName", name);
            return true;
        }
        else {
            return false;
        }
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public MemberDTO getMemberOne(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String id = (String) request.getSession().getAttribute("sessionMemberId");
        return memberDAO.getMemberOne(id);
    }
}
