package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import war.Config;
/**
 * 游戏界面的初始化
 * @author Administrator
 *
 */
public class GamePanel extends JPanel implements Runnable
{
	Logger logger = Logger.getLogger("GamePanel");
	public War war;
	BufferedWriter toServer;
	BufferedReader fromServer;

	public GamePanel(War war, BufferedWriter toServer, BufferedReader fromServer) throws Exception
	{
		this.war = war;
		this.toServer = toServer;
		this.fromServer = fromServer;
	}

	/**
	 * 绘制游戏界面
	 */
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, 500, 500);
		super.paint(g);
		// 玩家0  信息
		int hp = war.player[0].follower.hp;
		int mp = war.player[0].follower.mp;
		g.drawString("hp    mp", Config.gamePanelX0+53, 35);
		g.drawString("玩家 1 ： " + hp + "     " + mp, Config.gamePanelX0, 50);
		// 玩家1  信息
		hp = war.player[1].follower.hp;
		mp = war.player[1].follower.mp;
		g.drawString("hp    mp", Config.gamePanelX0+253, 35);
		g.drawString("玩家 2 ： " + hp + "     " + mp, Config.gamePanelX0+200, 50);

		// 画框
		g.drawRect(Config.gamePanelX0, Config.gamePanelY0, Config.mapSize*Config.w, Config.mapSize*Config.w);// 画一个框
		// 画地图（包括障碍物、角色）
		synchronized(war.map)
		{
			for(int i = 0; i < Config.mapSize; i++)
			{
				for(int j = 0; j < Config.mapSize; j++)
				{
					g.drawString(war.map[i][j], (j+1)*(Config.w)+75, (i+1)*(Config.w)+75);
				}
			}
			g.setColor(Color.RED);
			int x = war.player[war.selfIndex].follower.x;
			int y = war.player[war.selfIndex].follower.y;
			if(x >= 0 && y >= 0)
				g.drawString(war.map[x][y], (y+1)*(Config.w)+75, (x+1)*(Config.w)+75);
			x = war.player[war.selfIndex].tower.x;
			y = war.player[war.selfIndex].tower.y;
			if(x >= 0 && y >= 0)
				g.drawString(war.map[x][y], (y+1)*(Config.w)+75, (x+1)*(Config.w)+75);
			g.setColor(Color.BLACK);
		}
	}
	
	void paintWin(Graphics g)
	{
		super.paint(g);
		Font font = new Font("宋体",Font.BOLD,32);
		g.setFont(font);
		g.drawString("WIN！", 200, 180);
	}

	void paintLose(Graphics g)
	{
		super.paint(g);
		Font font = new Font("宋体",Font.BOLD,32);
		g.setFont(font);
		g.drawString("LOSE！", 200, 180);
	}

	@Override
	public void run()
	{
		while(war.isWin == -1)
		{
			this.repaint();
			try
			{
				Thread.sleep(200);
			} 
			catch (InterruptedException e) 
			{   
				logger.info(e.getMessage());
			}
		}

		if(war.isWin == 1)
		{
			try 
			{
				toServer.write(0);
				toServer.flush();
			}
			catch (IOException e) 
			{
				logger.info(e.getMessage());
			}
			paintWin(this.getGraphics());
		}
		else if(war.isWin == 0)
		{
			paintLose(this.getGraphics());
		}
	}
}
