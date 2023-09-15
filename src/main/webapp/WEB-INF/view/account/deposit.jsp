<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="/WEB-INF/view/layout/header.jsp"%>


<div class="col-sm-8">
	<h2>입금 페이지(인증)</h2>
	<h5>어서오세요 환영합니다.</h5>
	<div class="bg-light p-md-5 h-75">
		<div class="form-group">
			<form action="" method="post">
				<div class="form-group">
					<label for="amount">입금 금액</label>
					<input type="text" id="amount" class="form-control" placeholder="입금 금액을 입력하세요" name="amount">
				</div>
				<div class="form-group">
					<label for="dAccountNumber">입금 계좌번호</label>
					<input type="text" id="dAccountNumber" class="form-control" placeholder="입금 계좌번호를 입력하세요" name="dAccountNumber">
				</div>
				<button type="submit" class="btn btn-primary">입금</button>
			</form>
		</div>
	</div>
	
	</div>
	</div>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>