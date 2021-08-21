package war;

import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;

import game.War;

/**
 * �ӵ��࣬�������ӵĹ�������
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
	 * �ж������Ƿ��й������� ������Ӧ�ķ�����й�������
	 * @param f �������������
	 * @throws InterruptedException shoot()�е�sleep
	 */
	public void run()
	{
		if(dir==KeyEvent.VK_UP)
		{
			// ȷ�ϸ÷�����Ըı䣨����true���������ı�
			// ���������֣�delHp������ս���߽�ֹͣ
			while(war.check(attackIndex, "w", x, y))
			{
				try
				{
					war.map[x-1][y] = "��";
					if("��".equals(war.map[x][y]))
						war.map[x][y] = "��";
					x--;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "��";
			x--;
		}
		else if(dir==KeyEvent.VK_LEFT)
		{
			// ȷ�ϸ÷�����Ըı䣨����true���������ı�
			// ���������ֻ���ս���߽�ֹͣ
			while(war.check(attackIndex, "a", x, y))
			{
				try 
				{
					war.map[x][y-1] = "��";
					if(war.map[x][y].equals("��"))
						war.map[x][y] = "��";
					y--;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "��";
			y--;
		}
		else if(dir==KeyEvent.VK_DOWN)
		{
			// ȷ�ϸ÷�����Ըı䣨����true���������ı�
			// ���������ֻ���ս���߽�ֹͣ
			while(war.check(attackIndex, "s", x, y))
			{
				try 
				{
					war.map[x+1][y] = "��";
					if(war.map[x][y].equals("��"))
						war.map[x][y] = "��";
					x++;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "��";
			x++;
		}
		else if(dir==KeyEvent.VK_RIGHT)
		{
			// ȷ�ϸ÷�����Ըı䣨����true���������ı�
			// ���������ֻ���ս���߽�ֹͣ
			while(war.check(attackIndex, "d", x, y))
			{
				try 
				{
					war.map[x][y+1] = "��";
					if(war.map[x][y].equals("��"))
						war.map[x][y] = "��";
					y++;
					Thread.sleep(200);
				}
				catch (InterruptedException e) 
				{
					logger.info(e.getMessage());
				}
			}
			war.map[x][y] = "��";
			y++;
		}
	}
}
