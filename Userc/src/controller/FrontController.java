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
		// FrontController -- 1.Ŭ���̾�Ʈ�� ��� ��û�� �޴��۾�
		// 2. � ��û������ �ľ��ϴ� �۾�(command)
		String reqUrl=request.getRequestURI();
		String ctxPath=request.getContextPath();
		//System.out.println(ctxPath);
		String command=reqUrl.substring(ctxPath.length());
		System.out.println(command);
		//3.��û�� ���� �б��ϴ� �۾�
		//4.�� ��û�� ���� ó��(�𵨰� ����)
		//HandlerMapping(�ڵ鷯 ����)
		String nextView=null;
		Controller controller=null;
		HandlerMapping mappings=new HandlerMapping();
		controller=mappings.getController(command);
		nextView=controller.requestHandle(request, response);
		/*if(command.equals("/list.do")) {//��ü ����Ʈ
			//pojo(plain old java Object):����� Ŭ������ ������
			//����Ʈ��Ʈ�ѷ��� �ؾ��� ���� ����ؼ� �۾��ϴ� �Y����..
			controller=new MemberListController();
			nextView=controller.requestHandle(request, response);
			
			//5. view �����ϴ� �κ�
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
		//5.view �����ϴ� �κ�
		if(nextView!=null) {
			
			if(nextView.indexOf("redirect:")!=-1) {String[] sp=nextView.split(":");
			response.sendRedirect(sp[1]);}else {RequestDispatcher rd=request.getRequestDispatcher(nextView);
			rd.forward(request, response);}
		}
	}

}
