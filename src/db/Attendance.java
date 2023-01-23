//Attendance(출석일수X, 목표일수O)를 담을 DTO을 정의함

package db;

public class Attendance {
	//필드 선언
	private int attendance_idx; //기본키
	private Member member; //아이디 : String id(부모의 pk)보다 부모 자체를 보유해야 사용이 편함
	private int yy;//연
	private int mm;//월
	private int target; //목표
	
	// ------------------------------------------------------------------------

	//setter, getter
	public int getAttendance_idx() {
		return attendance_idx;
	}
	public void setAttendance_idx(int attendance_idx) {
		this.attendance_idx = attendance_idx;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public int getYy() {
		return yy;
	}
	public void setYy(int yy) {
		this.yy = yy;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
}
