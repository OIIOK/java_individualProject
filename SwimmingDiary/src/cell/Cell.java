//요일(WEEK)과 날짜(DATE) 셀 생성 시 공통되는 요소를 따로 선언함

package cell;

import java.awt.Font;
import javax.swing.JPanel;

//클래스 선언
public class Cell extends JPanel { // paint 메소드 사용 위해 JPanel 상속받음
	// 필드 선언
	public String title; // 출력 제목(-> 요일, 날짜)
	int fontSize; // 폰트 사이즈
	int x; // 출력 위치
	int y; // 출력 위치
	
	// ------------------------------------------------------------------------
	
	// 생성자 선언
	public Cell(String title, int fontSize, int x, int y) {
		this.title = title;
		this.fontSize = fontSize;
		this.x = x;
		this.y = y;
	}
}