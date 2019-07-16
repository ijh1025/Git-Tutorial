package pojo;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import board.BoardDTO;

public class BoardDeleteController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		String userID=(String) session.getAttribute("userID");

		String boardID=request.getParameter("boardID");		
		BoardDAO dao=new BoardDAO();
		BoardDTO board=dao.getBoard(boardID);
		if(!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "�����Ҽ� �����ϴ�");
			return "redirect:boardWrite.do";
		}//���ε�� ������ �����ϱ�
		String savePath=request.getRealPath("/upload");
		String prev=dao.getRealFile(boardID);
		int result=dao.delete(boardID);
		if(result==-1) {
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "�����Ҽ� �����ϴ�");
			return "redirect:boardWrite.do";
		}else {
			File prevFile=new File(savePath+"/"+prev);
			if(prevFile.exists()) {
				prevFile.delete();
			}
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "������ �����ϼ̽��ϴ�");
			
		}
		
		return "redirect:boardView.do";
	}

}
