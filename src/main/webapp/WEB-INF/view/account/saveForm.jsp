<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<h2>계좌 생성 페이지</h2>
		<h5>어서 오세요!</h5>
		<br><br>
		<form action="/account/save" method="post">
		  <div class="form-group">
		    <label for="number">계좌번호</label>
		    <input type="text" class="form-control" placeholder="계좌번호를 입력하세요!" id="number" name="number" value="1000">
		  </div>
		  <div class="form-group">
		    <label for="password">계좌 비밀번호</label>
		    <input type="password" class="form-control" placeholder="비밀번호를 입력하세요!" id="password" name="password" value="1234">
		  </div>
		  <div class="form-group">
		    <label for="balance">입금 금액</label>
		    <input type="text" class="form-control" placeholder="입금 금액를 입력하세요!" id="balance" name="balance" value="100">
		  </div>
		  <button type="submit" class="btn btn-primary">계좌생성</button>
		</form>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>