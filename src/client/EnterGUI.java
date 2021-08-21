package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import game.Game;

public class EnterGUI
{
	Logger logger = Logger.getLogger("EnterGUI");
	Socket socket;
	BufferedWriter toServer = null;
	BufferedReader fromServer = null;
	LablePanel lablePanel;
	LableFrame lableFrame;
	ButtonListener btn_liser;
	KeyListener keyListener;
	
	public EnterGUI(Socket socket) throws IOException
	{
		this.socket = socket;
		this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		// 设置“开始游戏”的监听
		this.btn_liser = new ButtonListener();
		this.keyListener = new KeyListener();
		this.lablePanel = new LablePanel();
		lablePanel.startButton.addActionListener(btn_liser);
		lablePanel.textField.addKeyListener(keyListener);
		// frame
		lableFrame = new LableFrame();
		lableFrame.getContentPane().add(lablePanel);
	}

	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try 
			{
				new Game(socket, toServer, fromServer, lablePanel.textField.getText());
				lableFrame.dispose();
			} 
			catch (Exception e1) 
			{
				logger.info(e1.getMessage());
			}
	    }
	}

	public class KeyListener extends KeyAdapter
	{
		public void keyTyped(KeyEvent e) 
		{
			if(e.getKeyCode()==KeyEvent.VK_ENTER) 
			{
				try 
				{
					new Game(socket, toServer, fromServer, lablePanel.textField.getText());
					lableFrame.dispose();
				}
				catch (Exception e1) 
				{
					logger.info(e1.getMessage());
				}
			}
		}
	}
}