<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<h2>로그인</h2>
		<h5>어서 오세요!</h5>
		<br><br>
		<form action="/user/sign-in" method="post">
		  <div class="form-group">
		    <label for="username">아이디</label>
		    <input type="text" class="form-control" placeholder="아이디를 입력하세요!" id="username" name="username" value="ddd">
		  </div>
		  <div class="form-group">
		    <label for="password">비밀번호</label>
		    <input type="password" class="form-control" placeholder="비밀번호를 입력하세요!" id="password" name="password" value="ddd">
		  </div>
		  <button type="submit" class="btn btn-primary">로그인</button>
		  <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=12a14a8995ef6b6bf5235e1164edaba2&redirect_uri=http://localhost/user/kakao-callback">
		  	<img alt="" src="/images/kakao_login_small.png" style="width:75px;">
		  </a>
		  <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=iTerJzJBbIvU0qMDREvC&state=1234&redirect_uri=http://localhost:80/user/naver-callback">
		  	<img alt="" src="/images/btnG_완성형.png" width="130px" height="38px">
		  </a>
		</form>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>
	
	
	<!-- 숙제 -->
	<!-- 이벤트 전파 속성 - 버블링이 뭔지, 캡처링이 뭔지 -->