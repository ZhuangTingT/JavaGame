package game;

import java.io.IOException;
import java.util.Random;

import war.Config;
import war.Follower;
import war.Player;
import war.Renew;

public class War
{
	public int isWin = -1; // 1:胜利
	public boolean checkMatrix[][];
	public String map[][];
	public Player player[];
	int selfIndex;

	/**
	 * 初始化战场和双方玩家
	 * @param N_val 战场的规格大小
	 */
	public War(int selfIndex, String selfChoice) throws IOException
	{
		// 初始化地图内容和障碍标记
		this.map = new String [Config.mapSize][Config.mapSize];
		this.checkMatrix = new boolean [Config.mapSize][Config.mapSize];
		for(int i = 0; i < Config.mapSize; i++) 
		{
			map[i] = new String [Config.mapSize];
			checkMatrix[i] = new boolean [Config.mapSize];
			for(int j = 0; j < Config.mapSize; j++) 
			{
				map[i][j] = Config.empty;
				checkMatrix[i][j] = true;
			}
		}
		
		// 玩家初始化
		this.selfIndex = selfIndex;
		this.player = new Player [Config.playerNum];
		this.player[selfIndex] = new Player(map, checkMatrix, selfIndex, selfChoice);
	}
	
	public void setEnemy(String enemyChoice) throws IOException
	{
		this.player[1-selfIndex] = new Player(map, checkMatrix, 1-selfIndex, enemyChoice);
	}

	/**
	 * 检查操作方向所对应的下一个格子是否可以进行操作，如遇障碍物或者碰墙则返回false
	 * @param dir 操作方向
	 * @param x 操作前位置的x
	 * @param y 操作前位置的y
	 * @return 不可操作时返回false
	 */
	public boolean check(int attackIndex, String dir, int x, int y)
	{
		if("w".equals(dir) && x-1 >= 0)
		{
			if("□".equals(map[x-1][y])) // 可以移动
				return true;
			else if(attackIndex != -1)
			{
				delHp(attackIndex, 1-attackIndex, x-1, y);
				return false;
			}
		}
		else if("a".equals(dir) && y-1 >= 0)
		{
			if("□".equals(map[x][y-1]))
				return true;
			else if(attackIndex != -1)
			{
				delHp(attackIndex, 1-attackIndex, x, y-1);
				return false;
			}
		}
		else if("s".equals(dir) && x+1 < Config.mapSize)
		{
			if("□".equals(map[x+1][y]))
				return true;
			else if(attackIndex != -1)
			{
				delHp(attackIndex, 1-attackIndex, x+1, y);
				return false;
			}
		}
		else if("d".equals(dir) && y+1  < Config.mapSize)
		{
			if("□".equals(map[x][y+1]))
				return true;
			else if(attackIndex != -1)
			{
				delHp(attackIndex, 1-attackIndex, x, y+1);
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param attackIndex 攻击者
	 * @param attackedIndex 被攻击者
	 * @param x 箭的位置x
	 * @param y 箭的位置y
	 */
	void delHp(int attackIndex, int attackedIndex, int x, int y)
	{
		// 己方不受攻击
		if(attackIndex == attackedIndex)
			return;
		if(player[attackedIndex].follower.x==x && player[attackedIndex].follower.y==y)
		{
			// 扣血
			int temp = player[attackedIndex].follower.hp - player[attackIndex].follower.mp;
			if(temp < 0)
				player[attackedIndex].follower.hp = 0;
			else
				player[attackedIndex].follower.hp = temp;
			
			// 对手随从被击倒后计时复活（恢复hp和位置）
			if(player[attackedIndex].follower.hp <= 0)
			{
				player[attackedIndex].follower.setXY(-1, -1);
				map[x][y] = Config.empty;
				checkMatrix[x][y] = true;
				Renew re = new Renew(player[attackedIndex].follower, map, checkMatrix);
				Thread r = new Thread(re);
				r.start();
			}
		}
		
		if(player[attackedIndex].tower.x==x && player[attackedIndex].tower.y==y)
		{
			// 扣血
			int temp = player[attackedIndex].tower.hp - player[attackIndex].tower.mp;
			if(temp < 0 && attackIndex == selfIndex)
				isWin = 1;
			else
				player[attackedIndex].tower.hp = temp;
		}
	}
	
	public boolean changeWar(String dir, Follower f)
	{
		if(check(-1, dir, f.x, f.y) == false)
		{
			return false;
		}
		else
		{
			if("w".equals(dir))
			{
				map[f.x-1][f.y] = map[f.x][f.y];
				checkMatrix[f.x-1][f.y] = false;
			}
			else if("a".equals(dir))
			{
				map[f.x][f.y-1] = map[f.x][f.y];
				checkMatrix[f.x][f.y-1] = false;
			}
			else if("s".equals(dir))
			{
				map[f.x+1][f.y] = map[f.x][f.y];
				checkMatrix[f.x+1][f.y] = false;
			}
			else if("d".equals(dir))
			{
				map[f.x][f.y+1] = map[f.x][f.y];
				checkMatrix[f.x][f.y+1] = false;
			}
			map[f.x][f.y] = "□";
			checkMatrix[f.x][f.y] = true;
			return true;
		}
	}
	
}
