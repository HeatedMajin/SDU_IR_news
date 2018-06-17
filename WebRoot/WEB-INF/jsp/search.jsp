<%@ page import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/search-form.css">
	
</head>
<body>
	<section class="container">
		<!-- WaiFu logo -->
		<div>
			<img src="${pageContext.request.contextPath}/images/waifu.png" alt="waifu" width="240" height="90" id="logo"></img>
		</div>
		
		<!-- 输入框 -->
		<form id="qianshou" action="${pageContext.request.contextPath}/WaifuServlet" method="post" >
            <div class="search-wrapper">
                <div class="input-holder">
                	<!-- 输入框 -->
                    <input type="text" class="search-input" name="wd" placeholder="WAIFU一下，你就找到" autocomplete="off"/>
                    
                    <!-- 搜索按钮 -->
                    <button class="search-icon" onclick="searchToggle(this, event);" ><span></span></button>
                </div>
                
                <!-- 关闭按钮 -->
                <span class="close" onclick="searchToggle(this, event);" ></span>
            </div>
        </form>
	</section>
	
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
        function searchToggle(obj, evt){
            var container = $(obj).closest('.search-wrapper');

            if(!container.hasClass('active')){
                  container.addClass('active');
                  evt.preventDefault();
            }
            else if(container.hasClass('active') && $(obj).closest('.input-holder').length == 0){
                  container.removeClass('active');
                  // clear input
                  container.find('.search-input').val('');
                  // clear and hide result container when we press close
                  container.find('.result-container').fadeOut(100, function(){$(this).empty();});
            }
		}
	
	</script>
</body>
</html>