import java.awt.*;
import javax.swing.*;

import lib.member.*;

public class DiaryProgram extends JFrame{
	DiaryProgram(){
		JFrame startView = this;

		/* 화면 초기화 */
		setSize(400, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* 로그인 화면 출력 */
		new Login(startView);
		startView.revalidate();
		startView.repaint();
	}

	public static void main(String args[]){
		DiaryProgram diary = new DiaryProgram();	
	}
}
