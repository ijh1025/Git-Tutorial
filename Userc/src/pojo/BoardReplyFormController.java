package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class BoardReplyFormController  implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String boardID=request.getParameter("boardID");
		BoardDAO dao=new BoardDAO();
		BoardDTO board=dao.getBoard(boardID);
		
		request.setAttribute("board",board);
		
		String nextView="/WEB-INF/views/board/boardReply.jsp";
		return nextView;
	}

}
