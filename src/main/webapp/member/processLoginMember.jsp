<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.CartDAO" %>
<%@ include file="../inc/dbconn.jsp"%>
<%
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	String password = request.getParameter("password");

	String sql = "SELECT * FROM member WHERE id = ? AND password = ?";
	preparedStatement = connection.prepareStatement(sql);
	preparedStatement.setString(1, id);
	preparedStatement.setString(2, password);
	resultSet = preparedStatement.executeQuery();
	if (resultSet.next()) {
		session.setAttribute("sessionMemberId", id);
		session.setAttribute("sessionMemberName", resultSet.getString("name"));

		CartDAO cartDAO = new CartDAO();
		cartDAO.updateCartBylogin(session);
		response.sendRedirect("resultMember.jsp?msg=2");
	}
	else {
		response.sendRedirect("loginMember.jsp?error=1");
	}

%>
