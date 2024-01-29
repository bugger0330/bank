<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<h2>회원 가입</h2>
		<h5>어서 오세요!</h5>
		<br><br>
		<form action="/user/sign-up" method="post">
		  <div class="form-group">
		    <label for="username">아이디</label>
		    <input type="text" class="form-control" placeholder="아이디를 입력하세요!" id="username" name="username">
		  </div>
		  <div class="form-group">
		    <label for="password">비밀번호</label>
		    <input type="password" class="form-control" placeholder="비밀번호를 입력하세요!" id="password" name="password">
		  </div>
		  <div class="form-group">
		    <label for="fullname">이름</label>
		    <input type="text" class="form-control" placeholder="이름을 입력하세요!" id="fullname" name="fullname">
		  </div>
		  <button type="submit" class="btn btn-primary">회원가입</button>
		</form>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>
	
	
	<!-- 숙제 -->
	<!-- 이벤트 전파 속성 - 버블링이 뭔지, 캡처링이 뭔지 -->