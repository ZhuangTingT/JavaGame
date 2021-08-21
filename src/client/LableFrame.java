package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import war.Config;

/**
 * �˵�����
 * @author Administrator
 *
 */
public class LableFrame extends JFrame
{
	//���캯������ʼ���������
	public LableFrame()
	{
		this.setSize(Config.lableFrameW, Config.lableFrameH);
		this.setTitle("game");
		this.setResizable(false);
		this.setLocationRelativeTo(null); // λ������
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addWindowListener(new WindowListener());
	}
	
	class WindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		} 
	}
}
