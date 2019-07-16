<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%
   	String userID=null;
  	if(session.getAttribute("userID")!=null){
  		userID=(String)session.getAttribute("userID");
  	} 
  	String toID=null;
  	if(request.getParameter("toID")!=null){
  		toID=(String)request.getParameter("toID");
  	}
  	if(userID==null){session.setAttribute("messageType", "오류 메세지");
  					 session.setAttribute("messageContent", "현재 로그인 되어 있지 않습니다");
  					 response.sendRedirect("index.jps");
  					 return;
  	}
  	if(toID==null){session.setAttribute("messageType", "오류 메세지");
		 session.setAttribute("messageContent", "대화 상대가 지정 되어 있지 않습니다");
		 response.sendRedirect("index.jsp");
		 return;}
  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content="width=device-width, inital-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP Ajax 실시간 회원제 채팅 서비스</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
 function autoCloseingAlert(selector,delay) {
	var alert=$(selector).alert();
	alert.show();
	window.setTimeout(function() { alert.hide();},delay);
}
 function submitFunction(){
	 var fromID='<%=userID%>';
	 var toID='<%=toID%>';
	 var chatContent=$('#chatContent').val();
	 $.ajax({
		 url:"chatSubmit.do",
		 type:"POST",
		 data:{fromID : fromID,toID : toID,chatContent : chatContent},
		 success:function(result){
			if(result==1){
				autoCloseingAlert('#successMessage',2000);
			}else if(result==0){
				autoCloseingAlert('#dangerMessage',2000);
			}else{
				autoCloseingAlert('#waringMessage',2000);
			}
		}		 
	 });
	 $('#chatContent').val('');
 }
var lastID=0;// ?
function chatListFunction(type) { //ten
	
	var fromID='<%=userID%>';
	var toID='<%=toID%>';
	$.ajax({
		url:"chatList.do",
		type:"POST",
		data:{fromID:fromID, toID:toID, listType:type},
		success: chatListHTML,
		error : function(){alert("error");}
	});
}
function chatListHTML(data) {
	
	if(data=="") return;
	var chatList=JSON.parse(data.split("#")[0]);
	$.each(chatList, function(index,chatvo){
		if(chatvo.fromID=='<%=userID%>'){
			
		
		$('#chatList').append('<div class="row">'+
		  '<div class="col-lg-12">'+
		  '<div class="media">'+
		  '<a class="pull left" href="#">'+
		  '<img class="media-object img-circle" style="width:30px;height:30px;" src="${fromProfile}" alt="">'+
		  '</a>'+
		  '<div class="media-body">'+
		  '<h4 class="media-heading">'+
		  chatvo.fromID +
		  '<span class="small pull-right">'+
		  chatvo.chatTime +
		  '</span>' +
		  '</h4>' +
		  '<p>' +
		  chatvo.chatContent +
		  '</p>' +
		  '</div>' +
		  '</div>' +
		  '</div>' +
		  '</div>' +
		  '<hr>');
	}else{$('#chatList').append('<div class="row">'+
			  '<div class="col-lg-12">'+
			  '<div class="media">'+
			  '<a class="pull left" href="#">'+
			  '<img class="media-object img-circle" style="width:30px;height:30px;" src="${toProfile}" alt="">'+
			  '</a>'+
			  '<div class="media-body">'+
			  '<h4 class="media-heading">'+
			  chatvo.fromID +
			  '<span class="small pull-right">'+
			  chatvo.chatTime +
			  '</span>' +
			  '</h4>' +
			  '<p>' +
			  chatvo.chatContent +
			  '</p>' +
			  '</div>' +
			  '</div>' +
			  '</div>' +
			  '</div>' +
			  '<hr>');}
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	});
	lastID=Number(data.split("#")[1]);
}
function getInfiniteChat() {
	setInterval(function() {
		chatListFunction(lastID);
	},3000);
}
function getUnread() {
	$.ajax({
		url: "chatUnread.do",
		type: "POST",
		data: {userID : '<%=userID%>'},
		success:function(result){
			if(result>=1){
				showUnread(result);
			}else{
				showUnread('');
			}
		}
		
	});
}
function showUnread(result) {
	$('#unread').html(result);
}
function getInfiniteUnread(){
	setInterval(function(){
		getUnread();
	},4000);
}
</script>
</head>
<body>
  
  <nav class="navbar navbar-default">
  <div class="navbar-header">
  <button type="button" class="navbar-toggle collapsed"
  	data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
  	aria-expanded="false">
  <span class="icon-bar"></span>
  <span class="icon-bar"></span>
  <span class="icon-bar"></span>
  </button>
  <a class="navbar-brand" href="index.jsp">실시간 회원제 채팅 서비스</a>
  </div>
  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
  <ul class="nav navbar-nav">
  <li><a href="index.jsp">메인</a>
  <li><a href="find.do">친구찾기</a></li>
  <li><a href="box.do">메세지함<span id="unread" class="label label-info"></span></a></li>
  <li><a href="boardView.do">자유게시판</a></li>
  
  </ul>
  <%
  	if(userID!=null){
  %>
  <ul class="nav navbar-nav navbar-right">
  <li class="dropdown">
  <a href="#" class="dropdown-toggle"
  data-toggle="dropdown" role="button" aria-haspopup="true"
  aria-expanded="false">회원관리<span class="caret"></span>
  </a>
  <ul class="dropdown-menu">
  <li><a href="update.do">회원정보수정</a><li>
  <li><a href="profileUpdate.do">프로필 수정</a><li>
  <li><a href="logout.do">로그아웃</a><li>
  </ul>
 </li>
 </ul>
 <% } %> 
  </div>
  </nav>
  <div class="container bootstrap snippet">
  <div class="row">
  <div class="col-xs-12">
  <div class="portlet portlet-default">
  <div class="portlet-heading">
  <div class="portlet-title">
  <h4><i class="fa fa-circle text-green"></i>실시간 채팅창</h4>
  </div>
  <div class="clearfix"></div>
  </div>
  <div id="chat" class="panel-collapse collapse in">
  <div id="chatList" class="portlet-body chat-widget"
  style="overflow: auto; width: auto; height: 600px;">
  </div>
  <div class="portlet-footer">
  <div class="row" style="height: 90px;">
<div class="form-group col-xs-10">
<textarea style="height: 80px" id="chatContent" class="form-control"
placeholder="메시지를 입력하세요" maxlength="100">
</textarea>
</div>
<div class="form-group col-xs-2">
<button type="button" class="btn btn-default pull-right" onclick="submitFunction()">전송</button>
<div class="clearfix"></div>
</div>  
  </div>
  </div>
  </div>
  </div>
  </div>
  </div>
  </div>
  <div class="alert alert-success" id="successMessage" style="display: none;">
  <strong>메시지 전송에 성공했습니다.</strong>
  </div>
  <div class="alert alert-danger" id="dangerMessage" style="display: none;">
  <strong>이름과 내용을 모두 입력해주세요.</strong>
  </div>
  <div class="alert alert-waring" id="waringMessage" style="display: none;">
  <strong>데이터베이스 오류가 발생했습니다.</strong>
  </div>
    <%
  	String messageContent=null;
  	if(session.getAttribute("messageContent")!=null){
  		messageContent=(String)session.getAttribute("messageContent");
  		
  	}
  	String messageType=null;
  	if(session.getAttribute("messageType")!=null){
  		messageType=(String)session.getAttribute("messageType");
  	}
  	
  	if(messageContent !=null){
  %>
  <div class="modal fade" id="messageModal" tabindex="-1"role="dialog" aria-hidden= "true">
  	<div class="modal-diaLog vertical-align-center">
  	<div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success");%>">
  	<div class="modal-header panel-heading">
  	<button type="button" class="close" data-dismiss="modal">
  	<span aria-hidden="true">&times</span>
  	<span class="sr-only">Close</span>
  	</button>
  	<h4 class="modal-title">
  	   <%=messageType %>
  	</h4>
  	</div>
  	<div class="modal-body">
  	   <%=messageContent %>
  	</div>
  	<div class="modal-footer">
  	<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
  	</div>
  	</div>
  	</div>
  </div>
  <script>
  $('#messageModal').modal("show");
  </script>
  <%
  session.removeAttribute("messageContent");
  session.removeAttribute("messageType");
  	} %>
<script type="text/javascript">
$(document).ready(function() {
	getUnread();
	chatListFunction("0");
	getInfiniteChat();
	getInfiniteUnread();
});

</script>

</body>
</html>