package pojo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import chat.ChatDAO;
import chat.ChatDTO;

public class ChatBoxController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String userID=request.getParameter("userID");
		if(userID==null || userID.equals("")) {
			response.getWriter().write("");
		}else {
			try {
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
				e.printStackTrace();
			}
		}
		return null;
	}
private String getBox(String userID) {
	ChatDAO dao=new ChatDAO();
	List<ChatDTO> chatList=dao.getBox(userID);
	if(chatList.size()==0) return "";
	Gson g=new Gson();
	String json=g.toJson(chatList);
	
	int lastID=chatList.get(chatList.size()-1).getChatID();
	json=json+"#"+lastID;
	return json;

}
}
