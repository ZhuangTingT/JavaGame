package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import war.Config;

public class GameGUI extends JFrame implements Runnable
{
	Logger logger = Logger.getLogger("GameGUI");
	Socket socket;
	BufferedWriter toServer = null;
	BufferedReader fromServer = null;
	GamePanel gamePanel = null;
	War war = null;
	
	public GameGUI(War war, Socket socket, BufferedWriter toServer, BufferedReader fromServer) throws Exception
	{
		// 初始化窗口
		setSize(Config.mapSize*Config.w+200, Config.mapSize*Config.w+200);
		String name = "game";
		setTitle(name);
		setResizable(false);
		setLocationRelativeTo(null); // 位于中央 
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.socket = socket;
		this.toServer = toServer;
		this.fromServer = fromServer;
		this.war = war;
		
		// 初始化界面
		this.gamePanel = new GamePanel(war, toServer, fromServer);
		gamePanel.setFocusable(true);
		gamePanel.addKeyListener(new keyListener());
		add(gamePanel);
		
		Thread thread = new Thread(gamePanel);
		thread.start();
	}
	
	class keyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent e) 
		{
			try
			{
				toServer.write(e.getKeyCode());
				toServer.flush();
				operation(war.selfIndex, e.getKeyCode());
			}
			catch (IOException ioe) 
			{
				logger.info(ioe.getMessage());
			} 
			catch (InterruptedException e1) 
			{
				logger.info(e1.getMessage());
			}
		}
	}
	
	void operation(int index, int op) throws InterruptedException
	{
		if(war.isWin == -1)
		{
			if(op == KeyEvent.VK_DOWN || op == KeyEvent.VK_UP || op == KeyEvent.VK_LEFT || op == KeyEvent.VK_RIGHT)
				war.player[index].follower.shoot(war, index, op);
			
			if(op == KeyEvent.VK_W)
				war.player[index].follower.move(war, "w");
			
			if(op == KeyEvent.VK_A)
				war.player[index].follower.move(war, "a");
			
			if(op == KeyEvent.VK_S)
				war.player[index].follower.move(war, "s");
			
			if(op == KeyEvent.VK_D)
				war.player[index].follower.move(war, "d");
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			int op;
			while(war.isWin == -1)
			{
				// 获得对手的操作
				op = fromServer.read();
				if(op == 0) // 对手胜利
				{
					war.isWin = 0;
					toServer.close();
					fromServer.close();
					socket.close();
					break;
				}
				operation(1-war.selfIndex, op);
			}
		}
		catch(SocketException se)
		{
			logger.info(se.getMessage());
			System.out.println(socket.getInetAddress()+"退出游戏。");
		} 
		catch (IOException e)
		{
			logger.info(e.getMessage());
		} 
		catch (InterruptedException e) 
		{
			logger.info(e.getMessage());
		}
		finally
		{
			try
			{
				socket.close();
			} 
			catch (IOException e) 
			{
				logger.info(e.getMessage());
			}
		}
	}
}
