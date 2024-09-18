package lib.calendar;

import java.awt.*;
import javax.swing.*;

public class CalendarView extends JFrame{
	public CalendarView(String id){
		Container c = getContentPane();
		JPanel panel_top = new JPanel();
		JPanel panel_center = new JPanel();

		/* 화면 초기화 */
		setTitle("다이어리 캘린더");
		setExtendedState(JFrame.MAXIMIZED_BOTH); //전체화면으로 시작
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel_top.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panel_top.setLayout(new GridLayout(0, 3));

		/* 달력 화면 출력 */
		new MonthView(panel_top, panel_center, c, id);

		c.add(panel_top, BorderLayout.NORTH);
		c.add(panel_center, BorderLayout.CENTER);
		c.revalidate();
		c.repaint();
	}
}
