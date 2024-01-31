<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- header.jsp -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<!-- 여기는 메인 영역 -->
<div class="col-sm-8">
	<h2>나의 계좌 목록</h2>
	<h5>어서 오세요!</h5>

	<div class="bg-light">
		<!-- 만약 accountList null or not null 체크 -->
		<c:choose>
			<c:when test="${accountList != null }">
				<table class="table">
					<thead>
						<tr>
							<th>계좌 번호</th>
							<th>계좌 잔액</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${accountList }" var="account">
							<tr>
								<td>${account.number }</td>
								<td>${account.formatBalance() }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p>아직 생성된 계좌가 없습니다.</p>
			</c:otherwise>
		</c:choose>
	</div>
	<br>
	<br>
</div>
</div>
</div>
<!-- 여기는 메인 영역 -->
<!-- footer.jsp -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>