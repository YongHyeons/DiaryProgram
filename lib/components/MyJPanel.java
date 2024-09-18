package lib.components;

import java.awt.*;
import javax.swing.*;

/* 패널 재정의 */
public class MyJPanel extends JPanel{
	public MyJPanel(LayoutManager layout){
		super(layout);
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
