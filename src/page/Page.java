package page;
//모든 Page들의 공통 속성을 정의함

import java.awt.Dimension;
import javax.swing.JPanel;
import main.SDiaryMain;

public class Page extends JPanel{
	//필드 선언
	SDiaryMain sDiaryMain; //Page에서 대표로 Main 상속
	
	// ------------------------------------------------------------------------
	
	//생성자 선언
	public Page(SDiaryMain sDiaryMain) {
		this.sDiaryMain=sDiaryMain;
		setPreferredSize(new Dimension(sDiaryMain.PAGE_WIDTH, sDiaryMain.PAGE_HEIGHT)); //1000,700----------->크기조절하기!!
	}
}
