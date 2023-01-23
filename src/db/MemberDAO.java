//Member(아이디, 비밀번호, 별명)에 관련한 DAO(쿼리문)을 정의함

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
	// 필드 선언
	DBManager dbManager = DBManager.getInstance(); // DBManager 가져오기
	
	// ------------------------------------------------------------------------

	// 메소드 선언 : DB에 회원 등록하기
	public int insert(Member member) { // UserDTO
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		int result = 0;

		con = dbManager.getConnection(); // Connection 가져오기

		String sql = "insert into swmember(id, password, nickname)";
		sql += " values(?,?,?)";
		// id, password, nickname은 UserDTO에서 받아올 예정이니 매개변수로 UserDTO 연결함

		try {
			pstmt = con.prepareStatement(sql); // 컴파일된 sql문 가져오기

			pstmt.setString(1, member.getId());// 바인드 변수 채워주기
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());

			result = pstmt.executeUpdate(); // DML 쿼리문 수행하기
			// -> 행 수를 반환함(수행할 내용이 없을 경우 0 반환)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt); // DB자원 닫기
		}
		return result;
	}

	// 메소드 선언 : DB에서 입력 정보와 일치하는 회원 찾아 정보 반환하기
	public Member select(Member member) { // UserDTO
		Connection con = null; // Connection
		PreparedStatement pstmt = null; // 컴파일된 sql문
		ResultSet rs = null; // sql문 실행 결과 테이블
		Member member2 = null; // 로그인 성공 시 회원 정보를 담아둘 DTO

		con = dbManager.getConnection(); // Connection 가져오기

		String sql = "select * from swmember where id=? and password=?";
		// id, password는 UserDTO에서 받아올 예정이니 매개변수로 UserDTO 연결함

		try {
			pstmt = con.prepareStatement(sql); // 컴파일된 sql문 가져오기

			pstmt.setString(1, member.getId());// 바인드 변수 채워주기
			pstmt.setString(2, member.getPassword());

			rs = pstmt.executeQuery(); // 쿼리문 수행하기
			// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)
			if (rs.next()) { // 0에서 시작하여 이동할 다음 레코드가 존재한다면(=일치하는 회원이 있다면)
				// -> 일치하는 회원의 레코드는 1건이기 때문에 반복문이 아닌 조건문을 사용함
				member2 = new Member(); // 빈 DTO 생성
				// JoinPage에서 insert문을 통해 DTO를 DB에 담아놓았음. 이를 꺼내어 새로운 DTO에 담음
				member2.setId(rs.getString("id"));
				member2.setPassword(rs.getString("password"));
				member2.setNickname(rs.getString("nickname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs); // DB자원 닫기
		}
		return member2; // 회원정보 반환
	}
}