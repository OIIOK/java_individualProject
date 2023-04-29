//Main Page를 정의 

package main;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import db.DBManager;
import page.DetailPage;
import page.DiaryPage;
import page.JoinPage;
import page.LoginPage;
import page.MapPage;
import page.Page;
import page.SidePage;

public class SDiaryMain extends JFrame {

	// 필드 선언
	static SidePage sidePage;
	JPanel container; // 페이지를 담을 패널
	public JPanel TabContainer; // 탭을 담을 패널
	JTabbedPane tabbedPane; // 탭

	// 페이지 관련
	public Page pages[] = new Page[5]; // 페이지
	public static final int LOGINPAGE = 0; // 페이지 상수로 표현
	public static final int JOINPAGE = 1;
	public static final int DIARYPAGE = 2;
	public static final int BOARDPAGE = 3;
	public static final int MAPPAGE = 4;

	public static final int PAGE_WIDTH = 1000;
	public static final int PAGE_HEIGHT = 730; // 700------------------------------>크기조절하기!!

	// DB 관련
	DBManager dbManager = DBManager.getInstance();

	// ------------------------------------------------------------------------

	// 생성자 선언
	public SDiaryMain(SidePage sidePage) {
		// 제목 생성
		super("Let's Swimming!");
		this.sidePage = sidePage;

		// 페이지 생성
		container = new JPanel();
		container.setPreferredSize(new Dimension(PAGE_WIDTH, PAGE_HEIGHT));
		
		createPage(); // 페이지 생성
		
		add(container);

		// 탭 생성
		TabContainer = new JPanel();
		tabbedPane = createTabbedPane();
		TabContainer.add(tabbedPane);
		container.add(TabContainer);
		TabContainer.setVisible(true);
		pages[0].setVisible(false);
		pages[1].setVisible(false);

		// 윈도우세팅
		setSize(1000, 700);
		setVisible(true);
		//setResizable(false); // 사이즈 변경 불가//---------------------------------------------
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});

		// 기본으로 보여질 페이지 번호
		showHide(LOGINPAGE); 
	}

	// ------------------------------------------------------------------------

	// 메소드 선언 : 페이지 생성
	public void createPage() {
		
		// 생성
		pages[0] = new LoginPage(this);
		pages[1] = new JoinPage(this);
		
		DiaryPage d = new DiaryPage(this);
		DetailPage b = new DetailPage(this);
		MapPage m = new MapPage(this);
		
		pages[2] = d;
		pages[3] = b;
		pages[4] = m;

		// 부착
		for (int i = 0; i < pages.length; i++) {
			container.add(pages[i]);
		}
	}

	// 메소드 선언 : 탭 생성
	public JTabbedPane createTabbedPane() {
		// 생성
		tabbedPane = new JTabbedPane();

		// 부착
		tabbedPane.addTab("Calender", pages[2]);
		tabbedPane.addTab("Diary", pages[3]);
		//tabbedPane.addTab("Board", pages[4]);

		return tabbedPane; // 반환
	}

	// 메소드 선언 : 화면 전환
	public void showHide(int page) {
		for (int i = 0; i < pages.length; i++) {
			if (page == i) {
				pages[i].setVisible(true);
			} else {
				pages[i].setVisible(false);
			}
		}
	}

	// 메인메소드 선언
	public static void main(String[] args) {
		new SDiaryMain(sidePage);
	}
}
