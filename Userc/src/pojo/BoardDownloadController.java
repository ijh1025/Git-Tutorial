package pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;

public class BoardDownloadController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String boardID=request.getParameter("boardID");
		
		String root=request.getSession().
				    getServletContext().getRealPath("/");
		
		String savePath=root+"upload";
		String fileName="";
		String realFile="";
		
		BoardDAO dao=new BoardDAO();
		fileName=dao.getFile(boardID);
		System.out.println(fileName);
		realFile=dao.getRealFile(boardID);
		// 에러처리~~
		InputStream in=null;
		OutputStream os=null;
		File file=null;
		boolean skip=false;
		String client="";
		try {
            try {            	
				file=new File(savePath, realFile);
				in=new FileInputStream(file);				
			} catch (Exception e) {
                e.printStackTrace();
                skip=true;
			}	
            // 다운로드....
            client=request.getHeader("User-Agent");
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "Generated Data");
            if(!skip){
            	if(client.indexOf("MSIE") != -1){
            		response.setHeader("Content-Disposition", "attachment; filename="+new String(fileName.getBytes("KSC5601"),"ISO8859_1"));
            	}else{
            		fileName=new String(fileName.getBytes("UTF-8"),"iso-8859-1");
            		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName +"\"");
            		response.setHeader("Content-Type","application/octet-stream; charset=UTF-8");
            	}
            	response.setHeader("Content-Length", ""+file.length());
                os=response.getOutputStream();
                byte b[]=new byte[(int)file.length()];
                int leng=0;
                while((leng=in.read(b)) > 0){
                	os.write(b, 0, leng);
                }
            }else{
            	response.setContentType("text/html;charset=utf-8");
            	response.getWriter().println("<script>alert('파일을 찾을수 없습니다.');history.back();</script>");
            }
            in.close();
            os.close();
		} catch (Exception e) {
           e.printStackTrace();
		}
		return null;
	}	
}
