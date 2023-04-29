//Member(아이디, 비밀번호, 별명)을 담을 DTO를 정의함

package db;

public class Member {
	//필드 선언
	private String id;
	private String password ;
	private String nickname ;
	
	// ------------------------------------------------------------------------
	
	//setter, gettter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
