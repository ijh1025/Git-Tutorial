package board;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import user.UserDTO;

public class BoardDAO {
	 private static SqlSessionFactory sqlSessionFactory;
     //초기화 블럭
     static{    	 
   	try {
   		String resource = "user/config.xml";
   		InputStream inputStream = Resources.getResourceAsStream(resource);
   		sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
           e.printStackTrace();
		} 
     }
     // 게시판쓰기
     public int write(BoardDTO vo){
     	SqlSession session=sqlSessionFactory.openSession();
     	try {
 		   int cnt=session.insert("write", vo); 
     	   session.commit();
     	   return cnt;
 		} catch (Exception e) {
             e.printStackTrace();
 		} finally{
 			session.close();
 		}
     	    return -1; // 데이터베이스 오류...
    }
    // 게시판 전체 리스트 가져오기
    public List<BoardDTO> getList(String pageNumber){
    	PageVO page=new PageVO();
    	page.setStart(Integer.parseInt(pageNumber)*10);
    	page.setEnd((Integer.parseInt(pageNumber)-1)*10);
    	SqlSession session=sqlSessionFactory.openSession();
    	List<BoardDTO> list=null;    	
    	try {
			 list=session.selectList("getList", page);
	    	} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
    	return list;
    }
    // 게시판 조회수 올리기
    public int hit(String boardID){
    	SqlSession session=sqlSessionFactory.openSession();    	
    	int cnt=0;
    	try {
			 cnt=session.update("hit", boardID);
			 session.commit();
			 return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
    	return -1;    	
    }
    // 선택한 게시물 가겨오기
	public BoardDTO getBoard(String boardID) {
		SqlSession session=sqlSessionFactory.openSession();    	
		BoardDTO vo=null;
    	try {
			 vo=session.selectOne("getBoard", boardID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
    	return vo;   
	}
	// 파일 다운로드하기위한 파일 이름 얻기(처음 업로드한 이름)
	public String getFile(String boardID){
		SqlSession session=sqlSessionFactory.openSession();    	
		String vo=null;
    	try {
			 vo=session.selectOne("getFile", boardID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
    	return vo; 
	}
	// 파일 다운로드하기위한 파일 이름 얻기(실제로 저장된 파일이름)
	public String getRealFile(String boardID){
		SqlSession session=sqlSessionFactory.openSession();    	
		String vo=null;
    	try {
			 vo=session.selectOne("getRealFile", boardID);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
    	return vo; 
	}	
	// update 코드 작성
	public int update(BoardDTO vo){
		SqlSession session=sqlSessionFactory.openSession();
		try {
		      int cnt=session.update("board.boardMapper.update", vo);
		      session.commit();
		      return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return -1;
	}
	// delete 코드 작성
	public int delete(String boardID){
		SqlSession session=sqlSessionFactory.openSession();
		try {
		      int cnt=session.update("board.boardMapper.delete", boardID);
		      session.commit();
		      return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return -1;
	}
    // 답변쓰기                            |-->부모글번호가 저장(boardID=>boardGroup)
    //                         |-->boardSequence+1=>boardSequence
	//                         |-->boardLevel+1=>boardLevel
	public int reply(BoardDTO vo){
    	SqlSession session=sqlSessionFactory.openSession();
    	try {
		   int cnt=session.insert("reply", vo); 
    	   session.commit();
    	   return cnt;
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			session.close();
		}
    	    return -1; // 데이터베이스 오류...
   }
	public int replyUpdate(BoardDTO vo){
    	SqlSession session=sqlSessionFactory.openSession();
    	try {
		   int cnt=session.insert("replyUpdate", vo); 
    	   session.commit();
    	   return cnt;
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			session.close();
		}
    	    return -1; // 데이터베이스 오류...
   }
   public boolean nextPage(String pageNumber){
		SqlSession session=sqlSessionFactory.openSession();
    	try {
		   List<BoardDTO> list=session.selectList("nextPage", Integer.parseInt(pageNumber)*10); 
       	   if(list.size()!=0){
		      return true;
       	   }
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			session.close();
		}
        return false; // 데이터베이스 오류...		
	}
   public int targetPage(String pageNumber){
 		SqlSession session=sqlSessionFactory.openSession();
     	try {
 		    int cnt=session.selectOne("targetPage", (Integer.parseInt(pageNumber)-1)*10); 
        	if(cnt!=0){
 		      return cnt/10;
        	}
 		} catch (Exception e) {
             e.printStackTrace();
 		} finally{
 			session.close();
 		}
         return 0; // 데이터베이스 오류...		
 	}
}




