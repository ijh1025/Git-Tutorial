package user;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


								//데이터베이스 연결객체를 재활용하는 기법
public class UserDAO {//MyBatis API : ConnectionPool(커넥션풀
	
	
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
	
//절취선
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
//	private void getConnection(){ // 드라이버 로딩와 커넥션해주는 메소드
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
//		} // 1드라이버 로딩 forName안에
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
//			} // 5_0.순서대로 닫음
//			if (conn != null) {
//				conn.close();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//절취선
	public int login(UserDTO vo) {
		SqlSession session=sqlSessionFactory.openSession();
		try {
			// 해당사용자가 존재하지 않음
			UserDTO dto=session.selectOne("login",vo);
			if(dto!=null) {
				if(vo.getUserPassword().equals(dto.getUserPassword())) {
					return 1;//로그인성공
				}
				return 2;//비밀번호가 틀림
			}else {
				return 0;//해당사용자가 존재하지 않음
			}
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		return -1;// 데이터베이스 오류.
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
			// 해당사용자가 존재하지 않음
			dto=session.selectOne("getUser",userID);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return dto;// 데이터베이스 오류.
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
