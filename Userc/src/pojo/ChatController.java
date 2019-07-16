package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;

public class ChatController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		String userID=null;
	  	if(session.getAttribute("userID")!=null){
	  		userID=(String)session.getAttribute("userID");
	  	} 
	  	String toID=null;
	  	if(request.getParameter("toID")!=null){
	  		toID=(String)request.getParameter("toID");
	  	}
		
	  	UserDAO dao=new UserDAO();
	  	String fromProfile=dao.getProfile(userID);
	  	System.out.println(fromProfile);
	  	String toProfile=dao.getProfile(toID);
	  	
	  	session.setAttribute("fromProfile", fromProfile);
	  	session.setAttribute("toProfile", toProfile);
	  	
	  	
		String nextView="/WEB-INF/views/chat.jsp";
		return nextView;
	}

}
