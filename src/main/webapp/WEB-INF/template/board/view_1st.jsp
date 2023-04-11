<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Board</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body>
	<jsp:include page="/inc/menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">게시판</h1>
		</div>
	</div>

	<div class="container">
		<div class="form-group row">
			<label class="col-sm-2 control-label">성명</label>
			<div class="col-sm-3">${board.name}</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">제목</label>
			<div class="col-sm-5">${board.subject}</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">내용</label>
			<div class="col-sm-8" style="word-break: break-all;">
				${board.content}
			</div>
		</div>
		<div class="form-group row">
			<div class="col-sm-offset-2 col-sm-10">
				<c:if test="${sessionMemberId == board.id}">
					<p><span class="btn btn-danger" onclick="goDelete();">삭제</span>
						<span class="btn btn-success" onclick="goUpdate();">수정</span></p>
				</c:if>
				<a href="/list.do?page=${page}" class="btn btn-primary">목록</a>
			</div>
		</div>
		<form name="frmUpdate" method="post">
			<input type="hidden" name="num" value="${num}">
			<input type="hidden" name="page" value="${page}">
		</form>
		<script>
			let goUpdate = function () {
				const frm = document.frmUpdate;
				frm.action = "/modifyForm.do";
				frm.submit();
			}
			let goDelete = function () {
				if (confirm("삭제하시겠습니까?")) {
					const frm = document.frmUpdate;
					frm.action = "/removeAction.do";
					frm.submit();
				}
			}
		</script>
	<hr>
	</div>
	<jsp:include page="/inc/footer.jsp" />
</body>
</html>


