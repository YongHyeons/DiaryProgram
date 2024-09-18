package lib.calendar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import lib.components.MyJLabel;

public class YearView extends CalendarData{
/*
	public YearView(JPanel panel_top, JPanel panel_center, Container c){
		super(panel_top, panel_center, c, 'Y');
		
		printTopPanel();

		printCalendar();
	}

	public YearView(JPanel panel_top, JPanel panel_center, Container c, int year, int month){
		super(panel_top, panel_center, c, 'Y', null);
		currentToday.set(Calendar.YEAR, year);
		currentToday.set(Calendar.MONTH, month);
		
		printTopPanel();

		printCalendar();
	}
*/

	public YearView(JPanel panel_top, JPanel panel_center, Container c, int year, int month, String id){
		super(panel_top, panel_center, c, 'Y', id);
		currentToday.set(Calendar.YEAR, year);
		currentToday.set(Calendar.MONTH, month);
		
		/* 상단 부분 출력 */
		printTopPanel();

		/* 달력 부분 출력 */
		printCalendar();
	}

	private void printCalendar(){
		gridBagLayout = new GridBagLayout();
		gridConstraint = new GridBagConstraints();

		/* 그리드 사이 공백 채움 */
		gridConstraint.fill = GridBagConstraints.HORIZONTAL;

		/* 그리드백레이아웃 설정 */
		panel_center.setLayout(gridBagLayout);
		panel_months = new JPanel[12];

		for(int i = 0; i < 12; i++){
			/* 배열에서 리스너에 값을 전달하기 위해 상수 사용 */
			final int month = i;

			panel_months[i] = new JPanel();
			panel_months[i].setLayout(new GridLayout(7, 7, 5, 5));
			panel_months[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			panel_months[i].addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					for(Component component : ((JPanel)e.getSource()).getComponents()){
						component.setForeground(Color.BLACK);
					}
				}

				public void mouseExited(MouseEvent e){
					for(Component component : ((JPanel)e.getSource()).getComponents()){
						component.setForeground(Color.GRAY);
					}
				}

				public void mouseClicked(MouseEvent e){
					panel_top.removeAll();
					panel_center.removeAll();
					new MonthView(panel_top, panel_center, c, today_year, month, id);
					c.revalidate();
					c.repaint();
				}
			});
		}

		/* 달력 데이터 설정 */
		setCalendar();

		/* 4행 6열 그리드 레이아웃 생성 */
		for(int i = 0; i < 6; i++){
			addGrid(new MyJLabel(i + 1 + "월"), i, 0);
			addGrid(panel_months[i], i, 1);
			addGrid(new MyJLabel(i + 7 + "월"), i, 2);
			addGrid(panel_months[i + 6], i, 3);
		}
	}

	private void addGrid(JComponent component, int x, int y){
		/* 그리드 좌표 */ 
		gridConstraint.gridx = x;
		gridConstraint.gridy = y;

		/* 가로로 가득찬 그리드 레이아웃 */
		gridConstraint.weightx = 1; 

		/* 그리드 레이아웃 조건 설정 */
		gridBagLayout.setConstraints(component, gridConstraint);

		component.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
		panel_center.add(component);
	}
}
