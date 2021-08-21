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
	public int x0 = 0; // ��
	public int y0 = 0; // ��
	public int x = 0; // ��
	public int y = 0; // ��
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
	 * ���Ӧ�������
	 * @param dir �������index �����
	 * @param f ����shoot���������
	 * @throws InterruptedException sleep����
	 */
	public void shoot(War war, int attackIndex, int dir) throws InterruptedException
	{
		Bullet bullet = new Bullet(war, attackIndex, dir, x, y);
		Thread th = new Thread(bullet);
		th.start();
	}

	/**
	 * ��ӵ��ƶ�����
	 * @param war ս����Ϣ
	 * @param dir ����ƶ�����
	 */
	public void move(War war, String dir)
	{
		if("w".equals(dir))
		{
			// �ж��ƶ��ķ����Ƿ����ϰ�������Ƿ�Ϊս��֮�⣬���ƶ�����true
			if(war.changeWar(dir, this)) 
				x--; //�ı�ٿصĽ�ɫ������
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
