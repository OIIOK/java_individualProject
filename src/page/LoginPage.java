//1. 접속페이지

package page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.Member;
import db.MemberDAO;
import main.SDiaryMain;

public class LoginPage extends Page {
	// 필드 선언
	
	
	JPanel p_form; // 양식 담을 패널
	JLabel la_pagetitle; // 페이지 제목
	JLabel la_id; // ID 입력 문구
	JLabel la_password;// 입력 문구
	JTextField t_id;// ID 입력란
	JPasswordField t_password;// Password 입력란
	JLabel la_blank;// 디자인용
	JButton bt_login; // 로그인 버튼
	JButton bt_join; // 가입 버튼

	// DB관련
	MemberDAO memberDAO;
	public Member member; //로그인 한 사용자의 DTO

	// ------------------------------------------------------------------------

	// 생성자 선언
	public LoginPage(SDiaryMain sDiaryMain) {
		super(sDiaryMain); // Page에서 변수로 선언되어 있으므로 연결만 하면 됨
		memberDAO = new MemberDAO();
	
		// 생성
		p_form = new JPanel();
		la_pagetitle = new JLabel("ApuuDiary");
		la_id = new JLabel("ID");
		la_password = new JLabel("Password");
		t_id = new JTextField();
		t_password = new JPasswordField();
		la_blank = new JLabel("");
		bt_login = new JButton("로그인");
		bt_join = new JButton("가입");

		// 디자인
		this.setLayout(null);
		this.setBackground(new Color(56, 76, 159));
		int form_y = 230;
		int form_width = 250;
		int form_heigh = 185;// --------------------------------------------------------높이 나중에 조절하기
		la_pagetitle.setBounds((sDiaryMain.PAGE_WIDTH) / 2 - (form_width) / 2, 150, form_width, 80);
		la_pagetitle.setFont(new Font("카페24 써라운드", Font.BOLD, 40)); // 제목 크기
		la_pagetitle.setForeground(Color.WHITE); //제목 색상
		la_pagetitle.setHorizontalAlignment(JLabel.CENTER);
		p_form.setBounds((sDiaryMain.PAGE_WIDTH) / 2 - (form_width) / 2, form_y, form_width, form_heigh);
		p_form.setBackground(Color.GRAY);
		Dimension d = new Dimension(200, 25); // Label, TextField 크기
		la_id.setPreferredSize(d);
		la_password.setPreferredSize(d);
		t_id.setPreferredSize(d);
		t_password.setPreferredSize(d);
		la_blank.setPreferredSize(new Dimension(200, 10));
		Dimension d2 = new Dimension(97, 27); // JButton 크기
		bt_login.setPreferredSize(d2);
		bt_login.setBackground(new Color(243, 233, 183));
		bt_join.setPreferredSize(d2);
		bt_join.setBackground(Color.WHITE);

		// 부착

		add(la_pagetitle);
		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(la_password);
		p_form.add(t_password);
		p_form.add(la_blank);
		p_form.add(bt_login);
		p_form.add(bt_join);
		add(p_form);

		// 로그인 버튼에 이벤트 연결
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkBlank(); // 공란 확인 후 로그인하기
			}
		});

		// Password 입력란에 엔터키 => 로그인 버튼 이벤트 연결
		t_password.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					checkBlank(); // 공란 확인 후 로그인하기
				}
			}
		});

		// 가입 버튼에 이벤트 연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sDiaryMain.showHide(sDiaryMain.JOINPAGE); // JoinPage 띄우기
			}
		});
	}

	// ------------------------------------------------------------------------

	// 메소드 선언 : 공란 확인 후 로그인하기
	public void checkBlank() {
		// 1. 공란일 경우 경고메시지 띄우기
		String id = t_id.getText();
		String password = new String(t_password.getPassword());

		if (id.equals("")) {
			JOptionPane.showMessageDialog(this, "Id를 입력하세요");
		} else if (password.equals("")) {
			JOptionPane.showMessageDialog(this, "Password를 입력하세요");
		} else {
			login(); // 2. 로그인하기
		}
	}

	// 메소드 선언 : 로그인하기
	public void login() {
		// 1. DTO에 로그인정보 담고 DAO에서 DB와 일치하는 회원을 찾아 DTO를 넘겨받기
		Member dto = new Member(); // 빈 DTO 생성
		dto.setId(t_id.getText()); // DTO에 정보 담기
		dto.setPassword(new String(t_password.getPassword())); // 반환값이 char이기에 형변환해줌

		Member obj = memberDAO.select(dto); // DAO에서 DB와 일치하는 회원을 찾아 DTO 넘겨받기
		
		// 2. 로그인 성공 메시지 띄우기
		if (obj != null) { // 일치하는 회원이 있다면
			member = obj;
			JOptionPane.showMessageDialog(this, "로그인 성공!"); // this = LoginPage
			sDiaryMain.showHide(ABORT);
			// 3. Tab 띄우기
			sDiaryMain.pages[0].setVisible(false);
			sDiaryMain.TabContainer.setVisible(true);
			
			DiaryPage diaryPage=(DiaryPage)sDiaryMain.pages[SDiaryMain.DIARYPAGE];
			DetailPage boardPage = (DetailPage) sDiaryMain.pages[sDiaryMain.BOARDPAGE];			
			
			diaryPage.createWeek(); // 요일 생성, 부착
			diaryPage.createDateCell(); // 날짜 셀 생성, 부착
			diaryPage.printCalender(); // 달력 내용 출력
			diaryPage.printAttendance(); // 목표일수 출력과 jProgressBar 출력

			
			boardPage.printPage(); // 페이지 내용 출력
			
		} else {
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");
		}
	}
}
