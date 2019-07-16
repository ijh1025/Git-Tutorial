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
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할수 없습니다");
			return "redirect:boardWrite.do";
		}//업로드된 파일을 삭제하기
		String savePath=request.getRealPath("/upload");
		String prev=dao.getRealFile(boardID);
		int result=dao.delete(boardID);
		if(result==-1) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할수 없습니다");
			return "redirect:boardWrite.do";
		}else {
			File prevFile=new File(savePath+"/"+prev);
			if(prevFile.exists()) {
				prevFile.delete();
			}
			session.setAttribute("messageType", "성공 메세지");
			session.setAttribute("messageContent", "삭제에 성공하셨습니다");
			
		}
		
		return "redirect:boardView.do";
	}

}
