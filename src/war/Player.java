package war;

import java.io.IOException;
import war.Follower;

/**
 * �����
 * @author Administrator
 *
 */
public class Player
{
	public Follower follower;
	public Follower tower;
	
	public Player(String map[][], boolean checkMatrix[][], int index, String Choice) throws IOException 
	{
		// ��ʼ����Ӻ�������Ϣ
		if(Choice.contains("1"))
			follower = new Follower("a", 200, 30, index, map, checkMatrix);	
		if(Choice.contains("2"))
			follower = new Follower("b", 300, 20, index, map, checkMatrix);
		tower = new Follower("��", 450, 30, index, map, checkMatrix);
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

		// ���������λ�ñ�־��Ϊ�ϰ���
		int x = follower.x;
		int y = follower.y;
		map[x][y] = follower.name;
		checkMatrix[x][y] = false;
		// ��������λ�ñ�־��Ϊ�ϰ���
		x = tower.x;
		y = tower.y;
		map[x][y] = tower.name;
		checkMatrix[x][y] = false;
	}
}