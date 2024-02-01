<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- header.jsp -->
	<%@ include file="/WEB-INF/view/layout/header.jsp" %>
    <!-- 여기는 메인 영역 -->
	<div class="col-sm-8">
		<div class="bg-light p-md-5">
			<h2>계좌 상세 보기</h2>
			<h5>어서 오세요!</h5>
			<br><br>
			<div class="title-main">
				<div class="title-box1">
					<div>${principal.username } 님의 계좌</div>
					<div>계좌 번호 : ${account.number }</div>
					<div>잔액 : ${account.formatBalance() }</div>
				</div>
				<div class="title-box2">
					<div>
						<a class="text-blue" href="/account/detail/${account.id }">전체 조회</a>
					</div>
					<div>
						<a class="text-blue" href="/account/detail/${account.id }?type=deposit">입금 조회</a>
					</div>
					<div>
						<a class="text-blue" href="/account/detail/${account.id }?type=withdraw">출금 조회</a>
					</div>
				</div>
			</div>
			<table>
				<tr>
					<th>날짜</th>
					<th>보낸이</th>
					<th>받은이</th>
					<th>입출금 금액</th>
					<th>계좌 잔액</th>
				</tr>
				<c:forEach var="history" items="${historyList }">
					<tr>
						<td>${history.formatCreatedAt() }</td>
						<td>${history.sender }</td>
						<td>${history.receiver }</td>
						<td>${history.amount }</td>
						<td>${history.formatBalance() }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
  </div>
</div>
    <!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
	<%@ include file="/WEB-INF/view/layout/footer.jsp" %>