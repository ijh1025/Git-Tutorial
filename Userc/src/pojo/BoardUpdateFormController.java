package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import board.BoardDTO;
import user.UserDAO;
import user.UserDTO;

public class BoardUpdateFormController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		String userID=null;
		if(session.getAttribute("userID") !=null) {
			userID=(String) session.getAttribute("userID");
		}
		if(userID==null) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "로그인 되어 있지 않습니다.");
			//response.sendRedirect("index.jsp");
			return "redirect:index.jsp";
		}
		UserDAO dao=new UserDAO();
		UserDTO user=dao.getUser(userID);
		String boardID=request.getParameter("boardID");
		
		if(boardID==null || boardID.equals("")) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할수 없습니다.");
			//response.sendRedirect("index.jsp");
			return "redirect:index.jsp";
		}
		BoardDAO boardDAO=new BoardDAO();
		BoardDTO board=boardDAO.getBoard(boardID);
		
		if(!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "!!접근할수 없습니다.");
			//response.sendRedirect("index.jsp");
			return "redirect:index.jsp";
		}
		request.setAttribute("board", board);
		String nextView="/WEB-INF/views/board/boardUpdate.jsp";
		return nextView;
	}

}
