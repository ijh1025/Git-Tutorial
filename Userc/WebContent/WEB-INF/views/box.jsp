<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
  <%
   	String userID=null;
  	if(session.getAttribute("userID")!=null){
  		userID=(String)session.getAttribute("userID");
  	}
  	if(userID==null){
  		session.setAttribute("messageType","���� �޽���");
  		session.setAttribute("messageContent","���� �α����� �Ǿ����� �ʽ��ϴ�");
  		response.sendRedirect("login.do");
  		return;
  	}
  %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content="width=device-width, inital-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP Ajax �ǽð� ȸ���� ä�� ����</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
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
function chatBoxFunction() {
	var userID='<%=userID%>';
	$.ajax({
		url: "chatBox.do",
		type: "POST",
		data: {userID : userID},
		success: chatBoxListHTML,
		error : function(){alert("error");}
	});
}
function chatBoxListHTML(data) {
	var userID='<%=userID%>';
	if(data=="")return;
	$('#boxTable').html('');
	var result=JSON.parse(data.split("#")[0]);
	for(var i=0;i<result.length;i++){
		if(result[i].fromID==userID){
			result[i].fromID=result[i].toID;
		}else{
			result[i].toID=result[i].fromID;
		}
		addBox(result[i].fromID,result[i].toID,result[i].chatContent,result[i].chatTime);
	}
}
function addBox(lastID,toID,chatContent,chatTime) {
	$("#boxTable").append('<tr onclick="location.href=\'chat.do?toID='+toID+'\'">'+
			'<td style="width:150px;"><h5>'+lastID+'</h5></td>'+
			'<td>'+
			'<h5>'+ chatContent + '</h5>' +
			'<div class="pull-right">'+ chatTime + '</div>'+
			'</td>'+
			'</tr>')
	
}
function getInfiniteBox() {
	setInterval(function() {
		chatBoxFunction();
	},3000);
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
  <a class="navbar-brand" href="index.jsp">�ǽð� ȸ���� ä�� ����</a>
  </div>
  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
  <ul class="nav navbar-nav">
  <li><a href="index.jsp">����</a>
  <li><a href="find.do">ģ��ã��</a></li>
  <li class="active"><a href="box.do">�޼�����<span id="unread" class="label label-info"></span></a></li>
  <li><a href="boardView.do">�����Խ���</a></li>
  
  </ul>
  
  <ul class="nav navbar-nav navbar-right">
  <li class="dropdown">
  <a href="#" class="dropdown-toggle"
  data-toggle="dropdown" role="button" aria-haspopup="true"
  aria-expanded="false">ȸ������<span class="caret"></span>
  </a>
  <ul class="dropdown-menu">
  <li><a href="update.do">ȸ����������</a><li>
  <li><a href="profileUpdate.do">������ ����</a><li>
  <li><a href="logout.do">�α׾ƿ�</a><li>
  </ul>
 </li>
 </ul>

  </div>
  </nav>
  <div class="container">
  <table class="table" style="margin: 0 auto;">
  <thead>
  <tr>
  <td><h4>�ְ���� �޽��� ���</h4></td>
  </tr>
  </thead>
  <div style="overflow: auto; width: 100%; max-height: 450px;">
  <table class="table table-bordered table-hover"
  style="text-align: center; border: 1px solid #dddddd; margin: : 0 auto;">
  <tbody id="boxTable">
  </tbody>
  </table>
  </div>
  </table>
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
  	<div class="modal-content <% if(messageType.equals("���� �޽���")) out.println("panel-warning"); else out.println("panel-success");%>">
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
  	<button type="button" class="btn btn-primary" data-dismiss="modal">Ȯ��</button>
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
    <%
  if (userID !=null){
  
  %>
  <script type="text/javascript">
  $(document).ready(function(){
	 getUnread();
	 getInfiniteUnread();
	 chatBoxFunction();
	 getInfiniteBox();
  });
  </script>
  <% } %>
</body>
</html>