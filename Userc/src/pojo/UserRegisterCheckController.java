package pojo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;

public class UserRegisterCheckController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		String userID=request.getParameter("userID");
		if(userID==null ||userID.equals("")) {
			out.print("-1");
		}
		
		UserDAO dao=new UserDAO();
		//System.out.println(userID);
		int value=dao.registerCheck(userID);
		
		out.print(value);
		
		return null;
	}

}
