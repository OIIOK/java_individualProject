//4. 소통페이지

package page;

import java.awt.Color;

import main.SDiaryMain;

public class MapPage extends Page{
	//필드 선언
	// ------------------------------------------------------------------------
	//생성자 선언
	public MapPage(SDiaryMain sDiaryMain) {
		super(sDiaryMain); //Page에서 변수로 선언되어 있으므로 연결만 하면 됨
		this.setBackground(Color.DARK_GRAY);
	}
	// ------------------------------------------------------------------------
	//메소드 선언
}
