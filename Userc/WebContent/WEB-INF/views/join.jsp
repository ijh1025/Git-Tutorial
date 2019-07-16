<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content="width=device-width, inital-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP Ajax �ǽð� ȸ���� ä�� ����</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
function registerCheckFn(){
	var userID=$('#userID').val();
	$.ajax({
		url:"userRegisterCheck.do",
		type:"POST",
		data :{userID : userID},
		success : function(result) {
			if(result==1){
				$('#checkMessage').html("����Ҽ��ִ� ���̵��Դϴ�.");
				$('#checkType').attr('class','modal-content panel-success');
			}else{
				$('#checkMessage').html("����Ҽ����� ���̵��Դϴ�.");
				$('#checkType').attr('class','modal-content panel-warning');
			}
			$('#checkModal').modal("show");
		}
	});
}
function passwordCheckFn(){
	var userPassword1=$('#userPassword1').val();
	var userPassword2=$('#userPassword2').val();
	if(userPassword1 != userPassword2){
		$('#passwordCheckMessage').html('��й�ȣ�� ���� ��ġ ���� �ʽ��ϴ�');
	}else{
		$('#passwordCheckMessage').html('');
	}
}
</script>
</head>
<body>
  <%
   	String userID=null;
  	if(session.getAttribute("userID")!=null){
  		userID=(String)session.getAttribute("userID");
  	}  
  	if(userID!=null){
  		session.setAttribute("messageType","���� �޽���");
  		session.setAttribute("messageContent", "���� �α����� �Ǿ� �ֽ��ϴ�.");
  		response.sendRedirect("index.jsp");
  		return;
  	}
  %>
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
  <li><a href="boardView.do">�����Խ���</a></li>
  </ul>
  <%
  	if(userID==null){
  %>
  <ul class="nav navbar-nav navbar-right">
  <li class="dropdown">
  <a href="#" class="dropdown-toggle"
  data-toggle="dropdown" role="button" aria-haspopup="true"
  aria-expanded="false">�����ϱ�<span class="caret"></span>
  </a>
  <ul class="dropdown-menu">
  <li><a href="login.do">�α���</a>
  <li class="active"><a href="join.do">ȸ������</a>
  </ul>
  </li>
  </ul>
  <%
  	} %> 
  </div>
  </nav>
  <div class="container">
  <form action="userResigter.do" method="post">
  <table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
  <thead>
  <tr>
  <th colspan="3"><h4>ȸ����Ͼ��</h4></th>
  </tr>
  </thead>
  <tbody>
  <tr>
  <td style="width: 110px"><h5>���̵�</h5></td>
  <td><input class="form-control" type="text" id="userID" name="userID" maxlength="20" placeholder="���̵� �Է��ϼ���"></td>
   <td style="width: 110px"><button class="btn btn-primary" onclick="registerCheckFn()" type="button">�ߺ�üũ</button></td>
  </tr>
  <tr>
  <td style="width: 110px"><h5>��й�ȣ</h5></td>
  <td colspan="2"><input onkeyup="passwordCheckFn()" class="form-control" type="password" id="userPassword1" name="userPassword1" maxlength="20" placeholder="��й�ȣ�� �Է��ϼ���"></td>
  </tr>
  <tr>
  <td style="width: 110px"><h5>��й�ȣ Ȯ��</h5></td>
  <td colspan="2"><input onkeyup="passwordCheckFn()" class="form-control" type="password" id="userPassword2" name="userPassword2" maxlength="20" placeholder="��й�ȣ�� �Է��ϼ���"></td>
  </tr>
  <tr>
  <td style="width: 110px"><h5>�̸�</h5></td>
  <td colspan="2"><input class="form-control" type="text" id="userName" name="userName" maxlength="20" placeholder="�̸��� �Է��ϼ���"></td>
  </tr>
  <tr>
   <td style="width: 110px"><h5>����</h5></td>
  <td colspan="2"><input class="form-control" type="number" id="userAge" name="userAge" maxlength="20" placeholder="���̸� �Է��ϼ���"></td>
  </tr>
  <tr>
   <td style="width: 110px"><h5>����</h5></td>
  <td colspan="2">
  <div class="form-group" style="text-align: center; margin: 0 auto;">
  <div class="btn-group" data-toggle="buttons">
  <label class="btn btn-primary active">
  <input type="radio" name="userGender" autocomplete="off" value="����" checked>����
  </label>
  <label class="btn btn-primary">
  <input type="radio" name="userGender" autocomplete="off" value="����">����
  </label>
  
  </div>
  </div>
  </td>
  </tr>
  <tr>
   <td style="width: 110px"><h5>�̸���</h5></td>
  <td colspan="2"><input class="form-control" type="email" id="userEmail" name="userEmail" maxlength="20" placeholder="�̸����� �Է��ϼ���"></td>
  </tr>
  <tr>
  <td style="text-align: left;" colspan="3">
  <h5 style="color: red;" id="passwordCheckMessage"></h5>
  <input class="btn btn-primary pull-right" type="submit" value="���"></td>
  </tr>
  
  </tbody>
  </table>
  </form>
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