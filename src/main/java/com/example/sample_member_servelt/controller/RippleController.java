package com.example.sample_member_servelt.controller;

import com.example.sample_member_servelt.dto.RippleDTO;
import com.example.sample_member_servelt.service.BoardService;
import com.example.sample_member_servelt.service.RippleService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@WebServlet("/ripple/*")
public class RippleController extends HttpServlet {
    RippleService rippleService = RippleService.getInstance();
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
            case "/ripple/add":
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (rippleService.addRipple(req)) {
                        jsonObject.put("result", "true");
                    }
                    else {
                        jsonObject.put("result", "false");
                    }
                    resp.getWriter().print(jsonObject.toJSONString());
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/ripple/get":
                try {
                    List<RippleDTO> ripples = rippleService.getRipples(req);
                    JSONArray jsonArray = new JSONArray();
                    for (RippleDTO ripple: ripples) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("rippleId", ripple.getRippleId());
                        jsonObject.put("name", ripple.getName());
                        jsonObject.put("content", ripple.getContent());
                        jsonObject.put("insertDate", ripple.getInsertDate());
                        jsonObject.put("ip", ripple.getIp());
                        jsonObject.put("isLogin", ripple.isLogin());
                        jsonArray.add(jsonObject);
                    }
                    resp.getWriter().print(jsonArray.toJSONString());

                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/ripple/delete":
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (rippleService.removeRipple(req)) {
                        jsonObject.put("result", "true");
                    }
                    else {
                        jsonObject.put("result", "false");
                    }
                    resp.getWriter().print(jsonObject.toJSONString());
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
}
