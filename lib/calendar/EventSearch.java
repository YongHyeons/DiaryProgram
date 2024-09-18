package lib.calendar;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.util.Vector.*;
import java.sql.*;

public class EventSearch extends JFrame	implements FocusListener, ActionListener, ListSelectionListener{
	Container ct = getContentPane();

	JPanel top = new JPanel();
	JPanel right = new JPanel();	
	JPanel left = new JPanel();
	JPanel left_a = new JPanel();
	JPanel right_a = new JPanel();
	JPanel right_a2 = new JPanel();
	JPanel right_b = new JPanel();
	JPanel right_c = new JPanel();
	JPanel right_d = new JPanel();
	JPanel right_e = new JPanel();

	JTextField Event_listitem, Event_place, Event_start, Event_finish, Search_box;
	JButton Info_Modified, Info_Delete, Search_bt, add_event;
	JLabel Event_list, Detail_info, search_list;
	JTextArea Info_box;
	JList Events;
	Vector<String> vec;
	Vector<Integer> Event_key = new Vector<Integer>();
	int key_num;

	Font f1 = new Font("맑은 고딕", Font.BOLD, 25);
	Font f2 = new Font("맑은 고딕", 0, 20);

	String id;

	public EventSearch(String id, int year, int month, int date){	
		this.id = id;
		setSize(1100, 600);//win.setSize(750, 750);
		setLocationRelativeTo(null);
       		//setLocation(200, 200);
       		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       		show();

		ct.setLayout(new BorderLayout());
		setResizable(false);
		
		ct.add(top, BorderLayout.NORTH);
		ct.add(right, BorderLayout.CENTER);
		ct.add(left, BorderLayout.WEST);
		

		top.setLayout(new FlowLayout());
		left.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		right.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		right.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 10));
		top.setPreferredSize(new Dimension(1300,80));
		left.setPreferredSize(new Dimension(550,500));
		right.setPreferredSize(new Dimension(450,500));
		
		
		
		left_a.setLayout(new BorderLayout(10,10));
		right_a.setLayout(new BorderLayout(170,10));
      		right_a2.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
      		right_b.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		right_c.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		right_d.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		right_e.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		
		//top
		Search_box = new JTextField(15);
		Search_bt = new JButton("검색");

		//left
		search_list = new JLabel("검색결과");
		add_event = new JButton("추가");
		Events = new JList();
		Events.setPreferredSize(new Dimension(500,410));
		vec = new Vector<String>();

		//right
		Detail_info = new JLabel("세부내용");
		Info_Modified = new JButton("수정");
		Info_Delete = new JButton("삭제");
		Event_place = new JTextField("위치",25);
		Event_start = new JTextField("시작날짜",25);
		Event_finish = new JTextField("종료날짜",25);
		Info_box = new JTextArea(8,25);
		Info_box.setLineWrap(true);

		top.add(Search_box); top.add(Search_bt);

		left_a.add(search_list, BorderLayout.CENTER); left_a.add(add_event, BorderLayout.EAST);
		left.add(left_a); left.add(Events);
		
		right_a2.add(Info_Modified); right_a2.add(Info_Delete);
		right.add(right_a); right.add(right_b); right.add(right_c); right.add(right_d); right.add(right_e);
		
		right_a.add(Detail_info,BorderLayout.CENTER); 
		right_a.add(right_a2,BorderLayout.EAST);
		right_b.add(Event_place); right_c.add(Event_start); right_d.add(Event_finish);
		right_e.add(Info_box);

		Event_place.addFocusListener(this);
		Event_start.addFocusListener(this);
		Event_finish.addFocusListener(this);
		Search_bt.addActionListener(this);
		Events.addListSelectionListener(this);
		Info_Delete.addActionListener(this);

		search_list.setFont(f1); Detail_info.setFont(f1); Search_box.setFont(f1); Search_bt.setFont(f1); 
		Info_Modified.setFont(f2); Event_place.setFont(f2); Event_start.setFont(f2); Event_finish.setFont(f2); 
		Events.setFont(f2); Info_box.setFont(f2); Info_Delete.setFont(f2);


		Vector<String> test_v = new Vector<String>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		}catch(ClassNotFoundException la){System.err.println("드라이버 로드에 실패했습니다.");}
	
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
			System.out.println("DB 연결 완료.");
			Statement dbst = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			String strSql = "SELECT * FROM diary_event WHERE CONCAT(" + year + ", LPAD(" + month + ", 2, 0), LPAD(" + date + ", 2, 0)) BETWEEN CONCAT(begin_year, LPAD(begin_month, 2, 0), LPAD(begin_date, 2, 0)) and concat(end_year, LPAD(end_month, 2, 0), LPAD(end_date, 2, 0)) and id = '" + id + "';";
			ResultSet result = dbst.executeQuery(strSql);
			while(result.next()){
				vec.add(result.getString("title"));
				Event_key.addElement(result.getInt(1));
			}
			Events.setListData(vec);
			System.out.println("데이터 삽입 완료");
			dbst.close();
			con.close();
		}catch(SQLException l){System.out.println("SQLException : " + l.getMessage());}
		
	}

	public EventSearch(String id, String title){	
		this.id = id;
		setSize(1100, 600);
		setLocationRelativeTo(null);
       		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       		show();

		ct.setLayout(new BorderLayout());
		setResizable(false);
		
		ct.add(top, BorderLayout.NORTH);
		ct.add(right, BorderLayout.CENTER);
		ct.add(left, BorderLayout.WEST);
		
		top.setLayout(new FlowLayout());
		left.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		right.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		right.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 10));
		top.setPreferredSize(new Dimension(1300,80));
		left.setPreferredSize(new Dimension(550,500));
		right.setPreferredSize(new Dimension(450,500));




		left_a.setLayout(new BorderLayout(10,10));
		right_a.setLayout(new BorderLayout(170,10));
      		right_a2.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
      		right_b.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		right_c.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		right_d.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));
		right_e.setLayout(new FlowLayout(FlowLayout.LEFT,22,8));


		//top
		Search_box = new JTextField(15);
		Search_bt = new JButton("검색");

		//left
		search_list = new JLabel("검색결과");
		add_event = new JButton("추가");
		Events = new JList();
		Events.setPreferredSize(new Dimension(500,600));
		vec = new Vector<String>();

		//right
		Detail_info = new JLabel("세부내용");
		Info_Modified = new JButton("수정");
		Info_Delete = new JButton("삭제");
		Event_place = new JTextField("위치",25);
		Event_start = new JTextField("시작날짜",25);
		Event_finish = new JTextField("종료날짜",25);
		Info_box = new JTextArea(8,25);
		Info_box.setLineWrap(true);
		
		top.add(Search_box); top.add(Search_bt);

		left_a.add(search_list, BorderLayout.CENTER); left_a.add(add_event, BorderLayout.EAST);
		left.add(left_a); left.add(Events);
		
		right_a2.add(Info_Modified); right_a2.add(Info_Delete);
		right.add(right_a); right.add(right_b); right.add(right_c); right.add(right_d); right.add(right_e);
		
		right_a.add(Detail_info,BorderLayout.CENTER); 
		right_a.add(right_a2,BorderLayout.EAST);
		right_b.add(Event_place); right_c.add(Event_start); right_d.add(Event_finish);
		right_e.add(Info_box);

		Event_place.addFocusListener(this);
		Event_start.addFocusListener(this);
		Event_finish.addFocusListener(this);
		Search_bt.addActionListener(this);
		Events.addListSelectionListener(this);

		search_list.setFont(f1); Detail_info.setFont(f1); Search_box.setFont(f1); Search_bt.setFont(f1); 
		Info_Modified.setFont(f2); Event_place.setFont(f2); Event_start.setFont(f2); Event_finish.setFont(f2); 
		Events.setFont(f2); Info_box.setFont(f2); Info_Delete.setFont(f2);

		String strSql;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		}catch(ClassNotFoundException la){System.err.println("드라이버 로드에 실패했습니다.");}
	
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
			System.out.println("DB 연결 완료.");
			Statement dbst = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			strSql = "SELECT * FROM diary_event WHERE title LIKE '%" + title + "%' and id = '" + id + "';";
			ResultSet result = dbst.executeQuery(strSql);
			vec.clear();
			while(result.next())
			{
				Event_key.addElement(result.getInt(1));
				vec.addElement(result.getString(2));
			}
			Events.setListData(vec);
			System.out.println("데이터 삽입 완료");
			dbst.close();
			con.close();
		}catch(SQLException l){System.out.println("SQLException : " + l.getMessage());}
	}
	public void focusGained(FocusEvent e){
	
	if(e.getSource().equals(Event_place) && Event_place.getText().equals("위치"))
		Event_place.setText("");
	else if(e.getSource().equals(Event_start) && Event_start.getText().equals("시작날짜"))
		Event_start.setText("");
	else if(e.getSource().equals(Event_finish) && Event_finish.getText().equals("종료날짜"))
		Event_finish.setText("");
	}

	public void focusLost(FocusEvent e){

	if(e.getSource().equals(Event_place) && Event_place.getText().length()==0)
	Event_place.setText("위치");
	else if(e.getSource().equals(Event_start) && Event_start.getText().length()==0)
	Event_start.setText("시작날짜");
	else if(e.getSource().equals(Event_finish) && Event_finish.getText().length()==0)
	Event_finish.setText("종료날짜");
	}

	public void actionPerformed(ActionEvent ae){
		Event_key.clear();
		String strSql;
		String s = ae.getActionCommand();
		String t_title = Search_box.getText();
		if(s.equals("검색")){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException la){System.err.println("드라이버 로드에 실패했습니다.");}
		
			try{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
				System.out.println("DB 연결 완료.");
				Statement dbst = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				strSql = "SELECT * FROM diary_event WHERE title LIKE '%" + t_title + "%' and id ='" + id + "';";
				ResultSet result = dbst.executeQuery(strSql);
				vec.clear();
				while(result.next())
				{
					Event_key.addElement(result.getInt(1));
					vec.addElement(result.getString(2));
				}
				Events.setListData(vec);
				System.out.println("데이터 삽입 완료");
				dbst.close();
				con.close();
			}catch(SQLException l){System.out.println("SQLException : " + l.getMessage());}
		} else if(s.equals("삭제")){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException la){System.err.println("드라이버 로드에 실패했습니다.");}
		
			try{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
				System.out.println("DB 연결 완료.");
				Statement dbst = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				strSql = "DELETE FROM diary_event WHERE no = " + key_num ";";
				dbst.executeUpdate(strSql);
				ResultSet result = dbst.executeQuery(strSql);
				System.out.println("데이터 삭제 완료");
				dbst.close();
				con.close();
			}catch(SQLException l){System.out.println("SQLException : " + l.getMessage());}
		}
	}
	public void valueChanged(ListSelectionEvent se){
		String strSql;
		String s_title = Events.getSelectedValue().toString();
		key_num = Event_key.get(Events.getSelectedIndex());
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		}catch(ClassNotFoundException la){System.err.println("드라이버 로드에 실패했습니다.");}
	
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
			System.out.println("DB 연결 완료.");
			Statement dbst = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
			strSql = "SELECT * FROM diary_event WHERE no = '" + key_num +"' and title = '"+ s_title +"';";
			ResultSet result = dbst.executeQuery(strSql);
			while(result.next()){
				Event_place.setText(result.getString(3));
				Event_start.setText(result.getInt(5)+"-"+result.getInt(6)+"-"+result.getInt(7)+"   "+result.getInt(8)+":"+result.getInt(9));
				Event_finish.setText(result.getInt(10)+"-"+result.getInt(11)+"-"+result.getInt(12)+"   "+result.getInt(13)+":"+result.getInt(14));
				Info_box.setText(result.getString(4));
			}
			System.out.println("데이터 삽입 완료");
			dbst.close();
			con.close();
		}catch(SQLException l){System.out.println("SQLException : " + l.getMessage());}
	}
}
