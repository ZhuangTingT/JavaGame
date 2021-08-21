package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import war.Config;

public class Game
{
	int mapSize = 10;
	int selfIndex = -1;
	War war = null;

	public Game(Socket socket, BufferedWriter toServer, BufferedReader fromServer, String selfChoice) throws Exception
	{	
		// 获取己方下标
		selfIndex = fromServer.read();
		// 初始化战场（地图、玩家）
		war = new War(selfIndex, selfChoice);
		// 获得障碍物位置并设置障碍物
		int x, y;
		for(int i=0; i < Config.obsNum; i++)
		{
			x = fromServer.read();
			y = fromServer.read();
			war.map[x][y] = Config.obstruct;
			war.checkMatrix[x][y] = false;
		}
		
		// 告诉服务器已准备好开始游戏和角色的选择
		toServer.write(selfChoice+"\n");
		toServer.flush();
		// 获取对手角色
		String enemyChoice = fromServer.readLine();
		war.setEnemy(enemyChoice);
		
		// 初始化游戏界面
		GameGUI gameGUI = new GameGUI(war, socket, toServer, fromServer);
		Thread thread = new Thread(gameGUI);
		thread.start();
	}	
}
