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
		
		// ѡ���ɫ
		textField = new TextField("��ѡ����Ϸ��ɫ(1��2)", 3);
		textField.setBounds(175, 200, 150, 45);
		// ��ʼ��Ϸ��ť
		startButton = new JButton("��ʼ��Ϸ");
		startButton.setBounds(175, 250, 150, 45);

		this.add(startButton);
		this.add(textField);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawString("1�Ž�ɫ�� 200  20  20", 190, 130);
		g.drawString("2�Ž�ɫ�� 100  30  30", 190, 150);
	}
}
