package lib.member;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.sql.*;

import lib.calendar.*;

public class Login extends JFrame implements ActionListener, FocusListener//로그인
{
	JTextField id; JPasswordField passwd;	//아이디와 패스워드 생성
	JButton Login_bt, Sign_t, Sign_not, pass_t;
	String pass;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	JLabel Login_war;
	JFrame parent;
	
	public Login(JFrame parent){
		this.parent = parent;
		parent.setTitle("로그인");
		parent.setLayout(null);

		id = new JTextField("User ID");

		passwd = new JPasswordField ("Password");

		Login_bt = new JButton("LOGIN");

		Sign_t = new JButton("회원가입");
		Sign_t.setBorderPainted(false);
		Sign_t.setContentAreaFilled(false);

		pass_t = new JButton("비밀번호 찾기");
		pass_t.setBorderPainted(false);
		pass_t.setContentAreaFilled(false);

		Sign_not = new JButton("회원가입 없이 이용하기");
		Login_war = new JLabel();

		id.setBounds (70, 50, 245, 50);
		passwd.setBounds (70, 120, 245, 50);
		Login_bt.setBounds (70, 200, 245, 70);
		Sign_t.setBounds (138, 280, 110, 20);
		Sign_not.setBounds (70, 310, 245, 50);
		Login_war.setBounds (60, 370, 265, 50);
		pass_t.setBounds (100, 365, 190, 20);

		parent.add(id); parent.add(passwd); parent.add(Login_bt); parent.add(Login_war); parent.add(Sign_t); parent.add(Sign_not); parent.add(pass_t);
		id.addFocusListener(this);
		passwd.addFocusListener(this);
		Login_bt.addActionListener(this);
		Sign_t.addActionListener(this);
		Sign_not.addActionListener(this);
		pass_t.addActionListener(this);

		Login_war.setForeground(Color.RED);

	}
	public void focusGained(FocusEvent e){
		
		pass = new String(passwd.getPassword());
		if(e.getSource().equals(id) && id.getText().equals("User ID"))
			id.setText("");
		else if(e.getSource().equals(passwd) && pass.equals("Password"))
			passwd.setText("");
		
		}
	
		public void focusLost(FocusEvent e){
		pass = new String(passwd.getPassword());
		if(e.getSource().equals(id) && id.getText().length()==0)
		id.setText("User ID");
		else if(e.getSource().equals(passwd) && pass.length()==0)
		passwd.setText("Password");
		}

		public void actionPerformed(ActionEvent a){
			String s = a.getActionCommand();
			String strSql;
			String t_id = id.getText();
			pass = new String(passwd.getPassword());
			
			if(s == "LOGIN"){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException la){System.err.println("드라이버 로드에 실패했습니다.");}
		
			try{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/diary?serverTimezone=UTC","root","root");
				System.out.println("DB 연결 완료.");
				Statement dbst = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				strSql = "SELECT * FROM diary_member WHERE id = '" + t_id +"' and passwd = '"+ pass +"';";
				ResultSet result = dbst.executeQuery(strSql);
				if(result.next())
				{
					parent.dispose();
					new CalendarView(t_id);
				}
				else{
					Login_war.setText("User id 또는 Password를 다시 확인해 주세요.");
				}
				System.out.println("데이터 확인 완료");
				dbst.close();
				con.close();
			}catch(SQLException l){System.out.println("SQLException : " + l.getMessage());}
		}
		else if(s == "회원가입"){
			parent.getContentPane().removeAll();
			new MemberRegister(parent);
			parent.revalidate();
			parent.repaint();
		}
		else if(s == "회원가입 없이 이용하기"){
			parent.dispose();
			new CalendarView("guest");
		}
		else if(s == "비밀번호 찾기"){
			new FindPassword(parent, true);
		}
		}
}
