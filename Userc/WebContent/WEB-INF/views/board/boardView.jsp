<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <%
       String userID=null;
       if(session.getAttribute("userID") !=null){
    	   userID=(String)session.getAttribute("userID");   
       }  
       if(userID==null){
    	   session.setAttribute("messageType", "오류 메세지");
    	   session.setAttribute("messageContent", "현재 로그인 되어 있지 않습니다.");
           response.sendRedirect("index.jsp");
           return;
       }
  %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content="width=device-width, inital-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP Ajax 실시간 회원제 채팅 서비스</title>
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
         <a class="navbar-brand" href="index.jsp">실시간 회원제 채팅 서비스</a> 
     </div>  
     <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
             <li><a href="index.jsp">메인</a>
             <li><a href="find.do">친구찾기</a></li>
             <li><a href="box.do">메세지함<span id="unread" class="label label-info"></span></a></li>
             <li class="active"><a href="boardView.do">자유게시판</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
           <li class="dropdown">
               <a href="#" class="dropdown-toggle"
                 data-toggle="dropdown" role="button" aria-haspopup="true"
                 aria-expanded="false">회원관리<span class="caret"></span>
                </a>  
                <ul class="dropdown-menu">
                     <li><a href="update.do">회원정보수정</a></li>
                     <li><a href="profileUpdate.do">프로필 수정</a></li>
	                 <li><a href="logout.do">로그아웃</a></li>	                 
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
                <th colspan="5"><h4>자유게시판</h4></th>
              </tr>
              <tr>
                <th style="background-color: #fafafa; color: #000000; width: 70px;">번호</th>
                <th style="background-color: #fafafa; color: #000000;">제목</th>
                <th style="background-color: #fafafa; color: #000000;">작성자</th>
                <th style="background-color: #fafafa; color: #000000; width: 100px;">작성일자</th>
                <th style="background-color: #fafafa; color: #000000; width: 70px;">조회수</th>
              </tr>
          </thead>   
          <tbody>
          <c:forEach var="board" items="${list}">
             <tr>
                <td>${board.boardID}</td>
                <td style="text-align: left;">
                <c:forEach var="i" begin="1" end="${board.boardLevel}">
                  <span class="glyphicon glyphicon-arrow-right" 
                                             aria-hidden="true"></span>
                </c:forEach>
                <a href="boardShow.do?boardID=${board.boardID}">
                 <c:if test="${board.boardAvailable==0}">
                      (삭제된 게시물입니다.)
                 </c:if>
                 <c:if test="${board.boardAvailable!=0}">
                      ${board.boardTitle}
                 </c:if>
                </a></td>
                <td>${board.userID}</td>
                <td>
                <fmt:parseDate var="boardDate" pattern="yyyy-MM-dd"
                                         value="${board.boardDate}"/>
                <fmt:formatDate pattern="yyyy-MM-dd" value="${boardDate}"/>
                </td>
                <td>${board.boardHit}</td>
             </tr>
           </c:forEach> 
             <tr>
               <td colspan="5">
                 <a href="boardWrite.do" class="btn btn-primary pull-right"
                                                    type="submit">글쓰기</a>
                 <!-- 페이지 네비게이션 구축 -->
                 <ul class="pagination" style="margin: 0 auto;">
	                 <c:if test="${startPage!=1}">
	                    <li><a href="boardView.do?pageNumber=${startPage-1}">
	                          <span class="glyphicon glyphicon-chevron-left"></span></a></li>
	                 </c:if>
	                 <c:if test="${startPage==1}">
	                    <li><span class="glyphicon glyphicon-chevron-left" style="color: gray;"></span></li>
	                 </c:if>	
	                 <c:forEach var="i" begin="${startPage}" end="${pageNumber-1}">
	                    <li><a href="boardView.do?pageNumber=${i}">${i}</a></li>
	                 </c:forEach> 
	                 <li class="active"><a href="boardView.do?pageNumber=${pageNumber}">${pageNumber}</a></li>         
                     <c:forEach var="j" begin="${pageNumber+1}" end="${pageNumber+targetPage}">
                       <c:if test="${j<startPage+10}">
                         <li><a href="boardView.do?pageNumber=${j}">${j}</a></li> 
                       </c:if>
                     </c:forEach>
                     <c:if test="${targetPage+pageNumber>startPage+9}">
                       <li><a href="boardView.do?pageNumber=${startPage+10}">
                         <span class="glyphicon glyphicon-chevron-right"></span>
                       </a></li>
                     </c:if>
                     <c:if test="${targetPage+pageNumber<startPage+9}">
                       <li><span class="glyphicon glyphicon-chevron-right" style="color: gray;"></span></li>
                     </c:if>
                 </ul>                                   
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
           <div class="modal-content <% if(messageType.equals("오류 메시지"))
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
                 data-dismiss="modal">확인</button>
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

