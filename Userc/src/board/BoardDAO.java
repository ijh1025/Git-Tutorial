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
     //�ʱ�ȭ ��
     static{    	 
   	try {
   		String resource = "user/config.xml";
   		InputStream inputStream = Resources.getResourceAsStream(resource);
   		sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
           e.printStackTrace();
		} 
     }
     // �Խ��Ǿ���
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
     	    return -1; // �����ͺ��̽� ����...
    }
    // �Խ��� ��ü ����Ʈ ��������
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
    // �Խ��� ��ȸ�� �ø���
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
    // ������ �Խù� ���ܿ���
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
	// ���� �ٿ�ε��ϱ����� ���� �̸� ���(ó�� ���ε��� �̸�)
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
	// ���� �ٿ�ε��ϱ����� ���� �̸� ���(������ ����� �����̸�)
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
	// update �ڵ� �ۼ�
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
	// delete �ڵ� �ۼ�
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
    // �亯����                            |-->�θ�۹�ȣ�� ����(boardID=>boardGroup)
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
    	    return -1; // �����ͺ��̽� ����...
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
    	    return -1; // �����ͺ��̽� ����...
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
        return false; // �����ͺ��̽� ����...		
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
         return 0; // �����ͺ��̽� ����...		
 	}
}




