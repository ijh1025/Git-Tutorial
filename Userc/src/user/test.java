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
	//�ʱ�ȭ ��
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
			// �ش����ڰ� �������� ����
			UserDTO dto=session.selectOne("registerCheck",userID);
			//System.out.println(dto.toString());
			
			if(dto!=null) {
				return 0;//�̹� �����ϴ� ȸ��
			}else {
				return 1;//��밡���� ȸ��
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return -1;// �����ͺ��̽� ����.
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
		vo.setFromID("��");
		vo.setToID("�ȵ�");
		vo.setChatContent("������?");
		//System.out.println(vo.toString());
		int a=submit(vo);
		//System.out.println(a);
		assertEquals(null,a);
	}

}
