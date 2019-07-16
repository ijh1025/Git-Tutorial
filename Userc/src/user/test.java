package user;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import chat.ChatDTO;

class test {
	
	
	private static SqlSessionFactory sqlSessionFactory;
	//초기화 블럭
	static {
		try {
			String resource = "user/config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int registerCheck(String userID) {
		SqlSession session=sqlSessionFactory.openSession();
		try {
			// 해당사용자가 존재하지 않음
			UserDTO dto=session.selectOne("registerCheck",userID);
			//System.out.println(dto.toString());
			
			if(dto!=null) {
				return 0;//이미 존재하는 회원
			}else {
				return 1;//사용가능한 회원
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return -1;// 데이터베이스 오류.
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
	@Test
	void test() {
		//int a=registerCheck("a");
		SqlSession session=sqlSessionFactory.openSession();
		//UserDTO dto=session.selectOne("registerCheck","c");
		ChatDTO vo=new ChatDTO();
		vo.setFromID("왜");
		vo.setToID("안되");
		vo.setChatContent("이유는?");
		//System.out.println(vo.toString());
		int a=submit(vo);
		//System.out.println(a);
		assertEquals(null,a);
	}

}
