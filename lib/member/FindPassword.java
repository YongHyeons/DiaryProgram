package lib.member;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.sql.*;

import lib.components.MyJLabel;

public class FindPassword extends MemberData{
	FindPassword(JFrame frame, boolean mode){
		super(frame, mode);

		Container c = getContentPane();

		panel_info = new JPanel(new GridLayout(7, 1));
		panel_info.setBorder(BorderFactory.createEmptyBorder(0, 20, 8, 20));

		panel_info.add(new MyJLabel("아이디 *"));
		panel_info.add(text_id);
		panel_info.add(new MyJLabel("비밀번호 찾기 질문 *"));
		panel_info.add(combo_pw_question);
		panel_info.add(new MyJLabel("비밀번호 찾기 답변 *"));
		panel_info.add(text_pw_answer);
		panel_info.add(panel_button);
	
		c.add(panel_info);

		btn_check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Component components[] = panel_info.getComponents();

				String id = text_id.getText();
				int pw_question = combo_pw_question.getSelectedIndex();
				String pw_answer = text_pw_answer.getText();

				if(!id.equals("")){
					String db_sql = "SELECT * FROM diary_member WHERE id = '" + id + "' AND pw_question = " + pw_question + " AND pw_answer = '" + pw_answer + "';";

					dbConnection();
					dbExecuteSQL(db_sql, "SELECT");
					try {
						if(db_result.next())
							showMessageDialog("회원가입", "비밀번호는 " + db_result.getString("passwd") + " 입니다.");
						else
							showMessageDialog("회원가입", "회원정보가 일치하지 않습니다.");
					} catch(SQLException e){	}

					dbDisconnection();
				}
			}
		});

		btn_cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});

		/* 화면 초기화 */
		setTitle("비밀번호 찾기");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
