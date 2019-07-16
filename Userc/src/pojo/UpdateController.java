package pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;
import user.UserDTO;

public class UpdateController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userID=(String) request.getSession().getAttribute("userID");
		UserDAO dao=new UserDAO();
		UserDTO user=dao.getUser(userID);
		request.getSession().setAttribute("user", user);
		
		String nextView="/WEB-INF/views/update.jsp";
		return nextView;
	}

}
