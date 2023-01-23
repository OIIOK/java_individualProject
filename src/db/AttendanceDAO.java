//Attendance(출석일수X, 목표일수O)에 관련한 DAO을 정의함

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDAO {
	
	static DBManager dbManager = DBManager.getInstance();
	
	// 메소드 선언 : DB에 목표일수 등록하기
		public int insert(Attendance attendance) { // AttendanceDTO
			Connection con = null; // Connection
			PreparedStatement pstmt = null; // 컴파일된 sql문
			int result = 0;
			
			con = dbManager.getConnection(); // Connection 가져오기
			
			String sql = "insert into swattendance(attendance_idx, id, yy, mm, target)";
			sql += " values(seq_swattendance.nextval,?,?,?,?)";
			// diary_idx는 시퀀스로 부여할 예정이고 id는 부모의 외래키를 포함한 나머지는 AttendanceDTO에서 받아올 예정임

			try {
				pstmt = con.prepareStatement(sql); // 컴파일된 sql문 가져오기
				pstmt.setString(1, attendance.getMember().getId());// 바인드 변수 채워주기. 부모의 외래키 꺼내기
				pstmt.setInt(2, attendance.getYy());
				pstmt.setInt(3, attendance.getMm());
				pstmt.setInt(4, attendance.getTarget());

				result = pstmt.executeUpdate(); // DML 쿼리문 수행하기
				// -> 행 수를 반환함(수행할 내용이 없을 경우 0 반환)
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt); // DB자원 닫기
			}
			return result;
		}

		
		// 메소드 선언 : DB에서 두개 테이블 합치고 해당 월의 정보와 일치하는 목표일수 한건 반환하기(innerjoin, 기준 : id)
				public static Attendance select(String id, int yy, int mm) { // 년, 월
					Connection con = null; // Connection
					PreparedStatement pstmt = null; // 컴파일된 sql문
					ResultSet rs = null; // sql문 실행 결과 테이블
					Attendance  attendance=null; //한건의 결과 담을 DTO
				
					con = dbManager.getConnection(); // Connection 가져오기

					StringBuffer sql = new StringBuffer(); // 수정 가능한 String
					sql.append(" select m.id as id, password, nickname, attendance_idx, yy, mm, target");
					sql.append(" from swmember m, swattendance a");
					sql.append(" where m.id =a.id");
					sql.append(" and m.id=?  and yy=? and mm=?");
					
					try {
						pstmt = con.prepareStatement(sql.toString()); // StringBuffer 길이 확정. 컴파일된 sql문 가져오기
						pstmt.setString(1, id);// 바인드 변수 채워주기
						pstmt.setInt(2, yy);
						pstmt.setInt(3, mm);
						
						rs = pstmt.executeQuery(); // 쿼리문 수행하기
						// -> ResultSet를 반환함(수행할 내용이 없을 경우 null 반환)

						if (rs.next()) { // 다음 레코드가 존재하지 않을 때까지
							Member member2 = new Member(); // 빈 DTO 생성
							attendance = new Attendance(); // 빈 DTO 생성
							attendance.setMember(member2); // 서로 연결

							// DiaryPage에서 insert문을 통해 DTO를 DB에 담아놓았음. 이를 꺼내어 새로운 DTO에 담음
							member2.setId(rs.getString("id"));
							member2.setPassword(rs.getString("password"));
							member2.setNickname(rs.getString("nickname"));
							
							attendance.setAttendance_idx(rs.getInt("attendance_idx"));
							attendance.setYy(rs.getInt("yy"));
							attendance.setMm(rs.getInt("mm"));
							attendance.setTarget(rs.getInt("target"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						dbManager.release(pstmt, rs); // DB자원 닫기
					}
					return attendance;// 목표일수정보 반환
				}

}

