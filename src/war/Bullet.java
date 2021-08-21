package war;

import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;

import game.War;

/**
 * 子弹类，以完成随从的攻击操作
 * @author Administrator
 *
 */
public class Bullet implements Runnable
{
	Logger logger = Logger.getLogger("Bullet");
	War war;
	int attackIndex;
	int dir;
	int x;
	int y;

	public Bullet(War war, int attackIndex, int dir, int x, int y) throws InterruptedException
	{
		this.war = war;
		this.attackIndex = attackIndex;
		this.dir = dir;
		this.x = x;
		this.y = y;
	}

	/**
	 * 判断四周是否有攻击对象， 并对相应的方向进行攻击操作
	 * @param f 发动攻击的随从
	 * @throws InterruptedException shoot()中的sleep
	 */
	public void run()
	{
		if(dir==KeyEvent.VK_UP)
		{
			// 确认该方向可以改变（返回true），并逐格改变
			// 当碰到对手（delHp）或者战场边界停止
			while(war.check(attackIndex, "w", x, y))
			{
				try
				{
					war.map[x-1][y] = "↑";
					if("↑".equals(war.map[x][y]))
						war.map[x][y] = "□";
					x--;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "□";
			x--;
		}
		else if(dir==KeyEvent.VK_LEFT)
		{
			// 确认该方向可以改变（返回true），并逐格改变
			// 当碰到对手或者战场边界停止
			while(war.check(attackIndex, "a", x, y))
			{
				try 
				{
					war.map[x][y-1] = "←";
					if(war.map[x][y].equals("←"))
						war.map[x][y] = "□";
					y--;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "□";
			y--;
		}
		else if(dir==KeyEvent.VK_DOWN)
		{
			// 确认该方向可以改变（返回true），并逐格改变
			// 当碰到对手或者战场边界停止
			while(war.check(attackIndex, "s", x, y))
			{
				try 
				{
					war.map[x+1][y] = "↓";
					if(war.map[x][y].equals("↓"))
						war.map[x][y] = "□";
					x++;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "□";
			x++;
		}
		else if(dir==KeyEvent.VK_RIGHT)
		{
			// 确认该方向可以改变（返回true），并逐格改变
			// 当碰到对手或者战场边界停止
			while(war.check(attackIndex, "d", x, y))
			{
				try 
				{
					war.map[x][y+1] = "→";
					if(war.map[x][y].equals("→"))
						war.map[x][y] = "□";
					y++;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "□";
			y++;
		}
	}
}
