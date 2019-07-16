package pojo;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.BoardDAO;
import board.BoardDTO;

public class BoardUpdateController implements Controller {
	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// cos.jar
		request.setCharacterEncoding("euc-kr");
		MultipartRequest multi=null;
		int fileMaxSize=1024*1024*10;
		String savePath=request.getRealPath("/upload");
		try {
			multi=new MultipartRequest(request,
					                   savePath,
					                   fileMaxSize,
					                   "euc-kr",
					                   new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "파일크기는 10MB를 넘을 수 없습니다.");
			return "redirect:boardWrite.do";
		}
		
		String userID=multi.getParameter("userID");
		HttpSession session=request.getSession();
		if(!userID.equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할수 없습니다.");
			//response.sendRedirect("index.jsp");
			return "redirect:index.jsp";
		}
		//---------------------------------------------
		String boardID=multi.getParameter("boardID");
		String boardTitle=multi.getParameter("boardTitle");
		String boardContent=multi.getParameter("boardContent");
		if(boardTitle==null || boardTitle.equals("") ||
		   boardContent==null || boardContent.equals("")){
		   session.setAttribute("messageType", "오류 메시지");
		   session.setAttribute("messageContent", "내용을 모두 입력하세요.");
		   return "redirect:boardWrite.do";
		}
		String boardFile="";
		String boardRealFile="";
		File file=multi.getFile("boardFile");
		BoardDAO dao=new BoardDAO();
		BoardDTO vo=new BoardDTO();
		
		if(file !=null) {
			boardFile=multi.getOriginalFileName("boardFile");
			boardRealFile=file.getName();
			String prev=dao.getRealFile(boardID);
			File prevFile=new File(savePath+"/"+prev);
			if(prevFile.exists()){
				prevFile.delete();
			}
		}else{
			boardFile=dao.getFile(boardID);
			boardRealFile=dao.getRealFile(boardID);
		}		
		vo.setUserID(userID);
		vo.setBoardID(Integer.parseInt(boardID));
		vo.setBoardTitle(boardTitle);
		vo.setBoardContent(boardContent);
		vo.setBoardFile(boardFile);
		vo.setBoardRealFile(boardRealFile);		
		dao.update(vo);
		///////////////////////////////////////////////
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 수정되었습니다.");
		return "redirect:boardView.do";
	}

}
