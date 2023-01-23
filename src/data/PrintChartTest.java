package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler.LabelType;
import org.knowm.xchart.style.Styler.LegendPosition;

public class PrintChartTest extends JFrame {
	PieChart pieChart; // 차트
	XChartPanel<PieChart> p_pieChart; // 차트 담을 패널
	JPanel container; // 페이지를 담을 패널

	// 생성자
	public PrintChartTest() {
		container = new JPanel();
		container.setPreferredSize(new Dimension(200, 300));
		container.setBackground(Color.YELLOW);

		// 차트 생성
		pieChart = new PieChartBuilder().width(280).height(400).title("").build();

		p_pieChart = new XChartPanel<PieChart>(pieChart);
		p_pieChart.setPreferredSize(new Dimension(300, 400));

		customizeChart(); // Chart Customize 하기

		container.add(p_pieChart);
		add(container, BorderLayout.WEST);

		// 윈도우세팅
		setSize(1000, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // System.exit(0) 하면 지워줄 예정
		setResizable(false); // 사이즈 변경 불가

	}

	// 메소드 선언 : Chart Customize 하기
	public void customizeChart() {
		// Label 설정
		pieChart.getStyler().setLabelType(LabelType.Percentage);
		pieChart.getStyler().setLabelsFontColorAutomaticEnabled(false);
		pieChart.getStyler().setLabelsFontColor(Color.WHITE);
		pieChart.getStyler().setLabelsDistance(0.7); // 중심으로부터의 거리

		// Series(영역) 내용, 설정
		pieChart.addSeries("자유형", 60);
		pieChart.addSeries("배영", 20);
		pieChart.addSeries("평영", 15);
		pieChart.addSeries("접영", 5);
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
		pieChart.getStyler().setPlotBackgroundColor(Color.DARK_GRAY); // 바탕색
		pieChart.getStyler().setPlotBorderVisible(false); // 전체 테두리
	}

	public static void main(String[] args) {
		new PrintChartTest();
	}
}
