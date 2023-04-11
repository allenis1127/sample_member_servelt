<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
//	BoardDTO board = (BoardDTO) request.getAttribute("board");
//	int num = ((Integer) request.getAttribute("num")).intValue();
//	int pageNum = ((Integer) request.getAttribute("page")).intValue();
%>
<html>
<head>
<title>Board</title>
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
		<!-- 리플 목록 -->
		<!-- 리플 목록 출력 영역 -->
		<div class="form-group row user-repple-list">
			<ul>

			</ul>
		</div>
		<!--// 리플 목록 출력 영역 -->
		<script>
			const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성

			const getRipples = function () {
				/* 목록을 가지고 옴 */
				let num = document.querySelector('form[name=frmUpdate] input[name=num]');
				xhr.open('GET', '/ripple/get?num=' + num.value);
				xhr.send();
				xhr.onreadystatechange = function () {
					if (xhr.readyState !== XMLHttpRequest.DONE) return;

					if (xhr.status === 200) { //  서버(url)에 문서가 존재함
						console.log(xhr.response);
						const json = JSON.parse(xhr.response);
						for (let data of json) {
							console.log(data);
						}
						insertRippleTag(json);
					} else { //  서버(url)에 문서가 존재하지 않음.
						console.error('Error', xhr.status, xhr.statusText);
					}
				}
			}
			const insertRippleTag = function (items) {
				/* 목록에 요소를 추가. 처음 로딩시 목록을 출력하거나, 새로운 글 등록시, 삭제시 사용 */
				let tagUl = document.querySelector('.user-repple-list ul');
				tagUl.innerHTML = '';
				for (let item of items) {
					let tagNew = document.createElement('li');
					tagNew.innerHTML = item.content + ' | ' + item.name + ' | ' + item.insertDate;
					if (item.isLogin === true) {
						tagNew.innerHTML +=
								' <span class="btn btn-danger" onclick="goRippleDelete(\'' + item.rippleId + '\');">>삭제</span>';
					}
					tagNew.setAttribute('class', 'list-group-item');
					tagUl.append(tagNew);
				}
			}
			const goRippleDelete = function(rippleId) {
				if (confirm("삭제하시겠습니까?")) {
					const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성
					//xhr.open('POST', '../board/ajax_delete.jsp?rippleId=' + ID);
					xhr.open('POST', '/ripple/delete?rippleId=' + rippleId);
					xhr.send();
					xhr.onreadystatechange = () => {
						if (xhr.readyState !== XMLHttpRequest.DONE) return;

						if (xhr.status === 200) { //  서버(url)에 문서가 존재함
							console.log(xhr.response);
							const json = JSON.parse(xhr.response);
							if (json.result === 'true') {
								getRipples();
							}
							else  {
								alert("삭제에 실패했습니다.");
							}
						}
						else { //  서버(url)에 문서가 존재하지 않음.
							console.error('Error', xhr.status, xhr.statusText);
						}
					}
				}
			}
			document.addEventListener("DOMContentLoaded", function () {
				getRipples();
			});
		</script>
		<!--// 리플 목록 -->
		<!-- 리플 등록, 로그인 상태에서만 나옴. -->
		<hr>
		<c:if test="${sessionMemberId != null}">
			<form name="frmRipple" class="form-horizontal" method="post">
				<input type="hidden" name="num" value="${board.num}">
				<div class="form-group row">
					<label class="col-sm-2 control-label" >성명</label>
					<div class="col-sm-3">
						<input name="name" type="text" class="form-control" value="${sessionMemberName}" placeholder="name">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-2 control-label">내용</label>
					<div class="col-sm-8">
						<textarea name="content" class="form-control" cols="50" rows="3"></textarea>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-3">
						<span class="btn btn-primary" name="goRippleSubmit">등록</span>
					</div>
				</div>
			</form>
			<script>
				document.addEventListener("DOMContentLoaded", function () {
					const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성

					document.querySelector('span[name=goRippleSubmit]').addEventListener('click', function () {
						/* 등록 버튼 클릭 시 */
                        // 값을 들고옴
						let num = document.querySelector('input[name=num]');
						let name = document.querySelector('input[name=name]');
						let content = document.querySelector('textarea[name=content]');

						xhr.open('POST', '/ripple/add?num=' + num.value + '&name=' + name.value + '&content=' + content.value);
						xhr.send();
						xhr.onreadystatechange = () => {
							if (xhr.readyState !== XMLHttpRequest.DONE) return;

							if (xhr.status === 200) { //  서버(url)에 문서가 존재함
								console.log(xhr.response);
								const json = JSON.parse(xhr.response);
								if (json.result === 'true') {
									content.value = ''; // input 태그에 입력된 값 삭제.
									getRipples();
								}
								else  {
									alert("등록에 실패했습니다.");
								}
							}
							else { //  서버(url)에 문서가 존재하지 않음.
								console.error('Error', xhr.status, xhr.statusText);
							}
						}
					});

				});
			</script>
		</c:if>
		<!--// 리플 등록-->


		<form name="frmRippleDelete" class="form-horizontal" method="post">
			<input type="hidden" name="num" value="{board.num}">
			<input type="hidden" name="pageNum" value="${nowPageNum}">
			<input type="hidden" name="rippleId">

		</form>
		<div class="form-group row">
		<div class="col-sm-offset-2 col-sm-10">
			<c:if test="${sessionMemberId == board.id}">
				<p>
					<span class="btn btn-danger" onclick="goDelete();">삭제</span>
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


