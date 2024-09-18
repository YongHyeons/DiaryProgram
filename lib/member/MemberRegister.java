package lib.member;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.sql.*;

import lib.components.MyJLabel;

public class MemberRegister extends MemberData{
	MemberRegister(JFrame parentView){
		parentView.setTitle("회원가입");
		parentView.setLayout(new BorderLayout());

		panel_info = new JPanel(new GridLayout(11, 1));
		panel_info.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

		panel_info.add(new MyJLabel("아이디 (최대 10자) *"));
		panel_info.add(text_id);
		panel_info.add(new MyJLabel("비밀번호 (최대 20자) *"));
		panel_info.add(text_pw);
		panel_info.add(new MyJLabel("비밀번호 확인*"));
		panel_info.add(text_pw_check);
		panel_info.add(new MyJLabel("비밀번호 찾기 질문 *"));
		panel_info.add(combo_pw_question);
		panel_info.add(new MyJLabel("비밀번호 찾기 답변 (최대 20자) *"));
		panel_info.add(text_pw_answer);
		panel_info.add(panel_button);

		parentView.add(panel_info);
		text_id.requestFocus();
	
		btn_check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Component components[] = panel_info.getComponents();

				/* 입력 값에 오류가 있는지 확인 */
				try {
					checkEmptyData(components);
					checkTextSize(text_id, 10);
					checkTextSize(text_pw, 20);
					checkTextSize(text_pw_check, 20);
					checkTextSize(text_pw_answer, 20);

					String id = text_id.getText();
					String passwd = text_pw.getText();
					String passwd_check = text_pw_check.getText();
					int pw_question = combo_pw_question.getSelectedIndex();
					String pw_answer = text_pw_answer.getText();

					if(!passwd.equals(passwd_check)){
						showMessageDialog("회원가입", "비밀번호가 서로 맞지 않습니다");
						text_pw_check.setText("");
						text_pw_check.requestFocus();
						return;
					}

					String db_sql = "INSERT INTO diary_member VALUES(";
					db_sql += "'" + id + "',";
					db_sql += "'" + passwd + "',";
					db_sql += "'" + pw_question + "',";
					db_sql += "'" + pw_answer + "');";
				
					dbConnection();
					dbExecuteSQL(db_sql, "INSERT");
					dbDisconnection();
					showMessageDialog("회원가입", "회원가입이 완료되었습니다.");

					parentView.getContentPane().removeAll();
					new Login(parentView);
					parentView.revalidate();
					parentView.repaint();
				} catch(EmptyDataException e){
					showMessageDialog("회원가입", e.msg);
					e.component.requestFocus();	
				} catch(TextSizeException e){
					showMessageDialog("회원가입", e.msg);
					e.component.setText("");
					e.component.requestFocus();	
				}
			}
		});

		btn_cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				parentView.getContentPane().removeAll();
				new Login(parentView);
				parentView.revalidate();
				parentView.repaint();
			}
		});

		/* 아이디 중복확인 */
		text_id.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent fe){
				String id = text_id.getText();

				if(!id.equals("")){
					String db_sql = "SELECT * FROM diary_member WHERE id = '" + id + "';";

					dbConnection();
					dbExecuteSQL(db_sql, "SELECT");
					try {
						if(db_result.next()){
							showMessageDialog("회원가입", "이미 존재하는 아이디 입니다.");
							text_id.setText("");
							text_id.requestFocus();
						}
						else		
							showMessageDialog("회원가입", "사용할 수 있는 아이디 입니다.");
					} catch(SQLException e){}
					dbDisconnection();
				}
			}
		});
	}
}
