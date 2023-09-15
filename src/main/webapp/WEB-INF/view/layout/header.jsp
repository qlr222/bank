<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap 4 Website Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="jumbotron text-center banner--imge" style="margin-bottom:0">
  <h1>Tencoding Bank</h1> 
  <img alt="banner" src="https://picsum.photos/seed/picsum/200/200">
</div>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <a class="navbar-brand" href="#">MENU</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="/main-page">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/user/sign-in">Sign In</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/user/sign-up">Sign Up</a>
      </li>    
    </ul>
  </div>  
</nav>

<div class="container" style="margin-top:30px">
  <div class="row">
    <div class="col-sm-4">
      <h2>About Me</h2>
      <h5>Photo of me:</h5>
      <div class="m--profile"></div>
      <p>tencoding bank app</p>
      <h3>Some Links</h3>
      <p>Lorem ipsum dolor sit ame.</p>
      <ul class="nav nav-pills flex-column">
      	<li class="nav-item">
          <a class="nav-link" href="/account/save">계좌 생성</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/account/list">계좌 목록</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/account/deposit">입금</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/account/withdraw">출금</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/account/transfer">이체</a>
        </li>
        
      </ul>
      <hr class="d-sm-none">
    </div>
<!-- end of header.jsp -->