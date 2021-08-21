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
 * ��Ϸ����ĳ�ʼ��
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
	 * ������Ϸ����
	 */
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, 500, 500);
		super.paint(g);
		// ���0  ��Ϣ
		int hp = war.player[0].follower.hp;
		int mp = war.player[0].follower.mp;
		g.drawString("hp    mp", Config.gamePanelX0+53, 35);
		g.drawString("��� 1 �� " + hp + "     " + mp, Config.gamePanelX0, 50);
		// ���1  ��Ϣ
		hp = war.player[1].follower.hp;
		mp = war.player[1].follower.mp;
		g.drawString("hp    mp", Config.gamePanelX0+253, 35);
		g.drawString("��� 2 �� " + hp + "     " + mp, Config.gamePanelX0+200, 50);

		// ����
		g.drawRect(Config.gamePanelX0, Config.gamePanelY0, Config.mapSize*Config.w, Config.mapSize*Config.w);// ��һ����
		// ����ͼ�������ϰ����ɫ��
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
		Font font = new Font("����",Font.BOLD,32);
		g.setFont(font);
		g.drawString("WIN��", 200, 180);
	}

	void paintLose(Graphics g)
	{
		super.paint(g);
		Font font = new Font("����",Font.BOLD,32);
		g.setFont(font);
		g.drawString("LOSE��", 200, 180);
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
