package client;

import java.awt.Graphics;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JPanel;

import war.Config;


public class LablePanel extends JPanel
{	
	JButton startButton;
	TextField textField;
	
	public LablePanel()
	{
		setLayout(null);
		
		// 选择角色
		textField = new TextField("请选择游戏角色(1或2)", 3);
		textField.setBounds(175, 200, 150, 45);
		// 开始游戏按钮
		startButton = new JButton("开始游戏");
		startButton.setBounds(175, 250, 150, 45);

		this.add(startButton);
		this.add(textField);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawString("1号角色： 200  20  20", 190, 130);
		g.drawString("2号角色： 100  30  30", 190, 150);
	}
}
