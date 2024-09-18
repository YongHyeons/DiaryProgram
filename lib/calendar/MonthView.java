package lib.calendar;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class MonthView extends CalendarData{
/*
	public MonthView(JPanel panel_top, JPanel panel_center, Container c){
		super(panel_top, panel_center, c, 'M', null);
		
		printTopPanel();

		printCalendar();
	}
*/

	public MonthView(JPanel panel_top, JPanel panel_center, Container c, String id){
		super(panel_top, panel_center, c, 'M', id);
		
		/* 상단 부분 출력 */
		printTopPanel();

		/* 달력 부분 출력 */
		printCalendar();
	}
/*
	public MonthView(JPanel panel_top, JPanel panel_center, Container c, int year, int month){
		super(panel_top, panel_center, c, 'M', null);
		currentToday.set(Calendar.YEAR, year);
		currentToday.set(Calendar.MONTH, month);
		
		printTopPanel();

		printCalendar();
	}
*/

	public MonthView(JPanel panel_top, JPanel panel_center, Container c, int year, int month, String id){
		super(panel_top, panel_center, c, 'M', id);
		currentToday.set(Calendar.YEAR, year);
		currentToday.set(Calendar.MONTH, month);
		
		/* 상단 부분 출력 */
		printTopPanel();

		/* 달력 부분 출력 */
		printCalendar();
	}

	public void printCalendar(){
		panel_center.setLayout(new GridLayout(7, 7));

		/* 달력 데이터 설정 */
		setCalendar();
	}
}
