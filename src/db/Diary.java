//Diary를 담을 DTO을 정의함

package db;

public class Diary {
	//필드 선언
	private int diary_idx;  //기본키
	private Member member; //아이디 : String id(부모의 pk)보다 부모 자체를 보유해야 사용이 편함
	private int yy;//연
	private int mm;//월
	private int dd;//일
	private String memo;//메모
	private String feeling;//평가
	
	// ------------------------------------------------------------------------

	//setter, getter
	public int getDiary_idx() {
		return diary_idx;
	}
	public void setDiary_idx(int diary_idx) {
		this.diary_idx = diary_idx;
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
	public int getDd() {
		return dd;
	}
	public void setDd(int dd) {
		this.dd = dd;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
}
