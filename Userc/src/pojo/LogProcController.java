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
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "��系���� �Է����ּ���");
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
		request.getSession().setAttribute("messageType", "���� �޼���");
		request.getSession().setAttribute("messageContent", "�α��ο� ���� �߽��ϴ�");
		response.sendRedirect("index.jsp");
	}else if(result==2) {request.getSession().setAttribute("messageType", "���� �޼���");
	request.getSession().setAttribute("messageContent", "��й�ȣ�� �ٽ� Ȯ���ϼ���");
	response.sendRedirect("login.do");}
	else if(result==0){
		request.getSession().setAttribute("messageType", "���� �޼���");
		request.getSession().setAttribute("messageContent", "���̵� �������� �ʽ��ϴ�.");
		response.sendRedirect("login.do");
	
	}else if(result==-1){request.getSession().setAttribute("messageType", "���� �޼���");
	request.getSession().setAttribute("messageContent", "������ ���̽� ���� �߻�");
	response.sendRedirect("login.do");
}

		
		return null;
	}

}
