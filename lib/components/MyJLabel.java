package lib.components;

import java.awt.*;
import javax.swing.*;

/* 레이블 재정의 */
public class MyJLabel extends JLabel{
	public MyJLabel(){
		super();
	}

	public MyJLabel(String text){
		super(text);
		setFont(new Font("맑은고딕", Font.BOLD, 15));	
		setBorder(0, 5, 0, 5);
	}

	public MyJLabel(ImageIcon icon){
		super(icon);
		setBorder(0, 10, 0, 10);
	}

	/* Empty Border 설정 */
	public void setBorder(int up, int left, int down, int right){
		this.setBorder(BorderFactory.createEmptyBorder(up, left, down, right));
	}

	/* Color Border 설정 */
	public void setBorder(int up, int left, int down, int right, Color color){
		this.setBorder(BorderFactory.createMatteBorder(up, left, down, right, color));
	}
}
