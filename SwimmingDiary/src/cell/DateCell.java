//날짜(DATE) 셀을 정의

//66~69 보는 용

package cell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import db.Diary;
import db.DiaryDAO;
import db.Swim;
import db.SwimDAO;
import main.SDiaryMain;
import page.DiaryPage;
import page.LoginPage;
import page.SidePage;

public class DateCell extends Cell {
	// 필드 선언
	SDiaryMain sDiaryMain;
	// DiaryPage diaryPage;
	SidePage sidePage;
	DiaryDAO diaryDAO = new DiaryDAO();
	SwimDAO swimDAO = new SwimDAO();
	public String feeling;
	public String totaldistance;
	public String totaltime;
	public Color color = Color.WHITE;
	// ------------------------------------------------------------------------

	// 생성자 선언
	public DateCell(SDiaryMain sDiaryMain, SidePage sidePage, String title, String feeling, String totaldistance,
			String totaltime, int fontSize, int x, int y) {
		super(title, fontSize, x, y);
		this.sDiaryMain = sDiaryMain;
		// this.diaryPage = diaryPage;
		this.sidePage = sidePage;
		this.feeling = feeling;
		this.totaldistance = totaldistance;
		this.totaltime = totaltime;

		// 셀 테두리 주기
		Border border = new LineBorder(Color.DARK_GRAY);
		this.setBorder(border); // ---------------------------------------------------------안나옴

		// 셀 클릭 시 이벤트 생성 : 콤보박스에 해당 날짜 채우기
		this.addMouseListener(new MouseAdapter() {
			private int title;

			public void mouseClicked(MouseEvent e) {
				// 내용 채우기
				sidePage.setTextField(); // yy,mm
				sidePage.t_date.setText(DateCell.this.title); // dd
				
				sidePage.getDetail();
				
				// setBackground는 페인트 때문에 먹히지 않음
				if (color == Color.WHITE) {
					color = new Color(240, 240, 240); // 색 주기

					// 필요한 정보 연결하기
					LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE];
					DiaryPage diaryPage = (DiaryPage) sDiaryMain.pages[SDiaryMain.DIARYPAGE];
					String id = loginpage.member.getId();
					int yy = diaryPage.currentCalendar.get(Calendar.YEAR);
					int mm = diaryPage.currentCalendar.get(Calendar.MONTH) + 1;
					int dd = this.title;
					
				} else {
					color = Color.WHITE;
				}
				repaint();
			}
		});
	}

	// ------------------------------------------------------------------------

	// @Override : 그리기
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // Graphics2D로 업그레이드
		g2.clearRect(0, 0, 120, 120); // 새로 고침

		g2.setColor(color); // 물감 선택
		g2.fillRect(0, 0, 110, 100); // 채우기 (Cell 크기 : 100, 70)

		g2.setColor(new Color(56, 76, 159)); // 물감 선택
		Font font = new Font("굴림", Font.BOLD, fontSize);
		g2.setFont(font); // 폰트 세팅
		g2.drawString(title, x - 40, y); // 날짜 그리기
		g2.drawString(feeling, x + 10, y); // 내용 그리기

		g2.setColor(Color.DARK_GRAY); // 물감 선택
		Font font2 = new Font("굴림", Font.PLAIN, fontSize);
		g2.setFont(font); // 폰트 세팅
		g2.drawString(totaldistance, x - 15, y + 20); // 내용 그리기
		g2.drawString(totaltime, x - 15, y + 40); // 내용 그리기
	}
}
