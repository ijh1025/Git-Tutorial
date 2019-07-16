package user;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


								//�����ͺ��̽� ���ᰴü�� ��Ȱ���ϴ� ���
public class UserDAO {//MyBatis API : ConnectionPool(Ŀ�ؼ�Ǯ
	
	
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
	
//���뼱
//	private Connection conn = null;
//	private PreparedStatement psmt = null;
//	private int cnt;
//	private ResultSet rs;
//	private String login_id;
//	private static MemberDAO dao;
//	
//	private MemberDAO() {
//		
//	}
//	public static MemberDAO getDAO() {
//		if(dao==null)
//			dao=new MemberDAO();
//		return dao;
//	}
//
//	private void getConnection(){ // ����̹� �ε��� Ŀ�ؼ����ִ� �޼ҵ�
//		
//		InputStream in=this.getClass().getResourceAsStream("db.properties");
//		Properties pro=new Properties();
//		
//		try {
//		pro.load(in);
//		String driver=pro.getProperty("driver");
//		String url=pro.getProperty("url");
//		String id=pro.getProperty("id");
//		String pw=pro.getProperty("pw");
//		
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, id, pw);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // 1����̹� �ε� forName�ȿ�
//		catch (SQLException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void close() {
//
//		try {
//			if (rs != null) {
//				rs.close();
//			}
//			if (psmt != null) {
//				psmt.close();
//			} // 5_0.������� ����
//			if (conn != null) {
//				conn.close();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//���뼱
	public int login(UserDTO vo) {
		SqlSession session=sqlSessionFactory.openSession();
		try {
			// �ش����ڰ� �������� ����
			UserDTO dto=session.selectOne("login",vo);
			if(dto!=null) {
				if(vo.getUserPassword().equals(dto.getUserPassword())) {
					return 1;//�α��μ���
				}
				return 2;//��й�ȣ�� Ʋ��
			}else {
				return 0;//�ش����ڰ� �������� ����
			}
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		return -1;// �����ͺ��̽� ����.
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
	public int register(UserDTO vo) {
		SqlSession session=sqlSessionFactory.openSession();
		try {
			int cnt=session.insert("register",vo);
			session.commit();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return -1;
	}
	public UserDTO getUser(String userID) {
		SqlSession session=sqlSessionFactory.openSession();
		UserDTO dto=null;
		try {
			// �ش����ڰ� �������� ����
			dto=session.selectOne("getUser",userID);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return dto;// �����ͺ��̽� ����.
	}
	public int update(UserDTO vo) {
		SqlSession session=sqlSessionFactory.openSession();
		try {
			int cnt=session.update("update",vo);
			session.commit();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return -1;
	}
	public int profile(String userID,String userProfile) {
		SqlSession session=sqlSessionFactory.openSession();
		UserDTO vo= new UserDTO();
		vo.setUserID(userID);
		vo.setUserProfile(userProfile);
		try {
			int cnt=session.update("profile",vo);
			session.commit();
			return cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return -1;
	}
	public String getProfile(String userID) {
		SqlSession session=sqlSessionFactory.openSession();
		try {
			String userProfile=session.selectOne("getProfile",userID);
			//System.out.println(userProfile);
			if(userProfile.equals("")) {
				return "http://localhost:8081/Userc/images/icon.png";
			}
			return "http://localhost:8081/Userc/upload/"+userProfile;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return "http://localhost:8081/Userc/images/icon.png";
	}
}
