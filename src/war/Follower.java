package war;

import java.awt.event.KeyEvent;

import game.War;

class Aim
{
	boolean w = false;
	boolean a = false;
	boolean s = false;
	boolean d = false;
}

public class Follower 
{
	public String name = "?";
	public int x0 = 0; // 行
	public int y0 = 0; // 列
	public int x = 0; // 行
	public int y = 0; // 列
	public int hp0 = 0;
	public int hp = 0;
	public int mp = 0;
	
	int selfIndex;
	String map[][];
	boolean checkMatrix[][];

	public Follower(String name, int hp, int mp, int selfIndex, String map[][], boolean checkMatrix[][])
	{
		this.name = name;
		this.hp0 = hp;
		this.hp = hp;
		this.mp = mp;
		this.selfIndex = selfIndex;
		this.map = map;
		this.checkMatrix = checkMatrix;
	}

	public void setX0Y0(int x, int y)
	{
		this.x0 = x;
		this.y0 = y;
	}

	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * 向对应方向射箭
	 * @param dir 射箭方向，index 射箭方
	 * @param f 进行shoot操作的随从
	 * @throws InterruptedException sleep函数
	 */
	public void shoot(War war, int attackIndex, int dir) throws InterruptedException
	{
		Bullet bullet = new Bullet(war, attackIndex, dir, x, y);
		Thread th = new Thread(bullet);
		th.start();
	}

	/**
	 * 随从的移动操作
	 * @param war 战场信息
	 * @param dir 随从移动方向
	 */
	public void move(War war, String dir)
	{
		if("w".equals(dir))
		{
			// 判断移动的方向是否有障碍物，或者是否为战场之外，可移动返回true
			if(war.changeWar(dir, this)) 
				x--; //改变操控的角色的坐标
		}
		else if("a".equals(dir))
		{
			if(war.changeWar(dir, this))
				y--;
		}
		else if("s".equals(dir))
		{
			if(war.changeWar(dir, this))
				x++;
		}
		else if("d".equals(dir))
		{
			if(war.changeWar(dir, this))
				y++;
		}
	}
}
