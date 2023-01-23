//Diary에 관련한 DAO(쿼리문)를 정의함

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {

	DBManager dbManager = DBManager.getInstance();
	private Diary diary2;

	// 메소드 선언 : DB에 다이어리 등록하기
	public int insert(Diary diary) { // DiaryDTO
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		String sql = "insert into swdiary(diary_idx, id, yy, mm, dd, memo, feeling)";
		sql += " values(seq_swdiary.nextval,?,?,?,?,?,?)";
		// diary_idx는 시퀀스로 부여할 예정이고 id는 부모의 외래키를 포함한 나머지는 DiaryDTO에서 받아올 예정임

		try {
			pstmt = con.prepareStatement(sql); // 컴파일된 sql문 가져오기
			pstmt.setString(1, diary.getMember().getId());// 바인드 변수 채워주기. 부모의 외래키 꺼내기
			pstmt.setInt(2, diary.getYy());
			pstmt.setInt(3, diary.getMm());
			pstmt.setInt(4, diary.getDd());
			pstmt.setString(5, diary.getMemo());
			pstmt.setString(6, diary.getFeeling());

			result = pstmt.executeUpdate(); // DML 쿼리문 수행하기
			// -> 행 수를 반환함(수행할 내용이 없을 경우 0 반환)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt); // DB자원 닫기
		}
		return result;
	}

	// 메소드 선언 : DB에서 두개 테이블 합치고 해당 아이디와 월의 정보와 일치하는 다이어리 모두 반환하기(innerjoin, 기준 : id)
	public List select(String id, int yy, int mm) { // 아이디, 년, 월
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		List<Diary> list = new ArrayList<Diary>(); // 로그인 성공 시 다이어리 정보를 담아둘 List

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append("select m.id as id, password, nickname, diary_idx, yy, mm, dd, memo, feeling");
		sql.append(" from swmember m, swdiary d");
		sql.append(" where m.id =d.id");
		sql.append(" and m.id=? and yy=? and mm=? order by dd asc");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);// 바인드 변수 채워주기
			pstmt.setInt(2, yy);
			pstmt.setInt(3, mm);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)

			while (rs.next()) { // 다음 레코드가 존재하지 않을 때까지
				Member member2 = new Member(); // 빈 DTO 생성
				Diary diary2 = new Diary(); // 빈 DTO 생성
				diary2.setMember(member2); // 서로 연결

				// DiaryPage에서 insert문을 통해 DTO를 DB에 담아놓았음. 이를 꺼내어 새로운 DTO에 담음
				member2.setId(rs.getString("id"));
				member2.setPassword(rs.getString("password"));
				member2.setNickname(rs.getString("nickname"));
				diary2.setDiary_idx(rs.getInt("diary_idx"));
				diary2.setYy(rs.getInt("yy"));
				diary2.setMm(rs.getInt("mm"));
				diary2.setDd(rs.getInt("dd"));
				diary2.setMemo(rs.getString("memo"));
				diary2.setFeeling(rs.getString("feeling"));

				// 채워진 DTO를 ArrayList에 추가하기
				list.add(diary2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs); // DB자원 닫기
		}
		return list;// 다이어리정보 반환
	}

	// 메소드 오버로드 : DB에서 두개 테이블 합치고 해당 아이디와 날짜의 정보와 일치하는 다이어리 한건 반환하기(innerjoin, 기준 :
	// id)
	public Diary select(String id, int yy, int mm, int dd) { // 년, 월
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		Diary diary2 = null;

		con = dbManager.getConnection(); // Connection 가져오기

		StringBuffer sql = new StringBuffer(); // 수정 가능한 String
		sql.append("select m.id as id, password, nickname, diary_idx, yy, mm, dd, memo, feeling");
		sql.append(" from swmember m, swdiary d");
		sql.append(" where m.id =d.id");
		sql.append(" and m.id=? and yy=? and mm=? and dd=?");

		try {
			pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
			pstmt.setString(1, id);// 바인드 변수 채워주기
			pstmt.setInt(2, yy);// 바인드 변수 채워주기
			pstmt.setInt(3, mm);
			pstmt.setInt(4, dd);

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)

			if (rs.next()) { // 다음 레코드가 존재하지 않을 때까지
				Member member2 = new Member(); // 빈 DTO 생성
				diary2 = new Diary(); // 빈 DTO 생성
				diary2.setMember(member2); // 서로 연결

				// DiaryPage에서 insert문을 통해 DTO를 DB에 담아놓았음. 이를 꺼내어 새로운 DTO에 담음
				member2.setId(rs.getString("id"));
				member2.setPassword(rs.getString("password"));
				member2.setNickname(rs.getString("nickname"));

				diary2.setDiary_idx(rs.getInt("diary_idx"));
				diary2.setYy(rs.getInt("yy"));
				diary2.setMm(rs.getInt("mm"));
				diary2.setDd(rs.getInt("dd"));
				diary2.setMemo(rs.getString("memo"));
				diary2.setFeeling(rs.getString("feeling"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs); // DB자원 닫기
		}
		return diary2;// 다이어리정보 반환
	}

	// 메소드 선언 : 수정하기
	public int update(Diary diary) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();
		String sql = "update swdiary set memo=?, feeling=? where diary_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(diary.getMemo()));
			pstmt.setInt(2, Integer.parseInt(diary.getFeeling()));
			pstmt.setInt(3, diary.getDiary_idx());
			result = pstmt.executeUpdate();
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
		String sql = "delete from swdiary where id=? and yy=? and mm=? and dd=?";
		

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
