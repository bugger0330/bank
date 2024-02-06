<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<h2>이메일 인증</h2>
		<h5>어서 오세요!</h5>
		<br><br>
		<div>
			<div class="input-box">
				<div class="title">이메일주소</div>
				<div>
					<input type="text" class="email" value="bugger0330@naver.com">
					<button type="button" class="btn1">이메일 인증</button>
				</div>
			</div>
			<div class="input-box">
				<div class="title">인증번호</div>
				<div>
					<input type="text" class="email-num">
					<button type="button" class="btn2">번호 인증</button>
				</div>
			</div>
			<div class="input-box">
				<div class="title">비밀번호</div>
				<div>
					<input type="password" class="password">
				</div>
			</div>
			<button type="button" class="btn3">회원가입</button>
		</div>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>
	
	
	<!-- 숙제 -->
	<!-- 이벤트 전파 속성 - 버블링이 뭔지, 캡처링이 뭔지 -->
<script src="/js/signup2.js"></script>