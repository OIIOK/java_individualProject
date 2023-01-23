//5. 지도페이지

package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import db.DetailTable;
import db.DiaryDAO;
import db.SwimDAO;
import main.SDiaryMain;

public class DetailPage extends Page {
	// 필드 선언
	JPanel p_container;

	// 서쪽영역
	JPanel p_west;
	public JLabel la_stats; // 통계 문구
	XChartPanel<PieChart> p_pieChart; // 차트 담을 패널
	PieChart pieChart; // 차트

	// 센터영역(표 담을 예정)
	JPanel p_center;
	JPanel p_celender_t; // 달력_제목 영역
	JLabel la_null;// 디자인용
	JLabel la_celender_t;// 달력_제목
	JButton bt_prev; // 달력_이전 버튼
	JButton bt_next;// 달력_다음 버튼
	JTable table; // 상세테이블
	JScrollPane scroll; // 테이블 담을 영역

	// 현재 날짜 정보
	public Calendar currentCalendar = Calendar.getInstance(); // 선택한 월의 달력

	// DB 관련
	DetailTable detailTable;
	public DiaryDAO diaryDAO = new DiaryDAO();
	public SwimDAO swimDAO = new SwimDAO();

	// ------------------------------------------------------------------------
	// 생성자 선언

	public DetailPage(SDiaryMain sDiaryMain) {
		super(sDiaryMain); // Page에서 변수로 선언되어 있으므로 연결만 하면 됨
		this.setLayout(new BorderLayout());
		// this.setBackground(new Color(56, 76, 159));
		Font font = new Font("굴림", Font.BOLD, 15);
		int height = 30;

		// 서쪽영역(통계 담을 예정)-------------------
		// 생성
		p_west = new JPanel();
		la_stats = new JLabel(("///월 한눈에 보기")); // 변수 대입할 예정
		pieChart = new PieChartBuilder().width(280).height(400).title("").build();
		p_pieChart = new XChartPanel<PieChart>(pieChart);
		// 디자인
		p_west.setLayout(null);
		p_west.setPreferredSize(new Dimension(300, 650));
		p_west.setBackground(Color.DARK_GRAY);
		la_stats.setFont(font); // 통계 문구 크기
		la_stats.setFont(new Font("카페24 써라운드", Font.BOLD, 20)); // 제목 크기
		la_stats.setForeground(Color.WHITE); // 제목 색상
		la_stats.setBounds(80, 120, 300, height); // height 30
		p_pieChart.setPreferredSize(new Dimension(280, 380));
		p_pieChart.setBackground(Color.DARK_GRAY);
		p_pieChart.setBounds(30, 160, 250, 350);

		// 센터영역(표 담을 예정)-------------------
		// 생성
		p_center = new JPanel();
		p_celender_t = new JPanel();
		la_null=new JLabel("");
		la_celender_t = new JLabel("///월 ///일"); // 변수 대입할 예정
		bt_prev = new JButton("<<");
		bt_next = new JButton(">>");
		table = new JTable( detailTable=new DetailTable(sDiaryMain) );
		scroll = new JScrollPane(table);
		// 디자인
		p_center.setBackground(new Color(56, 76, 159));
		p_center.setPreferredSize(new Dimension(700, 650));
		p_celender_t.setPreferredSize(new Dimension(700, 100));
		p_celender_t.setBackground(new Color(56, 76, 159));
		la_null.setPreferredSize(new Dimension(700, 40));
		la_celender_t.setFont(new Font("카페24 써라운드", Font.BOLD, 23)); // 제목 크기
		la_celender_t.setForeground(Color.WHITE); // 글색상
		scroll.setPreferredSize(new Dimension(680, 530));

		// 부착
		p_west.add(la_stats);
		p_west.add(p_pieChart);
		add(p_west, BorderLayout.WEST);
		p_celender_t.add(la_null);
		p_celender_t.add(bt_prev);
		p_celender_t.add(la_celender_t);
		p_celender_t.add(bt_next);
		p_center.add(p_celender_t);
		p_center.add(scroll);
		add(p_center);

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

				printPage(); // 페이지 내용 출력
				getDetail();
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

				printPage(); // 페이지 내용 출력
				 
			}
		});
	}

	// ------------------------------------------------------------------------
	// 메소드 선언 : 연,월 구하기
	public void title() {
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH);

		String title = yy + "년 " + (mm + 1) + "월 "; // 월은 0부터 시작함
		la_celender_t.setText(title);

		String month = (mm + 1) + "월 한눈에 보기";
		la_stats.setText(month);
	}

	// 메소드 선언 : Chart Customize 하기
	public void customizeChart() {
		p_pieChart = null;

		// 한달 기준으로 한 수영종목의 각 합계 구하기
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE];
		String id = loginpage.member.getId();
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH) + 1;

		int totalFreestyle = SwimDAO.sumFreestyle(id, yy, mm); // 수영기록
		int totalBackstroke = SwimDAO.sumBackstroke(id, yy, mm); // 수영기록
		int totalBreaststroke = SwimDAO.sumBreaststroke(id, yy, mm); // 수영기록
		int totalButterfly = SwimDAO.sumButterfly(id, yy, mm); // 수영기록
		// System.out.println(yy+"년 "+mm+"월 자유형 총합 : "+totalFreestyle);

		// Series(영역) 내용, 설정

		pieChart.addSeries("자유형 총 활동거리(m)", totalFreestyle);
		pieChart.addSeries("배영 총 활동거리(m)", totalBackstroke);
		pieChart.addSeries("평영 총 활동거리(m)", totalBreaststroke);
		pieChart.addSeries("접영 총 활동거리(m)", totalButterfly);

		Color[] sliceColors = new Color[] { new Color(173, 66, 189), new Color(66, 70, 189), new Color(189, 160, 66),
				new Color(54, 152, 110) };
		pieChart.getStyler().setSeriesColors(sliceColors);
		pieChart.getStyler().setChartBackgroundColor(Color.DARK_GRAY); // 전체 테두리 색상

		// 범례 설정
		pieChart.getStyler().setLegendVisible(true); // 범례 보이기
		pieChart.getStyler().setLegendBackgroundColor(Color.DARK_GRAY);// 범례 배경 색상
		pieChart.getStyler().setChartFontColor(Color.WHITE); // 범례 폰트 색상
		pieChart.getStyler().setLegendPosition(LegendPosition.OutsideS); // 범례 위치

		// 차트 전체 색상 설정
		pieChart.getStyler().setPlotBackgroundColor(Color.DARK_GRAY);
		pieChart.getStyler().setPlotBorderVisible(false); // 전체 테두리

		updateUI();
	}

	// 메소드 선언 : 세부내용 내용 출력
	public void getDetail() {
		LoginPage loginpage = (LoginPage) sDiaryMain.pages[SDiaryMain.LOGINPAGE];
		String id = loginpage.member.getId();
		int yy = currentCalendar.get(Calendar.YEAR);
		int mm = currentCalendar.get(Calendar.MONTH)+1;
		
		detailTable.diaryList = diaryDAO.select(id, yy, mm);
		//detailTable.swimList = diaryDAO.select(id, yy, mm);
		
		table.updateUI();
	}
	
	// 메소드 선언 : 페이지 내용 출력
	public void printPage() {
		title(); // 연,월 구하기

		pieChart.removeSeries("자유형 총 활동거리(m)"); // 차트 생성 전에 비우기
		pieChart.removeSeries("배영 총 활동거리(m)");
		pieChart.removeSeries("평영 총 활동거리(m)");
		pieChart.removeSeries("접영 총 활동거리(m)");
		customizeChart(); // Chart Customize 하기
		
		getDetail(); //세부내용 내용 출력
	}
	

}
