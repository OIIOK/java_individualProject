//요일(WEEK) 셀을 정의

package cell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WeekCell extends Cell{
	//생성자 선언
	public WeekCell(String title, int fontSize, int x, int y) {
		super(title, fontSize, x, y);
	}
	
	// ------------------------------------------------------------------------
	
	//	@Override : 그리기
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g; //Graphics2D로 업그레이드
		
		//셀 설정
		g2.setColor(Color.DARK_GRAY); //물감 선택
		g2.fillRect(0, 0, 110, 100); //채우기 (Cell 크기 : 100, 70)
		
		//폰트 설정
		g2.setColor(Color.WHITE); //물감 선택
		Font font = new Font("카페24 써라운드", Font.BOLD, fontSize);
		g2.setFont(font); //폰트 세팅
		g2.drawString(title, x, y); // 폰트 가로, 세로 위치
	}
}
