//Swim을 담을 DTO을 정의함

package db;

public class Swim {
//필드 선언
	private int swim_idx; //기본키, 
	private Member member; //아이디 : String id(부모의 pk)보다 부모 자체를 보유해야 사용이 편함
	private int yy;//연
	private int mm;//월
	private int dd;//일
	private int freestyle; //자유형
	private int backstroke; //배영
	private int breaststroke; //평영
	private int butterfly; //접영
	private int totaltime; //총 활동시간
	
	// ------------------------------------------------------------------------

	//setter, getter
	public int getSwim_idx() {
		return swim_idx;
	}
	public void setSwim_idx(int swim_idx) {
		this.swim_idx = swim_idx;
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
	public int getFreestyle() {
		return freestyle;
	}
	public void setFreestyle(int freestyle) {
		this.freestyle = freestyle;
	}
	public int getBackstroke() {
		return backstroke;
	}
	public void setBackstroke(int backstroke) {
		this.backstroke = backstroke;
	}
	public int getBreaststroke() {
		return breaststroke;
	}
	public void setBreaststroke(int breaststroke) {
		this.breaststroke = breaststroke;
	}
	public int getButterfly() {
		return butterfly;
	}
	public void setButterfly(int butterfly) {
		this.butterfly = butterfly;
	}
	public int getTotaltime() {
		return totaltime;
	}
	public void setTotaltime(int totaltime) {
		this.totaltime = totaltime;
	}
}
