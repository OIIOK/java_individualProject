
//3. 일기페이지

package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.sun.tools.javac.Main;

import cell.DateCell;
import cell.WeekCell;
import db.Attendance;
import db.AttendanceDAO;
import db.Diary;
import db.DiaryDAO;
import db.Swim;
import db.SwimDAO;
import main.SDiaryMain;

public class DiaryPage extends Page {
	// 필드 선언

	SidePage sidePage;
	
	int westWidth = 300;
	int pageHeight = 600;
	String setDate;

	// 서쪽영역(통계, 등록, 수정삭제 담을 예정)
	JPanel p_west;
	JPanel p_side; // 사이드 페이지 영역
	JLabel la_icon; // 아이콘

	// 센터영역(출석율, 달력 담을 예정)
	JPanel p_center;
	JPanel p_north2; // 목표 영역
	JLabel la_target; // 목표 문구
	public JTextField t_target; // 목표 입력란
	JButton bt_regist; // 목표 등록버튼
	JButton bt_edit; // 목표 수정버튼
	JLabel la_target2; // 목표 문구
	JLabel la_achievement; // 달성률 문구
	JProgressBar jProgressBar; // 달성률 프로그래스바
	JPanel p_celender_t; // 달력_제목 영역
	JLabel la_celender_t;// 달력_제목
	JButton bt_prev; // 달력_이전 버튼
	JButton bt_next;// 달력_다음 버튼
	JPanel p_celender_w; // 달력_요일 영역
	JPanel p_celender_d; // 달력_날짜 영역

	// 에니메이션 관련
	Thread loopThread; // 에니메이션 구현을 위한 스레드
	double x = -300; // 현재위치
	double targetX; // 목표지점
	double a = 0.05; // 비율계수
	boolean fold = true; // 논리값

	// 출석율 관련
	Thread prograssBarThread; // 프로그래스바 스레드
	int min = 0;
	int max = 100;
	int n; // 진행률
	// int velX; // 진행률의 증가 폭을 결정함
	// int time; // 스레드의 sleep() 속도를 결정함
	// boolean flag = true; //논리값

	// 요일 관련
	WeekCell[] weekCells = new WeekCell[7];
	String[] weekCellTitles = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };

	// 날짜 관련
	DateCell[][] dateCells = new DateCell[6][7];

	// 현재 날짜 정보
	public Calendar currentCalendar = Calendar.getInstance(); // 선택한 월의 달력
	public String date;

	// DB 관련
	DiaryDAO diaryDAO = new DiaryDAO();
	AttendanceDAO attendanceDAO = new AttendanceDAO();

	// 생성자 선언
	public DiaryPage(SDiaryMain sDiaryMain) {
		super(sDiaryMain); // Page에서 변수로 선언되어 있으므로 연결만 하면 됨
		DetailPage boardPage = (DetailPage) sDiaryMain.pages[sDiaryMain.BOARDPAGE];			
		sidePage = new SidePage(sDiaryMain, this);

		this.setLayout(new BorderLayout());
		//this.setBackground(Color.YELLOW);
		Font font = new Font("굴림", Font.BOLD, 15);
		int height = 30;

		// 서쪽영역(통계, 등록, 수정삭제 담을 예정)
		// 생성
		try {
			p_west = new JPanel() {
				//Image background = new ImageIcon(Main.class.getResource("../data/supplies.png")).getImage();
				BufferedImage background = ImageIO.read(new File("E:\\Workspace\\java_individualProject\\SwimmingDiary\\src\\data\\supplies.png"));
				public void paint(Graphics g) {
					g.drawImage(background, 0, 0, null);
				}
			};
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		p_side = new JPanel();

		// 디자인
		p_west.setLayout(null);
		p_west.setPreferredSize(new Dimension(westWidth, pageHeight));
		// p_west.setBackground(Color.RED);
		
		//사이드패널
		p_side.setLayout(null);
		p_side.setPreferredSize(new Dimension(westWidth, pageHeight + 25));
		p_side.setBackground(new Color(56, 76, 159));
		// 아이콘 생성
		try {
			URL url = new URL(
					"https://cdn1.iconfinder.com/data/icons/academics-white-with-multicolor-round-corner-backg/2048/Pencil-64.png");
			ImageIcon Icon = new ImageIcon(url); // 사이즈조절
			Image image = Icon.getImage();
			Image resizedImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(resizedImage);
			la_icon = new JLabel(resizedIcon);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		la_icon.setBounds(260, 10, 40, 40);

		// 센터영역(목표달성률, 달력 담을 예정)
		// 생성
		p_center = new JPanel();
		p_north2 = new JPanel();
		la_target = new JLabel("목표 출석일수는 : ", JLabel.LEFT);
		t_target = new JTextField("0");
		la_target2 = new JLabel("일!");
		bt_regist = new JButton("등록");
		bt_edit = new JButton("수정");
		la_achievement = new JLabel("달성률 : ");
		jProgressBar = new JProgressBar(min, max);
		p_celender_t = new JPanel();
		la_celender_t = new JLabel("///월 ///일"); // 변수 대입할 예정
		bt_prev = new JButton("<<");
		bt_next = new JButton(">>");
		p_celender_w = new JPanel();
		p_celender_d = new JPanel();
		// 디자인
		// p_center.setBackground(Color.MAGENTA);
		p_north2.setPreferredSize(new Dimension(sDiaryMain.PAGE_WIDTH - westWidth, 100)); // 700,100
		p_north2.setLayout(null);
		//p_north2.setBackground(new Color(56, 76, 159));
		la_target.setBounds(10, 20, 130, height - 2);
		// la_target.setBackground(Color.ORANGE); // 배경색상
		// la_target.setOpaque(true);
		la_target.setFont(font);
		la_target.setForeground(Color.BLACK); // 글색상
		t_target.setPreferredSize(new Dimension(30, 25));
		t_target.setBounds(140, 20, 30, height - 4);
		t_target.setFont(font);
		la_target2.setBounds(170, 20, 30, height - 2);
		la_target2.setFont(font);
		la_target2.setForeground(Color.BLACK); // 글색상
		bt_regist.setBounds(200, 20, 60, height - 4);
		bt_edit.setBounds(265, 20, 60, height - 4);
		la_achievement.setFont(font);
		la_achievement.setBounds(10, 50, 65, height + 2);
		la_achievement.setOpaque(true);
		la_achievement.setFont(font);
		la_achievement.setForeground(Color.BLACK); // 글색상
		jProgressBar.setBounds(75, 50, 600, height + 2);
		jProgressBar.setForeground(new Color(241, 92, 34));
		jProgressBar.setBackground(new Color(230, 230, 230));
		jProgressBar.setStringPainted(true); // 진행률 표시
		p_celender_t.setPreferredSize(new Dimension(sDiaryMain.PAGE_WIDTH - westWidth, 50)); // 700,100
		//p_celender_t.setBackground(Color.WHITE);
		la_celender_t.setFont(new Font("카페24 써라운드", Font.BOLD, 23)); // 제목 크기
		la_celender_t.setForeground(new Color(56, 76, 159)); // 글색상
		p_celender_w.setLayout(new GridLayout(1, 7)); // Cell을 넣을 칸을 나누어줌
		p_celender_w.setPreferredSize(new Dimension(sDiaryMain.PAGE_WIDTH - westWidth, 50)); // 700,100
		p_celender_w.setBackground(Color.DARK_GRAY);
		p_celender_d.setLayout(new GridLayout(6, 7));
		p_celender_d.setPreferredSize(new Dimension(sDiaryMain.PAGE_WIDTH - westWidth, (pageHeight - 200))); // 700,100
		p_celender_d.setBackground(Color.WHITE);

		// 부착
		p_side.add(la_icon);
		p_side.add(sidePage);
		p_west.add(p_side);
		add(p_west, BorderLayout.WEST);
		add(p_center);
		p_north2.add(la_target);
		p_north2.add(t_target);
		p_north2.add(la_target2);
		p_north2.add(bt_regist);
		p_north2.add(bt_edit);
		p_north2.add(la_achievement);
		p_north2.add(jProgressBar);
		p_center.add(p_north2);
		p_celender_t.add(bt_prev);
		p_celender_t.add(la_celender_t);
		p_celender_t.add(bt_next);
		p_center.add(p_celender_t);
		p_center.add(p_celender_w);
		p_center.add(p_celender_d);

		// 에니메이션 스레드 생성
		loopThread = new Thread() {
			public void run() {
				while (true) {
					tick(); // 사이드바의 물리적 변화
					render(); // 사이드바 출력
					try {
						Thread.sleep(10);// 스레드 속도
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopThread.start();

		// 햄버거 메뉴와 리스너 연결
		la_icon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fold = !fold;
				if (fold) {
					targetX = 0;
				} else {
					targetX = -260;
				}
			}
		});

		// 목표 등록 버튼에 이벤트 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				targetRegist(); // 목표일수 등록
			}
		});

		// target 입력란에 엔터키 => 등록 버튼 이벤트 연결
		t_target.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					targetRegist(); // 목표일수 등록
				}
			}
		});

		// 다음 버튼에 이벤트 연결 : 다음 월로 달력 이동
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 월은 0부터 시작하며 11(=12월)이 지나면 다시 0으로 리셋됨
				// get : 현재 월을 얻어와
				int getMm = currentCalendar.get(Calendar.MONTH);
				// System.out.println("현재 월 : " + getMm); // 현재 월 : 11 (-> 12월)

				// set : 다음 월로 수정하기
				currentCalendar.set(Calendar.MONTH, getMm + 1); // 항목, 내용
				// System.out.println("수정된 월 : " + currentCalendar.get(Calendar.MONTH)); // 수정된
				// 월 : 0 (-> 1월)
				
				t_target.setText("0");
				printCalender(); // 달력 내용 출력
				printAttendance(); // 목표일수 출력과 jProgressBar 출력
			}
		});

		// 이전 버튼에 이벤트 연결 : 이전 월로 달력 이동
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 월은 0부터 시작하며 11(=12월)이 지나면 다시 0으로 리셋됨
				// get : 현재 월을 얻어와
				int mm = currentCalendar.get(Calendar.MONTH);
				// System.out.println("현재 월 : " + mm); // 현재 월 : 11 (-> 12월)

				// set : 이전 월로 수정하기
				currentCalendar.set(Calendar.MONTH, mm - 1); // 항목, 내용
				// System.out.println("수정된 월 : " + currentCalendar.get(Calendar.MONTH)); // 수정된
				// 월 : 10 (-> 9월)
				t_target.setText("0");
				printCalender(); // 달력 내용 출력
				printAttendance(); // 목표일수 출력과 jProgressBar 출력
			}
		});
	}

	// ------------------------------------------------------------------------
	
	// 메소드 선언 : 사이드바의 물리적 변화
	public void tick() {
		x = x + a * (targetX - x);
	}

	// 메소드 선언 : 사이드바 출력
	public void render() {
		p_side.setBounds((int) x, 0, westWidth, pageHeight + 25);
		p_side.updateUI(); // 새로고침
	}

	// 메소드 선언 : 목표일수 등록
	public void targetRegist() {
		// 빈 DTO 생성
		Attendance attendance = new Attendance();
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[sDiaryMain.LOGINPAGE];
		attendance.setMember(loginpage.member);
		// 내용 가져오기
		String yy = sidePage.t_year.getText();
		String mm = sidePage.t_month.getText();
		System.out.println("t_target에서 가져온 목표일수 : " + t_target.getText());
		String target = t_target.getText();
		// DTO에 정보 담기
		attendance.setYy(currentCalendar.get(Calendar.YEAR));
		attendance.setMm(currentCalendar.get(Calendar.MONTH) + 1); // 달은 0부터 시작함
		attendance.setTarget(Integer.parseInt(target));
		// DAO에 넘겨 DB테이블에 담기
		int result = attendanceDAO.insert(attendance);

		if (result > 0) {
			JOptionPane.showMessageDialog(this, "등록성공");
		} else {
			JOptionPane.showMessageDialog(this, "목표일수를 입력하세요");
		}
	}
	
	// 메소드 선언 : 목표일수 출력과 jProgressBar 출력
	public void printAttendance() {

		// 선택한 월의 달력에 다이어리가 몇개 등록되어 있는지 구하기
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE];
		String id = loginpage.member.getId();
		
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH) + 1;
		List<Diary> diaryList = diaryDAO.select(id, yy, mm); // 다이어리
		Attendance attendance = AttendanceDAO.select(id, yy, mm); // 목표일자
		int target = attendance.getTarget();

		if (attendance != null) { // 해당하는 날에 가져온 레코드가 있다면 = 목표일수를 등록했다면
			// 목표일수 출력하기
			t_target.setText(Integer.toString(target));
			System.out.println(yy + "년 " + mm + "월 목표일수는 : " + target);
			// jProgressBar 출력하기
			double diaryNum = (double) diaryList.size();
			double targetNum = (double) target;
			int n = (int) Math.ceil(diaryNum / targetNum * 100); 
			jProgressBar.setValue(n); // 진행률을 반영함
			updateUI();

		} else {
			t_target.setText("0");
			JOptionPane.showMessageDialog(this, "목표일수를 등록하세요");
		}
	}

	// 메소드 선언 : 요일 생성, 부착
	public void createWeek() {
		for (int i = 0; i < weekCells.length; i++) {
			weekCells[i] = new WeekCell(weekCellTitles[i], 23, 20, 30); // 제목, 내용, 폰트크기, ,x, y
			p_celender_w.add(weekCells[i]);
		}
	}

	// 메소드 선언 : 날짜 셀 생성, 부착
	public void createDateCell() {
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				String feeling = (String) sidePage.cb_feeling.getSelectedItem();
				String totalDistance = sidePage.t_distance.getText();
				String totalTime = sidePage.t_totaltime.getText();
				dateCells[i][a] = new DateCell(sDiaryMain, sidePage, "", "", "", "", 13, 45, 18);
				// -> DateCell(SidePage sidePage, String title, String feeling, String
				// totaldistance, String totaltime, int fontSize, int x, int y)
				// dateCells[i][a].setBackground(Color.red);
				p_celender_d.add(dateCells[i][a]);
			}
		}
	}

	// 메소드 선언 : 연,월 구하기
	public void title() {
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH);

		String title = yy + "년 " + (mm + 1) + "월 "; // 월은 0부터 시작함
		la_celender_t.setText(title);
	}

	// 메소드 선언 : 해당 월 무슨 요일부터 시작하는지 구하기
	public int getStartDayOfWeek() { // return값 있음
		Calendar Calculation = Calendar.getInstance(); // 계산용
		int yy = currentCalendar.get(Calendar.YEAR); // 연
		int mm = currentCalendar.get(Calendar.MONTH); // 월

		Calculation.set(yy, mm, 1); // 1일로 조작함

		int day = Calculation.get(Calendar.DAY_OF_WEEK); // 해당 월 1일에서 요일 가져오기

		return day;
	}

	// 메소드 선언 : 해당 월 며칠까지인지 구하기
	public int getLastDayOfMonth() {
		Calendar Calculation = Calendar.getInstance(); // 계산용
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH);

		Calculation.set(yy, mm + 1, 0); // 다음달 0일(=해당 월 마지막일)로 조작함

		int day = Calculation.get(Calendar.DATE); // 다음달 0일 추출

		return day;
	}

	// 메소드 선언 : 날짜 구하기
	public void date() {
		int startNum = 0; // 시작 시점 좌표
		int dateNum = 0; // 출력할 날짜

		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				DateCell datecell = dateCells[i][a];
				startNum++;

				if (startNum >= getStartDayOfWeek() && dateNum < getLastDayOfMonth()) {
					dateNum++; // n이 시작 요일을 넘어섰을 때부터 증가할 예정
					setDate = Integer.toString(dateNum);
					datecell.title = setDate;
					// datecell.title = Integer.toString(dateNum);
				} else {
					datecell.title = "";
				}
			}
		}
		p_celender_d.repaint();
	}

	// 메소드 선언 : 입력한 정보를 날짜 셀에 출력하고 색칠하기
	public void printDateCell() {
		// 선택한 월의 달력에 다이어리가 몇개 등록되어 있는지 구하기
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE];
		String id = loginpage.member.getId();
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH);
		List<Diary> diaryList = diaryDAO.select(id, yy, mm + 1); // 다이어리
		List<Swim> swimList = SwimDAO.select(id, yy, mm + 1); // 수영기록
		System.out.println((mm + 1) + "월에 등록된 다이어리 수 : " + diaryList.size());
		System.out.println((mm + 1) + "월에 등록된 운동기록 수 : " + swimList.size());

		// 전체 날짜 셀을 대상으로 반복문 수행
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				if (dateCells[i][a].title.equals("") == false) { // 날짜가 빈 셀이 아니라면
					// 등록된 내용이 있는 날짜 구하기
					int date = Integer.parseInt(dateCells[i][a].title);

					// 등록된 다이어리 수만큼 반복문 수행
					for (int n = 0; n < diaryList.size(); n++) {
						// 다이어리 꺼내기
						Diary obj = diaryList.get(n);

						if (date == obj.getDd()) { // 가져온 다이어리에 해당하는 날에
							dateCells[i][a].feeling = obj.getFeeling();
							dateCells[i][a].color = new Color(243, 233, 183);
						}
					}

					// 등록된 운동기록 수만큼 반복문 수행
					for (int n = 0; n < swimList.size(); n++) {
						// 수영기록 꺼내기
						Swim obj2 = swimList.get(n);

						if (date == obj2.getDd()) { // 가져온 운동기록에 해당하는 날에
							int totaldistance = obj2.getFreestyle() + obj2.getBackstroke() + obj2.getBreaststroke()
									+ obj2.getButterfly();
							// System.out.println("계산된 총 거리는 : "+totaldistance);

							dateCells[i][a].totaldistance = "거리 : " + totaldistance;
							dateCells[i][a].totaltime = "시간 : " + obj2.getTotaltime();
						}
					}
				}
			}
		}
		p_celender_d.repaint();
	}

	// 메소드 선언 : 초기화하기
	public void removeDateCell() {
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				// -> DateCell(SidePage sidePage, String , String , String , String , int
				// fontSize, int x, int y)
				dateCells[i][a].title = "";
				dateCells[i][a].feeling = "";
				dateCells[i][a].totaltime = "";
				dateCells[i][a].totaldistance = "";
				dateCells[i][a].color = Color.WHITE;
			}
		}
		p_celender_d.repaint();
	}

	// 메소드 선언 : 달력 내용 출력
	public void printCalender() {
		title(); // 연,월 구하기
		removeDateCell(); // 날짜 셀 초기화하기
		date(); // 날짜 구하기
		printDateCell(); // 입력한 정보를 날짜 셀에 출력하고 색칠하기
	}
}
