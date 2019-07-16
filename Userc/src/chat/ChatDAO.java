package chat;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
                                // �����ͺ��̽� ���ᰴü��  [��Ȱ���ϴ� ���]
public class ChatDAO { // MyBatis API : ConnectionPool(Ŀ�ؼ�Ǯ)
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
     public List<ChatDTO> getChatListByID(String fromID, String toID, 
    		                             String chatID){
    	 List<ChatDTO> chatList=null;
    	 SqlSession session=sqlSessionFactory.openSession();
    	 ChatDTO vo=new ChatDTO();
    	 vo.setFromID(fromID);
    	 vo.setToID(toID);
    	 vo.setChatID(Integer.parseInt(chatID));
    	 try {
		   chatList=session.selectList("getChatListByID", vo);	
		 } catch (Exception e) {
			e.printStackTrace();
		 }finally{
			session.close();
		 }    
    	 return chatList; // ����Ʈ��ȯ
     }
     public List<ChatDTO> getChatListByRecent(String fromID, String toID, 
    		                                  int number){
    	 List<ChatDTO> chatList=null;
    	 SqlSession session=sqlSessionFactory.openSession();
    	 ChatDTO vo=new ChatDTO();
    	 vo.setFromID(fromID);
    	 vo.setToID(toID);
    	 vo.setChatID(number);
    	 try {
		   chatList=session.selectList("getChatListByRecent", vo);	
		 } catch (Exception e) {
			e.printStackTrace();
		 }finally{
			session.close();
		 }    
    	 return chatList; // ����Ʈ��ȯ
     }
     
     public int submit(ChatDTO vo){
    	 SqlSession session=sqlSessionFactory.openSession();
    	 try {
			int cnt=session.insert("submit", vo);
			session.commit();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
    	 return -1;
     }
      public int readChat(String fromID, String toID){
    	 SqlSession session=sqlSessionFactory.openSession();
    	 ChatDTO vo=new ChatDTO();
    	 vo.setFromID(fromID);
    	 vo.setToID(toID);    	 
    	 try {
			int cnt=session.update("readChat", vo);
			session.commit();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}   
    	 return -1;
     }
      public int getAllUnreadChat(String userID){
     	 SqlSession session=sqlSessionFactory.openSession();
     	 try {
 			int cnt=session.selectOne("getAllUnreadChat", userID);
 			if(cnt!=0){
 				return cnt;
 			}
 			return 0;
 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally{
 			session.close();
 		}   
     	 return -1;
      }
//�޼����� ���
      public List<ChatDTO> getBox(String userID){
			List<ChatDTO> chatList=null;
			SqlSession session=sqlSessionFactory.openSession();
			
			try {
				chatList=session.selectList("getBox", userID);	
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				session.close();
			}    
			return chatList; // ����Ʈ��ȯ
			}

}





