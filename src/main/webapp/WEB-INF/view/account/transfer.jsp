<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<div class="bg-light p-md-5">
			<h2>이체 페이지</h2>
			<h5>어서 오세요!</h5>
			<br><br>
			<form action="/account/transfer" method="post">
			  <div class="form-group">
			    <label for="amount">이체 금액</label>
			    <input type="text" class="form-control" placeholder="출금 금액을 입력하세요!" id="amount" name="amount" value="1">
			  </div>
			  <div class="form-group">
			    <label for="wAccountNumber">출금 계좌번호</label>
			    <input type="text" class="form-control" placeholder="출금 계좌번호를 입력하세요!" id="wAccountNumber" name="wAccountNumber" value="1000">
			  </div>
			  <div class="form-group">
			    <label for="dAccountNumber">이체 계좌번호</label>
			    <input type="text" class="form-control" placeholder="출금 계좌번호를 입력하세요!" id="dAccountNumber" name="dAccountNumber" value="1111">
			  </div>
			  <div class="form-group">
			    <label for="password">출금 계좌 비밀번호</label>
			    <input type="password" class="form-control" placeholder="비밀번호를 입력하세요!" id="password" name="password" value="1234">
			  </div>
			  <button type="submit" class="btn btn-primary">이체</button>
			</form>
		</div>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>