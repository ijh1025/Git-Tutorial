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
function findFunction(){
	var userID=$('#findID').val();
	$.ajax({
		url : "userRegisterCheck.do",
		type:"POST",
		data:{userID:userID},
		success: function(result){
			if(result==0){
				$('#checkMessage').html('ģ��ã�⿡ �����߽��ϴ�.');
				$('#checkType').attr('class','modal-content panel-success');
				getFriend(userID);
			}else{
				$('#checkMessage').html('ģ���� ã���� �����ϴ�.');
				$('#checkType').attr('class','modal-content panel-warning');
				failFriend();
			}
			$('#checkModal').modal("show");
		}
	});
}
function getFriend(findID) {
	$('#friendResult').html('<thead>'+
			'<tr>'+
			'<th><h4>�˻� ���</h4></th>'+
			'</tr>'+
			'</thead>'+
			'<tbody>'+
			'<tr>'+
			'<td style="text-align:center;"><h3>'+findID+'</h3>'+
			'<a href="chat.do?toID='+findID+'"class="btn btn-primary pull-right">'+
			'�޽��� ������</a></td>'+
			'</tr>'+
			'</tbody>'
			);
}
function failFriend() {
	$('#friendResult').html('');
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
  <a class="navbar-brand" href="index.jsp">�ǽð� ȸ���� ä�� ����</a>
  </div>
  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
  <ul class="nav navbar-nav">
  <li><a href="index.jsp">����</a>
  <li class="active"><a href="find.do">ģ��ã��</a></li>
   <li><a href="box.do">�޼�����<span id="unread" class="label label-info"></span></a></li>
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
  <table class="table table-bordered table-hover" style="text-align: center; border=1px solid #dddddd;">
  <thead>
  <tr>
  <th colspan="2"><h4>�˻����� ģ�� ã��</h4></th>
  </tr>
  </thead>
  <tbody>
  <tr>
  <td style="width: 110px;"><h5>ģ�� ���̵�</h5></td>
  <td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="ã�� ���̵� �Է��ϼ���"></td>
  </tr>
  <tr>
  <td colspan="2"><button class="btn btn-primary pull-right" onclick="findFunction()">�˻�</button></td>
  </tr>
  </tbody>
  </table>
  </div>
  <div class="container">
  <table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border=1px solid #dddddd;">
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
	 getInfiniteUnread() 
  });
  <% } %>
  </script>
  <div class="modal fade" id="checkModal"tabindex="-1"role="dialog" aria-hidden= "true">
  <div class="modal-diaLog vertical-align-center">
  	<div id="checkType" class="modal-content panel-info">
  	<div class="modal-header panel-heading">
  	<button type="button" class="close" data-dismiss="modal">
  	<span aria-hidden="true">&times</span>
  	<span class="sr-only">Close</span>
  	</button>
  	<h4 class="modal-title">Ȯ�� �޼���</h4>
  	</div>
  	<div id="checkMessage" class="modal-body">
  	</div>
  	<div class="modal-footer">
  	<button type="button" class="btn btn-primary" data-dismiss="modal">Ȯ��</button>
  	</div>
  	</div>
  	</div>
  </div>
</body>
</html>