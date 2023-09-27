<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<!-- start main.jsp -->
<div class="col-sm-8">
	<h2>로그인 페이지</h2>
	<h5>어서오세요 환영합니다.</h5>
	<div class="bg-light p-md-5 h-75">
	<!-- 로그인은 보안 때문에 예외적으로 POST 방식을 활용한다 -->
		<div class="form-group">
			<form action="/user/sign-in" method="post">
				<div class="form-group">
					<label for="username">username</label>
					<input class="form-control" type="text" id="username" name="username" placeholder="Enter username" value="길동">
				</div>
				<div class="form-group">
					<label for="pwd">password</label>
					<input class="form-control" type="password" id="pwd" name="password" placeholder="Enter password" value="1234">
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
				<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=eceda66395de20a268098856518bee51&redirect_uri=http://localhost/user/kakao/callback"><img alt="카카오" src="/images/kakao_login_medium.png" width="76.27px" height="38.21px"></a>
			</form>
		</div>
	</div>
	
	
	
	</div>
	</div>
</div>
<!-- end main.jsp -->


<!-- footer.jsp -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
