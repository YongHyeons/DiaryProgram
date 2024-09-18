package lib.member;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.sql.*;

import lib.components.MyJLabel;

public class MemberData extends JDialog{
	String hint[] = {"기억에 남는 추억의 장소는?", "자신의 인생 좌우명은?", "자신의 보물 제1호는?", "가장 기억에 남는 선생님 성함은?", "추억하고 싶은 날짜가 있다면?", "자신이 두번째로 존경하는 인물은?"};
	JPanel panel_info, panel_button;
	JTextField text_id, text_pw_answer;
	JPasswordField text_pw, text_pw_check;
	JComboBox combo_pw_question;
	JButton btn_check, btn_cancel;

	/* DB변수 */
	Connection db_con;
	Statement db_statement;
	ResultSet db_result;

	MemberData(){
		setMemberData();
	}

	MemberData(JFrame frame, boolean mode){
		super(frame, mode);
		setMemberData();
	}

	private void setMemberData(){
		panel_button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		text_id = new JTextField();
		text_pw = new JPasswordField();
		text_pw_check = new JPasswordField();
		combo_pw_question = new JComboBox(hint);
		text_pw_answer = new JTextField();
		btn_check = new JButton("확인");
		btn_cancel = new JButton("취소");

		/* 버튼 크기 재설정 */
		btn_check.setPreferredSize(new Dimension(90, 35));
		btn_cancel.setPreferredSize(new Dimension(90, 35));

		panel_button.add(btn_check);
		panel_button.add(btn_cancel);

		/* 글자 수 제한 */
		text_id.addKeyListener(new MyKeyAdapter(10));
		text_pw.addKeyListener(new MyKeyAdapter(20));
		text_pw_check.addKeyListener(new MyKeyAdapter(20));
		text_pw_answer.addKeyListener(new MyKeyAdapter(20));
	}

	/* 입력 값이 비어있는지 확인 */	
	void checkEmptyData(Component[] components) throws EmptyDataException{
		for(int i = 1; i < components.length; i += 2){
			/* 콤보박스 */
			if(i == components.length - 4)
				continue;

			if(((JTextComponent)components[i]).getText().equals(""))
				throw new EmptyDataException("입력되지 않은 데이터 : " + ((JLabel)components[i-1]).getText(), components[i]);
		}
	}

	/* 지정된 텍스트 크기를 벗어났는지 확인 */
	void checkTextSize(JTextComponent component, int size) throws TextSizeException{
		if(component.getText().length() > size)
			throw new TextSizeException("입력 값에 오류가 있습니다", component);
	}

	void showMessageDialog(String title, String content){
		JOptionPane.showMessageDialog(null, content, title, JOptionPane.PLAIN_MESSAGE);
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

	class MyKeyAdapter extends KeyAdapter{
		int size;

		MyKeyAdapter(int size){
			super();
			this.size = size;
		}

		public void keyTyped(KeyEvent e){
			JTextComponent temp_component = (JTextComponent)e.getSource();

			if(temp_component.getText().length() > size - 1)
				e.consume();
		}
	}
}

class EmptyDataException extends Exception{
	String msg;
	JTextComponent component;

	EmptyDataException(String msg, Component component){
		this.msg = msg;
		this.component = (JTextComponent)component;
	}
}

class TextSizeException extends Exception{
	String msg;
	JTextComponent component;

	TextSizeException(String msg, JTextComponent component){
		this.msg = msg;
		this.component = component;
	}
}
