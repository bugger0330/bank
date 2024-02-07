<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<div class="bg-light p-md-5">
			<h2>입금 페이지</h2>
			<h5>어서 오세요!</h5>
			<br><br>
			<form action="/account/deposit" method="post">
			  <div class="form-group">
			    <label for="amount">입금 금액</label>
			    <input type="text" class="form-control" placeholder="입금 금액을 입력하세요!" id="amount" name="amount" value="100">
			  </div>
			  <div class="form-group">
			    <label for="dAccountNumber">입금 계좌번호</label>
			    <input type="text" class="form-control" placeholder="입금 계좌번호를 입력하세요!" id="dAccountNumber" name="dAccountNumber" value="1000">
			  </div>
			  <button type="submit" class="btn btn-primary">입금</button>
			</form>
		</div>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>