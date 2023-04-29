//3-2. 사이드페이지 

package page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import db.Diary;
import db.DiaryDAO;
import db.Swim;
import db.SwimDAO;
import main.SDiaryMain;

public class SidePage extends JPanel {
	// 필드 선언
	DiaryPage diaryPage;
	SDiaryMain sDiaryMain;
	
	
	JPanel p_null; // 디자인 위해서
	JPanel p_null2; // 디자인 위해서
	JPanel p_null3; // 디자인 위해서
	JPanel p_null4; // 디자인 위해서

	JLabel la_guide; // 안내 문구
	JLabel la_year; // 년 문구
	JLabel la_month; // 월 문구
	JLabel la_date;// 일 문구
	JTextField t_year; // 년 입력란
	JTextField t_month; // 월 입력란
	public JTextField t_date;// 일 입력란 //DateCell에서 참조할 수 있도록
	JLabel la_freestyle; // 자유형 문구
	JLabel la_backstroke; // 배영 문구
	JLabel la_breaststroke; // 평영 문구
	JLabel la_butterfly; // 접영 문구
	public JTextField t_freestyle; // 자유형 입력란
	public JTextField t_backstroke; // 배영 입력란
	public JTextField t_breaststroke; // 평영 입력란
	public JTextField t_butterfly; // 접영 문구
	JButton bt_minus; // - 버튼
	JButton bt_plus; // + 버튼
	JButton bt_minus2; // - 버튼
	JButton bt_plus2; // + 버튼
	JButton bt_minus3; // - 버튼
	JButton bt_plus3; // + 버튼
	JButton bt_minus4; // - 버튼
	JButton bt_plus4; // + 버튼
	JLabel la_distance; // 총 활동거리 문구
	JLabel la_totaltime; // 총 활동시간 문구
	public JTextField t_distance; // 총 활동거리 입력란
	public JTextField t_totaltime;// 총 활동시간 입력란

	public JLabel la_memo; // 메모 문구
	public JLabel la_feeling; // 평가 문구
	public JTextArea ta_memo; // 메모 입력란
	JScrollPane scroll_memo; // 스크롤
	public JComboBox<String> cb_feeling; // 평가 콤보박스

	JButton bt_regist; // 등록 버튼
	JButton bt_delete; // 삭제 버튼

	// DB관련
	DiaryDAO diaryDAO = new DiaryDAO();
	SwimDAO swimDAO = new SwimDAO();
	Diary diary;
	Swim swim;

	// ------------------------------------------------------------------------
	// 생성자 선언
	public SidePage(SDiaryMain sDiaryMain, DiaryPage diaryPage) {
		this.sDiaryMain = sDiaryMain;
		this.diaryPage = diaryPage;
		
		// 생성
		p_null = new JPanel();
		p_null2 = new JPanel();
		p_null3 = new JPanel();
		p_null4 = new JPanel();

		la_guide = new JLabel("[ Select Date ]");
		la_year = new JLabel("년 ");
		la_month = new JLabel("월 ");
		la_date = new JLabel("일");
		t_year = new JTextField();
		t_month = new JTextField();
		t_date = new JTextField();

		la_freestyle = new JLabel("자유형(m)");
		la_backstroke = new JLabel("배   영(m)");
		la_breaststroke = new JLabel("평   영(m)");
		la_butterfly = new JLabel("접   영(m)");
		t_freestyle = new JTextField("0");
		t_backstroke = new JTextField("0");
		t_breaststroke = new JTextField("0");
		t_butterfly = new JTextField("0");
		bt_minus = new JButton("-");
		bt_plus = new JButton("+");
		bt_minus2 = new JButton("-");
		bt_plus2 = new JButton("+");
		bt_minus3 = new JButton("-");
		bt_plus3 = new JButton("+");
		bt_minus4 = new JButton("-");
		bt_plus4 = new JButton("+");
		la_distance = new JLabel("총 활동거리(m)  ");
		la_totaltime = new JLabel("총 활동시간(분)  ");
		t_distance = new JTextField();
		t_totaltime = new JTextField();

		la_memo = new JLabel("  메모 ");
		ta_memo = new JTextArea("");
		scroll_memo = new JScrollPane(ta_memo);
		la_feeling = new JLabel("  평가 ");
		cb_feeling = new JComboBox<String>();
		bt_regist = new JButton("등록");
		bt_delete = new JButton("삭제");

		// 디자인
		setBounds(0, 20, 300, 700);
		setBackground(new Color(56, 76, 159));
		int width = 35;
		int t_height = 18;
		Font font = new Font("굴림", Font.BOLD, 13);

		p_null.setPreferredSize(new Dimension(280, 100)); // la_guide 내리고 싶은 만큼!
		p_null.setBackground(new Color(56, 76, 159));
		p_null2.setPreferredSize(new Dimension(280, 30)); // 칸 띄고 싶은 만큼!
		p_null2.setBackground(new Color(56, 76, 159));
		p_null3.setPreferredSize(new Dimension(280, 30)); // 칸 띄고 싶은 만큼!
		p_null3.setBackground(new Color(56, 76, 159));
		p_null4.setPreferredSize(new Dimension(280, 30)); // 칸 띄고 싶은 만큼!
		p_null4.setBackground(new Color(56, 76, 159));

		la_guide.setPreferredSize(new Dimension(280, 30));
		la_guide.setFont(font);
		la_guide.setFont(new Font("카페24 써라운드", Font.BOLD, 20)); // 제목 크기
		la_guide.setForeground(Color.WHITE);
		la_guide.setHorizontalAlignment(JLabel.CENTER);
		la_year.setFont(font);
		la_year.setForeground(Color.WHITE);
		la_month.setFont(font);
		la_month.setForeground(Color.WHITE);
		la_date.setFont(font);
		la_date.setForeground(Color.WHITE);
		t_year.setPreferredSize(new Dimension(55, t_height));
		t_month.setPreferredSize(new Dimension(width, t_height));
		t_date.setPreferredSize(new Dimension(width, t_height));
		t_freestyle.setPreferredSize(new Dimension(width, t_height));

		la_freestyle.setFont(font);
		la_freestyle.setForeground(Color.WHITE);
		la_backstroke.setFont(font);
		la_backstroke.setForeground(Color.WHITE);
		la_breaststroke.setFont(font);
		la_breaststroke.setForeground(Color.WHITE);
		la_butterfly.setFont(font);
		la_butterfly.setForeground(Color.WHITE);
		Dimension swim = new Dimension(70, t_height);
		t_freestyle.setPreferredSize(swim);
		t_backstroke.setPreferredSize(swim);
		t_breaststroke.setPreferredSize(swim);
		t_butterfly.setPreferredSize(swim);
		Dimension button = new Dimension(43, t_height);
		bt_minus.setPreferredSize(button);
		bt_plus.setPreferredSize(button);
		bt_minus2.setPreferredSize(button);
		bt_plus2.setPreferredSize(button);
		bt_minus3.setPreferredSize(button);
		bt_plus3.setPreferredSize(button);
		bt_minus4.setPreferredSize(button);
		bt_plus4.setPreferredSize(button);
		la_distance.setFont(font);
		la_distance.setForeground(Color.WHITE);
		la_totaltime.setFont(font);
		la_totaltime.setForeground(Color.WHITE);
		Dimension total = new Dimension(123, t_height);
		t_distance.setPreferredSize(total);
		t_totaltime.setPreferredSize(total);

		int regist = 210;
		la_memo.setFont(font);
		la_memo.setForeground(Color.WHITE);
		scroll_memo.setPreferredSize(new Dimension(regist, 60));
		la_feeling.setFont(font);
		la_feeling.setForeground(Color.WHITE);
		cb_feeling.setPreferredSize(new Dimension(regist, t_height));
		cb_feeling.addItem("♥♥♥");
		cb_feeling.addItem("♡♥♥");
		cb_feeling.addItem("♡♡♥");
		cb_feeling.addItem("♡♡♡");

		// 부착
		add(p_null);
		add(la_guide);
		add(t_year);
		add(la_year);
		add(t_month);
		add(la_month);
		add(t_date);
		add(la_date);
		add(p_null2);

		add(la_freestyle);
		add(bt_minus);
		add(t_freestyle);
		add(bt_plus);
		add(la_backstroke);
		add(bt_minus2);
		add(t_backstroke);
		add(bt_plus2);
		add(la_breaststroke);
		add(bt_minus3);
		add(t_breaststroke);
		add(bt_plus3);
		add(la_butterfly);
		add(bt_minus4);
		add(t_butterfly);
		add(bt_plus4);
		add(la_distance);
		add(t_distance);
		add(la_totaltime);
		add(t_totaltime);
		add(p_null3);

		add(la_memo);
		add(scroll_memo);
		add(la_feeling);
		add(cb_feeling);
		add(p_null4);
		add(bt_regist);
		add(bt_delete);

		// 등록 버튼에 이벤트 연결 :
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist(); // 등록하기
				clear(); // 지우기
				DetailPage detailPage = (DetailPage) sDiaryMain.pages[sDiaryMain.BOARDPAGE]; //순서중요
				diaryPage.printAttendance(); // 목표일수 출력과 jProgressBar 출력
				detailPage.pieChart.removeSeries("자유형 총 활동거리(m)"); // 차트 생성 전에 비우기
				detailPage.pieChart.removeSeries("배영 총 활동거리(m)");
				detailPage.pieChart.removeSeries("평영 총 활동거리(m)");
				detailPage.pieChart.removeSeries("접영 총 활동거리(m)");
				detailPage.customizeChart(); // Chart Customize 하기
			}
		});

		// 삭제 버튼에 이벤트 연결 :
		bt_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete(); // 삭제하기
				clear(); // 지우기
				diaryPage.printCalender(); // 달력 내용 출력
				diaryPage.printAttendance(); // 목표일수 출력과 jProgressBar 출력
				DetailPage detailPage = (DetailPage) sDiaryMain.pages[sDiaryMain.BOARDPAGE]; //순서중요
				detailPage.pieChart.removeSeries("자유형 총 활동거리(m)"); // 차트 생성 전에 비우기
				detailPage.pieChart.removeSeries("배영 총 활동거리(m)");
				detailPage.pieChart.removeSeries("평영 총 활동거리(m)");
				detailPage.pieChart.removeSeries("접영 총 활동거리(m)");
				detailPage.customizeChart(); // Chart Customize 하기
				detailPage.printPage(); // 페이지 내용 출력
				detailPage.getDetail();
			}
		});

		// -버튼에 이벤트 연결
		bt_minus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_freestyle, -25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// -버튼에 이벤트 연결
		bt_minus2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_backstroke, -25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// -버튼에 이벤트 연결
		bt_minus3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_breaststroke, -25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// -버튼에 이벤트 연결
		bt_minus4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_butterfly, -25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// +버튼에 이벤트 연결
		bt_plus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_freestyle, 25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// +버튼에 이벤트 연결
		bt_plus2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_backstroke, 25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// +버튼에 이벤트 연결
		bt_plus3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_breaststroke, 25);// TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
		// +버튼에 이벤트 연결
		bt_plus4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setDistance(t_butterfly, 25); // TextFeild에 활동거리 채우기
				setTotalDistance(); // 총 활동거리 채우기
			}
		});
	}

	// ------------------------------------------------------------------------

	// 메소드 선언 : TextFeild에 날짜정보 채우기
	public void setTextField() {
		// 날짜정보 가져오기
		// yy,mm -> DiaryPage의 currentCalendar에서
		int yy = diaryPage.currentCalendar.get(Calendar.YEAR);
		int mm = diaryPage.currentCalendar.get(Calendar.MONTH);
		// dd -> 가져오지 않고 DiaryPage의 date()에서 DateCell 생성자에게 매개변수를 넘겨주기로

		// 날짜정보 채우기
		t_year.setText(Integer.toString(yy));
		t_month.setText(Integer.toString(mm + 1));
		// t_date.setText(diaryPage.setDate);

		updateUI();
	}

	// 메소드 선언 : TextFeild에 활동거리 채우기
	public void setDistance(JTextField fieldname, int distance) {
		int n = Integer.parseInt(fieldname.getText()); // 누적될 수 있도록
		n += distance; // 감소인지 증가인지 distance로 받을 예정

		fieldname.setText(Integer.toString(n)); // 채울 영역 fieldname로 받을 예정
		updateUI();
	}

	// 메소드 선언 : 총 활동거리 채우기
	public void setTotalDistance() {
		int freestyle = Integer.parseInt(t_freestyle.getText());
		int backstroke = Integer.parseInt(t_backstroke.getText());
		int breaststroke = Integer.parseInt(t_breaststroke.getText());
		int butterfly = Integer.parseInt(t_butterfly.getText());

		int totalDistance = freestyle + backstroke + breaststroke + butterfly;

		t_distance.setText(Integer.toString(totalDistance)); // 채울 영역 fieldname로 받을 예정
		updateUI();
	}

	// 메소드 선언 : 등록하기
	public void regist() {
		// 다이어리 부분
		// 빈 DTO 생성
		diary = new Diary();
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE]; //순서중요
		diary.setMember(loginpage.member);
		// 내용 가져오기
		String yy = t_year.getText();
		String mm = t_month.getText();
		String dd = t_date.getText();
		String memo = ta_memo.getText();
		String feeling = (String) cb_feeling.getSelectedItem();
		// DTO에 정보 담기
		diary.setYy(Integer.parseInt(yy));
		diary.setMm(Integer.parseInt(mm));
		diary.setDd(Integer.parseInt(dd));
		diary.setMemo(memo);
		diary.setFeeling(feeling);
		// DAO에 넘겨 DB테이블에 담기
		int result = diaryDAO.insert(diary);

		// 운동기록 부분
		// 빈 DTO 생성
		swim = new Swim();
		swim.setMember(loginpage.member);
		// 내용 가져오기
		String freestyle = t_freestyle.getText();
		String breaststroke = t_breaststroke.getText();
		String backstroke = t_backstroke.getText();
		String butterfly = t_butterfly.getText();
		String totaltime = t_totaltime.getText();
		// DTO에 정보 담기
		swim.setYy(Integer.parseInt(yy));
		swim.setMm(Integer.parseInt(mm));
		swim.setDd(Integer.parseInt(dd));
		swim.setFreestyle(Integer.parseInt(freestyle));
		swim.setBackstroke(Integer.parseInt(backstroke));
		swim.setBreaststroke(Integer.parseInt(breaststroke));
		swim.setButterfly(Integer.parseInt(butterfly));
		swim.setTotaltime(Integer.parseInt(totaltime));
		// DAO에 넘겨 DB테이블에 담기
		int result2 = SwimDAO.insert(swim);

		if (result > 0 && result2 > 0) {
			JOptionPane.showMessageDialog(this, "등록성공");
			diaryPage.printDateCell(); // 입력한 정보를 날짜 셀에 출력하고 색칠하기
			clear();
		}
	}

	// 메소드 선언 : 등록 시 TextFeild 비우기
	public void clear() {
		t_year.setText("");
		t_month.setText("");
		t_date.setText("");
		t_freestyle.setText("0");
		t_backstroke.setText("0");
		t_breaststroke.setText("0");
		t_butterfly.setText("0");
		t_distance.setText("");
		t_totaltime.setText("");
		ta_memo.setText("");
		cb_feeling.setSelectedItem("♥♥♥");
	}
	
	//메소드 선언 : 선택한 다이어리 한 건 가져오기
	public void getDetail( ) {
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE]; //순서중요
		String id = loginpage.member.getId();
		int yy =Integer.parseInt(t_year.getText());
		int mm =Integer.parseInt(t_month.getText());
		int dd = Integer.parseInt(t_date.getText());

		diary = new Diary();
		diary = diaryDAO.select(id, yy, mm, dd);
		
		swim = new Swim();
		swim = swimDAO.select(id, yy, mm, dd);
		
		if(diary !=null && swim !=null) {
			t_freestyle.setText(Integer.toString(swim.getFreestyle()));
			t_backstroke.setText(Integer.toString(swim.getBackstroke()));
			t_breaststroke.setText(Integer.toString(swim.getBreaststroke()));
			t_butterfly.setText(Integer.toString(swim.getButterfly()));
			t_totaltime.setText(Integer.toString(swim.getTotaltime()));
			ta_memo.setText(diary.getMemo());
			cb_feeling.setSelectedItem(diary.getFeeling());
			setTotalDistance(); //활동거리 채우기
		}
	}

	// 메소드 선언 : 삭제
	public void delete() {
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE]; //순서중요
		String id = loginpage.member.getId();
		int yy =Integer.parseInt(t_year.getText());
		int mm =Integer.parseInt(t_month.getText());
		int dd = Integer.parseInt(t_date.getText());
		
		int op = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?");
			if (op == JOptionPane.OK_OPTION) { // 승인시
				// 다이어리 부분 DB 삭제
				int n = diaryDAO.delete(id, yy, mm, dd);
				// 운동기록 부분 DB 삭제
				int m = swimDAO.delete(id, yy, mm, dd);
				if (n > 0) {
					JOptionPane.showMessageDialog(this, "삭제성공");
				}
			}
		}
	}