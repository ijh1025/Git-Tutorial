package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;
import user.UserDTO;

public class UserUpdateController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDTO vo=new UserDTO();
		request.setCharacterEncoding("euc-kr");
		
		String userID=request.getParameter("userID");
		String userPassword1=request.getParameter("userPassword1");
		String userPassword2=request.getParameter("userPassword2");
		String userName=request.getParameter("userName");
		String userAge=request.getParameter("userAge");
		String userGender=request.getParameter("userGender");
		String userEmail=request.getParameter("userEmail");
		
		HttpSession session=request.getSession();
		
		if(!userID.equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할수 없습니다");
			//response.sendRedirect("index.jsp");
			return "redirect:index.jsp";
		}
		
		vo.setUserAge(Integer.parseInt(userAge));
		vo.setUserEmail(userEmail);
		vo.setUserGender(userGender);
		vo.setUserID(userID);
		vo.setUserName(userName);
		vo.setUserPassword(userPassword1);
		
		
		
		
		
		//오류 검색 메시지 출력
		if(userID==null || userID.equals("")||
				userPassword1==null || userPassword1.equals("")||
				userPassword2==null || userPassword2.equals("")||
				userName==null || userName.equals("")||
				userAge==null || userAge.equals("")||
				userGender==null || userGender.equals("")||
				userEmail==null|| userEmail.equals("")) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "모든 내용을 입력하세요");
			return "/WEB-INF/views/update.jsp";
		}
		//비밀번호가 다르면 출력되는 메세지
		if(!userPassword1.equals(userPassword2)) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "비밀번호가 서로 다릅니다");
			return "redirect:update.do";
		}
				
		UserDAO dao=new UserDAO();
		int value=dao.update(vo);
		//회원가입 성공하면 출력되는 메세지
		if(value==1) {
			session.setAttribute("userID", userID);
			session.setAttribute("messageType", "성공 메세지");
			session.setAttribute("messageContent", "회원정보 수정에 성공하셨습니다.");
			return "redirect:index.jsp";
		}else {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "데이터베이스 오류 발생.");
			return "redirect:update.do";
		}
		
	}

}
