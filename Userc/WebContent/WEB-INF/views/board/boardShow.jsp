<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="board.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("lineChar", "\r"); %>   
<!DOCTYPE html>
<html>
  <%
       String userID=null;
       if(session.getAttribute("userID") !=null){
    	   userID=(String)session.getAttribute("userID");   
       }  
       if(userID==null){
    	   session.setAttribute("messageType", "���� �޼���");
    	   session.setAttribute("messageContent", "���� �α��� �Ǿ� ���� �ʽ��ϴ�.");
           response.sendRedirect("index.jsp");
           return;
       }

       BoardDTO dto=(BoardDTO)request.getAttribute("board");
       if(dto.getBoardAvailable()!=1){
	       session.setAttribute("messageType", "���� �޼���");
		   session.setAttribute("messageContent", "������ �Խù��Դϴ�.");
	       response.sendRedirect("boardView.do");
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
    function getUnread(){    
     	$.ajax({
    		url : "chatUnread.do",
    		type : "POST",
    		data : { userID : '<%=userID%>' },
    		success : function(result){
    			if(result>=1){
    				showUnread(result);
    			}else{
    				showUnread('');
    			}
    		}    		
    	});
    }    
    function showUnread(result){
    	$('#unread').html(result);
    }
    function getInfiniteUnread(){
    	setInterval(function(){
    		getUnread();    		
    	}, 4000);
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
             <li><a href="box.do">�޼�����<span id="unread" class="label label-info"></span></a></li>
             <li class="active"><a href="boardView.do">�����Խ���</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
           <li class="dropdown">
               <a href="#" class="dropdown-toggle"
                 data-toggle="dropdown" role="button" aria-haspopup="true"
                 aria-expanded="false">ȸ������<span class="caret"></span>
                </a>  
                <ul class="dropdown-menu">
                     <li><a href="update.do">ȸ����������</a></li>
                     <li><a href="profileUpdate.do">������ ����</a></li>
	                 <li><a href="logout.do">�α׾ƿ�</a></li>	                 
                </ul>             
           </li>        
        </ul>      
       </div>
  </nav>
  <div class="container">
      <table class="table table-borderd table-hover"
             style="text-align: center; border: 1px solid #dddddd">
          <thead>
              <tr>
                <th colspan="4"><h4>�Խù� ����</h4></th>
              </tr>
              <tr>
                  <td style="background-color: #fafafa; color:#000000; width: 80px;"><h5>����</h5></td>
                  <td colspan="3"><h5>${board.boardTitle}</h5></td>
              </tr>
              <tr>
                  <td style="background-color: #fafafa; color:#000000; width: 80px;"><h5>�ۼ���</h5></td>
                  <td colspan="3"><h5>${board.userID}</h5></td>
              </tr>
              <tr>
                  <td style="background-color: #fafafa; color:#000000; width: 80px;"><h5>�ۼ���¥</h5></td>
                  <td><h5>${board.boardDate}</h5></td>
                  <td style="background-color: #fafafa; color:#000000; width: 80px;"><h5>��ȸ��</h5></td>
                  <td><h5>${board.boardHit}</h5></td>
              </tr>
              <tr>
                  <td style="vertical-align:middle; min-height:150px; background-color: #fafafa; color:#000000; width: 80px;"><h5>�� ����</h5></td>
                  <td colspan="3" style="text-align: left;"><h5>${fn:replace(board.boardContent,lineChar,"<br/>")}</h5></td>
              </tr>
             <tr>
                  <td style="background-color: #fafafa; color:#000000; width: 80px;"><h5>÷������</h5></td>
                  <td colspan="3"><h5><a href="boardDownload.do?boardID=${board.boardID}">${board.boardFile}</a></h5></td>
              </tr>
          </thead>   
          <tbody>   
             <tr>
               <td colspan="5" style="text-align: right;">
                <a href="boardView.do" 
                    class="btn btn-primary">���</a>
                 <a href="boardReplyForm.do?boardID=${board.boardID}" 
                    class="btn btn-primary">�亯</a> 
                <c:if test="${userID==board.userID}">    
                 <a href="boardUpdateForm.do?boardID=${board.boardID}" 
                    class="btn btn-primary">����</a>
                 <a href="boardDelete.do?boardID=${board.boardID}" 
                    class="btn btn-primary" onclick="return confirm('������ ���� �Ͻðڽ��ϱ�?');">����</a> 
                </c:if>                       
               </td>
             </tr>
          </tbody>
      </table>
  </div>
 <%
      String messageContent=null;
      if(session.getAttribute("messageContent") !=null){
    	  messageContent=(String)session.getAttribute("messageContent"); 
      }
      String messageType=null;
      if(session.getAttribute("messageType") !=null){
    	  messageType=(String)session.getAttribute("messageType"); 
      }
      if(messageContent !=null) {
  %>
   <div class="modal fade" id="messageModal" tabindex="-1"
              role="dialog" aria-hidden="true">
        <div class="modal-dialog vertical-align-center">
           <div class="modal-content <% if(messageType.equals("���� �޽���"))
        	 out.println("panel-warning"); else out.println("panel-success"); %>">
               <div class="modal-header panel-heading">
                 <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times</span>
                    <span class="sr-only">Close</span>
                 </button>
                 <h4 class="modal-title">
                    <%=messageType%>
                 </h4>
               </div>           
               <div class="modal-body">
                    <%=messageContent%>
               </div> 
               <div class="modal-footer">
                 <button type="button" class="btn btn-primary" 
                 data-dismiss="modal">Ȯ��</button>
               </div>
           </div>
         </div>     
     </div>
    <script>
       $('#messageModal').modal("show");
    </script>
   <% session.removeAttribute("messageContent");
      session.removeAttribute("messageType");
     } %>
    <%
        if(userID !=null){
    %>
     <script type="text/javascript">
        $(document).ready(function(){
        	getUnread();
        	getInfiniteUnread()
        });
     </script>    
    <%
        }
    %>    
</body>
</html>

