package lib.calendar;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.JPanel;


//저장화면
public class EventRegister extends JFrame implements ActionListener, FocusListener, KeyListener
{
	Container ct = getContentPane();
	JTextField Event_name, Event_place, Event_start, Event_finish, time1, time2;
	JButton Save_bt, Delete_bt, All_day;
	JLabel Basic_info, Detail_info, repeat_lb;
	JComboBox repeat_cb;
	String [] repeat_list = {"반복 안함","매주","매년"};
   	JTextArea Info_box;
    	JPanel top, right, left, left_a, left_b, left_c, left_d, left_e;
    	Font f1 = new Font("맑은 고딕", 0, 25);
	Font f2 = new Font("맑은 고딕", Font.BOLD, 20);
	String repeat_value;
	int repeat_num = 3;
	int Event_num;
	int monthArr[] = {31,28,31,30,31,30,31,31,30,31,30,31};


//////////////////////////
////생성자에서 아이디 넘겨받아야함 그리고 이벤트 등록 시 아이디 값 추가하도록설정
////로그인 후 사용할 땐 아이디고 회원가입 없이 사용일 땐 guest로 들어감
////////////////////////////

	public EventRegister(String id){
		setSize(1100, 600);
		setLocationRelativeTo(null);
       		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       		show();
		
		ct.setLayout(new BorderLayout());
		setResizable(false);

		//panel 생성
		top = new JPanel();
		right = new JPanel();
        		left = new JPanel();
       		left_a = new JPanel();
        		left_b = new JPanel();
        		left_c = new JPanel();
        		left_d = new JPanel();
        		left_e = new JPanel();

		ct.add(top, BorderLayout.NORTH);
		ct.add(right, BorderLayout.CENTER);
        		ct.add(left, BorderLayout.WEST);
        
		//panel ui
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        		top.setBorder(new LineBorder(Color.WHITE,2));
		top.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        		top.setPreferredSize(new Dimension(d.width,80));

		left.setBorder(new TitledBorder(new LineBorder(Color.WHITE,2),"개인정보"));
		((javax.swing.border.TitledBorder) left.getBorder()).setTitleFont(f2);
       		left.setLayout(new GridLayout(5,1));
        		left_a.setLayout(new FlowLayout(FlowLayout.LEFT,15,10));
        		left_b.setLayout(new FlowLayout(FlowLayout.LEFT,15,10));
        		left_c.setLayout(new FlowLayout(FlowLayout.LEFT,15,10));
        		left_d.setLayout(new FlowLayout(FlowLayout.LEFT,15,10));
        		left_e.setLayout(new FlowLayout(FlowLayout.LEFT,15,10));
        		left.setPreferredSize(new Dimension(550,500));

		right.setBorder(new TitledBorder(new LineBorder(Color.WHITE,2),"상세정보"));
		((javax.swing.border.TitledBorder) right.getBorder()).setTitleFont(f2);
        		right.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		right.setPreferredSize(new Dimension(450,500));
		
		
		//top component
		Save_bt = new JButton("저장");
		Delete_bt = new JButton("닫기");
		
		//left component
		Event_name = new JTextField("이벤트이름",20); //한글 50자 100byte
		Event_place = new JTextField("위치",20);	//한글 50자 100byte

		Event_start = new JTextField("시작날짜",10);
		Event_finish = new JTextField("종료날짜",10);
		
				

		time1 = new JTextField("시작시간",6);
		time2 = new JTextField("종료시간",6);
		All_day = new JButton("종일");
		repeat_lb = new JLabel("반복");
		repeat_cb = new JComboBox(repeat_list);
		
		//right component
        		Info_box = new JTextArea(12,22); //한글 1000자 2000byte
		Info_box.setLineWrap(true);

		
		//화면배치
		top.add(Save_bt); top.add(Delete_bt); 

		left.add(left_a); left.add(left_b); left.add(left_c); left.add(left_d); left.add(left_e);
		
        		left_a.add(Event_name); left_b.add(Event_place);
		left_c.add(Event_start); left_c.add(time1); left_c.add(All_day);
		left_d.add(Event_finish); left_d.add(time2);
		left_e.add(repeat_lb); left_e.add(repeat_cb);

        right.add(Info_box);
        
        //top
        Save_bt.setFont(f2); Delete_bt.setFont(f2); 
        Save_bt.setBorderPainted(false);  Delete_bt.setBorderPainted(false);
		Save_bt.setContentAreaFilled(false); Delete_bt.setContentAreaFilled(false);
    
        //left
        Event_name.setFont(f1); Event_place.setFont(f1); 
        Event_start.setFont(f1); time1.setFont(f1); 
        Event_finish.setFont(f1); time2.setFont(f1); 
        All_day.setFont(f1); repeat_lb.setFont(f1); repeat_cb.setFont(f1); 

        //right
		Info_box.setFont(f1);
		
		//Listener 연결
		Save_bt.addActionListener(this);
		Delete_bt.addActionListener(this);

		Event_name.addFocusListener(this);
		Event_place.addFocusListener(this);
		Event_start.addFocusListener(this);
		Event_finish.addFocusListener(this);
		time1.addFocusListener(this);
		time2.addFocusListener(this);

		Event_start.addKeyListener(this);
		Event_finish.addKeyListener(this);
		time1.addKeyListener(this);
		time2.addKeyListener(this);
		

	} //class 마지막 
	
	public void actionPerformed(ActionEvent e){

		String s = e.getActionCommand();
		if(s == "닫기")
			dispose();
		else{
		String t_title, t_location, t_content; 
		int t_start_y, t_start_m, t_start_d,t_start_h, t_start_mn;
		int t_end_y, t_end_m, t_end_d, t_end_h, t_end_mn; //id는 로그인 이후 추가
		String idtest;
		String day_s[] = Event_start.getText().split("-");
		int plus_day=0;
		t_title = Event_name.getText(); 
		t_location =Event_place.getText(); 
		t_content = Info_box.getText();
		t_start_y = Integer.parseInt(day_s[0]);
		t_start_m = Integer.parseInt(day_s[1]);
		t_start_d = Integer.parseInt(day_s[2]);
		t_start_h = Integer.parseInt(time1.getText());
		t_start_mn = Integer.parseInt(time1.getText());
		t_end_y = Integer.parseInt(Event_finish.getText());
		t_end_m = Integer.parseInt(Event_finish.getText());
		t_end_d = Integer.parseInt(Event_finish.getText());
		t_end_h = Integer.parseInt(time2.getText());
		t_end_mn = Integer.parseInt(time2.getText());
		idtest ="test1";
		repeat_value = (String) repeat_cb.getSelectedItem();

		if(s.equals("저장")){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException a){System.err.println("드라이버 로드에 실패했습니다.");}
		
			try{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
				System.out.println("DB 연결 완료.");
				Statement dbst = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				String strSql = "INSERT INTO diary_event (title, location, content, begin_year, begin_month, begin_date, begin_hour, begin_minute, end_year, end_month, end_date, end_hour, end_minute, id) VALUES (" + " ' " +t_title+ "','" +t_location+ "','" +t_content+ "','" +t_start_y+ "','" +t_start_m+ "','" +t_start_d+ "','" +t_start_h+ "','" +t_start_mn+ "','" +t_end_y+ "','" +t_end_m+ "','" +t_end_d+ "','" +t_end_h+ "','" +t_end_mn+ "','" +idtest+"');";
				dbst.executeUpdate(strSql);
				System.out.println("데이터 삽입 완료");
				dbst.close();
				/*strSql = "SELECT * FROM (SELECT * FROM diary_event ORDER BY no DESC) WHERE ROWNUM = 1;";
						ResultSet result = dbst.executeQuery(strSql);
						Event_num = result.getInt(1);*/
				
				for(int i = 0; i<repeat_num; i++){
				if(repeat_value == "매주") {
						plus_day = 7;
						t_start_d += plus_day;

					if(t_start_y % 4 == 0 && t_start_y % 100 != 0 || t_start_y % 400 == 0) 
					monthArr[1] = 28;

					else{
					monthArr[1] = 29;
					}

					if(t_start_d > monthArr[t_start_m-1]){
					t_start_d = t_start_d - monthArr[t_start_m-1];
					t_start_m++;
					}

					if(t_start_m > 12){
					t_start_y++;
					t_start_m = t_start_m - 12;
					}
				}
				else if(repeat_value == "매년") {
					plus_day = 1;
					t_start_y++;
				}
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
				System.out.println("DB 연결 완료.");
				dbst = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				strSql = "INSERT INTO diary_event (title, location, content, begin_year, begin_month, begin_date, begin_hour, begin_minute, end_year, end_month, end_date, end_hour, end_minute, id) VALUES (" + " ' " +t_title+ "','" +t_location+ "','" +t_content+ "','" +t_start_y+ "','" +t_start_m+ "','" +t_start_d+ "','" +t_start_h+ "','" +t_start_mn+ "','" +t_end_y+ "','" +t_end_m+ "','" +t_end_d+ "','" +t_end_h+ "','" +t_end_mn+ "','" +idtest+"');";
				dbst.executeUpdate(strSql);
				System.out.println("데이터 삽입 완료");
				dbst.close();

				}
				
			
			}catch(SQLException a){System.out.println("SQLException : " + a.getMessage());}
		}
		} 
	}
	public void focusGained(FocusEvent e){
	
		if(e.getSource().equals(Event_name) && Event_name.getText().equals("이벤트이름"))
				Event_name.setText("");
			else if(e.getSource().equals(Event_place) && Event_place.getText().equals("위치"))
				Event_place.setText("");
			else if(e.getSource().equals(Event_start) && Event_start.getText().equals("시작날짜"))
				Event_start.setText("");
			else if(e.getSource().equals(Event_finish) && Event_finish.getText().equals("종료날짜"))
				Event_finish.setText("");
			else if(e.getSource().equals(time1) && time1.getText().equals("시작시간"))
				time1.setText("");
			else if(e.getSource().equals(time2) && time2.getText().equals("종료시간"))
				time2.setText("");
		
		}
	
	public void focusLost(FocusEvent e){
	
		if(e.getSource().equals(Event_name) && Event_name.getText().length()==0)
				Event_name.setText("이벤트이름");
			else if(e.getSource().equals(Event_place) && Event_place.getText().length()==0)
				Event_place.setText("위치");
			else if(e.getSource().equals(Event_start) && Event_start.getText().length()==0)
				Event_start.setText("시작날짜");
			else if(e.getSource().equals(Event_finish) && Event_finish.getText().length()==0)
				Event_finish.setText("종료날짜");
			else if(e.getSource().equals(time1) && time1.getText().length()==0)
				time1.setText("시작시간");
			else if(e.getSource().equals(time2) && time2.getText().length()==0)
				time2.setText("종료시간");
		}

	public void keyPressed(KeyEvent k){
		if(k.getKeyCode() != KeyEvent.VK_BACK_SPACE){

		/*if(k.getSource().equals(Event_start)){
			if(Event_start.getText().length()==4 || Event_start.getText().length()==7)
				Event_start.setText(Event_start.getText() + "-");
		}*/
		if(k.getSource().equals(Event_finish)){
			if(Event_finish.getText().length()==4 || Event_finish.getText().length()==7)
				Event_finish.setText(Event_finish.getText() + "-");
		}
		if(k.getSource().equals(time1)){
			if(time1.getText().length()==2)
				time1.setText(time1.getText() + ":");
		}
		if(k.getSource().equals(time2)){
			if(time2.getText().length()==2)
				time2.setText(time2.getText() + ":");
		}
		}
		}

	public void keyReleased(KeyEvent ka){
		;
	}
	public void keyTyped(KeyEvent kb){
		if(kb.getSource().equals(Event_start)){
			if(Event_start.getText().length()==4 || Event_start.getText().length()==7)
				Event_start.setText(Event_start.getText() + "-");
			if(Event_start.getText().length() >= 10){
				kb.consume();
				time1.requestFocus();
			}
		}
	
	}

}
