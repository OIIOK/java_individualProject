//Swim에 관련한 DAO(쿼리문)를 정의함

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SwimDAO {

	static DBManager dbManager = DBManager.getInstance();

	// 메소드 선언 : DB에 수영기록 등록하기
	public static int insert(Swim swim) { // SwimDTO
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		String sql = "insert into swswim(swim_idx, id, yy, mm, dd, freestyle, backstroke, breaststroke, butterfly, totaltime)";

		sql += " values(seq_swswim.nextval,?,?,?,?,?,?,?,?,?)";
		// diary_idx는 시퀀스로 부여할 예정이고 id는 부모의 외래키를 포함한 나머지는 DiaryDTO에서 받아올 예정임

		try {
			pstmt = con.prepareStatement(sql); // 컴파일된 sql문 가져오기
			pstmt.setString(1, swim.getMember().getId());// 바인드 변수 채워주기. 부모의 외래키 꺼내기
			pstmt.setInt(2, swim.getYy());
			pstmt.setInt(3, swim.getMm());
			pstmt.setInt(4, swim.getDd());
			pstmt.setInt(5, swim.getFreestyle());
			pstmt.setInt(6, swim.getBackstroke());
			pstmt.setInt(7, swim.getBreaststroke());
			pstmt.setInt(8, swim.getButterfly());
			pstmt.setInt(9, swim.getTotaltime());

			result = pstmt.executeUpdate(); // DML 쿼리문 수행하기
			// -> 행 수를 반환함(수행할 내용이 없을 경우 0 반환)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt); // DB자원 닫기
		}
		return result;
	}

	// 메소드 선언 : DB에서 두개 테이블 합치고 해당 아이디와 월의 정보와 일치하는 운동기록 모두 반환하기(innerjoin, 기준 : id)
	public static List select(String id, int yy, int mm) { // 년, 월
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		List<Swim> list = new ArrayList<Swim>(); // 로그인 성공 시 운동기록 정보를 담아둘 List

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append(
				"select m.id as id, password, nickname, swim_idx, yy, mm, dd, freestyle, backstroke, breaststroke, butterfly, totaltime");
		sql.append(" from swmember m, swswim s");
		sql.append(" where m.id =s.id");
		sql.append(" and m.id=?  and yy=? and mm=? order by dd asc");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);// 바인드 변수 채워주기
			pstmt.setInt(2, yy);// 바인드 변수 채워주기
			pstmt.setInt(3, mm);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)

			while (rs.next()) { // 다음 레코드가 존재하지 않을 때까지
				Member member2 = new Member(); // 빈 DTO 생성
				Swim swim2 = new Swim(); // 빈 DTO 생성
				swim2.setMember(member2); // 서로 연결

				// DiaryPage에서 insert문을 통해 DTO를 DB에 담아놓았음. 이를 꺼내어 새로운 DTO에 담음
				member2.setId(rs.getString("id"));
				member2.setPassword(rs.getString("password"));
				member2.setNickname(rs.getString("nickname"));
				swim2.setSwim_idx(rs.getInt("swim_idx"));
				;
				swim2.setYy(rs.getInt("yy"));
				swim2.setMm(rs.getInt("mm"));
				swim2.setDd(rs.getInt("dd"));
				swim2.setFreestyle(rs.getInt("freestyle"));
				swim2.setBackstroke(rs.getInt("backstroke"));
				swim2.setBreaststroke(rs.getInt("breaststroke"));
				swim2.setButterfly(rs.getInt("butterfly"));
				swim2.setTotaltime(rs.getInt("totaltime"));

				// 채워진 DTO를 ArrayList에 추가하기
				list.add(swim2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs); // DB자원 닫기
		}
		return list;// 다이어리정보 반환
	}

	// 메소드 선언 : DB에서 두개 테이블 합치고 해당 아이디와 월의 정보와 일치하는 운동기록 한건 반환하기(innerjoin, 기준 : id)
	public static Swim select(String id, int yy, int mm, int dd) { // 년, 월
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		Swim swim = null; // 한건의 결과 담을 DTO

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append(
				"select m.id as id, password, nickname, swim_idx, yy, mm, dd, freestyle, backstroke, breaststroke, butterfly, totaltime");
		sql.append(" from swmember m, swswim s");
		sql.append(" where m.id =s.id");
		sql.append(" and m.id=?  and yy=? and mm=? and dd=?");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			System.out.println(sql.toString());
			pstmt.setString(1, id);// 바인드 변수 채워주기
			pstmt.setInt(2, yy);// 바인드 변수 채워주기
			pstmt.setInt(3, mm);
			pstmt.setInt(4, dd);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)

			if (rs.next()) { // 다음 레코드가 존재하지 않을 때까지
				Member member2 = new Member(); // 빈 DTO 생성
				swim = new Swim(); // 빈 DTO 생성
				swim.setMember(member2); // 서로 연결

				// DiaryPage에서 insert문을 통해 DTO를 DB에 담아놓았음. 이를 꺼내어 새로운 DTO에 담음
				member2.setId(rs.getString("id"));
				member2.setPassword(rs.getString("password"));
				member2.setNickname(rs.getString("nickname"));

				swim.setSwim_idx(rs.getInt("swim_idx"));
				swim.setYy(rs.getInt("yy"));
				swim.setMm(rs.getInt("mm"));
				swim.setDd(rs.getInt("dd"));
				swim.setFreestyle(rs.getInt("freestyle"));
				swim.setBackstroke(rs.getInt("backstroke"));
				swim.setBreaststroke(rs.getInt("breaststroke"));
				swim.setButterfly(rs.getInt("butterfly"));
				swim.setTotaltime(rs.getInt("totaltime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs); // DB자원 닫기
		}
		return swim;// 다이어리정보 반환
	}

	// 메소드 선언 : DB에서 해당 아이디와 월 정보와 일치하는 운동기록의 합 가져오기(자유형)
	// sum(?) 해서 자유형, 배영, 접영, 평영을 대입하고 싶었으나 바인드 변수는 집계 함수의 피연산자로 올 수 없음
	public static int sumFreestyle(String id, int yy, int mm) {
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append("select sum(freestyle) as result");
		sql.append(" from swmember m, swswim s");
		sql.append(" where m.id =s.id");
		sql.append(" and m.id  =? and yy=? and mm=?");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);
			pstmt.setInt(2, yy);
			pstmt.setInt(3, mm);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)
			
			if(rs.next()) {
				result = rs.getInt("result");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 메소드 선언 : DB에서 해당 아이디와 월 정보와 일치하는 운동기록의 합 가져오기(배영)
	public static int sumBackstroke(String id, int yy, int mm) {
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append("select sum(backstroke) as result");
		sql.append(" from swmember m, swswim s");
		sql.append(" where m.id =s.id");
		sql.append(" and m.id  =? and yy=? and mm=?");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);
			pstmt.setInt(2, yy);
			pstmt.setInt(3, mm);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)
			
			if(rs.next()) {
				result = rs.getInt("result");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 메소드 선언 : DB에서 해당 아이디와 월 정보와 일치하는 운동기록의 합 가져오기(평영)
	public static int sumBreaststroke(String id, int yy, int mm) {
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append("select sum(breaststroke) as result");
		sql.append(" from swmember m, swswim s");
		sql.append(" where m.id =s.id");
		sql.append(" and m.id  =? and yy=? and mm=?");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);
			pstmt.setInt(2, yy);
			pstmt.setInt(3, mm);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)
			
			if(rs.next()) {
				result = rs.getInt("result");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 메소드 선언 : DB에서 해당 아이디와 월 정보와 일치하는 운동기록의 합 가져오기(접영)
	public static int sumButterfly(String id, int yy, int mm) {
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append("select sum(butterfly) as result");
		sql.append(" from swmember m, swswim s");
		sql.append(" where m.id =s.id");
		sql.append(" and m.id  =? and yy=? and mm=?");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);
			pstmt.setInt(2, yy);
			pstmt.setInt(3, mm);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)
			
			if(rs.next()) {
				result = rs.getInt("result");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//메소드 선언 : 수정하기
	public int update(Swim swim) {
		int result =0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con = dbManager.getConnection();
		String sql = "update swdiary set freestyle=?, backstroke=?, breaststroke=?, butterfly=?, totaltime=? where swim_idx=?";
	
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, swim.getFreestyle());
			pstmt.setInt(2, swim.getBackstroke());
			pstmt.setInt(3, swim.getBreaststroke());
			pstmt.setInt(4, swim.getButterfly());
			pstmt.setInt(5, swim.getTotaltime());
			pstmt.setInt(6, swim.getSwim_idx());
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	// 메소드 선언 : 삭제하기
		public int delete(String id, int yy, int mm, int dd) {
			int result = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			con = dbManager.getConnection();
			String sql = "delete from swswim where id=? and yy=? and mm=? and dd=?";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, yy);
				pstmt.setInt(3, mm);
				pstmt.setInt(4, dd);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt);
			}
			return result;
		}
}
