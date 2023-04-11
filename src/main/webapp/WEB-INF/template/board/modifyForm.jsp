<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Board</title>
</head>
<body>
<script type="text/javascript">
	if (${sessionMemberId == null}) {
		alert('로그인 해주세요.');
		location.href = '/member/login';
	}

	if (${sessionMemberId != board.id}) {
		alert('본인이 작성한 글만 수정할수 있습니다.');
		history.back();
	}
</script>
	<jsp:include page="/inc/menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">게시판</h1>
		</div>
	</div>

	<div class="container">
		<form name="frmUpdate" action="/modifyAction.do" class="form-horizontal" method="post">
			<input type="hidden" name="num" value="${board.num}">
			<input type="hidden" name="page" value="${page}>">
			<div class="form-group row">
				<label class="col-sm-2 control-label">성명</label>
				<div class="col-sm-3">
					<input name="name" class="form-control"	value="${board.name}">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">제목</label>
				<div class="col-sm-5">
					<input name="subject" class="form-control" value="${board.subject}" >
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label">내용</label>
				<div class="col-sm-8" style="word-break: break-all;">
					<textarea name="content" class="form-control" cols="50" rows="5">${board.content}</textarea>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10 ">
					<c:if test="${sessionMemberId == board.id}">
						<p><input type="submit" class="btn btn-success" value="수정"></p>
					</c:if>
					<a href="./list.do?page=${page}" class="btn btn-primary">목록</a>
				</div>
			</div>
		</form>
		<hr>
	</div>
	<jsp:include page="/inc/footer.jsp" />
</body>
</html>


