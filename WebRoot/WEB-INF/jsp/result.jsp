<%@ page import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/init.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/search-result.css">
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
		function changePage(target){
			var hidden = document.getElementById("hidden");
			hidden.value=target;
			$('form[id=qianshou]').attr('action','${pageContext.request.contextPath}/WaifuServlet' );
			$('form[id=qianshou]').submit();
		}
	</script>
</head>
<body>
	<!-- 上方的搜索框 -->
	<div class="container">
		
		<!-- Waifu Logo -->
		<img src="${pageContext.request.contextPath}/images/waifu.png" alt="waifu" width="110" height="50" id="logo"/>
			
		
		<form id="qianshou" action='${pageContext.request.contextPath}/WaifuServlet' method="post">
			<div class="search-wrapper active">
				
				<!-- 输入框 -->
				<div class="input-holder">

					<!-- 输入框 -->
					<input type="text" class="search-input" name="wd"
						placeholder="WAIFU一下就知道" autocomplete="off" value="${wd}" />
			
					<!-- 搜索小图标 -->
					<button class="search-icon" >
						<span></span>
					</button>
					
					<!-- 隐藏域：当前的页号 -->
					<input id="hidden" type="hidden" name='currentPage' value="1"/>
					
				</div>
				
				<!-- 输入框下的单选 -->
				<div class="wrapper">
					<c:choose>
					     <c:when test="${pageBean.sortType eq'time'}">
							<div class="sele"><input type="radio" name="sortType" value="relevant"/>相关</div>
					     	<div class="sele"><input type="radio" name="sortType" value="time" checked="checked"/>最新</div>
				        	<div class="sele"><input type="radio" name="sortType" value="click"/>最热</div>
					     </c:when>
					     <c:when test="${pageBean.sortType eq 'click'}">
							<div class="sele"><input type="radio" name="sortType" value="relevant"/>相关</div>
					     	<div class="sele"><input type="radio" name="sortType" value="time" />最新</div>
				        	<div class="sele"><input type="radio" name="sortType" value="click" checked="checked"/>最热</div>
					     </c:when>
					     <c:otherwise>
							<div class="sele"><input type="radio" name="sortType" value="relevant" checked="checked"/>相关</div>
					        <div class="sele"><input type="radio" name="sortType" value="time" />最新</div>
				        	<div class="sele"><input type="radio" name="sortType" value="click" />最热</div>
					     </c:otherwise>
					</c:choose>
		        	
				</div>
			</div>
			
		</form>
	</div>


	<div id="container">
	
		<!-- 每条新闻 -->
		<c:forEach items="${pageBean.context}" var="doc">
			<div class="result_item">
				
				<!-- 新闻标题 -->
				<h3 class="t">
					<a href="${doc.url}" target="_blank">${doc.title}</a>
				</h3>
				
				<!-- 发布时间，点击量 -->
				<p>1条新闻 - 发布时间:${doc.date} - 点击量：${doc.clickTimes}</p>
				
				<!-- 摘要 -->
				<div class="text">${doc.content}</div>
				<div>
					
					<!-- url 、 快照 -->
					<span class="url">${doc.url}</span>--<span class="kuaizhao">WAIFU快照</span>
				</div>
			</div>
		</c:forEach>

		<!-- 分页 -->
		
		<span id="page">
			<!-- 共n页、 当前第m页 --> 
			共[${pageBean.totalPage}]页,当前第[${pageBean.currentPage}]页 
			
			<!-- 上一页 -->
			<a	href="javascript:void(0)" onclick="changePage(${pageBean.previousPage})">上一页</a>
			
			<!-- 页号选项 --> 
			<c:forEach items="${pageBean.p}" var="pp">
				<c:if test="${pp==pageBean.currentPage}">
					<font color="red">${pp}</font>
				</c:if>
				<c:if test="${pp!=pageBean.currentPage}">
					<a href="javascript:void(0)" onclick="changePage(${pp})">${pp}</a>
				</c:if>
			</c:forEach>
			
			<!-- 下一页 --> 
			<a href="javascript:void(0)" onclick="changePage(${pageBean.nextPage})">下一页</a>
			
			<!-- 留白 -->
			<br/>
			<br/>
			<br/>
			<br/>
		</span>
	</div>
</body>
</html>