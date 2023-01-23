package db;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import main.SDiaryMain;
import page.DetailPage;

public class DetailTable extends AbstractTableModel {

	// 필드 선언
	SDiaryMain sDiaryMain;
	DetailPage boardPage;

	public List<Diary> diaryList = new ArrayList<Diary>();
	// public List<Swim> swimList = new ArrayList<Swim>();
	String[] column = { "연", "월", "일", "일기", "기분" };
	// String[] column = { "연", "월", "일", "자유형", "배영", "평영", "접영", "총 활동거리", "총
	// 활동시간", "메모", "평가" };
	// ------------------------------------------------------------------------

	// 생성자 선언
	public DetailTable(SDiaryMain sDiaryMain) {
		this.sDiaryMain = sDiaryMain;
		boardPage = (DetailPage) sDiaryMain.pages[sDiaryMain.BOARDPAGE];
	}

	// 메소드 @Override : 층수만큼
	public int getRowCount() {
		return diaryList.size();
	}

	// 메소드 @Override : 호수만큼
	public int getColumnCount() {
		return column.length;
	}

	// 메소드 @Override : 제목 출력
	public String getColumnName(int col) {
		return column[col];
	}

	// 메소드 @Override : 값 넣기
	public Object getValueAt(int row, int col) {
		Diary diary = diaryList.get(row);
		// Swim swim = swimList.get(row);
		String value = null;
		/*
		 * int freestyle = swim.getFreestyle(); int backstroke = swim.getBackstroke();
		 * int breaststroke = swim.getBreaststroke(); int butterfly =
		 * swim.getButterfly(); int totalDistance = freestyle + backstroke +
		 * breaststroke + butterfly; System.out.println("totalDistance : " +
		 * totalDistance);
		 */

		switch (col) {
		case 0:
			value = Integer.toString(diary.getYy());
			break;
		case 1:
			value = Integer.toString(diary.getMm());
			break;
		case 2:
			value = Integer.toString(diary.getDd());
			break;
		/*
		 * case 3: value = Integer.toString(swim.getFreestyle()); break; case 4: value =
		 * Integer.toString(swim.getBackstroke()); break; case 5: value =
		 * Integer.toString(swim.getBreaststroke()); break; case 6: value =
		 * Integer.toString(swim.getButterfly()); break; case 7: value =
		 * Integer.toString(totalDistance); break; case 8: value =
		 * Integer.toString(swim.getTotaltime()); break;
		 */
		case 3:
			value = diary.getMemo();
			break;
		case 4:
			value = diary.getFeeling();
			break;
		}
		return value;
	}

	// 메소드 @Override : 값 수정하기
	public void setValueAt(Object value, int row, int col) {
		// 프로그램의 값 변경하기 (DB에 반영된 것은 아님!)
		Diary diary = diaryList.get(row);
		// Swim swim = swimList.get(row);

		switch (col) {
		case 0:
			diary.setYy(Integer.parseInt((String) value));
			break;
		case 1:
			diary.setMm(Integer.parseInt((String) value));
			break;
		case 2:
			diary.setDd(Integer.parseInt((String) value));
			break;
		/*
		 * case 3: swim.setFreestyle(Integer.parseInt((String) value)); case 4:
		 * swim.setBackstroke(Integer.parseInt((String) value)); case 5:
		 * swim.setBreaststroke(Integer.parseInt((String) value)); case 6:
		 * swim.setButterfly(Integer.parseInt((String) value)); case 8:
		 * swim.setTotaltime(Integer.parseInt((String) value));
		 */
		case 3:
			diary.setMemo((String) value);
		case 4:
			diary.setFeeling((String) value);
		}
		System.out.println(row + ", " + col + "의 값을 " + value + "로 변경함");

		// DB에 반영하기
		boardPage.diaryDAO.update(diary);
		// boardPage.swimDAO.update(swim);
	}

	// 메소드 @Override : 값 수정여부 판단하기
	public boolean isEditable(int row, int col) {
		// 논리값으로 내가 0,0를 편집할 수 있어? 하고 질문하면 개발자가 대답함(true, false)
		// return true;
		// 하면 수정하능한 모양이 되는데 수정 값이 저장되진 않음 -> 값은 TableModel이 관리하기 때문

		boolean flag = false;

		switch (col) {
		case 0:
			flag = true;
			break;
		case 1:
			flag = true;
			break;
		case 2:
			flag = true;
			break;
		/*
		 * case 3: flag = true; break; case 4: flag = true; break; case 5: flag = true;
		 * break; case 6: flag = true; break; case 8: flag = true; break;
		 */
		case 3:
			flag = true;
			break;
		case 4:
			flag = true;
			break;
		}
		return flag;
	}
}
