<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*"%>
<%

	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	String password = request.getParameter("password");
	String name = request.getParameter("name");
	String gender = request.getParameter("gender");
	String year = request.getParameter("birthyy");
	String month = request.getParameterValues("birthmm")[0];
	String day = request.getParameter("birthdd");
	String birth = year + "/" + month + "/" + day;
	String mail1 = request.getParameter("mail1");
	String mail2 = request.getParameterValues("mail2")[0];
	String mail = mail1 + "@" + mail2;
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	try {
		String url = "jdbc:mariadb://localhost:3306/sample_jsp_book_market";
		String user = "root";
		String dbpassword = "3432";

		Class.forName("org.mariadb.jdbc.Driver");
		connection = DriverManager.getConnection(url, user, dbpassword);

	} catch (ClassNotFoundException e) {
		e.getMessage();
	} catch (SQLException ex) {
		out.println("데이터베이스 연결이 실패되었습니다.<br>");
		out.println("SQLException: " + ex.getMessage());
	}
	out.println(connection);
	int result = 0;
	String sql = "INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())";
	try {
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, name);
		preparedStatement.setString(4, gender);
		preparedStatement.setString(5, birth);
		preparedStatement.setString(6, mail);
		preparedStatement.setString(7, phone);
		preparedStatement.setString(8, address);
		result = preparedStatement.executeUpdate();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	finally {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}



	if (result >= 1) {
		response.sendRedirect("resultMember.jsp?msg=1");
	}
%>
