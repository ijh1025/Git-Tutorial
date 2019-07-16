package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.ChatDAO;

public class ChatUnreadController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ChatDAO dao=new ChatDAO();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String userID=request.getParameter("userID");
		if(userID==null || userID.equals("")) {
			response.getWriter().write("0");
		}else {
			int cnt=dao.getAllUnreadChat(userID);
			response.getWriter().write(cnt+"");
		}
		
		return null;
	}

}
