package pojo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import chat.ChatDAO;
import chat.ChatDTO;

public class ChatListController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String fromID=request.getParameter("fromID");
		String toID=request.getParameter("toID");
		String listType=request.getParameter("listType");
		if(fromID==null||fromID.equals("")||
		   toID==null||toID.equals("")||
		   listType==null||listType.equals("")) {
		   response.getWriter().write("");
		}else if(listType.equals("ten")) {
			response.getWriter().write(getTen(fromID,toID));
		}else {
			try {
				response.getWriter().write(getID(fromID,toID,listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
		
		return null;
	}
private String getTen(String fromID,String toID) {
	ChatDAO dao=new ChatDAO();
	List<ChatDTO> chatList=dao.getChatListByRecent(fromID,toID,100);
	if(chatList.size()==0) return "";
	Gson g=new Gson();
	String json=g.toJson(chatList);
	
	int lastID=chatList.get(chatList.size()-1).getChatID();
	json=json+"#"+lastID;
	dao.readChat(fromID, toID);
	return json;
}
private String getID(String fromID,String toID,String chatID) {
	ChatDAO dao=new ChatDAO();
	List<ChatDTO> chatList=dao.getChatListByID(fromID,toID,chatID);
	if(chatList.size()==0) return "";
	Gson g=new Gson();
	String json=g.toJson(chatList);
	
	int lastID=chatList.get(chatList.size()-1).getChatID();
	json=json+"#"+lastID;
	dao.readChat(fromID, toID);
	return json;
}
}
