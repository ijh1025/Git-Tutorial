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
		//���� �˻� �޽��� ���
		if(userID==null || userID.equals("")||
				userPassword1==null || userPassword1.equals("")||
				userPassword2==null || userPassword2.equals("")||
				userName==null || userName.equals("")||
				userAge==null || userAge.equals("")||
				userGender==null || userGender.equals("")||
				userEmail==null|| userEmail.equals("")) {
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "��� ������ �Է��ϼ���");
			return "/WEB-INF/views/join.jsp";
		}
		//��й�ȣ�� �ٸ��� ��µǴ� �޼���
		if(!userPassword1.equals(userPassword2)) {
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "��й�ȣ�� ���� �ٸ��ϴ�");
			return "redirect:join.do";
		}
				
		UserDAO dao=new UserDAO();
		int value=dao.register(vo);
		//ȸ������ �����ϸ� ��µǴ� �޼���
		if(value==1) {
			session.setAttribute("userID", userID);
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "ȸ�����Կ� �����ϼ̽��ϴ�.");
			return "redirect:index.jsp";
		}else {
			session.setAttribute("messageType", "���� �޼���");
			session.setAttribute("messageContent", "�̹������ϴ� ȸ���Դϴ�.");
			return "redirect:join.do";
		}
		
	}

}
