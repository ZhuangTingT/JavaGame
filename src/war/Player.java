package war;

import java.io.IOException;
import war.Follower;

/**
 * 玩家类
 * @author Administrator
 *
 */
public class Player
{
	public Follower follower;
	public Follower tower;
	
	public Player(String map[][], boolean checkMatrix[][], int index, String Choice) throws IOException 
	{
		// 初始化随从和塔的信息
		if(Choice.contains("1"))
			follower = new Follower("a", 200, 30, index, map, checkMatrix);	
		if(Choice.contains("2"))
			follower = new Follower("b", 300, 20, index, map, checkMatrix);
		tower = new Follower("△", 450, 30, index, map, checkMatrix);
		if(index == 0)
		{
			follower.setX0Y0(0, 0);
			follower.setXY(0, 0);
			tower.setXY((int)Config.mapSize/2, 0);
		}
		else
		{
			follower.setX0Y0(Config.mapSize-1, Config.mapSize-1);
			follower.setXY(Config.mapSize-1, Config.mapSize-1);
			tower.setXY((int)Config.mapSize/2, Config.mapSize-1);
		}

		// 将玩家所在位置标志设为障碍处
		int x = follower.x;
		int y = follower.y;
		map[x][y] = follower.name;
		checkMatrix[x][y] = false;
		// 将塔所在位置标志设为障碍处
		x = tower.x;
		y = tower.y;
		map[x][y] = tower.name;
		checkMatrix[x][y] = false;
	}
}