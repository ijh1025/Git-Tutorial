package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;
import user.UserDTO;

public class LogProcController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userID=request.getParameter("userID");
		String userPassword=request.getParameter("userPassword");
		
		if(userID==null || userID.equals("")||
		   userPassword==null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "모든내용을 입력해주세요");
			response.sendRedirect("login.do");
			return null;
			
		}
		UserDTO vo=new UserDTO();
		vo.setUserID(userID);
		vo.setUserPassword(userPassword);
		
		UserDAO dao=new UserDAO();
		int result=dao.login(vo);
		if(result==1) {
			request.getSession().setAttribute("userID", userID);
		request.getSession().setAttribute("messageType", "성공 메세지");
		request.getSession().setAttribute("messageContent", "로그인에 성공 했습니다");
		response.sendRedirect("index.jsp");
	}else if(result==2) {request.getSession().setAttribute("messageType", "오류 메세지");
	request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인하세요");
	response.sendRedirect("login.do");}
	else if(result==0){
		request.getSession().setAttribute("messageType", "오류 메세지");
		request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
		response.sendRedirect("login.do");
	
	}else if(result==-1){request.getSession().setAttribute("messageType", "오류 메세지");
	request.getSession().setAttribute("messageContent", "데이터 베이스 오류 발생");
	response.sendRedirect("login.do");
}

		
		return null;
	}

}
