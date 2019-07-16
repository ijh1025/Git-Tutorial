package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;
import user.UserDTO;

public class UserResigterController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userID=request.getParameter("userID");
		String userPassword1=request.getParameter("userPassword1");
		String userPassword2=request.getParameter("userPassword2");
		String userName=request.getParameter("userName");
		String userAge=request.getParameter("userAge");
		String userGender=request.getParameter("userGender");
		String userEmail=request.getParameter("userEmail");
		String userProfile=request.getParameter("");
		
		UserDTO vo=new UserDTO();
		vo.setUserAge(Integer.parseInt(userAge));
		vo.setUserEmail(userEmail);
		vo.setUserGender(userGender);
		vo.setUserID(userID);
		vo.setUserName(userName);
		vo.setUserPassword(userPassword1);
		vo.setUserProfile(userProfile);
		System.out.println(vo);
		
		HttpSession session=request.getSession();
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
			return "/WEB-INF/views/join.jsp";
		}
		//비밀번호가 다르면 출력되는 메세지
		if(!userPassword1.equals(userPassword2)) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "비밀번호가 서로 다릅니다");
			return "redirect:join.do";
		}
				
		UserDAO dao=new UserDAO();
		int value=dao.register(vo);
		//회원가입 성공하면 출력되는 메세지
		if(value==1) {
			session.setAttribute("userID", userID);
			session.setAttribute("messageType", "성공 메세지");
			session.setAttribute("messageContent", "회원가입에 성공하셨습니다.");
			return "redirect:index.jsp";
		}else {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "이미존재하는 회원입니다.");
			return "redirect:join.do";
		}
		
	}

}
