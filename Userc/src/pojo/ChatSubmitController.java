package pojo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.ChatDAO;
import chat.ChatDTO;

public class ChatSubmitController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String fromID=request.getParameter("fromID");
		String toID=request.getParameter("toID");
		String chatContent=request.getParameter("chatContent");
		ChatDTO vo=new ChatDTO();
		vo.setFromID(fromID);
		vo.setToID(toID);
		vo.setChatContent(chatContent);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		if(fromID==null || fromID.equals(" ") ||
		   toID==null || toID.equals("") ||
		   chatContent==null || chatContent.equals("")) {
			out.print("0");
		}else {
			ChatDAO dao=new ChatDAO();
			int v=dao.submit(vo);
			out.print(v);
		}
		
		return null;
	}

}
