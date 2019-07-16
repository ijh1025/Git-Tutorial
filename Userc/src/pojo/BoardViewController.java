package pojo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class BoardViewController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BoardDAO dao=new BoardDAO();
		String pageNumber="1";
		
		
		if(request.getParameter("pageNumber")!=null) {
			pageNumber=request.getParameter("pageNumber");
		}
		boolean nextPage=dao.nextPage(pageNumber);
		List<BoardDTO> list=dao.getList(pageNumber);
		
		request.setAttribute("list", list);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("nextPage", nextPage);
		//페이징 처리에 필요한 변수의 값 구하기
		int startPage=(Integer.parseInt(pageNumber)/10)*10+1;
		if(Integer.parseInt(pageNumber)%10==0)startPage-=10;
		int targetPage=dao.targetPage(pageNumber);
		request.setAttribute("startPage", startPage);
		request.setAttribute("targetPage", targetPage);
		
		
		String nextView="/WEB-INF/views/board/boardView.jsp";
		return nextView;
	}

}
