package lib.calendar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

import lib.components.*;

public class CalendarData extends JFrame{
	/* 상수 변수 */
	final GregorianCalendar TODAY = new GregorianCalendar();
	final String DAYS[] = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
	final int DAY_OF_MONTH[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	/* 날짜 데이터 */
	GregorianCalendar currentToday = new GregorianCalendar();
	int today_year = currentToday.get(Calendar.YEAR);
	int today_month = currentToday.get(Calendar.MONTH);
	int today_date = TODAY.get(Calendar.DATE);
	int start_day, last_date;

	/* 컴포넌트 */
	Container c;
	JTextField text_search;
	ImageIcon img_prev = new ImageIcon("img/prev.png");
	ImageIcon img_next = new ImageIcon("img/next.png");
	ImageIcon img_search = new ImageIcon("img/search.png");
	GridBagLayout gridBagLayout;
	GridBagConstraints gridConstraint;
	JPanel panel_top, panel_center, panel_months[];
	JPanel panel_top_left, panel_top_center, panel_top_right;
	JComponent component_dates[] = new JComponent[42];

	/* 재정의 컴포넌트 */
	MyJLabel label_year, label_month, label_yearView, label_monthView;
	MyJLabel label_today, label_prev, label_next, label_search;
	MyJPanel panel_dates[] = new MyJPanel[42];

	/* 화면 모드(M = 월별, Y = 연도별) */
	char view;

	/* DB 변수 */
	Connection db_con;
	Statement db_statement;
	ResultSet db_result;

	/* 이벤트 등록 및 검색창 */
	EventRegister win_register;
	EventSearch win_search;

	/* 사용자를 구분하기 위한 변수 */
	String id;

	public CalendarData(JPanel panel_top, JPanel panel_center, Container c, char view, String id){
		this.panel_top = panel_top;
		this.panel_center = panel_center;
		this.c = c;
		this.view = view;
		this.id = id;
	}

	public void printTopPanel(){
		panel_top_left = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		panel_top_center = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel_top_right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		label_year = new MyJLabel(today_year + "년");
		label_month = new MyJLabel(today_month + 1 + "월");
		label_today = new MyJLabel("오늘");
		label_monthView = new MyJLabel("월별");
		label_yearView = new MyJLabel("연도별");
		label_prev = new MyJLabel(new ImageIcon(img_prev.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		label_next = new MyJLabel(new ImageIcon(img_next.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		label_search = new MyJLabel(new ImageIcon(img_search.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

		text_search = new JTextField("이벤트 제목 검색", 15);
		text_search.setForeground(Color.GRAY);
		text_search.setHorizontalAlignment(SwingConstants.CENTER);
		text_search.addFocusListener(new FocusAdapter(){
			public void focusGained(FocusEvent e){
				if(text_search.getText().equals("이벤트 제목 검색"))
					text_search.setText("");
				text_search.setHorizontalAlignment(SwingConstants.LEFT);
			}
		});

		/* 레이블 마우스 포인터 변경 */
		label_prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_next.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_today.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_yearView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_monthView.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_search.setCursor(new Cursor(Cursor.HAND_CURSOR));

		panel_top_left.add(label_year);
		if(view == 'M')
			panel_top_left.add(label_month);
		panel_top_left.add(label_prev);
		panel_top_left.add(label_next);
		panel_top_center.add(text_search);
		panel_top_center.add(label_search);
		panel_top_right.add(label_today);
		if(view == 'M')
			panel_top_right.add(label_yearView);
		else
			panel_top_right.add(label_monthView);

		panel_top.add(panel_top_left);
		panel_top.add(panel_top_center);
		panel_top.add(panel_top_right);

		/* 이전(연, 월) 이동 */
		label_prev.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(view == 'M')
					currentToday.set(Calendar.MONTH, today_month - 1);
				else
					currentToday.set(Calendar.YEAR, today_year - 1);
				setCalendar();
			}
		});

		/* 다음(연, 월) 이동 */
		label_next.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(view == 'M')
					currentToday.set(Calendar.MONTH, today_month + 1);
				else
					currentToday.set(Calendar.YEAR, today_year + 1);
				setCalendar();
			}
		});

		/* 현재 날짜로 이동 */
		label_today.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				currentToday.set(Calendar.YEAR, TODAY.get(Calendar.YEAR));
				currentToday.set(Calendar.MONTH, TODAY.get(Calendar.MONTH));
				setCalendar();
			}
		});

		/* 월별 화면으로 이동 */
		label_monthView.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				panel_top.removeAll();
				panel_center.removeAll();
				new MonthView(panel_top, panel_center, c, today_year, today_month, id);
				c.revalidate();
				c.repaint();
			}
		});

		/* 연도별 화면으로 이동 */
		label_yearView.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				panel_top.removeAll();
				panel_center.removeAll();
				new YearView(panel_top, panel_center, c, today_year, today_month, id);
				c.revalidate();
				c.repaint();
			}
		});

		/* 검색 화면으로 이동 */
		label_search.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(text_search.getText().length() != 0 && !text_search.getText().equals("이벤트 제목 검색"))
					/* 검색 창을 중복으로 여는 행위 금지 */
					if(win_search == null)
						win_search = new EventSearch(id, text_search.getText());
					else {
						win_search.dispose();
						win_search = new EventSearch(id, text_search.getText());
					}
			}
		});

		/* 검색 창에서 엔터로 검색 */
		text_search.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					/* 검색 창을 중복으로 여는 행위 금지 */
					if(win_search == null)
						win_search = new EventSearch(id, text_search.getText());
					else {
						win_search.dispose();
						win_search = new EventSearch(id, text_search.getText());
					}
			}
		});
	}

	public void setCalendar(){
		today_year = currentToday.get(Calendar.YEAR);
		today_month = currentToday.get(Calendar.MONTH);
		label_year.setText(today_year + "년");
		label_month.setText(today_month + 1 + "월");

		if(view == 'M'){
			panel_center.removeAll();

			/* 시작 날짜 위치 구하기 */
			start_day = getStartDay(today_year, today_month);

			/* 마지막 날짜 구하기 */
			last_date = getLastDate(today_month);

			/* 요일 출력 */
			printDays(panel_center);

			/* 날짜 초기화 */
			for(int i = 0; i < component_dates.length; i++){
				panel_dates[i] = new MyJPanel(new GridLayout(4, 1));
				panel_dates[i].setBorder(2, 5, 2, 0);
				component_dates[i] = new MyJPanel(new BorderLayout());
				((MyJPanel)component_dates[i]).setBorder(1, 0, 0, 0, Color.GRAY);
				component_dates[i].add(panel_dates[i], BorderLayout.CENTER);
				panel_center.add(component_dates[i]);
			}
			
			/* 날짜 출력 */
			dbConnection();
			for(int i = 0; i < last_date; i++){
				final int idx = start_day + i;
				final int idx_date = i + 1;

				component_dates[start_day + i].add(new JLabel(i + 1 + "일"), BorderLayout.NORTH);
				component_dates[start_day + i].setCursor(new Cursor(Cursor.HAND_CURSOR));
				component_dates[start_day + i].addMouseListener(new MouseAdapter(){
					public void mouseEntered(MouseEvent e){
						((MyJPanel)component_dates[idx]).setBorder(1, 1, 0, 1, Color.RED);
						((MyJPanel)component_dates[idx + 7]).setBorder(1, 0, 0, 0, Color.RED);
					}

					public void mouseExited(MouseEvent e){
						((MyJPanel)component_dates[idx]).setBorder(1, 0, 0, 0, Color.GRAY);
						((MyJPanel)component_dates[idx + 7]).setBorder(1, 0, 0, 0, Color.GRAY);
					}

					public void mouseClicked(MouseEvent e){
						/* 새로 등록하는건지 기존에 추가하는건지 확인하기 위한 쿼리 */
						String db_sql = "SELECT * FROM diary_event WHERE CONCAT(" + today_year + ", LPAD(" + (today_month + 1) + ", 2, 0), LPAD(" + idx_date + ", 2, 0)) BETWEEN CONCAT(begin_year, LPAD(begin_month, 2, 0), LPAD(begin_date, 2, 0)) and concat(end_year, LPAD(end_month, 2, 0), LPAD(end_date, 2, 0)) and id = '" + id + "';";
						dbConnection();
						dbExecuteSQL(db_sql, "SELECT");
						try {
							/* 기존 이벤트가 있는 경우 */
							if(db_result.next())
								new EventSearch(id, "test");
								//new EventSearch(id, today_year, today_month + 1, idx_date);
				
							/* 기존 이벤트가 없는 경우 */
							else {
								/* 중복해서 이벤트 등록창 여는 행위 금지 */
								if(win_register == null)
									win_register = new EventRegister(id);
								else {
									win_register.dispose();
									win_register = new EventRegister(id);
								}
							}
						} catch(SQLException se){}
						dbDisconnection();
					}
				});

				/* 날짜에 해당하는 이벤트 제목 출력 */
				String db_sql = "SELECT title FROM diary_event WHERE CONCAT(" + today_year + ", LPAD(" + (today_month + 1) + ", 2, 0), LPAD(" + (i + 1) + ", 2, 0)) BETWEEN CONCAT(begin_year, LPAD(begin_month, 2, 0), LPAD(begin_date, 2, 0)) and concat(end_year, LPAD(end_month, 2, 0), LPAD(end_date, 2, 0)) and id = '" + id + "';";
				dbExecuteSQL(db_sql, "SELECT");
	
				try {
					while(db_result.next()){
						JLabel temp_title = new JLabel(db_result.getString("title"));
						temp_title.setForeground(Color.GRAY);
						panel_dates[start_day + i].add(temp_title);
					}
				} catch(SQLException e){}
			}
			dbDisconnection();
			checkToday();
		} else {
			for(int i = 0; i < 12; i++){
				panel_months[i].removeAll();

				/* 시작 날짜 위치 구하기 */
				start_day = getStartDay(today_year, i);

				/* 마지막 날짜 구하기 */
				last_date = DAY_OF_MONTH[i];

				/* 요일 출력 */
				printDays(panel_months[i]);
			
				/* 날짜 초기화 */
				for(int j = 0; j < component_dates.length; j++){
					component_dates[j] = new MyJLabel();
					((MyJLabel)component_dates[j]).setBorder(1, 1, 1, 1);
					panel_months[i].add(component_dates[j]);
				}
				
				/* 날짜 출력 */
				for(int j = 0; j < last_date; j++){
					component_dates[start_day + j].setForeground(Color.GRAY);
					((MyJLabel)component_dates[start_day + j]).setText(j + 1 + "");
					((MyJLabel)component_dates[start_day + j]).setHorizontalAlignment(SwingConstants.CENTER);
				}	
			}
			checkToday();
		}
	}

	/* 오늘 날짜 표시 */
	private void checkToday(){
		if(today_year == TODAY.get(Calendar.YEAR) && today_month == TODAY.get(Calendar.MONTH)){
			Component components[] = (view == 'M') ? panel_center.getComponents() : panel_months[today_month].getComponents();

			/* 요일은 건너뛰기 위해 6부터 시작 */
			Component temp_today = components[6 + start_day + today_date];
			
			/* 오늘 날짜 표시 */
			if(view == 'M')
				((JPanel)temp_today).getComponent(1).setForeground(Color.RED);
			else {
				((MyJLabel)temp_today).setBorder(1, 1, 1, 1, Color.RED);
				((MyJLabel)temp_today).setFont(new Font("맑은고딕", 1, 13));
			}
		}
	}

	/* 시작 날짜 위치 구하기 */
	private int getStartDay(int year, int month){
		GregorianCalendar date = new GregorianCalendar(year, month, 1);
		return date.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/* 마지막 날짜 구하기 */
	private int getLastDate(int month){
		int last_date = DAY_OF_MONTH[month];

		/* 해당연도가 윤년인지 검사 */	
		if(currentToday.isLeapYear(today_year) && month == 1)
			last_date++; 	

		return last_date;
	}

	/* 요일 출력 */
	private void printDays(JPanel panel_month){
		for(String str : DAYS){
			JLabel label_day;

			if(view == 'M')
				label_day = new MyJLabel(str);
			else {
				label_day = new JLabel("" + str.charAt(0));
				label_day.setFont(new Font("맑은고딕", 1, 13));
				label_day.setForeground(Color.GRAY);
			}
			
			label_day.setHorizontalAlignment(SwingConstants.CENTER);
			panel_month.add(label_day);
		}
	}

	public void dbConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC-ODBC 드라이버를 정상적으로 로드했습니다.");
		} catch(ClassNotFoundException e){
			System.err.println("JDBC-ODBC 드라이버 로드에 실패했습니다.");
		}

		try {
			db_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC", "root", "root");
			db_statement = db_con.createStatement();
		} catch(SQLException e){
			System.err.println("SQL Exception : " + e.getMessage());
		}	
		
	}

	public void dbExecuteSQL(String db_sql, String db_query_type){
		try {
			switch(db_query_type){
				case "INSERT": db_statement.executeUpdate(db_sql); break;
				case "SELECT": db_result = db_statement.executeQuery(db_sql); break;
			}

			System.out.println("SQL Query문이 정상적으로 실행되었습니다.");
		} catch(SQLException e){
			System.err.println("SQL Exception : " + e.getMessage());
		}	
	}
	
	public void dbDisconnection(){
		try {
			db_statement.close();
			db_con.close();
		} catch(SQLException e){}
	}
}
