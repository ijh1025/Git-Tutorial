package controller;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import pojo.Controller;

//@WebServlet("*.do")
public class FrontController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		//System.out.println(request.getRequestURI());
		// FrontController -- 1.클라이언트의 모든 요청을 받는작업
		// 2. 어떤 요청인지를 파악하는 작업(command)
		String reqUrl=request.getRequestURI();
		String ctxPath=request.getContextPath();
		//System.out.println(ctxPath);
		String command=reqUrl.substring(ctxPath.length());
		System.out.println(command);
		//3.요청에 따라 분기하는 작업
		//4.각 요청에 따른 처리(모델과 연동)
		//HandlerMapping(핸들러 매핑)
		String nextView=null;
		Controller controller=null;
		HandlerMapping mappings=new HandlerMapping();
		controller=mappings.getController(command);
		nextView=controller.requestHandle(request, response);
		/*if(command.equals("/list.do")) {//전체 리스트
			//pojo(plain old java Object):평범한 클래스로 만들어라
			//프론트컨트롤러가 해야할 일을 대신해서 작업하는 갳에들..
			controller=new MemberListController();
			nextView=controller.requestHandle(request, response);
			
			//5. view 연동하는 부분
			//RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/memberList.jsp");
			//rd.forward(request, response);
		}else if(command.equals("/insertHtml.do")) {
			//nextView="/WEB-INF/views/member.html";
			//RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/member.html");
			//rd.forward(request, response);
			controller=new MemberInsertHtmlController();
			nextView=controller.requestHandle(request, response);
		}else if(command.equals("/insert.do")) {
			controller=new MemberInsertController();
			nextView=controller.requestHandle(request, response);
			
			String userid=request.getParameter("userid");
			String userpw=request.getParameter("userpw");
			String username=request.getParameter("username");
			String email=request.getParameter("email");
			memberVO vo=new memberVO(userid, userpw, username, email);
			dao.memInsert(vo);
			nextView="redirect:list.do";
			
			//response.sendRedirect("list.do");
		}else if(command.equals("/delete.do")) {
			controller=new MemberDeleteController();
			nextView=controller.requestHandle(request, response);
			
			String userid=request.getParameter("userid");
			dao.memDelete(userid);
			nextView="redirect:list.do";
			
		}
		else if(command.equals("/content.do")) {
			controller=new MemberContentController();
			nextView=controller.requestHandle(request, response);
			
		}*/
		//5.view 연동하는 부분
		if(nextView!=null) {
			
			if(nextView.indexOf("redirect:")!=-1) {String[] sp=nextView.split(":");
			response.sendRedirect(sp[1]);}else {RequestDispatcher rd=request.getRequestDispatcher(nextView);
			rd.forward(request, response);}
		}
	}

}
