<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.sample_member_servelt.dto.BoardDTO" %>
<%
	String sessionMemberId = (String) session.getAttribute("sessionMemberId");
	List boardList = (List) request.getAttribute("boardlist");
	int totalRecord = ((Integer) request.getAttribute("totalRecord")).intValue();
	int totalPage = ((Integer) request.getAttribute("totalPage")).intValue();
%>
<html>
<head>
	<title>Board</title>
	<script type="text/javascript">
		function checkForm() {
			if (${sessionMemberId == null}) {
				alert("로그인 해주세요.");
				return false;
			}

			location.href = "./addForm.do";
		}
	</script>
</head>
<body>
<jsp:include page="/inc/menu.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">게시판</h1>
	</div>
</div>
<div class="container">
	<form action="/list.do" method="post">
		<div>
			<div class="text-right">
				<span class="badge badge-success">전체 <%=totalRecord%>건	</span>
			</div>
		</div>
		<div style="padding-top: 50px">
			<table class="table table-hover">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>조회</th>
					<th>글쓴이</th>
				</tr>
				<%
					for (int j = 0; j < boardList.size(); j++) {
						BoardDTO list = (BoardDTO) boardList.get(j);
				%>
				<tr>
					<td><%=list.getNum()%></td>
					<td><a href="./view.do?num=<%=list.getNum()%>&page=${page}"><%=list.getSubject()%></a></td>
					<td><%=list.getRegistDay()%></td>
					<td><%=list.getHit()%></td>
					<td><%=list.getName()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
		<div align="center">
			<c:set var="page" value="${page}" />
			<c:forEach var="i" begin="1" end="<%=totalPage%>">
				<a href="<c:url value="./list.do?page=${i}" /> ">
					<c:choose>
						<c:when test="${page == i}">
							<font color='4C5317'><b> [${i}]</b></font>
						</c:when>
						<c:otherwise>
							<font color='4C5317'> [${i}]</font>

						</c:otherwise>
					</c:choose>
				</a>
			</c:forEach>
		</div>
		<div align="left">
			<table>
				<tr>
					<td width="100%" align="left">&nbsp;&nbsp;
						<select name="items" class="txt">
							<option value="subject">제목에서</option>
							<option value="content">본문에서</option>
							<option value="name">글쓴이에서</option>
						</select> <input name="text" type="text" /> <input type="submit" id="btnAdd" class="btn btn-primary " value="검색 " />
					</td>
					<td width="100%" align="right">
						<a href="#" onclick="checkForm(); return false;" class="btn btn-primary">&laquo;글쓰기</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<hr>
</div>
<jsp:include page="/inc/footer.jsp" />
</body>
</html>





