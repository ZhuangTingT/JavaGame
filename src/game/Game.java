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
		// ��ȡ�����±�
		selfIndex = fromServer.read();
		// ��ʼ��ս������ͼ����ң�
		war = new War(selfIndex, selfChoice);
		// ����ϰ���λ�ò������ϰ���
		int x, y;
		for(int i=0; i < Config.obsNum; i++)
		{
			x = fromServer.read();
			y = fromServer.read();
			war.map[x][y] = Config.obstruct;
			war.checkMatrix[x][y] = false;
		}
		
		// ���߷�������׼���ÿ�ʼ��Ϸ�ͽ�ɫ��ѡ��
		toServer.write(selfChoice+"\n");
		toServer.flush();
		// ��ȡ���ֽ�ɫ
		String enemyChoice = fromServer.readLine();
		war.setEnemy(enemyChoice);
		
		// ��ʼ����Ϸ����
		GameGUI gameGUI = new GameGUI(war, socket, toServer, fromServer);
		Thread thread = new Thread(gameGUI);
		thread.start();
	}	
}
