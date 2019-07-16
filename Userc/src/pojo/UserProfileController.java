package pojo;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import user.UserDAO;

public class UserProfileController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// cos.jar
		
		request.setCharacterEncoding("EUC-KR");
		MultipartRequest multi=null;
		
		int fileMaxSize=1024*1024*10;
		String savePath=request.getRealPath("/upload");
		System.out.println(savePath);
		try {
			multi=new MultipartRequest(request,savePath,fileMaxSize,"EUC-KR",new DefaultFileRenamePolicy());
		} catch (Exception e) {			
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다.");
			e.printStackTrace();
			return "redirect:profileUpdate.do";
		}
		
		String userID=multi.getParameter("userID");
		System.out.println(userID);
		HttpSession session=request.getSession();
		if(!userID.equals((String)session.getAttribute("userID"))) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할수 없습니다");
			return "redirect:index.jsp";
		}
		String fileName="";
		File file=multi.getFile("userProfile");
		if(file !=null) {
			String ext=file.getName().substring(file.getName().lastIndexOf(".")+1);
			if(ext.equals("jpg")||ext.equals("png")||ext.equals("gif")) {
				String prev=new UserDAO().getUser(userID).getUserProfile();
				File prevFile=new File(savePath+"/"+prev);
				if(prevFile.exists()) {
					prevFile.delete();
				}
				fileName=file.getName();
			}else {if(file.exists()) {
				file.delete();
			}
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "이미지 파일만 업로드 가능합니다.");
			return "redirect:profileUpate.do";
			}
			}
		new UserDAO().profile(userID,fileName);
		session.setAttribute("messageType", "성공 메세지");
		session.setAttribute("messageContent", "성공적으로 프로필이 변경되었습니다");
		return "redirect:index.jsp";
	}

}
